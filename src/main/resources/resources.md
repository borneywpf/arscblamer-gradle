02 00       // RES_TABLE_TYPE                    = 0x0002 0ffset = 0    start table head

0C 00       // ResTable_header headerSize        = 12
E0 07 00 00 // ResTable_header size              = 2016
01 00 00 00 // ResTable_header packageCount      = 1                     end table head

01 00       // RES_STRING_POOL_TYPE              = 0x0001 0ffset = 12   start sting pool head

1C 00       // ResStringPool_header headerSize   = 28
08 01 00 00 // ResStringPool_header size         = 264
07 00 00 00 // ResStringPool_header stringCount  = 7
01 00 00 00 // ResStringPool_header styleCount   = 1
00 01 00 00 // ResStringPool_header flags        = 256
3C 00 00 00 // ResStringPool_header stringsStart = 60
F0 00 00 00 // ResStringPool_header stylesStart  = 240                  end sting pool head

00 00 00 00 // 第1个字符串的 stringOffset 60+12+0=72        0ffset = 40
10 00 00 00 // 第2个字符串的 stringOffset 60+12+16=88 下面6个字符串一样就不一一解释了
20 00 00 00
24 00 00 00
40 00 00 00 
5F 00 00 00
86 00 00 00 
00 00 00 00 // 第1个style的 styleOffset 240+12+0=252
09          // 第1个字符串的字符数 9                          0ffset = 72
0D          // 第1个字符串的编码数 13  UTF-8字符串有两种长度：字符数量和编码长度；但是，UTF-16字符串只有1个长度：字符数。
E4 BD A0 E5 A5 BD 41 6E 64 72 6F 69 64 00        // 你好Android 
0D
0D
48 65 6C 6C 6F 52 65 73 6F 75 72 63 65 00        // HelloResource
01
01
62 00                                            // b
19
19
72 65 73 2F 64 72 61 77 61 62 6C 65 2F 64 72 61
77 61 62 6C 65 2E 78 6D 6C 00                    // res/drawable/drawable.xml
1C
1C
72 65 73 2F 6C 61 79 6F 75 74 2F 61 63 74 69 76
69 74 79 5F 6D 61 69 6E 2E 78 6D 6C 00           // res/layout/activity_main.xml
24
24
72 65 73 2F 6D 69 70 6D 61 70 2D 78 78 68 64 70
69 2D 76 34 2F 69 63 5F 6C 61 75 6E 63 68 65 72
2E 70 6E 67 00                                   // res/mipmap-xxhdpi-v4/ic_launcher.png
2B 
2B
72 65 73 2F 6D 69 70 6D 61 70 2D 78 78 78 68 64
70 69 2D 76 34 2F 69 63 5F 6C 61 75 6E 63 68 65
72 5F 72 6F 75 6E 64 2E 70 6E 67 00              // res/mipmap-xxxhdpi-v4/ic_launcher_round.png
                                                 注：字符串编码格式为UTF-8则字符串以0X00作为结束符,UTF-16则以0X0000作为结束符
02 00 00 00 // 第1个1style的 ResStringPool_ref nameIndex = 2    0ffset = 252 注意：一个字符串可以对应多个ResStringPool_span和一个ResStringPool_ref。ResStringPool_span在前描述字符串的样式,ResStringPool_ref在后固定值为0XFFFFFFFF作为占位符
02 00 00 00 // 第1个1style的 start
08 00 00 00 // 第1个1style的 end
FF FF FF FF // 循环判断第1个style的ResStringPool_span
FF FF FF FF FF FF FF FF //样式块最后会以两个值为0XFFFFFFFF的ResStringPool_ref作为结束

00 02       // RES_TABLE_PACKAGE_TYPE            = 0x0200 0ffset = 276   start package chunk head

20 01       // ResTable_package headerSize       = 288
CC 06 00 00 // ResTable_package size             = 1740
7F 00 00 00 // ResTable_package id               = 0x7F //包的ID,等于Package Id,一般用户包的值Package Id为0X7F,系统资源包的Package Id为0X01
63 00 6F 00 6D 00 2E 00 65 00 78 00 61 00 6D 00 // ResTable_package name = com.example.borney.helloresource
70 00 6C 00 65 00 2E 00 62 00 6F 00 72 00 6E 00
65 00 79 00 2E 00 68 00 65 00 6C 00 6C 00 6F 00
72 00 65 00 73 00 6F 00 75 00 72 00 63 00 65 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00
20 01 00 00 // ResTable_package typeStrings      = 288 //类型字符串资源池相对头部的偏移
00 00 00 00 // ResTable_package lastPublicType   = 0  //最后一个导出的Public类型字符串在类型字符串资源池中的索引，目前这个值设置为类型字符串资源池的元素个数。
C8 01 00 00 // ResTable_package keyStrings       = 456 //定义资源键符号表的ResStringPool_header的偏移量。 如果为零，则此包将从另一个基本包继承（重写其中的特定值）。
00 00 00 00 // ResTable_package lastPublicKey    = 0 //最后一个由其他人公开使用的keyStrings索引。
00 00 00 00 // ResTable_package typeIdOffset     = 0

