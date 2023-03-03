package com.futshop.futshop.Services;

import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;


    public List<ProdutoModel> listarProdutos(){
        return repository.findAll();
    }

    public List<ProdutoModel> listarPromocoes(){
        return repository.buscarPromocoes();
    }

    public List<ProdutoModel> listarNovidades(){
        return repository.buscarNovidades();
    }

    public List<ProdutoModel> buscarPorDescricao(String descricao){
        return repository.buscarPorDescricao(descricao);
    }

    public ProdutoModel buscarProdutoPorID(Long codigo){
        return repository.buscarPorID(codigo);
    }

    public ProdutoModel salvarProduto(ProdutoModel produto) {
        produto.setValorComDesconto(produto.getValorBase() - produto.getValorBase() * produto.getPromocao() / 100);
        return repository.save(produto);
    }

    public String deletarProdutoPorID(Long codigo){
        repository.deleteById(codigo);
        return "Produto deletado com sucesso!!";
    }

    public String deletarTodosProdutos(){
        repository.deleteAll();
        return "Todos produtos deletados com sucesso!!";
    }
}
