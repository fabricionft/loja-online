package com.futshop.futshop.Controller;

import com.futshop.futshop.DTO.Request.AdminLoginDTO;
import com.futshop.futshop.DTO.Request.UsuarioRequestDTO;
import com.futshop.futshop.DTO.Response.UsuarioResponseDTO;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Services.UsuarioService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.AlreadyBoundException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/usuario")
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
    public List<UsuarioResponseDTO> listarUsuarios(){
        return converterListaEmDTO(service.listarUsuarios());
    }

    @GetMapping(path = "/{codigo}")
    public ResponseEntity<?> buscarUsuarioPorID(@PathVariable Long codigo){
        return new ResponseEntity<>(converterEmDTO(service.buscarUsuarioPorID(codigo)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> salvarUsuario(@RequestBody UsuarioRequestDTO usuarioDTO) throws AlreadyBoundException {
        UsuarioModel usuario = service.salvarUsuario(converterEmEntidade(usuarioDTO));
        return new ResponseEntity<>(converterEmDTO(usuario), HttpStatus.CREATED);
    }

    //Parei aqui 11/03
    @GetMapping(path = "/validar/{email}/{senha}")
    public ResponseEntity<Boolean> validarSenha(@PathVariable String email,
                                                @PathVariable String senha){
        return service.validarSenha(email, senha);
    }

    @PostMapping(path = "/email/{email}/senha/{senha}")
    public ResponseEntity<?> fazerLogin(@PathVariable String email,
                                        @PathVariable String senha){
        UsuarioModel usuario = service.fazerLogin(email, senha);
        HttpStatus status = (usuario == null) ? HttpStatus.UNAUTHORIZED : HttpStatus.OK;
        return new ResponseEntity<>(usuario, status);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> fazerLogincomoAdministrador(@RequestBody AdminLoginDTO admin){
        String token = service.fazerLogincomoAdministrador(admin);
        HttpStatus status = (token == null) ? HttpStatus.UNAUTHORIZED : HttpStatus.OK;
        return new ResponseEntity<>((token != null) ? token : "Este usuário não existe", status);
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

    @DeleteMapping
    public ResponseEntity<?> excluirTodosUsuarios(){
        return  new ResponseEntity<>(service.excluirTodosUsuarios(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/usuario/{codigo}")
    public ResponseEntity<?> excluirUsuario(@PathVariable Long codigo){
       return new ResponseEntity<>(service.excluirUsuarioPorID(codigo), HttpStatus.OK);
    }
}