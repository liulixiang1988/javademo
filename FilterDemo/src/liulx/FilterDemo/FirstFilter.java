package liulx.FilterDemo;

import javax.servlet.*;
import java.io.IOException;

public class FirstFilter implements Filter {
    public void destroy() {
        System.out.println("Filter Destroy");
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Do filter before request");
        chain.doFilter(req, resp);
        System.out.println("Do filter after request");
    }

    public void init(FilterConfig config) throws ServletException {
        System.out.println("Init First Filter");
    }
}
