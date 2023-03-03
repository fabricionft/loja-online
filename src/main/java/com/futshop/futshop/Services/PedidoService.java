package com.futshop.futshop.Services;

import com.futshop.futshop.Model.CarrinhoModelUsuario;
import com.futshop.futshop.Model.PedidoModel;
import com.futshop.futshop.Model.CarrinhoModelPedido;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Repository.PedidoRepository;
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

    public PedidoModel salvarPedido( Long codigoCLiente, String formaPagamento, Integer quantidadeParcelas){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigoCLiente);
        PedidoModel pedido = new PedidoModel();
        Random rd = new Random();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        //Dados pedido
        pedido.setNumero(rd.nextLong(900000000)+100000000);
        pedido.setData(formatter.format(calendar.getTime()));
        pedido.setPagamento(formaPagamento);
        pedido.setQuantidaeParcelas(quantidadeParcelas);
        pedido.setValorParcela(usuario.getValorTotalItens() / quantidadeParcelas);

        //Dados Usuários
        pedido.setCodigoCliente(usuario.getCodigo());
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
}
