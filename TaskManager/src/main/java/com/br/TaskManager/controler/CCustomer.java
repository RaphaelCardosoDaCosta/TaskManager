package com.br.TaskManager.controler;

import com.br.TaskManager.dtos.CustomerDto;
import com.br.TaskManager.model.Customer;
import com.br.TaskManager.service.SCustomer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/register")
public class CCustomer {

    private final SCustomer serviceCustomer;

    public CCustomer(SCustomer serviceCustomer) {
        this.serviceCustomer = serviceCustomer;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> create(@RequestBody Customer body) {
        Customer customer = serviceCustomer.create(body);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "criado");
        response.put("data", customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        return ResponseEntity.ok().body(serviceCustomer.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Customer>> findById(@PathVariable  Long id) {
        return ResponseEntity.ok().body(serviceCustomer.getById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Customer> update(@PathVariable Long id, @RequestBody CustomerDto body) {
        return serviceCustomer.update(id, body).map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok().body(serviceCustomer.delete(id));
    }


}
