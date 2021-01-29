package subway.auth.infrastructure;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import subway.auth.service.AuthService;
import subway.common.exception.InvalidTokenException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private AuthService authService;

    public LoginInterceptor(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = AuthorizationExtractor.extract(request);
        if (accessToken == null || !authService.validateToken(accessToken)) {
            throw new InvalidTokenException(InvalidTokenException.INVALID_TOKEN);
        }
        return true;
    }

}