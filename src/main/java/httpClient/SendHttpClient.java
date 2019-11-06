package httpClient;

import httpClient.entity.ResponseHttpEntity;
import httpClient.util.HttpUtil;
import httpClient.util.JsonUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;

import java.nio.charset.Charset;

/**
 * @Author yangwen-bo
 * @Date 2019/11/5.
 * @Version 1.0
 *
 * 发送、接收http请求，并对请求，响应数据做相应处理
 */
public class SendHttpClient {

    /**
     *
     * @param url  请求，要发送的地址
     * @param object 请求，对象，这里是 RequestHttpEntity
     * @return ResponseHttpEntity 最终响应json数据处理成我们想要的实体对象
     */
    public static ResponseHttpEntity sendHttp(String url ,Object object){
        //导入httpclient-4.5.5.jar包
        CloseableHttpClient client = HttpClientHolder.getInstance().getClient();
        ContentType contentType = ContentType.APPLICATION_JSON.withCharset( Charset.forName( "GBK" ) );
        String requestJsonString = JsonUtil.toJsonString( object );
        System.out.println(requestJsonString);//将请求json字符串打印出来
        String responsqJsonString;
        try {
            CloseableHttpResponse response = HttpUtil.post( client,url,requestJsonString,contentType );
            responsqJsonString = HttpUtil.readResponse(response);
        }catch (Exception e){
            return new ResponseHttpEntity();
        }

        return JsonUtil.fromJsonString( responsqJsonString,ResponseHttpEntity.class );

    }

}
