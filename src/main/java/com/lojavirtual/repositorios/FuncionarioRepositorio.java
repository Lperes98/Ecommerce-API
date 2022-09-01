package com.lojavirtual.repositorios;

import com.lojavirtual.modelos.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {

    public Funcionario findByNomeIgnoringCase(String nome);

}
