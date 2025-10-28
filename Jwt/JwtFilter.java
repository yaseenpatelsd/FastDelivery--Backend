package com.Yua.FastDelivery.Delivery_App.Jwt;

import com.Yua.FastDelivery.Delivery_App.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String path=request.getRequestURI();
    if (path.equals("/register") || path.equals("/login") || path.equals("/account-verification")|| path.equals("/Otp-request")
    || path.equals("/Otp-request-for-password-reset") || path.equals("/password-reset") || path.equals("/admin/register")){
        filterChain.doFilter(request,response);
        return;
    }
    String authHeader=request.getHeader("Authorization");
    String username=null;
    String token=null;

    if (authHeader!=null && authHeader.startsWith("Bearer ")){
        token=authHeader.substring(7).trim();
        username=jwtUtil.extractUsernameFromToken(token);
    }

    if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
        UserDetails user=userService.loadUserByUsername(username);

        if (jwtUtil.isTokenValid(token)){
            UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authtoken);
        }
    }
    filterChain.doFilter(request,response);
    }
}
