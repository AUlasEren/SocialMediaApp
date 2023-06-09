package com.ulas.config.security;

import com.ulas.exception.ErrorType;
import com.ulas.exception.UserServiceException;
import com.ulas.utility.JwtTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenManager jwtTokenManager;
    @Autowired
    private JwtUserDetails jwtUserDetails;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        //System.out.println("====>"+authorizationHeader);
        if(authorizationHeader!=null&&authorizationHeader.startsWith("Bearer ")){
            String token = authorizationHeader.substring(7);
            Optional<String> userRole = jwtTokenManager.getRoleFromToken(token);
            if(userRole.isPresent()){//jwtTokenManager.validateToken(token)
                UserDetails userDetails = jwtUserDetails.loadUserByUserRole(userRole.get());
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }else{
                throw new UserServiceException(ErrorType.INVALID_TOKEN);
            }
        }
        filterChain.doFilter(request,response);
    }
}
