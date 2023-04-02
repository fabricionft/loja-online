package com.futshop.futshop.Controller;

import com.futshop.futshop.DTO.Request.UsuarioRequestDTO;
import com.futshop.futshop.DTO.Response.UsuarioResponseDTO;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioModel converterEmEntidade(UsuarioRequestDTO usuarioDTO){
        return modelMapper.map(usuarioDTO, UsuarioModel.class);
    }

    public UsuarioResponseDTO converterEmDTO(UsuarioModel usuario){
        return modelMapper.map(usuario, UsuarioResponseDTO.class);
    }

    public List<UsuarioResponseDTO> converterListaEmDTO(List<UsuarioModel> listaUsuarios){
        List<UsuarioResponseDTO> listaUsuarioDTO = new ArrayList<>();
        for(UsuarioModel usuario: listaUsuarios){
            listaUsuarioDTO.add(converterEmDTO(usuario));
        }
        return listaUsuarioDTO;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<UsuarioResponseDTO> listarUsuarios(){
        return converterListaEmDTO(service.listarUsuarios());
    }

    @GetMapping(path = "/{email}")
    public ResponseEntity<?> buscarUsuarioPorEmail(@PathVariable String email){
        return new ResponseEntity<>(converterEmDTO(service.buscarUsuarioPorEmail(email)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> salvarUsuario(@RequestBody UsuarioRequestDTO usuarioDTO){
        UsuarioModel usuario = service.salvarUsuario(converterEmEntidade(usuarioDTO));
        return new ResponseEntity<>(converterEmDTO(usuario), HttpStatus.CREATED);
    }

    @PostMapping(path = "/email/{email}/senha/{senha}")
    public ResponseEntity<?> fazerLogin(@PathVariable String email,
                                        @PathVariable String senha){
        return  new ResponseEntity<>(service.fazerLogin(email, senha), HttpStatus.OK);
    }

    @PutMapping(path = "/tipoUsuario/{codigo}/{senha}")
    public ResponseEntity<?> alterarTipodoUsuario(@PathVariable Long codigo,
                                                  @PathVariable String senha){
        return new ResponseEntity<>(service.alterarTipoDeUsuario(codigo, senha), HttpStatus.OK);
    }

    @PutMapping(path = "/{codigo}")
    public ResponseEntity<?> alterarEndereco(@PathVariable Long codigo,
                                             @RequestBody UsuarioRequestDTO endereco){
        UsuarioModel usuario = service.alterarEndereco(codigo, converterEmEntidade(endereco));
        return new ResponseEntity<>(converterEmDTO(usuario), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{codigo}/senhaAtual/{senhaAtual}/senhaNova/{senhaNova}")
    public ResponseEntity<?> alterarSenha(@PathVariable Long codigo,
                                          @PathVariable String senhaAtual,
                                          @PathVariable String senhaNova){
        return  new ResponseEntity<>(service.alterarSenha(codigo, senhaAtual, senhaNova), HttpStatus.OK);
    }


    @DeleteMapping(path = "/usuario/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> excluirUsuario(@PathVariable Long codigo){
       return new ResponseEntity<>(service.excluirUsuarioPorID(codigo), HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> excluirTodosUsuarios(){
        return  new ResponseEntity<>(service.excluirTodosUsuarios(), HttpStatus.OK);
    }
}