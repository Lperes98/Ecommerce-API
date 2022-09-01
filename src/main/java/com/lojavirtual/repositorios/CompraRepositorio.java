package com.lojavirtual.repositorios;

import com.lojavirtual.modelos.Cliente;
import com.lojavirtual.modelos.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepositorio extends JpaRepository<Compra, Long> {

}
