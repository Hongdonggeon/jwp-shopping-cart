package woowacourse.auth.config;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import woowacourse.auth.support.Auth;
import woowacourse.auth.support.AuthorizationExtractor;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.exception.nobodyexception.UnauthorizedTokenException;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    public LoginInterceptor(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("LoginInterceptor.preHandle");
        if (hasNotAnnotation(handler)) {
            return true;
        }

        validateToken(request);
        return true;
    }

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {
        log.info("LoginInterceptor.postHandle");
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception ex)
            throws Exception {
        log.info("LoginInterceptor.afterCompletion");
    }

    private boolean hasNotAnnotation(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        return Objects.isNull(handlerMethod.getMethodAnnotation(Auth.class));
    }

    private void validateToken(HttpServletRequest request) {
        String token = AuthorizationExtractor.extract(request);
        boolean isValidate = jwtTokenProvider.validateToken(token);
        if (!isValidate) {
            throw new UnauthorizedTokenException();
        }
    }
}
