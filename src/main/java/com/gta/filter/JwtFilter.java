package com.gta.filter;

import java.io.IOException;
import java.util.Collections;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gta.dto.ErrorResponse;
import com.gta.util.JwtUtil;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

/**
 * JWT 인증 필터
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private final JwtUtil jwtUtil;
	
	@Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            String token = authHeader.substring(7);

            try {
                Long userId = jwtUtil.getUserId(token);
                String role = jwtUtil.getRole(token);

                // request에 저장
                request.setAttribute("userId", userId);
                request.setAttribute("role", role);
                
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userId,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                authentication.setDetails(request);

                SecurityContextHolder.getContext().setAuthentication(authentication);
                
            } catch (ExpiredJwtException e) {
            	setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "만료된 토큰입니다.");
                return;
            } catch (JwtException e) {
                setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 토큰입니다.");
                return;
            } catch (Exception e) {
            	setErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "토큰 처리 중 오류가 발생했습니다.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
	
	private void setErrorResponse(HttpServletResponse response, int status, String message) throws IOException {
		SecurityContextHolder.clearContext();
		
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
