package woowacourse.auth.filter;

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
import org.springframework.stereotype.Component;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.nobodyexception.UnauthorizedTokenException;

@Slf4j
public class LoginFilter implements Filter {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginFilter(final JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        log.info("LoginFilter.init");
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
        log.info("LoginFilter.doFilter");
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        try {
            validateToken(servletRequest);
            chain.doFilter(request, response);
        } catch (Exception e) {
            HttpServletResponse servletResponse = (HttpServletResponse) response;
            servletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    @Override
    public void destroy() {
        log.info("LoginFilter.destroy");
    }

    private void validateToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        boolean isValidate = jwtTokenProvider.validateToken(token);
        if (!isValidate) {
            throw new UnauthorizedTokenException();
        }
    }
}