01 00       // RES_STRING_POOL_TYPE              = 0x0001   offset = 564    start sting pool head

1C 00       // ResStringPool_header headerSize   = 28
A8 00 00 00 // ResStringPool_header size         = 168
07 00 00 00 // ResStringPool_header stringCount  = 7
00 00 00 00 // ResStringPool_header styleCount   = 0
00 00 00 00 // ResStringPool_header flags        = 0
38 00 00 00 // ResStringPool_header stringsStart = 56
00 00 00 00 // ResStringPool_header stylesStart  = 0                        end sting pool head
00 00 00 00 // 第1个字符串的 stringOffset 564+56+0 = 620       0ffset = 592
0E 00 00 00 // 第2个字符串的 stringOffset 564+56+14=634 下面6个字符串一样就不一一解释了
1C 00 00 00
30 00 00 00
40 00 00 00
50 00 00 00
60 00 00 00 // 第7个字符串的 stringOffset 564+56+96=716
05          // 第1个字符串的字符数 5
00          // UTF-16 offset 占位符
63 00 6F 00 6C 00 6F 00 72 00 00 00               // color
05          // 第2个字符串的字符数 5
00
64 00 69 00 6D 00 65 00 6E 00 00 00               // dimen
08          // 第3个字符串的字符数 8
00
64 00 72 00 61 00 77 00 61 00 62 00 6C 00 65 00
00 00                                             // drawable
06          // 第4个字符串的字符数 6
00
6C 00 61 00 79 00 6F 00 75 00 74 00 00 00         // layout
06
00
6D 00 69 00 70 00 6D 00 61 00 70 00 00 00         // mipmap
06
00 
73 00 74 00 72 00 69 00 6E 00 67 00 00 00         // string
05                                                offset = 716
00
73 00 74 00 79 00 6C 00 65 00 00 00               // style
00 00   // 占位符

01 00       // RES_STRING_POOL_TYPE              = 0x0001   offset = 732    start sting pool head

1C 00       // ResStringPool_header headerSize   = 28
D0 00 00 00 // ResStringPool_header size         = 208
0A 00 00 00 // ResStringPool_header stringCount  = 10
00 00 00 00 // ResStringPool_header styleCount   = 0
00 01 00 00 // ResStringPool_header flags        = 256
44 00 00 00 // ResStringPool_header stringsStart = 68
00 00 00 00 // ResStringPool_header stylesStart  = 0
00 00 00 00 // 第1个字符串的 stringOffset 732+68+0 = 800       0ffset = 760
0E 00 00 00
1D 00 00 00 
30 00 00 00 
3B 00 00 00 
46 00 00 00
56 00 00 00 
64 00 00 00 
78 00 00 00 
83 00 00 00 // 第10个字符串的 stringOffset 732+68+131 = 931
0B
0B
63 6F 6C 6F 72 41 63 63 65 6E 74 00             // colorAccent
0C
0C
63 6F 6C 6F 72 50 72 69 6D 61 72 79 00          // colorPrimary
10 10 
63 6F 6C 6F 72 50 72 69 6D 61 72 79 44 61 72 6B // colorPrimaryDark
00
08
08
74 65 78 74 73 69 7A 65 00                      // textsize
08
08
64 72 61 77 61 62 6C 65 00                      // drawable
0D
0D
61 63 74 69 76 69 74 79 5F 6D 61 69 6E 00       // activity_main
0B
0B
69 63 5F 6C 61 75 6E 63 68 65 72 00             // ic_launcher
11
11
69 63 5F 6C 61 75 6E 63 68 65 72 5F 72 6F 75 6E 
64 00                                           // ic_launcher_round
08
08
61 70 70 5F 6E 61 6D 65 00                      // app_name
04
04 
54 65 78 74 00                                  // Text
00 00       // style 占位符

02 02       // RES_TABLE_TYPE_SPEC_TYPE         = 0x0202   offset = 940

