package com.lojavirtual.repositorios;

import com.lojavirtual.modelos.Cliente;
import com.lojavirtual.modelos.Papel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PapelRepositorio extends JpaRepository<Papel, Long> {

}
