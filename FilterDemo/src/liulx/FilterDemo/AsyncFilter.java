package liulx.FilterDemo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by liulixiang on 2015/4/22.
 * Description:
 */
@WebFilter(filterName = "AsyncFilter",value = {"/index.jsp"},
        asyncSupported = true,
        dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.ASYNC})
public class AsyncFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Start ... AsyncFilter");
        chain.doFilter(req, resp);
        System.out.println("End... AsyncFilter");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
