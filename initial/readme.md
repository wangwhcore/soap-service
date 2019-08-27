#### WebService 示例
###### 使用方法：
    jdk 1.8及其以上环境
    将gs-producting-web-service-0.1.0.jar 拷贝到D:/
    使用CMD 命令行，进入：D:/
    执行  java -jar  gs-producting-web-service-0.1.0.jar

###### 访问地址
    http://localhost:8080/ws/countries.wsdl

###### 参考资料
    https://spring.io/guides/gs/producing-web-service/#initial

###### 使用SOAP UI 5.5（5.2不成功）调用，或直接浏览器访问
    访问地址：http://localhost:8080/ws
    soapui调用 方法 getName， 传入String ，返回值也为该 String
###### linux使用以下方式
    curl -s --header "content-type: text/xml" -d @request.xml http://192.168.9.1:8080/ws |xmllint request.xml --format -


#### Restful 示例
    访问地址：http://localhost:8080/greeting?name=1232324234