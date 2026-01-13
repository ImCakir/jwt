package com.caglacakir.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    //filter katmanı oldugunu söylemek için OncePerRequestFilter extend etmemiz gerekir
    // Atılan tüm istekler Controller a düşmeden bu filter katmanına düşer.

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserDetailsService userDetailsService; //userDetails ınterface ını yoneten servı
    //bunun içerisinde  loadUserByUsername(String username) var. Buraya tokendan gelen username ile user tablosundakı username aynı mı kotnrolu yapacak

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //Bearer Fsdfdfdgfkglfdbhdgkljgldgkdg --> token, Bearer ise postmanda headerdan secıyoruz.
        String header;
        String token;
        String username;

        header = request.getHeader("Authorization"); //token alındı

        if(header == null) {
            filterChain.doFilter(request, response);
            return; //controller a düşmez direkt isteği atana geri döner
        }

        token = header.substring(7); //Bearer_ = buraya kadar olan kısım 7 karaktr.Burayı atar, attıgı noktada token başlar
        try{
           username = jwtService.getUsernameByToken(token); // token ııcnden username ı coz
            if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username); // verıtabanından kontrol eder. Tokendan geldi bır username, db de var bır username. karsılastır
                if(userDetails != null && jwtService.isTokenExpired(token)) {
                    //Kişiyi SecurityContext' e koyabılırız. Kişi içeri alınabılır
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                    authentication.setDetails(userDetails);
                    //Controller a düşebilmek için setlememiz gerekir.Burayı doldurdugumuz için controller a alır
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch (ExpiredJwtException e) {
            System.out.println("Token süresi dolmuştur : " + e.getMessage());

        } catch (Exception e) {
           System.out.println("Genel bir hata oluştu: " + e.getMessage());
        }
       filterChain.doFilter(request, response); // süreci devam ettirir
    }

}
