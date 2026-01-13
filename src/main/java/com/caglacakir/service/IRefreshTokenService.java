package com.caglacakir.service;

import com.caglacakir.jwt.AuthResponse;
import com.caglacakir.jwt.RefreshTokenRequest;

public interface IRefreshTokenService {

    public AuthResponse refreshToken(RefreshTokenRequest request);
}
