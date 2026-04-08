package com.salermo.springcrud.entities;

import java.io.Serializable;

public class Category implements Serializable {

    //atributos

    private static final long serialVersionUID = 1L; //isso serve para converter o objeto de java em bytes, para poder passar em rede por exemplo

    private Long id;
    private String name;

    //Construtor
    public Category(){
    }

    public Category(Long id, String name){
        this.id = id;
        this.name = name;
    }

    //getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    //Aqui ele vai comparar apenas o id
        @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Category other = (Category) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}


