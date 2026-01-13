package com.caglacakir.controller.impl;

import com.caglacakir.controller.IRestEmployeeController;
import com.caglacakir.dto.DtoEmployee;
import com.caglacakir.service.IEmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class RestEmployeeControllerImpl implements IRestEmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @GetMapping("/{id}")
    @Override
    public DtoEmployee findEmployeeById(@PathVariable(value = "id") Long id) {
        return employeeService.findEmployeeById(id) ;
        //filter a düşecek. herhangi bir token ı yok.filter ın bağlı olduğu controller a gitme, kullanıcıya tekrardan geri dön.
        //status kod: 403 dön. Bu servısı kullanmaya yetkın yoktur demek. Erişim izni vermez..Token almamız gerekiyor.
        //JwtAuthenticationFilter' daki SecurityContextHolder.getContext().setAuthentication(authentication); çalışmıcak.Çünkü
        //filterChain.doFilter(request, response); bu metod sonlandırıldı.

    }

}
