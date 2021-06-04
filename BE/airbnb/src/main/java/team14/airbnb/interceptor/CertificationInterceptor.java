package team14.airbnb.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import team14.airbnb.exception.UnauthorizedException;
import team14.airbnb.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CertificationInterceptor implements HandlerInterceptor {
    private final UserService userService;

    @Autowired
    public CertificationInterceptor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String jwt = request.getHeader("Authorization");
        if (jwt == null) {
            throw new UnauthorizedException("Authorization 코드가 존재하지 않습니다.");
        }

        request.setAttribute("user", userService.getUserIdFromJwt(jwt));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }
}
