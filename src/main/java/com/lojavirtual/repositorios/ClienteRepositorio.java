package com.lojavirtual.repositorios;

import com.lojavirtual.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    public Cliente findByNomeIgnoringCase(String nome);
}
