package com.salermo.springcrud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salermo.springcrud.dto.CategoryDTO;
import com.salermo.springcrud.entities.Category;
import com.salermo.springcrud.repositories.CategoryRepository;
import com.salermo.springcrud.services.exceptions.EntityNotFoundException;



@Service //Responsavel por registrar essa classe como um componente que vai participar do sistema de injeção de dependencia automatizado do spring
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll(){
        List<Category> list = repository.findAll();
                                                   //Aqui faço a conversao do list em uma categoryDTO     
        return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());
/*         //Convertendo a lista de categoria para categoriaDTO
        List<CategoryDTO> listDto = new ArrayList<>(); //instancio uma lista vazia

        //Crio um for para percorrer a lista, dou o apelido para cada elemento de cat
        for(Category cat : list){
            listDto.add(new CategoryDTO(cat)); //Instancio um dto com essa categoria e adiciono a listDTO
        }
        return listDto; */
    }


    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id); //O Optional é uma abordagem para evitar trabalhar com valor nulo
        Category entity = obj.orElseThrow(() -> new EntityNotFoundException("Entity not found")); //O orElseThrow permite que eu retorne outra coisa caso não haja nada no objeto, nesse caso uso uma expressão lambda para retornar minha classe de exceção
        return new CategoryDTO(entity);
    }
}
