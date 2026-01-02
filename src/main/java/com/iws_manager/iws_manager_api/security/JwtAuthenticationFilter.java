package com.iws_manager.iws_manager_api.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // Imprimir todos los headers recibidos para diagnóstico
            System.out.println("--- Headers Recibidos ---");
            java.util.Collections.list(request.getHeaderNames())
                    .forEach(headerName -> System.out.println(headerName + ": " + request.getHeader(headerName)));
            System.out.println("-------------------------");

            String jwt = parseJwt(request);
            if (jwt != null) {
                String username = jwtUtils.extractUsername(jwt);
                System.out.println("DEBUG: Intento de acceso con usuario: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                    if (jwtUtils.validateToken(jwt, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        System.out.println("DEBUG: Autenticación OK para: " + username + " con roles: "
                                + userDetails.getAuthorities());
                    } else {
                        System.out.println("DEBUG: Token INVALIDO para: " + username);
                    }
                }
            } else {
                System.out.println("DEBUG: No se detectó token en la petición.");
            }
        } catch (Exception e) {
            System.err.println("DEBUG ERROR: " + e.getMessage());
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth)) {
            if (headerAuth.startsWith("Bearer ")) {
                return headerAuth.substring(7);
            }
            // Si el usuario olvidó poner "Bearer ", devolvemos el valor tal cual
            return headerAuth;
        }

        return null;
    }
}
