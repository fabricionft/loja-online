package com.futshop.futshop.Controller;

import com.futshop.futshop.Model.CarrinhoModel;
import com.futshop.futshop.Model.TesteModel;
import com.futshop.futshop.Repository.TesteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/teste")
@RestController
public class TesteController {

    @Autowired
    private TesteRepository repository;

    @PostMapping
    public TesteModel salvar(){
        CarrinhoModel itens = new CarrinhoModel();
        TesteModel teste = new TesteModel();

        itens.setCodigo(1L);
        itens.setDescricaoProduto("produto.getDescricao()");
        itens.setTamanho("produto.getTamanho()");
        itens.setQuantidade(1);
        itens.setPrecoUnitario(10.0);
        itens.setPrecoFinal(10.0);

        teste.setItens(itens);

        return repository.save(teste);
    }
}
