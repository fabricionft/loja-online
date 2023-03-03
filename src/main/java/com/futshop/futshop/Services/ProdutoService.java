package com.futshop.futshop.Services;

import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;


    public List<ProdutoModel> listarProdutos(){
        return repository.findAll();
    }

    public List<ProdutoModel> filtrarPorTipo(@PathVariable String tipo){
        return repository.buscarPorTipo(tipo);
    }

    public List<ProdutoModel> ordenarPromocaoEmOrdemDecrescente(){
        return repository.promocaoDecrescente();
    }

    public List<ProdutoModel> ordenarValorEmOrdemCrescente(){
        return repository.valorCrescente();
    }

    public List<ProdutoModel> ordenarValorEmOrdemDecrescente(){
        return repository.valorDecrescente();
    }

    public List<ProdutoModel> buscarPorDescricao(String descricao){
        return repository.buscarPorDescricao(descricao);
    }

    public ProdutoModel buscarProdutoPorID(Long codigo){
        return repository.buscarPorID(codigo);
    }

    public ProdutoModel salvarProduto(ProdutoModel produto) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        produto.setValorComDesconto(produto.getValorBase() - produto.getValorBase() * produto.getPromocao() / 100);
        produto.setDataPostagem(formatter.format(calendar.getTime()));
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
