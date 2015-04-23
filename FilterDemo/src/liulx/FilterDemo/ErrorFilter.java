package liulx.FilterDemo;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by liulixiang on 2015/4/22.
 * Description:
 */
public class ErrorFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("ºÏ≤‚µΩ¥ÌŒÛ–≈œ¢");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
