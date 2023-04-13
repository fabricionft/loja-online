package com.futshop.futshop.acont;

import com.futshop.futshop.adt.request.EnderecoRequestDTO;
import com.futshop.futshop.adt.request.UsuarioRequestDTO;
import com.futshop.futshop.adt.response.LoginResponseDTO;
import com.futshop.futshop.adt.response.UsuarioResponseDTO;
import com.futshop.futshop.amo.UsuarioModel;
import com.futshop.futshop.aserv.TokenService;
import com.futshop.futshop.aserv.UsuarioService;
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
    private UsuarioService usuarioService;

    @Autowired
    private TokenService tokenService;

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
    public ResponseEntity<?> listarUsuarios(){
        return new ResponseEntity<>(converterListaEmDTO(usuarioService.listarUsuarios()), HttpStatus.OK);
    }

    @GetMapping(path = "/{email}")
    public ResponseEntity<?> buscarUsuarioPorEmail(@PathVariable String email){
        return new ResponseEntity<>(converterEmDTO(usuarioService.buscarUsuarioPorEmail(email)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> salvarUsuario(@RequestBody UsuarioRequestDTO usuarioRequest){
        UsuarioModel usuario = usuarioService.salvarUsuario(converterEmEntidade(usuarioRequest));
        return new ResponseEntity<>(converterEmDTO(usuario), HttpStatus.CREATED);
    }

    @PostMapping(path = "/email/{email}/senha/{senha}")
    public ResponseEntity<?> fazerLogin(@PathVariable String email,
                                        @PathVariable String senha){
        UsuarioModel usuario = usuarioService.fazerLogin(email, senha);
        LoginResponseDTO loginResponse = modelMapper.map(usuario, LoginResponseDTO.class);
        loginResponse.setToken(tokenService.gerarToken(usuario));
        return  new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }

    @PutMapping(path = "/tipoUsuario/{codigo}/{senha}")
    public ResponseEntity<?> alterarTipodoUsuario(@PathVariable Long codigo,
                                                  @PathVariable String senha){
        return new ResponseEntity<>(converterEmDTO(usuarioService.alterarTipoDeUsuario(codigo, senha)), HttpStatus.OK);
    }

    @PutMapping(path = "/{codigo}")
    public ResponseEntity<?> alterarEndereco(@PathVariable Long codigo,
                                             @RequestBody EnderecoRequestDTO endereco){
        UsuarioModel usuario = usuarioService.alterarEndereco(codigo, endereco);
        return new ResponseEntity<>(converterEmDTO(usuario), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{codigo}/senhaAtual/{senhaAtual}/senhaNova/{senhaNova}")
    public ResponseEntity<?> alterarSenha(@PathVariable Long codigo,
                                          @PathVariable String senhaAtual,
                                          @PathVariable String senhaNova){
        return  new ResponseEntity<>(usuarioService.alterarSenha(codigo, senhaAtual, senhaNova), HttpStatus.OK);
    }

    @DeleteMapping(path = "/usuario/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> excluirUsuario(@PathVariable Long codigo){
       return new ResponseEntity<>(usuarioService.excluirUsuarioPorID(codigo), HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> excluirTodosUsuarios(){
        return  new ResponseEntity<>(usuarioService.excluirTodosUsuarios(), HttpStatus.OK);
    }
}