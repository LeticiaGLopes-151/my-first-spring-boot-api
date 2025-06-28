package com.example.myfirstapp.zip.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.myfirstapp.zip.entities.Category;
import com.example.myfirstapp.zip.entities.Product;
import com.example.myfirstapp.zip.repositories.CategoryRepository;
import com.example.myfirstapp.zip.repositories.ProductRepository;
import com.example.myfirstapp.zip.exceptions.DatabaseException;
import com.example.myfirstapp.zip.exceptions.ResourceNotFoundException; //

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findAll() {
        return repository.findAll();
    }

    public Product findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Product insert(Product obj) {
        Set<Category> categoriesFromRequest = new HashSet<>(obj.getCategories());
        obj.getCategories().clear();

        for (Category cat : categoriesFromRequest) {
            // **MUDANÇA AQUI: Usar findById para verificar a existência da categoria**
            Optional<Category> optionalCategory = categoryRepository.findById(cat.getId());
            if (optionalCategory.isPresent()) {
                obj.getCategories().add(optionalCategory.get());
            } else {
                // Lança uma exceção ResourceNotFoundException se a categoria não for encontrada
                throw new ResourceNotFoundException("Category with ID " + cat.getId() + " not found for product."); //
            }
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

        Set<Category> categoriesFromRequest = new HashSet<>(obj.getCategories());
        entity.getCategories().clear();
        for (Category cat : categoriesFromRequest) {
            // **MUDANÇA AQUI: Usar findById também para o método updateData**
            Optional<Category> optionalCategory = categoryRepository.findById(cat.getId());
            if (optionalCategory.isPresent()) {
                entity.getCategories().add(optionalCategory.get());
            } else {
                // Lança uma exceção ResourceNotFoundException se a categoria não for encontrada
                throw new ResourceNotFoundException("Category with ID " + cat.getId() + " not found for product update."); //
            }
        }
    }
}