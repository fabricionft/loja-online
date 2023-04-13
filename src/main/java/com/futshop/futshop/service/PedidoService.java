package com.futshop.futshop.service;

import com.futshop.futshop.exception.RequestException;
import com.futshop.futshop.model.*;
import com.futshop.futshop.repository.PedidoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ModelMapper modelMapper;


    private CarrinhoPedidoModel converterEmCarrinhoPedido(CarrinhoUsuarioModel carrinho){
        return modelMapper.map(carrinho, CarrinhoPedidoModel.class);
    }

    public List<PedidoModel> listarPedidos(){
        return pedidoRepository.findAll();
    }

    public List<PedidoModel> buscarListaDePedidosPorIdDoCliente(Long codigoCliente){
        UsuarioModel usuario = usuarioService.isUserByCode(codigoCliente);
        return pedidoRepository.buscarListaPorId(usuario.getCodigo());
    }

    public PedidoModel buscarPedidoPorIdDoPedido(Long codigo){
        return isOrderByCode(codigo);
    }

    public PedidoModel fazerPedido(Long codigoCLiente, String formaPagamento, Integer quantidadeParcelas){
        UsuarioModel usuario = usuarioService.isUserByCode(codigoCLiente);
        Random rd = new Random();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        if(verificarEstoque(usuario.getItens())){
            String endereco = usuario.getCep()+", "+usuario.getEstado()+", "+usuario.getCidade() +", "+usuario.getBairro()+", "+usuario.getRua()+", "+usuario.getNumero()+", "+usuario.getComplemento();

            List<CarrinhoPedidoModel> listaItens = new ArrayList<>();
            for(CarrinhoUsuarioModel item: usuario.getItens()){
                listaItens.add(converterEmCarrinhoPedido(item));
            }

            PedidoModel pedido = new PedidoModel(
         null,
                usuario.getCodigo(),
         rd.nextLong(900000000)+100000000,
                formatter.format(calendar.getTime()),
          "Aguardando confirmação",
    null,
                formaPagamento,
                quantidadeParcelas,
                (usuario.getValorTotalItens() / quantidadeParcelas),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getCelular(),
                usuario.getEmail(),
                endereco,
                listaItens,
                usuario.getQuantidadeItens(),
                usuario.getValorTotalItens()
            );

            reduzirEstoque(usuario.getItens());
            usuario.getItens().clear();
            usuarioService.atualizarUsuario(usuario);
            return pedidoRepository.save(pedido);
        }
        else return null;
    }

    private boolean verificarEstoque(List<CarrinhoUsuarioModel> itens){
        for(CarrinhoUsuarioModel item: itens){
            for(ProdutoModel produto: produtoService.listarProdutos()){
                if(item.getQuantidade() > produto.getQuantidadeEstoque())
                    throw new RequestException("Algum(ns) do(s) item(ns) selecionados não possuem quantidade suficiente em estoque!");
            }
        }
        return true;
    }

    private void reduzirEstoque(List<CarrinhoUsuarioModel> itens){
        for(CarrinhoUsuarioModel item: itens){
            for(ProdutoModel produto: produtoService.listarProdutos()){
                if(item.getCodigo().equals(produto.getCodigo()) && item.getQuantidade() <= produto.getQuantidadeEstoque()){
                    produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.getQuantidade());
                    produtoService.atualizarProduto(produto);
                }
            }
        }
    }

    public PedidoModel mudarStatusPedido(Long codigo, Integer acao, String motivo){
        List<ProdutoModel> produtos = produtoService.listarProdutos();
        PedidoModel pedido = isOrderByCode(codigo);

        if(acao.equals(1)) pedido.setStatus("Pedido confirmado");
        if(acao.equals(2)) {
            pedido.setMotivoRejeicao(motivo);
            pedido.setStatus("Pedido negado");
            for(CarrinhoPedidoModel item: pedido.getItens()){
                for(ProdutoModel produto: produtos) {
                    if(item.getCodigo().equals(produto.getCodigo())){
                        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() + item.getQuantidade());
                    }
                }
            }
        }
        return pedidoRepository.save(pedido);
    }

    //Validações
    public PedidoModel isOrderByCode(Long codigo){
        Optional<PedidoModel> pedido = pedidoRepository.buscarPorId(codigo);
        if(pedido.isEmpty()) throw new RequestException("Pedido inexistente");
        else return  pedido.get();
    }
}
