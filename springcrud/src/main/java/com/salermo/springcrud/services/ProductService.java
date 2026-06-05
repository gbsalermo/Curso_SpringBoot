package com.salermo.springcrud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.salermo.springcrud.dto.ProductDTO;
import com.salermo.springcrud.entities.Product;
import com.salermo.springcrud.repositories.ProductRepository;
import com.salermo.springcrud.services.exceptions.DataBaseException;
import com.salermo.springcrud.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;



@Service //Responsavel por registrar essa classe como um componente que vai participar do sistema de injeção de dependencia automatizado do spring
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(PageRequest pageRequest){
        Page<Product> list = repository.findAll(pageRequest);                                                  
        return list.map(x -> new ProductDTO(x));

    }


    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id); //O Optional é uma abordagem para evitar trabalhar com valor nulo
        Product entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found")); //O orElseThrow permite que eu retorne outra coisa caso não haja nada no objeto, nesse caso uso uma expressão lambda para retornar minha classe de exceção
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        //entity.setName(dto.getName());
        entity = repository.save(entity);
        return new ProductDTO(entity);

    }


    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try{
        Product entity = repository.getReferenceById(id); //Uso o getReferenceById que não toca no banco de dados, ele instancia temporariamente um obj com os dados e id, ai quando manda salvar que ele acessa o banco de dados(Sendo essencial para não ir ao banco de dados duas vezes, por isso é utilizado no update)
        //entity.setName(dto.getName());
        entity = repository.save(entity); //Agora salvo
        return new ProductDTO(entity);
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
