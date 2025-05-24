package com.br.TaskManager.service;

import com.br.TaskManager.dtos.CustomerDto;
import com.br.TaskManager.model.Customer;
import com.br.TaskManager.repository.RCustomer;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SCustomer {

    private final RCustomer repositoryCustomer;

    public SCustomer(RCustomer repositoryCustomer) {
        this.repositoryCustomer = repositoryCustomer;
    }

    public Customer create(Customer data) {
        return repositoryCustomer.save(data);
    }

    public Optional<Customer> getById(Long id) {
        return repositoryCustomer.findById(id);
    }

    public List<Customer> getAll() {
        return repositoryCustomer.findAll();
    }

    public Optional<Customer> update(Long id, CustomerDto data) {
        return repositoryCustomer.findById(id).map(user -> {
            if(data.nome() != null) {
                user.setNome(data.nome());
            }
            if(data.email() != null) {
                user.setEmail(data.email());
            }
            if(data.senha() != null) {
                user.setSenha(data.senha());
            }
            return repositoryCustomer.save(user);
        });
    }

    public String delete(Long id) {
        repositoryCustomer.deleteById(id);
        return "usuario removido.";
    }

}
