package com.caglacakir.service;

import com.caglacakir.dto.DtoUser;
import com.caglacakir.jwt.AuthRequest;
import com.caglacakir.jwt.AuthResponse;

public interface IAuthService {

    public DtoUser register(AuthRequest request);

    public AuthResponse authenticate(AuthRequest request);
}
