package com.example.myfirstapp.zip.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.myfirstapp.zip.entities.Order;
import com.example.myfirstapp.zip.entities.User;
import com.example.myfirstapp.zip.repositories.OrderRepository;
import com.example.myfirstapp.zip.repositories.UserRepository;
import com.example.myfirstapp.zip.exceptions.ResourceNotFoundException;
import com.example.myfirstapp.zip.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private UserRepository userRepository;

    public List<Order> findAll() {
        return repository.findAll();
    }

    public Order findById(Long id) {
        Optional<Order> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Order insert(Order obj) {
        // Agora, pegamos o clientId do novo campo transient
        // Se clientId for null, lançamos a exceção
        if (obj.getClientId() == null) {
            throw new IllegalArgumentException("Client ID must be provided for Order insertion.");
        }
        
        // Buscamos o usuário gerenciado usando o clientId
        User client = userRepository.getReferenceById(obj.getClientId());
        
        // Associamos o usuário gerenciado ao Order
        obj.setClient(client);
        
        return repository.save(obj);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Order update(Long id, Order obj) {
        try {
            Order entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Order entity, Order obj) {
        entity.setMoment(obj.getMoment());
        entity.setOrderStatus(obj.getOrderStatus());
        // A atualização do cliente em um pedido existente é menos comum, mas pode ser adicionada:
        // User client = userRepository.getReferenceById(obj.getClient().getId());
        // entity.setClient(client);
    }
}