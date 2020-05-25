package com.junhwa.springboot.config.auth;

import com.junhwa.springboot.config.auth.dto.SessionUser;
import com.junhwa.springboot.domain.user.User;
import com.junhwa.springboot.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;
    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object securityContextObject = httpSession.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
        if (securityContextObject == null)
            return null;

        SessionUser sessionUser = null;
        SecurityContextImpl securityContext = (SecurityContextImpl) securityContextObject;
        Object obj = securityContext.getAuthentication().getPrincipal();

        if (obj instanceof DefaultOAuth2User) {
            DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) obj;
            User user = userService.findByEmail((String) defaultOAuth2User.getAttributes().get("email"));
            sessionUser = new SessionUser(user);
        } else {

            User user = userService.findById(securityContext.getAuthentication().getName());   //비소셜 로그인의 경우 getName()으로 ID가 반환됨.
            if (user != null)
                sessionUser = new SessionUser(user);
        }
        return sessionUser;
    }
}