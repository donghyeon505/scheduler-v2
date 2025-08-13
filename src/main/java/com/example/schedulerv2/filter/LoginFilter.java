package com.example.schedulerv2.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/", "/users/signup", "/users/login"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        if (!isWhiteList(requestURI)) {

            HttpSession session = httpRequest.getSession(false);

            if ("GET".equals(httpRequest.getMethod()) && PatternMatchUtils.simpleMatch("/schedules*", requestURI)) {
                chain.doFilter(request, response);
                return;
            }

            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 해주세요");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
