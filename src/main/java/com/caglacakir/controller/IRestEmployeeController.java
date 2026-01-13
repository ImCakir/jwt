package com.caglacakir.controller;

import com.caglacakir.dto.DtoEmployee;

public interface IRestEmployeeController {

    public DtoEmployee findEmployeeById(Long id);
}