10 00       // ResTable_typeSpec headerSize     = 16
1C 00 00 00 // ResTable_typeSpec size           = 28
01          // ResTable_typeSpec id             = 0x01
00 00 00    // ResTable_typeSpec res0 res1 占位符
03 00 00 00 // ResTable_typeSpec entryCount     = 3
00 00 00 00 // 数组resources[0]的大小为0
00 00 00 00 // 数组resources[1]的大小为0
00 00 00 00 // 数组resources[2]的大小为0

01 02       // RES_TABLE_TYPE_TYPE              = 0x0201   offset = 968

4C 00       // ResTable_type     headerSize     = 76
88 00 00 00 // ResTable_type     size           = 136
01          // ResTable_type     id             = 0x01
00 00 00    // ResTable_type res0 res1 占位符
03 00 00 00 // ResTable_type entryCount         = 3
58 00 00 00 // ResTable_type entriesStart       = 88
38 00 00 00 // ResTable_config size             = 56
00 00       // ResTable_config mcc              = 0
00 00       // ResTable_config mnc              = 0
00 00       // ResTable_config language{}       = {0,0}
00 00       // ResTable_config country{}        = {0,0}
00          // ResTable_config orientation      = 0
00          // ResTable_config touchscreen      = 0 
00 00       // ResTable_config density          = 0 
00          // ResTable_config keyboard         = 0 
00          // ResTable_config navigation       = 0 
00          // ResTable_config inputFlags       = 0 
00          // ResTable_config inputPad0        = 0 对齐位
00 00       // ResTable_config screenWidth      = 0 
00 00       // ResTable_config screenHeight     = 0 
00 00       // ResTable_config sdkVersion       = 0 
00 00       // ResTable_config minorVersion     = 0 
00          // ResTable_config screenLayout     = 0 
00          // ResTable_config uiMode           = 0 
00 00       // ResTable_config smallestScreenWidthDp = 0 
00 00       // ResTable_config screenWidthDp    = 0 
00 00       // ResTable_config screenHeightDp   = 0 
00 00 00 00 // ResTable_config localeScript{}   = {0,0，0,0}
00 00 00 00 00 00 00 00 // ResTable_config localeVariant{} = {0,0，0,0,0，0,0,0}
00          // ResTable_config screenLayout2    = 0
00          // ResTable_config screenConfigPad1 = 0 占位符
00 00       // ResTable_config screenConfigPad2 = 0 占位符 bytesRead = 52
00 00 00 00 // ResTable_config unknown{}        = {0,0,0,0} //size = cofigsize - 52
00 00 00 00 // ResTable_type 第1个ResTable_entry(Entry)的offset
10 00 00 00 // ResTable_type 第2个ResTable_entry(Entry)的offset
20 00 00 00 // ResTable_type 第3个ResTable_entry(Entry)的offset
08 00       // ResTable_type 第1个ResTable_entry headerSize = 8
00 00       // ResTable_type 第1个ResTable_entry flags      = 0 
00 00 00 00 // ResTable_type 第1个ResTable_entry keyIndex   = 0 //引用标识此条目的ResTable_package :: keyStrings
08 00       // ResTable_type 第1个ResTable_entry的Res_value size     = 8
00          // ResTable_type 第1个ResTable_entry的Res_value res0     = 0 //占位符
1D          // ResTable_type 第1个ResTable_entry的Res_value dataType = 0x1D
81 40 FF FF // ResTable_type 第1个ResTable_entry的Res_value data = 0xFFFF4081
08 00       // ResTable_type 第2个ResTable_entry headerSize = 8
00 00 
01 00 00 00 
08 00
00 
1D 
B5 51 3F FF // ResTable_type 第2个ResTable_entry的Res_value data = 0xFFFF51B5
08 00       // ResTable_type 第3个ResTable_entry headerSize = 8
00 00 
02 00 00 00 
08 00
00 
1D 9F 3F 30 // ResTable_type 第3个ResTable_entry的Res_value data = 0x303F9F1D
FF          // Entry 添加结束

02 02       // RES_TABLE_TYPE_SPEC_TYPE         = 0x0202

10 00       // ResTable_typeSpec headerSize     = 16
14 00 00 00 // ResTable_typeSpec size           = 20
02          // ResTable_typeSpec id             = 0x02
00 00 00    // ResTable_typeSpec res0 res1 占位符
01 00 00 00 // ResTable_typeSpec entryCount     = 1
00 00 00 00 // ResTable_typeSpec 占位符

01 02       // RES_TABLE_TYPE_TYPE              = 0x0201

