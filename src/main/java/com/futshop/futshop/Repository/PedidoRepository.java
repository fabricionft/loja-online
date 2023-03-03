package com.futshop.futshop.Repository;

import com.futshop.futshop.Model.PedidoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoModel, Long> {

    @Query(value = "select * from pedidos where codigo_cliente = ?", nativeQuery = true)
    public List<PedidoModel> buscarPedidosPorID(Long codigo);

    @Query(value = "select * from pedidos where codigo = ?", nativeQuery = true)
    public PedidoModel buscarPedidoPorID(Long codigo);
}
