package liulx.FilterDemo;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by liulixiang on 2015/4/22.
 * Description:
 */
public class SecondFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("Second Filter Before Do Filter");
        chain.doFilter(req, resp);
        System.out.println("Second Filter After Do Filter");

    }

    public void init(FilterConfig config) throws ServletException {

    }

}
