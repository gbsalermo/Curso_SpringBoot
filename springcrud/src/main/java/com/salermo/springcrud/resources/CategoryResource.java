package com.salermo.springcrud.resources;

//import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salermo.springcrud.entities.Category;
//import org.springframework.web.bind.annotation.RequestParam;
import com.salermo.springcrud.services.CategoryService;


//Primeiro recurso rest
//A api é implementada por meio dos controladores rest
//O termo usado para referenciar esses recursos é resources, por isso o nome do pacote
//Esse é o controlador rest da camada
//As camadas seguem Controlador -> service -> repository

@RestController //Informa que a classe vai cuidar das requisições e respostas https
@RequestMapping(value = "/categories") //Direciona as requisições HTTP para os metodos corretos
public class CategoryResource {

    @Autowired
    private CategoryService service;


    //endpoints
    @GetMapping
    public ResponseEntity<List<Category>> findAll(){
        List<Category> list = service.findAll(); //Chamo a list do service como é o seguimento da camada
    /*    List<Category> list = new ArrayList<>();
        list.add(new Category(1L, "Books")); //Aqui adiciono a primeira categoria, o L maiusculo indica que o numero vai ser um long que é o id da categoria
        list.add(new Category(2L, "Eletronics")); */
        return ResponseEntity.ok().body(list) ;
    }

    /*TESTE
    @GetMapping("/")
    public String test(){
        return "API FUNCIONANDO";
    }*/


    }
    
