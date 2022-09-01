package com.lojavirtual.repositorios;

import com.lojavirtual.modelos.Produtos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProdutosRepositorio extends JpaRepository<Produtos, Long> {

    public Produtos findByNomeIgnoringCase(String nome);


}
