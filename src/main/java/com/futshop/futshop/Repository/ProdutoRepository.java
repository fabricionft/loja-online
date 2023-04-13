package com.futshop.futshop.repository;

import com.futshop.futshop.model.ProdutoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository <ProdutoModel, Long> {

    @Query(value = "select * from produto where codigo = ?", nativeQuery = true)
    public Optional<ProdutoModel> buscarPorID(Long codigo);

    //Pesquisar
    @Query(value = "select * from produto  where descricao like %?%", nativeQuery = true)
    public List<ProdutoModel> buscarPorDescricao(String descricao);

    //Filtrar
    @Query(value = "select * from produto where tipo = ?", nativeQuery = true)
    public List<ProdutoModel> buscarPorTipo(String tipo);

    //Ordenar
    @Query(value = "select * from produto order by promocao desc", nativeQuery = true)
    public List<ProdutoModel> promocaoDecrescente();

    @Query(value = "select * from produto order by valor_com_desconto asc", nativeQuery = true)
    public List<ProdutoModel> valorCrescente();

    @Query(value = "select * from produto order by valor_com_desconto desc", nativeQuery = true)
    public List<ProdutoModel> valorDecrescente();
}
