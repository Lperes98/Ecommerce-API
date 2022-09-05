package com.lojavirtual.repositorios;

import com.lojavirtual.modelos.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    public Cliente findByNomeIgnoringCase(String nome);

    @Query("from Cliente where email=?1")
    public List<Cliente> buscarClienteEmail(String email);
}
