package com.example.myfirstapp.zip.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.myfirstapp.zip.entities.Category; // Nova importação
import com.example.myfirstapp.zip.entities.Product;
import com.example.myfirstapp.zip.repositories.CategoryRepository; // Nova importação
import com.example.myfirstapp.zip.repositories.ProductRepository;
import com.example.myfirstapp.zip.exceptions.DatabaseException;
import com.example.myfirstapp.zip.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository; // Injetar CategoryRepository para gerenciar relações

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Product insert(Product obj) {
        // Garante que as categorias associadas no objeto Product sejam buscadas do banco
        // para que o JPA as gerencie corretamente e evite persistir categorias duplicadas
        obj.getCategories().clear(); // Limpa as categorias que vieram no obj para adicionar as do banco
        for (Category cat : obj.getCategories()) { // Itera sobre as categorias que vieram no obj
            Category category = categoryRepository.getReferenceById(cat.getId()); // Busca a categoria do banco
            obj.getCategories().add(category); // Adiciona a categoria gerenciada pelo JPA
        }
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

    public Product update(Long id, Product obj) {
        try {
            Product entity = repository.getReferenceById(id);
            updateData(entity, obj);
            return repository.save(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    private void updateData(Product entity, Product obj) {
        entity.setName(obj.getName());
        entity.setDescription(obj.getDescription());
        entity.setPrice(obj.getPrice());
        entity.setImgUrl(obj.getImgUrl());

        entity.getCategories().clear(); // Limpa as categorias atuais do produto
        for (Category cat : obj.getCategories()) {
            Category category = categoryRepository.getReferenceById(cat.getId()); // Busca a categoria do banco
            entity.getCategories().add(category); // Adiciona a categoria gerenciada
        }
    }
}