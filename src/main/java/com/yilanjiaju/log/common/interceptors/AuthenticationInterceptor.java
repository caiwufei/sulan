package com.yilanjiaju.log.common.interceptors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       /* String uri = request.getRequestURI();

        String sessionId = (String) request.getSession().getAttribute("token");
        //String sessionId = CookieUtil.getCookie(request, LoginConstant.COOKIES_KEY_NAME);
        if (StringUtils.isBlank(sessionId)) {
            return ResponseUtil.invalidAccess(response);
        }

        //getUserFromCacheBySession调用，如果有user，就已经刷新了过期时间
        User user = AppContext.getBean(AuthenticationService.class).getUserFromCacheBySession(sessionId);
        if (null == user) {
            return ResponseUtil.invalidAccess(response);
        }

        //设置请求线程用户
        AppContext.setCurrentUser(user);
        AppContext.setSession(sessionId);
        AppContext.getBean(AuthenticationService.class).refreshAccessToken(sessionId);*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }


}
