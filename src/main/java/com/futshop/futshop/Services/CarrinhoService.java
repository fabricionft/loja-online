package com.futshop.futshop.Services;

import com.futshop.futshop.Model.CarrinhoModelUsuario;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Model.UsuarioModel;
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

    public UsuarioModel adcionarItem(Long codigoProd, Long codigoUser){
        ProdutoModel produto = produtoService.isProductByCode(codigoProd);
        UsuarioModel usuario = usuarioService.isUserByCode(codigoUser);
        CarrinhoModelUsuario itens = new CarrinhoModelUsuario();

        if(produto.getQuantidadeEstoque() > 0){
            int cont = 0;
            for(CarrinhoModelUsuario item: usuario.getItens()){
                if(item.getCodigo().equals(produto.getCodigo())){
                    soma(item, produto);
                    cont++;
                }
            }

            if(cont == 0){
                itens.setCodigo(produto.getCodigo());
                itens.setImagem(produto.getImagem());
                itens.setDescricaoProduto(produto.getDescricao());
                itens.setTamanho(produto.getTamanho());
                itens.setQuantidade(1);
                itens.setPrecoUnitario(produto.getValorComDesconto());
                itens.setPrecoFinal(itens.getPrecoUnitario());

                usuario.setItens(itens);
            }
        }

        produtoService.atualizarProduto(produto);
        return usuarioService.atualizarUsuario(usuario);
    }

    public UsuarioModel alterarQuantidadeItem(Long codigoProd, Long codigoUser, Integer acao){
        ProdutoModel produto = produtoService.isProductByCode(codigoProd);
        UsuarioModel usuario = usuarioService.isUserByCode(codigoUser);

        for(CarrinhoModelUsuario item: usuario.getItens()){
            if(item.getCodigo().equals(codigoProd) && acao == 1 && produto.getQuantidadeEstoque() > 0) {
                soma(item, produto);
                break;
            }
            if(item.getCodigo().equals(codigoProd) && acao == 2){
                subtracao(produto, item, codigoUser);
                break;
            }
        }

        produtoService.atualizarProduto(produto);
        return usuarioService.atualizarUsuario(usuario);
    }

    public UsuarioModel excluirItem(Long codigoProd, Long codigoUser){
        ProdutoModel produto = produtoService.isProductByCode(codigoProd);
        UsuarioModel usuario = usuarioService.isUserByCode(codigoUser);


        for(int i = 0; i <= usuario.getItens().size(); i++){
            if(usuario.getItens().get(i).getCodigo().equals(codigoProd)){
                usuario.getItens().remove(i);
                break;
            }
        }

        produtoService.atualizarProduto(produto);
        return usuarioService.atualizarUsuario(usuario);
    }

    public UsuarioModel excluirItens(Long codigo){
        UsuarioModel usuario = usuarioService.isUserByCode(codigo);
        usuario.getItens().clear();

        return usuarioService.atualizarUsuario(usuario);
    }

    //Soma quantidade de cada item
    public void soma(CarrinhoModelUsuario item, ProdutoModel produto){
        if(produto.getQuantidadeEstoque() > item.getQuantidade()){
            item.setQuantidade(item.getQuantidade() + 1);
            item.setPrecoFinal(item.getPrecoUnitario() * item.getQuantidade());
        }
    }

    //Subtrai quantidade de cada item
    public void subtracao(ProdutoModel produto, CarrinhoModelUsuario item, Long codigoCli){
        if(item.getQuantidade() == 1) excluirItem(produto.getCodigo(), codigoCli);
        else{
            item.setQuantidade(item.getQuantidade() - 1);
            item.setPrecoFinal(item.getPrecoUnitario() * item.getQuantidade());
        }
    }
}
