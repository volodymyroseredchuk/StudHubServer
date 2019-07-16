package com.softserve.academy.studhub.security.jwt;

import com.softserve.academy.studhub.security.constants.JwtConstants;
import com.softserve.academy.studhub.security.services.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {

            String jwt = getJwt(request);

            if (jwt != null && tokenProvider.validateJwtToken(jwt)) {

                Integer id = tokenProvider.getIdFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserById(id);
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error(JwtConstants.CANNOT_SET_USER_JWT, e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {

        String authHeader = request.getHeader(JwtConstants.JWT_HEADER);

        if (authHeader != null && authHeader.startsWith(JwtConstants.JWT_TYPE)) {
            return authHeader.replace(JwtConstants.JWT_TYPE, "");
        }

        return null;
    }
}