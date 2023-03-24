package com.futshop.futshop.Services;

import com.futshop.futshop.Exceptions.RequestException;
import com.futshop.futshop.Model.*;
import com.futshop.futshop.Repository.PedidoRepository;
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


    private CarrinhoModelPedido converterEmCarrinhoPedido(CarrinhoModelUsuario carrinho){
        return modelMapper.map(carrinho, CarrinhoModelPedido.class);
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

    public PedidoModel fazerPedido( Long codigoCLiente, String formaPagamento, Integer quantidadeParcelas){
        UsuarioModel usuario = usuarioService.isUserByCode(codigoCLiente);
        PedidoModel pedido = new PedidoModel();
        Random rd = new Random();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        if(verificarEstoque(usuario.getItens())){
            reduzirEstoque(usuario.getItens());

            //Dados pedido
            pedido.setNumero(rd.nextLong(900000000)+100000000);
            pedido.setData(formatter.format(calendar.getTime()));
            pedido.setPagamento(formaPagamento);
            pedido.setQuantidaeParcelas(quantidadeParcelas);
            pedido.setValorParcela(usuario.getValorTotalItens() / quantidadeParcelas);

            //Dados Usuários
            pedido.setCodigoCliente(usuario.getCodigo());
            pedido.setCpfCliente(usuario.getCpf());
            pedido.setNomeCliente(usuario.getNome());
            pedido.setEmail(usuario.getEmail());
            pedido.setCelular(usuario.getCelular());
            pedido.setEndereco(usuario.getCep()+", "+usuario.getEstado()+", "+usuario.getCidade() +", "+usuario.getBairro()
                    +", "+usuario.getRua()+", "+usuario.getNumero()+", "+usuario.getComplemento());

            //Dados itens
            List<CarrinhoModelPedido> lista = new ArrayList<>();
            for(CarrinhoModelUsuario item: usuario.getItens()){
                lista.add(converterEmCarrinhoPedido(item));
            }

            pedido.setItens(lista);
            pedido.setQuantidadeItens(usuario.getQuantidadeItens());
            pedido.setValor(usuario.getValorTotalItens());

            usuario.getItens().clear();
            usuarioService.atualizarUsuario(usuario);

            return pedidoRepository.save(pedido);
        }
        else return null;
    }

    private boolean verificarEstoque(List<CarrinhoModelUsuario> itens){
        for(CarrinhoModelUsuario item: itens){
            for(ProdutoModel produto: produtoService.listarProdutos()){
                if(item.getQuantidade() > produto.getQuantidadeEstoque())
                    throw new RequestException("Algum(ns) do(s) item(ns) selecionados não possuem quantidade suficiente em estoque!");
            }
        }
        return true;
    }

    private void reduzirEstoque(List<CarrinhoModelUsuario> itens){
        for(CarrinhoModelUsuario item: itens){
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
            for(CarrinhoModelPedido item: pedido.getItens()){
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
