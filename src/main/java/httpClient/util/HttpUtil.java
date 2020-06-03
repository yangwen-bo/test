package main.java.httpClient.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @Author yangwen-bo
 * @Date 2019/11/5.
 * @Version 1.0
 *
 * http数据处理工具类
 */
public class HttpUtil {

    private static final HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

    public static CloseableHttpResponse post(CloseableHttpClient client, String url, String dat, ContentType type){
        HttpPost post = new HttpPost( url );
        HttpEntity entity = EntityBuilder.create().setText( dat ).setContentType( type ).build();
        post.setEntity( entity );
        CloseableHttpResponse resp;
        try {
            resp = client.execute( post );
            return resp;
        }catch (IOException e){
            System.out.println(e);
        }
        return null;
    }

    public static String readResponse(HttpResponse response){
        HttpEntity resEntity = response.getEntity();
        if(resEntity == null){
            return null;
        }
        try {
            Charset charset = ContentType.getOrDefault( resEntity ).getCharset();
            String contentCharset = charset == null ? HTTP.DEF_CONTENT_CHARSET.name() : charset.name();
            byte[] date = EntityUtils.toByteArray( resEntity );
            return new String( date,contentCharset );
        }catch (IOException e){

        }finally {
            try {
                EntityUtils.consume( resEntity );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
