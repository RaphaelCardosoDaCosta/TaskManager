package com.br.TaskManager.repository;

import com.br.TaskManager.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RCustomer extends JpaRepository<Customer, Long> {
    public Optional<Customer> findByEmail(String email);
}
