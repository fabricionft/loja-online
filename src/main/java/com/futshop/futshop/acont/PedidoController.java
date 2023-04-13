package com.futshop.futshop.acont;

import com.futshop.futshop.aserv.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> listarPedidos(){
        return new ResponseEntity<>(pedidoService.listarPedidos(), HttpStatus.OK);
    }

    @GetMapping(path = "/cliente/{codigoCliente}")
    public ResponseEntity<?> buscarPedidosPorIdDoCliente(@PathVariable Long codigoCliente){
        return new ResponseEntity<>(pedidoService.buscarListaDePedidosPorIdDoCliente(codigoCliente), HttpStatus.OK);
    }

    @GetMapping(path = "pedido/{codigo}")
    public ResponseEntity<?> buscarPedidoPorIdDoPedido(@PathVariable Long codigo){
        return new ResponseEntity<>(pedidoService.buscarPedidoPorIdDoPedido(codigo), HttpStatus.OK);
    }

    @PostMapping(path = "/cliente/{codigoCLiente}/formaPagamento/{formaPagamento}/quantidadeParcelas/{quantidadeParcelas}")
    public ResponseEntity<?> fazerPedido(@PathVariable Long codigoCLiente,
                                         @PathVariable String formaPagamento,
                                         @PathVariable Integer quantidadeParcelas){
        return new ResponseEntity<>(pedidoService.fazerPedido(codigoCLiente, formaPagamento, quantidadeParcelas), HttpStatus.CREATED);
    }

    @PutMapping(path = "/pedido/{codigo}/acao/{acao}/motivo/{motivo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> alterarStatusPedido(@PathVariable Long codigo,
                                                 @PathVariable Integer acao,
                                                 @PathVariable String motivo){
        return new ResponseEntity<>(pedidoService.mudarStatusPedido(codigo, acao, motivo), HttpStatus.OK);
    }
}
