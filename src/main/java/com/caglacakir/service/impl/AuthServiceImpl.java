package com.caglacakir.service.impl;

import com.caglacakir.dto.DtoUser;
import com.caglacakir.jwt.AuthRequest;
import com.caglacakir.jwt.AuthResponse;
import com.caglacakir.jwt.JwtService;
import com.caglacakir.model.RefreshToken;
import com.caglacakir.model.User;
import com.caglacakir.repository.RefreshTokenRepository;
import com.caglacakir.repository.UserRepository;
import com.caglacakir.service.IAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis()+ 1000*60*60*4));
        refreshToken.setUser(user);

        return refreshToken;
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        try {
            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
            authenticationProvider.authenticate(auth); // db deki kullanı adı ve şifre ile girilen kullanıcı adı ve şifreyi karşılaştırır

            Optional<User> optionalUser = userRepository.findByUsername(request.getUsername());
            String accessToken = jwtService.generateToken(optionalUser.get());

            RefreshToken refreshToken = createRefreshToken(optionalUser.get());
            refreshTokenRepository.save(refreshToken); //db ye kaydet

            return new AuthResponse(accessToken, refreshToken.getRefreshToken());

        }catch (Exception e) {
            System.out.println("Kullanıcı adı veya şifre hatalı");
        }
        return null;
    }

    @Override
    public DtoUser register(AuthRequest request) {
        DtoUser dto = new DtoUser();
        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);
        BeanUtils.copyProperties(savedUser, dto);
        return dto;
    }


}
