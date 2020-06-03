package main.java.httpClient.test;


import main.java.httpClient.SendHttpClient;
import main.java.httpClient.entity.RequestHttpEntity;

/**
 * @Author yangwen-bo
 * @Date 2019/11/6.
 * @Version 1.0
 *
 * 测试httpClient
 */
public class HttpClientTest {
    public static void main(String[] args) {

        RequestHttpEntity requestHttpEntity = new RequestHttpEntity("1355551234","11111","liushuihao","laiyuan");

        SendHttpClient.sendHttp( "http://www.baidu.com",requestHttpEntity );
    }
}
