package com.caglacakir.service.impl;

import com.caglacakir.jwt.AuthResponse;
import com.caglacakir.jwt.JwtService;
import com.caglacakir.jwt.RefreshTokenRequest;
import com.caglacakir.model.RefreshToken;
import com.caglacakir.model.User;
import com.caglacakir.repository.RefreshTokenRepository;
import com.caglacakir.service.IRefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenImpl implements IRefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtService jwtService; //Token oluşturuyor idi.


    public boolean isRefreshTokenExpired(Date expiredDate) {
        return new Date().before(expiredDate);
    }

    public RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(UUID.randomUUID().toString());
        refreshToken.setExpireDate(new Date(System.currentTimeMillis()+ 1000*60*60*4));
        refreshToken.setUser(user);

        return refreshToken;
    }

    // njjdkd fkfkflk kfkflf kfkflkf kflkfl rastgele bir şey geldiğinde
    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Optional<RefreshToken> optional = refreshTokenRepository.findByRefreshToken(request.getRefreshToken());
        if(optional.isEmpty()) {
            System.out.println("REFRESH TOKEN GEÇERSİZDİR :" + request.getRefreshToken());
        }
        RefreshToken refreshToken = optional.get(); //varolan kaydı al.

        if(!isRefreshTokenExpired(refreshToken.getExpireDate())) {
            System.out.println("REFRESH TOKEN EXPİRE OLMUŞTUR : " + refreshToken.getRefreshToken());
        }

        String accessToken =  jwtService.generateToken(refreshToken.getUser());
        //burası UserDetails ister ancak refreshToken içerisinde de User vardır.
        //access 2 saat geçerli refresh token ında 1 saat süresi kaldı.İkiside bittiğinde token yenilenmeyecektir.
        // refreshtoken ı da yenıden oluşturursak sistemde kalırız.
        RefreshToken savedRefreshToken = refreshTokenRepository.save( createRefreshToken(refreshToken.getUser()));



        return new AuthResponse(accessToken, savedRefreshToken.getRefreshToken());
    }
}
