/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.think.arsc;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Nullable;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Pulls useful information from an APK's resources.arsc file. This can be used to see the different
 * resource configurations in the APK, their size, and the entries in those configurations.
 * <p>
 * <p>This can also be used to get a list of the different entry names, or to get a list of resource
 * entries for which no default value exists (baseless keys).
 * <p>
 * <p>Example usage to save all resource configurations to a CSV file:
 * <p>
 * <pre>ArscDumper.jar --apk=/apk_dir/my.apk --output=/csv_dir/my.csv --type=configs</pre>
 *
 * <p>This CSV could then be sorted by "Null Entries" in descending order to spot resource
 * configurations that could potentially be removed for large byte savings.
 */
public class ArscDumper {

  private static final String RESOURCES_ARSC = "resources.arsc";
  private static class Params {
    String apkFile;
    List<String> types;
    String output;
  }

  public static void main(String[] args) throws IOException {
    Optional<Params> optional = parseParams(args);
    if (optional.isPresent()) {
      Params params = optional.get();
      File apk = new File(params.apkFile);
      ResourceTableChunk tableChunk = providesResourceTableChunk(apk);
      if (params.output != null) {
        ArscDumper.dumpWriteTableChunk(tableChunk, params);
      } else {
        ArscDumper.dumpRunTableChunk(tableChunk);
      }
    }
  }

  private static Optional<Params> parseParams(String[] args) {
    Params params = new Params();
    CommandLineParser parser = new DefaultParser();
    Options options = new Options();
    options.addOption("help", false, "print this message");
    options.addRequiredOption("a", "apk", true, "input apk file");
    options.addOption("type", true, "the output type in excel file, can split by ',',like string,color ");
    options.addOption("o", true, "out put file");
    try {
      // parse the command line arguments
      CommandLine line = parser.parse(options, args);
      if (line.hasOption("apk")) {
        params.apkFile = line.getOptionValue("apk");
      }

      if (line.hasOption("type")) {
        params.types = Arrays.asList(line.getOptionValue("type").split(","));
      }
      if (line.hasOption("o")) {
        params.output = line.getOptionValue("o");
      }

      if (params.apkFile != null) {
        File apk = new File(params.apkFile);
        if (apk.exists()) {
          return Optional.of(params);
        }
      }
    } catch (ParseException exp) {
      // oops, something went wrong
      System.err.println("Parsing failed.  Reason: " + exp.getMessage());
    }
    printUsage(options);
    return Optional.absent();
  }

  private static void printUsage(Options options) {
    HelpFormatter formatter = new HelpFormatter();
    formatter.printHelp("arscblamer", options);
  }

