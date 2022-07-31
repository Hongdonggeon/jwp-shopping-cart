package woowacourse.auth.config;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        log.info("CustomFilter.init");
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        log.info("CustomFilter.doFilter");
        chain.doFilter(request, response);

    }

    @Override
    public void destroy() {
        log.info("CustomFilter.destroy");
    }
}
