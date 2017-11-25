关于resources.arsc的文件分析可以参见[我的博客](http://thinkdevos.net/2017/11/14/2017-11-14-arse/)
# arscblamer

该工程以[android-arscblamer](https://github.com/google/android-arscblamer)为基础做修改，主要用来解析apk文件中的resources.arsc文件并输出解析内容。

# 构建工程

google的android-arscblamer是用[bazel](https://bazel.build/)进行构建的，这个构建工具比较适合google内部使用，但不方便我们的编译和debug；修改后的工程是基于gradle构建的，使用比较简单

## 编译

用如下命令进行编译
```
./gradlew build
```

## 编译jar包

用如下命令编译成jar
```
./gradlew jar
```

# 使用

最终编译的jar包在工程的build/libs下，arsc-library-1.0-SNAPSHOT.jar
命令行 **java -jar build/libs/arsc-library-1.0-SNAPSHOT.jar** 输出使用信息
```
usage: arscblamer
 -a,--apk <arg>      input apk file
 -h,--help           print this message
 -o,--output <arg>   out put file
 -t,--type <arg>     the output type in excel file, can split by ',',like
                     string,color
```

- 使用示例

```
java -jar build/libs/arsc-library-1.0-SNAPSHOT.jar --apk xxxx.apk --type string,layout -o yyyy.xls
```

如上就会解析xxxx.apk并将string和layout资源输出在yyyy.xls文件中
