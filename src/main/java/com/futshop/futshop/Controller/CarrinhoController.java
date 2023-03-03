package com.futshop.futshop.Controller;

import com.futshop.futshop.Model.CarrinhoModel;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import com.futshop.futshop.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


    @PostMapping(path = "/produto/{codigo}/usuario/{codigoCli}")
    public void adcionarItem(@PathVariable Long codigo,
                             @PathVariable Long codigoCli){
        CarrinhoModel itens = new CarrinhoModel();
        ProdutoModel produto = produtoRepository.buscarPorID(codigo);
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigoCli);

        if(produto.getQuantidadeEstoque() > 0){
            int cont = 0;
            for(CarrinhoModel item: usuario.getItens()){
                if(produto.getCodigo() == item.getCodigo()){
                    soma(produto, item);
                    cont++;
                }
            }

            if(cont == 0){
                itens.setCodigo(produto.getCodigo());
                itens.setDescricaoProduto(produto.getDescricao());
                itens.setTamanho(produto.getTamanho());
                itens.setQuantidade(1);
                itens.setPrecoUnitario(produto.getValor());
                itens.setPrecoFinal(itens.getPrecoUnitario());

                usuario.setItens(itens);
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - 1);
            }
        }

        produtoRepository.save(produto);
        usuarioRepository.save(usuario);
    }

    @PutMapping(path = "/produto/{codigo}/usuario/{codigoCli}/acao/{acao}")
    public List<CarrinhoModel> alterarItem(@PathVariable Long codigo,
                                           @PathVariable Long codigoCli,
                                           @PathVariable String acao){
        ProdutoModel produto = produtoRepository.buscarPorID(codigo);
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigoCli);

        for(CarrinhoModel item: usuario.getItens()){
            if(item.getCodigo() == codigo && acao.equals("+") && produto.getQuantidadeEstoque() > 0) {
                soma(produto, item);
                break;
            }
            if(item.getCodigo() == codigo && acao.equals("-")){
                subtracao(produto, item, codigoCli);
                break;
            }
        }

        usuarioRepository.save(usuario);
        produtoRepository.save(produto);
        return usuario.getItens();
    }

    @DeleteMapping(path = "/produto/{codigoProd}/usuario/{codigoCli}")
    public UsuarioModel excluirItem(@PathVariable Long codigoProd,
                                    @PathVariable Long codigoCli){
        ProdutoModel produto = produtoRepository.buscarPorID(codigoProd);
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigoCli);

        for(int i = 0; i <= usuario.getItens().size(); i++){
            if(usuario.getItens().get(i).getCodigo() == codigoProd){
                produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + usuario.getItens().get(i).getQuantidade());
                usuario.getItens().remove(i);
                break;
            }
        }

        produtoRepository.save(produto);
        return usuarioRepository.save(usuario);
    }

    @DeleteMapping(path = "/usuario/{codigo}")
    public void excluirItens(@PathVariable Long codigo){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        for(CarrinhoModel produto: usuario.getItens()){
            ProdutoModel prod = produtoRepository.buscarPorID(produto.getCodigo());
            prod.setQuantidadeEstoque(prod.getQuantidadeEstoque() + produto.getQuantidade());
            produtoRepository.save(prod);
        }

        usuario.getItens().clear();
        usuarioRepository.save(usuario);
    }

    public void soma(ProdutoModel produto, CarrinhoModel item){
        item.setQuantidade(item.getQuantidade() + 1);
        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - 1);
        item.setPrecoFinal(item.getPrecoUnitario() * item.getQuantidade());
    }

    public void subtracao(ProdutoModel produto, CarrinhoModel item, Long codigoCli){
        if(item.getQuantidade() == 1) excluirItem(produto.getCodigo(), codigoCli);
        else{
            item.setQuantidade(item.getQuantidade() - 1);
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + 1);
            item.setPrecoFinal(item.getPrecoUnitario() * item.getQuantidade());
        }
    }
}
