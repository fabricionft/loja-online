package com.futshop.futshop.repository;

import com.futshop.futshop.model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    @Query(value = "select * from pedidos where codigo_cliente = ?", nativeQuery = true)
    public List<PedidoModel> buscarListaPorId(Long codigo);

    @Query(value = "select * from pedidos where codigo = ?", nativeQuery = true)
    public Optional<PedidoModel> buscarPorId(Long codigo);
}