4C 00       // ResTable_type     headerSize     = 76
60 00 00 00 // ResTable_type     size           = 96
02          // ResTable_type     id             = 0x02
00 00 00    // ResTable_type res0 res1 占位符
01 00 00 00 // ResTable_type entryCount         = 1
50 00 00 00 // ResTable_type entriesStart       = 80
38 00 00 00 // ResTable_config size             = 56
00 00 
00 00
00 00 
00 00 
00 
00 
00 00       // ResTable_config density          = 0 
00 
00 
00 
00 
00 00       // ResTable_config screenWidth      = 0 
00 00
00 00 
00 00 
00 
00 
00 00       // ResTable_config smallestScreenWidthDp = 0 
00 00 
00 00 
00 00 00 00 // ResTable_config localeScript{}   = {0,0，0,0}
00 00 00 00 00 00 00 00 // ResTable_config localeVariant{} = {0,0，0,0,0，0,0,0}
00 
00 
00 00       // ResTable_config screenConfigPad2 = 0 占位符 bytesRead = 52
00 00 00 00 // ResTable_config unknown{}        = {0,0,0,0} //size = cofigsize - 52
00 00 00 00 // ResTable_type 第1个ResTable_entry(Entry)的offset
08 00       // ResTable_type 第1个ResTable_entry headerSize = 8
00 00       // ResTable_type 第1个ResTable_entry flags      = 0 
03 00 00 00 // ResTable_type 第1个ResTable_entry keyIndex   = 0x03
08 00       // ResTable_type 第1个ResTable_entry的Res_value
00 
05
02 0C 00 00 

02 02       // RES_TABLE_TYPE_SPEC_TYPE         = 0x0202

10 00 
14 00 00 00 
03          // ResTable_typeSpec id             = 0x03
00 00 00
01 00 00 00 
00 00 00 00 

01 02       // RES_TABLE_TYPE_TYPE              = 0x0201
4C 00 
60 00 00 00
03          // ResTable_type     id             = 0x03
00 00 00 
01 00 00 00 // ResTable_type entryCount         = 1
50 00 00 00 
38 00 00 00 // ResTable_config size             = 56
00 00 
00 00 
00 00 
00 00 
00 
00 
00 00 
00 
00 
00 
00
00 00 
00 00 
00 00 
00 00 
00 
00 
00 00 
00 00 
00 00
00 00 00 00 
00 00 00 00 00 00 00 00 
00 
00 
00 00
00 00 00 00 // ResTable_config unknown{}        = {0,0,0,0} //size = cofigsize - 52
00 00 00 00 // ResTable_type 第1个ResTable_entry(Entry)的offset
08 00 
00 00 
04 00 00 00 // ResTable_type 第1个ResTable_entry keyIndex   = 0x04
08 00 
00 
03 
03 00 00 00 

02 02       // RES_TABLE_TYPE_SPEC_TYPE         = 0x0202

10 00 
14 00 00 00
04          // ResTable_typeSpec id             = 0x04
00 00 00 
01 00 00 00 
00 00 00 00 

01 02       // RES_TABLE_TYPE_TYPE              = 0x0201
4C 00
60 00 00 00 
04          // ResTable_type     id             = 0x04
00 00 00 
01 00 00 00 // ResTable_type entryCount         = 1
50 00 00 00
38 00 00 00 // ResTable_config size             = 56
00 00 
00 00 
00 00 
00 00 
00 
00 
00 00
00 
00 
00 
00 
00 00 
00 00 
00 00 
00 00 
00 
00 
00 00
00 00 
00 00 
00 00 00 00 
00 00 00 00 00 00 00 00
00 
00 
00 00 
00 00 00 00 // ResTable_config unknown{}        = {0,0,0,0} //size = cofigsize - 52
00 00 00 00 // ResTable_type 第1个ResTable_entry(Entry)的offset
08 00 
00 00 
05 00 00 00  // ResTable_type 第1个ResTable_entry keyIndex   = 0x05
08 00 
00 
03 
04 00 00 00 

02 02       // RES_TABLE_TYPE_SPEC_TYPE         = 0x0202

10 00
18 00 00 00 
05          // ResTable_typeSpec id             = 0x05
00 00 00 
02 00 00 00 // ResTable_typeSpec entryCount     = 2
00 00 00 00
00 00 00 00 

01 02       // RES_TABLE_TYPE_TYPE              = 0x0201

