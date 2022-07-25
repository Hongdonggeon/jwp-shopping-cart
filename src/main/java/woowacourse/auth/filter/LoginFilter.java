package woowacourse.auth.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;

@Slf4j
public class LoginFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginFilter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        log.info("LoginFilter init");
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        final boolean isValid = validateToken(servletRequest, response);

        if (!isValid) {
            servletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        log.info("LoginFilter destroy");
    }

    private boolean validateToken(HttpServletRequest request, final ServletResponse servletResponse) {
        String token = AuthorizationExtractor.extract(request);
        return jwtTokenProvider.validateToken(token);
    }
}
