package com.futshop.futshop.Controller;

import com.futshop.futshop.DTO.Response.CarrinhoResponseDTO;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Services.CarrinhoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carrinhos")
public class CarrinhoController {

    @Autowired
    private CarrinhoService service;

    @Autowired
    private ModelMapper modelMapper;

    private CarrinhoResponseDTO converterEmDTO(UsuarioModel usuario){
        return modelMapper.map(usuario, CarrinhoResponseDTO.class);
    }

    @GetMapping(path = "/{codigo}")
    public ResponseEntity<?> listarItensUsuario(@PathVariable Long codigo){
        return new ResponseEntity<>(converterEmDTO(service.listarItensUsuario(codigo)), HttpStatus.OK);
    }

    @PostMapping(path = "/produto/{codigo}/usuario/{codigoUser}")
    public ResponseEntity<?> adcionarItem(@PathVariable Long codigo,
                                          @PathVariable Long codigoUser){
        return new ResponseEntity<>(converterEmDTO(service.adcionarItem(codigo, codigoUser)), HttpStatus.CREATED);
    }

    @PutMapping(path = "/produto/{codigo}/usuario/{codigoUser}/acao/{acao}")
    public ResponseEntity<?> alterarItem(@PathVariable Long codigo,
                                         @PathVariable Long codigoUser,
                                         @PathVariable Integer acao){
        return new ResponseEntity<>(converterEmDTO(service.alterarQuantidadeItem(codigo, codigoUser, acao)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/produto/{codigoProd}/usuario/{codigoUser}")
    public ResponseEntity<?> excluirItem(@PathVariable Long codigoProd,
                                         @PathVariable Long codigoUser){
        return new ResponseEntity<>(converterEmDTO(service.excluirItem(codigoProd, codigoUser)), HttpStatus.OK);
    }

    @DeleteMapping(path = "/usuario/{codigo}")
    public ResponseEntity<?> excluirItens(@PathVariable Long codigo){
        return new ResponseEntity<>(converterEmDTO(service.excluirItens(codigo)), HttpStatus.OK);
    }
}
