package com.salermo.springcrud.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salermo.springcrud.entities.Category;
import com.salermo.springcrud.repositories.CategoryRepository;

@Service //Responsavel por registrar essa classe como um componente que vai participar do sistema de injeção de dependencia automatizado do spring
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll(){
        return repository.findAll();
    }
}
