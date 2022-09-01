package com.lojavirtual.repositorios;

import com.lojavirtual.modelos.Compra;
import com.lojavirtual.modelos.ItensCompra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItensCompraRepositorio extends JpaRepository<ItensCompra, Long> {
    
}
