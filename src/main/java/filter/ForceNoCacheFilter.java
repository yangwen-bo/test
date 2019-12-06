package filter;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * @Author yangwen-bo
 * @Date 2019/11/8.
 * @Version 1.0
 *
 * 使浏览器不缓存页面的过滤器
 */
public class ForceNoCacheFilter extends Filter {
    @Override
    public void doFilter(HttpExchange httpExchange, Chain chain) throws IOException {

    }

    @Override
    public String description() {
        return null;
    }
}
