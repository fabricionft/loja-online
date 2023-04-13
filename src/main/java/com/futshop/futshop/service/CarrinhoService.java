package com.futshop.futshop.service;

import com.futshop.futshop.exception.RequestException;
import com.futshop.futshop.model.CarrinhoUsuarioModel;
import com.futshop.futshop.model.ProdutoModel;
import com.futshop.futshop.model.UsuarioModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarrinhoService {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoService produtoService;


    public UsuarioModel listarItensUsuario(Long codigo) {
        return usuarioService.isUserByCode(codigo);
    }

    public CarrinhoUsuarioModel buscarItem(Long codigoProd, UsuarioModel usuario){
        for(CarrinhoUsuarioModel item: usuario.getItens())
            if(item.getCodigo().equals(codigoProd)) return item;
        return  new CarrinhoUsuarioModel(0);//Construtor personalizado onde passo apenas a quantidade
    }

    public UsuarioModel adcionarItem(Long codigoProd, Long codigoUser){
        ProdutoModel produto = produtoService.isProductByCode(codigoProd);
        UsuarioModel usuario = usuarioService.isUserByCode(codigoUser);
        CarrinhoUsuarioModel item = buscarItem(codigoProd, usuario);

        if(item.getQuantidade() > 0) soma(item, produto);
        else{
            CarrinhoUsuarioModel novoItem = new CarrinhoUsuarioModel(
                produto.getCodigo(),
                produto.getImagem(),
                produto.getDescricao(),
                1,
                produto.getTamanho(),
                produto.getValorComDesconto(),
                produto.getValorComDesconto()
            );
            usuario.setItens(novoItem);
        }

        produtoService.atualizarProduto(produto);
        return usuarioService.atualizarUsuario(usuario);
    }

    public UsuarioModel alterarQuantidadeItem(Long codigoProd, Long codigoUser, Integer acao){
        ProdutoModel produto = produtoService.isProductByCode(codigoProd);
        UsuarioModel usuario = usuarioService.isUserByCode(codigoUser);
        CarrinhoUsuarioModel item = buscarItem(codigoProd, usuario);

        if(acao.equals(1)) soma(item, produto);
        else subtracao(produto, item, codigoUser);

        return usuarioService.atualizarUsuario(usuario);
    }

    public UsuarioModel excluirItem(Long codigoProd, Long codigoUser){
        UsuarioModel usuario = usuarioService.isUserByCode(codigoUser);
        CarrinhoUsuarioModel item = buscarItem(codigoProd, usuario);

        usuario.getItens().remove(item);

        return usuarioService.atualizarUsuario(usuario);
    }

    public UsuarioModel excluirItens(Long codigo){
        UsuarioModel usuario = usuarioService.isUserByCode(codigo);
        usuario.getItens().clear();
        return usuarioService.atualizarUsuario(usuario);
    }

    //Soma quantidade de cada item
    public void soma(CarrinhoUsuarioModel item, ProdutoModel produto){
        if(produto.getQuantidadeEstoque() > item.getQuantidade()){
            item.setQuantidade(item.getQuantidade() + 1);
            item.setPrecoFinal(item.getPrecoUnitario() * item.getQuantidade());
        }else throw  new RequestException("O produto selecionado não possui mais unidades em estoque!");
    }

    //Subtrai quantidade de cada item
    public void subtracao(ProdutoModel produto, CarrinhoUsuarioModel item, Long codigoCli){
        if(item.getQuantidade().equals(1)) excluirItem(produto.getCodigo(), codigoCli);
        else{
            item.setQuantidade(item.getQuantidade() - 1);
            item.setPrecoFinal(item.getPrecoUnitario() * item.getQuantidade());
        }
    }
}
