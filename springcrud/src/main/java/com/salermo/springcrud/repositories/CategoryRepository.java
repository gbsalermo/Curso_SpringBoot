package com.salermo.springcrud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salermo.springcrud.entities.Category;

//Classe da camada de acesso a dados
@Repository //Os objts aqui passam a ser gerenciados pelos spring e sao chamados pelo Autowired                                                    //Tipo da entidade e do id                  
public interface CategoryRepository extends JpaRepository<Category, Long>  {

}
