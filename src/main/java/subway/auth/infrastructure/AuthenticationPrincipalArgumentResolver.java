package subway.auth.infrastructure;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import subway.auth.domain.AuthenticationPrincipal;
import subway.auth.service.AuthService;
import subway.member.domain.LoginMember;
import subway.member.domain.Member;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private AuthService authService;

    public AuthenticationPrincipalArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public LoginMember resolveArgument(MethodParameter parameter,
                                       ModelAndViewContainer mavContainer,
                                       NativeWebRequest webRequest,
                                       WebDataBinderFactory binderFactory) {

        String token = AuthorizationExtractor.extract((HttpServletRequest) webRequest.getNativeRequest());
        Member member = null;

        if (authService.validateToken(token)) {
            member = authService.findMemberByToken(token);
        }

        return LoginMember.of(member);
    }
}