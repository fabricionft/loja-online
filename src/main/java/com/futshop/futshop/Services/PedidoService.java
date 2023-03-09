package com.futshop.futshop.Services;

import com.futshop.futshop.Model.*;
import com.futshop.futshop.Repository.PedidoRepository;
import com.futshop.futshop.Repository.ProdutoRepository;
import com.futshop.futshop.Repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ModelMapper modelMapper;

    private CarrinhoModelPedido converterEmCarrinhoPedido(CarrinhoModelUsuario carrinho){
        return modelMapper.map(carrinho, CarrinhoModelPedido.class);
    }

    public List<PedidoModel> listarPedidos(){
        return pedidoRepository.findAll();
    }

    public List<PedidoModel> buscarPedidosPorIdDoCliente(Long codigoCliente){
        return pedidoRepository.buscarPedidosPorID(codigoCliente);
    }

    public PedidoModel buscarPedidoPorID(Long codigo){
        return pedidoRepository.buscarPedidoPorID(codigo);
    }

    public PedidoModel fazerPedido( Long codigoCLiente, String formaPagamento, Integer quantidadeParcelas){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigoCLiente);
        PedidoModel pedido = new PedidoModel();
        Random rd = new Random();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        if(verificarEstoque(usuario.getCodigo())){
            reduzirEstoque(usuario.getCodigo());

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
            usuario.setQuantidadeItens(0);
            usuario.setValorTotalItens(0.0);
            usuarioRepository.save(usuario);

            return pedidoRepository.save(pedido);
        }
        else return null;
    }

    private void reduzirEstoque(Long codigo){
        List<ProdutoModel> produtos = produtoRepository.findAll();
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        for(CarrinhoModelUsuario item: usuario.getItens()){
            for(ProdutoModel produto: produtos){
                if(item.getCodigo().equals(produto.getCodigo()) && item.getQuantidade() <= produto.getQuantidadeEstoque()){
                    produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - item.getQuantidade());
                    produtoRepository.save(produto);
                }
            }
        }
    }
    private boolean verificarEstoque(Long codigo){
        List<ProdutoModel> produtos = produtoRepository.findAll();
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        for(CarrinhoModelUsuario item: usuario.getItens()){
            for(ProdutoModel produto: produtos){
                if(item.getQuantidade() > produto.getQuantidadeEstoque()) return false;
            }
        }
        return true;
    }

    public PedidoModel mudarStatusPedido(Long codigo, Integer acao, String motivo){
        List<ProdutoModel> produtos = produtoRepository.findAll();
        PedidoModel pedido = pedidoRepository.buscarPedidoPorID(codigo);

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
}
