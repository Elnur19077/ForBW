package bw.black.security;


import bw.black.enums.Role;
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
import org.springframework.web.filter.OncePerRequestFilter;
import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.Cookie;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
   /* @Autowired
    private RedisTemplate<String, String> redisTemplate;*/

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getServletPath();
        // /uploads/ ilə başlayan sorğuları bypass et (filtrə girmədən keç)
        if (path.startsWith("/uploads/")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = getJwtFromRequest(request);

        /*if (token != null && redisTemplate.opsForValue().get(token) != null) {
            throw new EshopException(ExceptionConstants.INVALID_REQUEST_DATA, "Profile logged out");
        }*/
        if (token != null && jwtGenerator.validateToken(token)) {
            String username = jwtGenerator.getUsernameFromJWT(token);
            customUserDetailsService.setRole(Role.valueOf(jwtGenerator.getRoleFromJWT(token)));
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);

    }

    private String getJwtFromRequest(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}