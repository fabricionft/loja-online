package com.futshop.futshop.Controller;

import com.futshop.futshop.Model.PedidoModel;
import com.futshop.futshop.Services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    List<PedidoModel> listarPedidos(){
        return service.listarPedidos();
    }

    @GetMapping(path = "/cliente/{codigoCliente}")
    public List<PedidoModel> buscarPedidosPorIdDoCliente(@PathVariable Long codigoCliente){
        return  service.buscarListaDePedidosPorIdDoCliente(codigoCliente);
    }

    @GetMapping(path = "pedido/{codigo}")
    public PedidoModel buscarPedidoPorIdDoPedido(@PathVariable Long codigo){
        return  service.buscarPedidoPorIdDoPedido(codigo);
    }

    @PostMapping(path = "/cliente/{codigoCLiente}/formaPagamento/{formaPagamento}/quantidadeParcelas/{quantidadeParcelas}")
    public PedidoModel fazerPedido(@PathVariable Long codigoCLiente,
                                   @PathVariable String formaPagamento,
                                   @PathVariable Integer quantidadeParcelas){
        return  service.fazerPedido(codigoCLiente, formaPagamento, quantidadeParcelas);
    }

    @PutMapping(path = "/pedido/{codigo}/acao/{acao}/motivo/{motivo}")
    @PreAuthorize("hasRole('ADMIN')")
    public PedidoModel alterarStatusPedido(@PathVariable Long codigo,
                                   @PathVariable Integer acao,
                                   @PathVariable String motivo){
        return service.mudarStatusPedido(codigo, acao, motivo);
    }
}
