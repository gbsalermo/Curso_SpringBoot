package com.salermo.springcrud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salermo.springcrud.dto.CategoryDTO;
import com.salermo.springcrud.entities.Category;
import com.salermo.springcrud.repositories.CategoryRepository;
import com.salermo.springcrud.services.exceptions.DataBaseException;
import com.salermo.springcrud.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;



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
        Category entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found")); //O orElseThrow permite que eu retorne outra coisa caso não haja nada no objeto, nesse caso uso uma expressão lambda para retornar minha classe de exceção
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category entity = new Category();
        entity.setName(dto.getName());
        entity = repository.save(entity);
        return new CategoryDTO(entity);

    }


    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try{
        Category entity = repository.getReferenceById(id); //Uso o getReferenceById que não toca no banco de dados, ele instancia temporariamente um obj com os dados e id, ai quando manda salvar que ele acessa o banco de dados(Sendo essencial para não ir ao banco de dados duas vezes, por isso é utilizado no update)
        entity.setName(dto.getName());
        entity = repository.save(entity); //Agora salvo
        return new CategoryDTO(entity);
        }
        catch (EntityNotFoundException e){
            throw new ResourceNotFoundException("Id not found" + id);
        }
    }


    public void delete(Long id) {
        try{
        repository.deleteById(id);
        }
        catch(EmptyResultDataAccessException e){
            throw new ResourceNotFoundException("Id not found" + id);
        }
        catch(DataIntegrityViolationException e){ //Para garantir que o usuario não exclua uma categoria que afete a integridade do sistema
            throw new DataBaseException("Integrity violation");
        }
    }
}
