package com.caglacakir.controller;

import com.caglacakir.dto.DtoUser;
import com.caglacakir.jwt.AuthRequest;
import com.caglacakir.jwt.AuthResponse;
import com.caglacakir.jwt.RefreshTokenRequest;

public interface IRestAuthController {

    public DtoUser register(AuthRequest request);

    public AuthResponse authenticate(AuthRequest request);

    public AuthResponse refreshToken(RefreshTokenRequest request);
}
