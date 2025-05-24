package com.br.TaskManager.controler;

import com.br.TaskManager.dtos.LoginRequestDto;
import com.br.TaskManager.dtos.LoginResponseDto;
import com.br.TaskManager.model.Customer;
import com.br.TaskManager.repository.RCustomer;
import com.br.TaskManager.service.SCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/login")
public class CLogin {

    private final SCustomer serviceCustomer;
    private final RCustomer repositoryCustomer;

    public CLogin(SCustomer serviceCustomer, RCustomer repositoryCustomer) {
        this.serviceCustomer = serviceCustomer;
        this.repositoryCustomer = repositoryCustomer;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequest) {
        Optional<Customer> customer = repositoryCustomer.findByEmail(loginRequest.email());

        if (customer.isPresent()) {
            Customer custom = customer.get();

            if (custom.getSenha().equals(loginRequest.senha())) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", custom.getId());
                map.put("nome", custom.getNome());
                map.put("email", custom.getEmail());
                map.put("CPF", custom.getCpf());

                return ResponseEntity.ok(new LoginResponseDto("Bem-vindo", map));
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDto("email ou senha invalidos", null));
    }

}