4C 00 
64 00 00 00 
05          // ResTable_type     id             = 0x05
00 00 00
02 00 00 00 // ResTable_type entryCount         = 2
54 00 00 00 
38 00 00 00 
00 00 
00 00
00 00 
00 00 
00 
00 
E0 01       // ResTable_config density          = 480
00 
00 
00 
00 
00 00 
00 00
00 00 
00 00 
00 
00 
00 00 
00 00 
00 00 
00 00 00 00
00 00 00 00 00 00 00 00 
00 
00 
00 00 
00 00 00 00 // ResTable_config unknown{}        = {0,0,0,0} //size = cofigsize - 52
00 00 00 00 
FF FF FF FF 
08 00 
00 00 
06 00 00 00
08 00 
00 
03          // ResTable_type 第1个ResTable_entry的Res_value dataType = 0x03
05 00 00 00 // ResTable_type 第1个ResTable_entry的Res_value data = 0x05

01 02       // RES_TABLE_TYPE_TYPE              = 0x0201

4C 00 
64 00 00 00
05          // ResTable_type     id             = 0x05
00 00 00 
02 00 00 00 
54 00 00 00 
38 00 00 00
00 00 
00 00 
00 00 
00 00 
00 
00 
80 02        // ResTable_config density          = 640
00 
00 
00 
00
00 00 
00 00 
00 00 
00 00 
00 
00 
00 00 
00 00 
00 00 
00 00 00 00 
00 00 00 00 00 00 00 00 
00 
00 
00 00
00 00 00 00 
FF FF FF FF 
00 00 00 00 
08 00 
00 00
07 00 00 00 
08 00 
00 
03 
06 00 00 00 

02 02       // RES_TABLE_TYPE_SPEC_TYPE         = 0x0202

10 00
14 00 00 00 
06          // ResTable_typeSpec id             = 0x06
00 00 00 
01 00 00 00 
04 00 00 00

01 02       // RES_TABLE_TYPE_TYPE              = 0x0201

4C 00 
60 00 00 00 
06          // ResTable_type     id             = 0x06
00 00 00 
01 00 00 00
50 00 00 00 
38 00 00 00 
00 00 
00 00 
00 00 
00 00
00 
00 
00 00 
00 
00 
00 
00 
00 00 
00 00 
00 00 
00 00
00 
00 
00 00 
00 00 
00 00 
00 00 00 00 
00 00 00 00 00 00 00 00 
00 
00 
00 00 
00 00 00 00 
00 00 00 00
08 00 
00 00 
08 00 00 00 
08 00 
00 
03 
01 00 00 00

01 02       // RES_TABLE_TYPE_TYPE              = 0x0201

4C 00 
60 00 00 00 
06           // ResTable_type     id             = 0x06
00 00 00 
01 00 00 00
50 00 00 00 
38 00 00 00 
00 00 
00 00 
7A 68       // ResTable_config language{}       = {0x68,0x7A}
43 4E       // ResTable_config country{}        = {0x4E,0x43}
00 
00 
00 00 
00 
00 
00 
00 
00 00 
00 00 
00 00 
00 00
00 
00 
00 00 
00 00 
00 00 
00 00 00 00 
00 00 00 00 00 00 00 00 
00 
00 
00 00 
00 00 00 00 
00 00 00 00
08 00 
00 00 
08 00 00 00 
08 00 
00 
03 
00 00 00 00

02 02       // RES_TABLE_TYPE_SPEC_TYPE         = 0x0202

10 00 
14 00 00 00 
07 
00 00 00 
01 00 00 00
00 00 00 00 

01 02       // RES_TABLE_TYPE_TYPE              = 0x0201

4C 00 
6C 00 00 00 
07 
00 00 00
01 00 00 00 
50 00 00 00 
38 00 00 00 
00 00 
00 00
00 00 
00 00 
00 
00 
00 00 
00 
00 
00  
00 
00 00 
00 00
00 00 
00 00 
00 
00 
00 00 
00 00 
00 00 
00 00 00 00
00 00 00 00 00 00 00 00 
00 
00 
00 00 
00 00 00 00
00 00 00 00 
10 00       // ResTable_type 第1个ResTable_entry headerSize = 16
01 00       // ResTable_type 第1个ResTable_entry flags      = 0x01 表明是一个复杂的resource
09 00 00 00 // ResTable_type 第1个ResTable_entry keyIndex   = 0x09
00 00 00 00 // ResTable_type 第1个ResTable_entry parentEntry= 0
01 00 00 00 // ResTable_type 第1个ResTable_entry valueCount = 0
95 00 01 01 
08 00       // ResTable_type 第1个ResTable_entry valueCount
00 
01          // ResTable_type 第1个ResTable_entry dataType   = 0x01
00 00 02 7F 
