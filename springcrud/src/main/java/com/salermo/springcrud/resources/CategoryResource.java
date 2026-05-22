package com.salermo.springcrud.resources;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.salermo.springcrud.dto.CategoryDTO;
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
    public ResponseEntity<Page<CategoryDTO>> findAll(
    		@RequestParam(value = "page", defaultValue = "0") Integer page,
    		@RequestParam(value = "linesPerPage", defaultValue = "12") Integer linesPerPage,
    		@RequestParam(value = "direction", defaultValue = "DESC") String direction,
    		@RequestParam(value = "orderBy", defaultValue = "createdAt") String orderBy
    		
    		){
    	
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
    	
        Page<CategoryDTO> list = service.findAllPaged(pageRequest); //Chamo a list do service como é o seguimento da camada
        return ResponseEntity.ok().body(list) ;
    }

    //Bucar uma categoria por id
    //Adicionei a anotação @PathVariable que serve para precompilar a rota e casar com o parametro recebido(no caso id)
       @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id){
        CategoryDTO dto = service.findById(id); //Chamo a list do service como é o seguimento da camada
        return ResponseEntity.ok().body(dto);
    }

    /*TESTE
    @GetMapping("/")
    public String test(){
        return "API FUNCIONANDO";
    }*/

    @PostMapping //No padrão Rest quando vamos inserir novos recursos usamos o post    
    public ResponseEntity<CategoryDTO> insert(@RequestBody CategoryDTO dto){
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);  //Uso o created uri para retornar o 201 created
    }

     @PutMapping(value = "/{id}") //No padrão Rest quando vamos atualizar novos recursos usamos o put    
    public ResponseEntity<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto){
        dto = service.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @DeleteMapping(value = "/{id}") //No padrão Rest quando vamos atualizar novos recursos usamos o put    
    public ResponseEntity<Void> delete(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    }
    