  /**
   * Returns a CSV row (as a list of strings) describing a particular resource configuration. If
   * showKeys is true, the "Keys" column will be populated with the keys of the resource entries in
   * that configuration. Otherwise, the "Keys" column will be blank.
   */
  private static void dumpWriteTableChunk(ResourceTableChunk chunk, Params params) {
    WritableWorkbook book = null;
    try {

      book = Workbook.createWorkbook(new File(params.output));
      StringPoolChunk chunkStringPool = chunk.getStringPool();
      Collection<PackageChunk> chunkPackages = chunk.getPackages();
      for (PackageChunk chunkPackage : chunkPackages) {

        StringPoolChunk typeStringPool = chunkPackage.getTypeStringPool();
        for (int index = 0, count = typeStringPool.getStringCount(); index < count; index++) {
          String type = typeStringPool.getString(index);
          if (params.types == null || params.types.size() == 0 || params.types.contains(type)) {
            WritableSheet sheet = book.createSheet(type, index);
            Collection<TypeChunk> typeChunks = chunkPackage.getTypeChunks(type);

            writeHead(sheet, typeChunks);

            writeID(sheet, typeChunks, chunkPackage);

            writeName(sheet, typeChunks);

            writeEntryValue(chunkStringPool, sheet, typeChunks);
          }
        }
      }

      book.write();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (RowsExceededException e) {
      e.printStackTrace();
    } catch (WriteException e) {
      e.printStackTrace();
    } finally {
      if (book != null) {
        try {
          book.close();
        } catch (IOException e) {
          e.printStackTrace();
        } catch (WriteException e) {
          e.printStackTrace();
        }
      }


    }
  }

  private static void writeID(WritableSheet sheet, Collection<TypeChunk> typeChunks, PackageChunk chunkPackage) throws WriteException {
    int cell = 0;
    for (TypeChunk typeChunk : typeChunks) {
      Collection<TypeChunk.Entry> values = typeChunk.getEntries().values();
      int row = 1;
      for (int i = 0; i < values.size(); i++) {
        String id = "0x" + Integer.toHexString(chunkPackage.getId()) + String.format("%02X", typeChunk.getId()) + String.format("%04X", i);
        Label label = new Label(cell, row, id);
        sheet.addCell(label);
        row++;
      }
    }
  }

  private static void writeEntryValue(StringPoolChunk chunkStringPool, WritableSheet sheet, Collection<TypeChunk> typeChunks) throws WriteException {
    int cell = 2;
    for (TypeChunk typeChunk : typeChunks) {
      Set<Entry<Integer, TypeChunk.Entry>> entries = typeChunk.getEntries().entrySet();
      for (Entry<Integer, TypeChunk.Entry> entry : entries) {
        TypeChunk.Entry value = entry.getValue();
        int row = getRowByName(typeChunks, value.key());
        Label label = null;
        if (value.isComplex()) {
          Map<Integer, ResourceValue> resourceValueMap = value.getValues();
          Collection<ResourceValue> resourceValues = resourceValueMap.values();
          StringBuilder sb = new StringBuilder();
          for (ResourceValue resourceValue : resourceValues) {
            sb.append(getResourcesValue(resourceValue, chunkStringPool)).append(',');
          }
          String s = sb.toString();
          if (s.length() > 0) {
            label = new Label(cell, row, s.substring(0, s.length() - 1));
          }
        } else {
          ResourceValue resourceValue = value.getValue();
          label = new Label(cell, row, getResourcesValue(resourceValue, chunkStringPool));
        }
        if (label != null) {
          sheet.addCell(label);
        } else {
          sheet.addCell(new Label(cell, row, ""));
        }
      }
      cell++;
    }
  }

  private static String getResourcesValue(ResourceValue resourceValue, StringPoolChunk chunkStringPool) {
    int data = resourceValue.getData();
    switch (resourceValue.getType()) {
      case STRING:
        return chunkStringPool.getString(data);
      case DIMENSION:
        return String.format("dimension(%d)", data);
      case INT_COLOR_RGB8:
        return String.format("rgb8(%s)", Integer.toHexString(data));
      case INT_COLOR_ARGB4:
        return String.format("argb4(%s)", Integer.toHexString(data));
      case INT_COLOR_ARGB8:
        return String.format("argb8(%s)", Integer.toHexString(data));
      case INT_COLOR_RGB4:
        return String.format("argb4(%s)", Integer.toHexString(data));
      case INT_HEX:
        return Integer.toHexString(data);
      case INT_DEC:
        return Integer.toString(data);
      case ATTRIBUTE:
        return String.valueOf(data);
      case INT_BOOLEAN:
        return data == 1 ? "true" : "false";
      case REFERENCE:
        return "@ref/0x" + Integer.toHexString(data);
      case FLOAT:
        return Float.toString(data);
      case FRACTION:
      case DYNAMIC_REFERENCE:
      case NULL:
      default:
        break;
    }
    return "";
  }

  private static int getRowByName(Collection<TypeChunk> typeChunks, String key) {
    for (TypeChunk typeChunk : typeChunks) {
      Set<Entry<Integer, TypeChunk.Entry>> entries = typeChunk.getEntries().entrySet();
      int row = 1;
      for (Entry<Integer, TypeChunk.Entry> entry : entries) {
        TypeChunk.Entry value = entry.getValue();
        if (value.key().equals(key)) {
          return row;
        }
        row++;
      }
    }
    return -1;
  }

  private static void writeName(WritableSheet sheet, Collection<TypeChunk> typeChunks) throws WriteException {
    int cell = 1;
    for (TypeChunk typeChunk : typeChunks) {
      Set<Entry<Integer, TypeChunk.Entry>> entries = typeChunk.getEntries().entrySet();
      int row = 1;
      for (Entry<Integer, TypeChunk.Entry> entry : entries) {
        TypeChunk.Entry value = entry.getValue();
        Label label = new Label(cell, row, value.key());
        sheet.addCell(label);
        row++;
      }
    }
  }

  private static void writeHead(WritableSheet sheet, Collection<TypeChunk> typeChunks) throws WriteException {
    int cell = 0;
    sheet.addCell(new Label(cell++, 0, "ID"));
    sheet.addCell(new Label(cell++, 0, "Name"));
    for (TypeChunk typeChunk : typeChunks) {
      ResourceConfiguration configuration = typeChunk.getConfiguration();
      Label label = new Label(cell, 0, configuration.toString());
      sheet.addCell(label);
      cell++;
    }
  }

  private static void dumpRunTableChunk(ResourceTableChunk chunk) {
    System.out.println("  |--StringPoolChunk");
    StringPoolChunk stringPool = chunk.getStringPool();
    for (int index = 0, count = stringPool.getStringCount(); index < count; index++) {
      System.out.println("    index[" + index + "]=" + stringPool.getString(index));
    }
    for (int index = 0, count = stringPool.getStyleCount(); index < count; index++) {
      System.out.println("    index[" + index + "]=" + stringPool.getStyle(index));
    }
    Collection<PackageChunk> chunkPackages = chunk.getPackages();
    for (PackageChunk chunkPackage : chunkPackages) {
      System.out.println("  |--PackageChunk");
      int id = chunkPackage.getId();
      System.out.println("    id=" + id);
      String packageName = chunkPackage.getPackageName();
      System.out.println("    packageName=" + packageName);

      StringPoolChunk typeStringPool = chunkPackage.getTypeStringPool();
      System.out.println("    |--typeStringPool");
      for (int index = 0, count = typeStringPool.getStringCount(); index < count; index++) {
        System.out.println("      index[" + index + "]=" + typeStringPool.getString(index));
      }
      for (int index = 0, count = typeStringPool.getStyleCount(); index < count; index++) {
        System.out.println("      index[" + index + "]=" + typeStringPool.getStyle(index));
      }

      StringPoolChunk keyStringPool = chunkPackage.getKeyStringPool();
      System.out.println("    |--keyStringPool");
      for (int index = 0, count = keyStringPool.getStringCount(); index < count; index++) {
        System.out.println("      index[" + index + "]=" + keyStringPool.getString(index));
      }
      for (int index = 0, count = keyStringPool.getStyleCount(); index < count; index++) {
        System.out.println("      index[" + index + "]=" + keyStringPool.getStyle(index));
      }

      Collection<TypeSpecChunk> specChunks = chunkPackage.getTypeSpecChunks();
      System.out.println("    |--specChunks");
      for (TypeSpecChunk specChunk : specChunks) {
        int specChunkId = specChunk.getId();
        int resourceCount = specChunk.getResourceCount();
        System.out.println("      specChunkId:" + specChunkId + " resourceCount:" + resourceCount);
      }

      System.out.println("    |--typeChunks");
      Collection<TypeChunk> typeChunks = chunkPackage.getTypeChunks();
      for (TypeChunk typeChunk : typeChunks) {
        int typeChunkId = typeChunk.getId();
        ResourceConfiguration configuration = typeChunk.getConfiguration();
        System.out.println("      typeChunkId:" + typeChunkId);
        System.out.println("      configuration:" + configuration);
        Set<Entry<Integer, TypeChunk.Entry>> entries = typeChunk.getEntries().entrySet();
        for (Entry<Integer, TypeChunk.Entry> entry : entries) {
          TypeChunk.Entry value = entry.getValue();
          String key = value.key();
          int flags = value.getFlags();
          String valueStr = "";
          if (value.isComplex()) {
            Map<Integer, ResourceValue> resourceValueMap = value.getValues();
            Collection<ResourceValue> resourceValues = resourceValueMap.values();
            StringBuilder sb = new StringBuilder();
            for (ResourceValue resourceValue : resourceValues) {
              sb.append(getResourcesValue(resourceValue, stringPool)).append(',');
            }
            String s = sb.toString();
            if (s.length() > 0) {
              valueStr = s.substring(0, s.length() - 1);
            }
          } else {
            ResourceValue resourceValue = value.getValue();
            valueStr = getResourcesValue(resourceValue, stringPool);
          }
          System.out.println("        key:" + key + " flags:" + flags + " value:" + valueStr);
        }
      }
    }
  }

  /**
   * Provides the resources.arsc resource table in the {@code apk}.
   */
  private static ResourceTableChunk providesResourceTableChunk(@Nullable File apk) throws IOException {
    Preconditions.checkNotNull(apk, "APK is required. Did you forget --apk=/my/app.apk?");
    byte[] resourceBytes = ApkUtils.getFile(apk, RESOURCES_ARSC);
    if (resourceBytes == null) {
      throw new IOException(String.format("Unable to find %s in APK.", RESOURCES_ARSC));
    }
    List<Chunk> chunks = new ResourceFile(resourceBytes).getChunks();
    Preconditions.checkState(chunks.size() == 1,
            "%s should only have one root chunk.", RESOURCES_ARSC);
    Chunk resourceTable = chunks.get(0);
    Preconditions.checkState(resourceTable instanceof ResourceTableChunk,
            "%s root chunk must be a ResourceTableChunk.", RESOURCES_ARSC);
    return (ResourceTableChunk) resourceTable;
  }
}
