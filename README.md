

# javautils
JAVA工具集合

## 如何部署

 ```shell
 mvn clean install -DskipTests
 ```

## 项目目录结构（v1.0.0）

|package|描述|
|:--|:--|
|org.cls.common |常用工具类，包括常量定义，正则表达式工具类，字符串工具类，IO流关闭，java对象拷贝(建议使用spring的BeanUtils)、身份证号码校验
|org.cls.media.tess4j |工具类，ocr文字识别，以及opencv得身份证人脸提取, 音频，二维码
|org.cls.encrypt|加密解密工具类，包括md5，aes，des
|org.cls.compress|zip，gzip等文件压缩工具(待补充)
|org.cls.office|excel读写工具类(待补充)
|org.cls.network|http请求工具类,android均可使用(待补充)
|org.cls.xml|xml解析工具类
|org.cls.jdbc|java jdbc工具类， 数据库表元数据解析，目前只解析mysql(待补充)
|org.cls.json|json-lib,jackson,fastjson,gson
|org.cls.ftl|java基于freemarker的模板生成工具
|org.cls.reflect|java proxy反射，asm
