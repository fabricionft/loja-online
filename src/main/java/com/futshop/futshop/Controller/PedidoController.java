package com.futshop.futshop.Controller;

import com.futshop.futshop.Model.PedidoModel;
import com.futshop.futshop.Services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping
    List<PedidoModel> listar(){
        return service.listarPedidos();
    }

    @GetMapping(path = "cliente/{codigoCliente}")
    public List<PedidoModel> buscarPedidosPorID(@PathVariable Long codigoCliente){
        return  service.buscarPedidosPorIdDoCliente(codigoCliente);
    }

    @GetMapping(path = "pedido/{codigo}")
    public PedidoModel buscarPorID(@PathVariable Long codigo){
        return  service.buscarPedidoPorID(codigo);
    }

    @PostMapping(path = "/codigo/{codigoCLiente}/formaPagamento/{formaPagamento}/quantidadeParcelas/{quantidadeParcelas}")
    public PedidoModel salvar(@PathVariable Long codigoCLiente,
                              @PathVariable String formaPagamento,
                              @PathVariable Integer quantidadeParcelas){
        return  service.salvarPedido(codigoCLiente, formaPagamento, quantidadeParcelas);
    }
}
