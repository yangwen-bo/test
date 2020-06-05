package httpClient;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.InitializingBean;

import java.util.concurrent.TimeUnit;

/**
 * @Author yangwen-bo
 * @Date 2019/11/5.
 * @Version 1.0
 *
 * httpclient连接控制对象，设置最大连接数，超时时间，请求时间，连接保持时间等httpclient连接参数设置
 * 如果是在项目中，需要初始就声明，可以在config（在程序启动时自动加载@import的）里面注入，将本对象初始化
 * @Bean(initMethod = "start" ,destroyMethod = "stop")
 * public HttpClientHolder httpClientHolder(){
 *     return HttpClientHolder.getInstance();
 * }
 */
public class HttpClientHolder implements InitializingBean{

    private int maxTotal = 2000;
    private int maxPerRoute = 200;
    private int connTimeout = 5000;
    private int requestTimeout = 5000;
    private int keepaliveTiemout = 1000;

    private CloseableHttpClient client;

    public static HttpClientHolder getInstance(){
        return InstanceHolder.instance;
    }

    private HttpClientHolder(){}

    /**
     * 私有静态内部类，实现类的单例形式，同一加载
     */
    private static final class InstanceHolder{
        public static final HttpClientHolder instance = new HttpClientHolder();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout( requestTimeout ).setConnectTimeout( connTimeout ).build();
        client = HttpClientBuilder.create().setDefaultRequestConfig( requestConfig ).evictExpiredConnections()
                .evictIdleConnections( keepaliveTiemout, TimeUnit.MILLISECONDS ).setMaxConnPerRoute( maxPerRoute ).build();
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public void setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
    }

    public int getMaxPerRoute() {
        return maxPerRoute;
    }

    public void setMaxPerRoute(int maxPerRoute) {
        this.maxPerRoute = maxPerRoute;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public int getRequestTimeout() {
        return requestTimeout;
    }

    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    public int getKeepaliveTiemout() {
        return keepaliveTiemout;
    }

    public void setKeepaliveTiemout(int keepaliveTiemout) {
        this.keepaliveTiemout = keepaliveTiemout;
    }

    public CloseableHttpClient getClient() {
        return client;
    }

    public void setClient(CloseableHttpClient client) {
        this.client = client;
    }
}
