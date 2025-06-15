package com.example.myfirstapp.zip.repositories; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.myfirstapp.zip.entities.Category; 

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}