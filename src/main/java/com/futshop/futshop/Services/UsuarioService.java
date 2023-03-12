package com.futshop.futshop.Services;

import com.futshop.futshop.DTO.Request.AdminLoginDTO;
import com.futshop.futshop.Model.*;
import com.futshop.futshop.Repository.PedidoRepository;
import com.futshop.futshop.Repository.ProdutoRepository;
import com.futshop.futshop.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    TokenService tokenService;

    public UsuarioService() {
    }

    public List<UsuarioModel> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public UsuarioModel buscarUsuarioPorID(Long codigo){
        return usuarioRepository.buscarPorID(codigo);
    }

    public UsuarioModel salvarUsuario(UsuarioModel usuario) throws AlreadyBoundException {
        for(UsuarioModel user: usuarioRepository.findAll()){
            if(user.getEmail().equals(usuario.getEmail())){
                throw  new AlreadyBoundException();
            }
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public ResponseEntity<UsuarioModel> fazerLogin(String email, String senha){
        if(validarSenha(email, senha).getBody()){
            UsuarioModel usuario = usuarioRepository.buscarPorLogin(email);
            return  new ResponseEntity<>(usuario, HttpStatus.OK);
        }
        return  new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    public ResponseEntity<String> fazerLogincomoAdministrador(AdminLoginDTO admin){
        UsuarioModel usuario = usuarioRepository.buscarPorLogin(admin.getEmail());
        if(usuario.getAdmin().equals(true)) return new ResponseEntity<>(tokenService.gerarToken(admin), HttpStatus.OK);
        else return new ResponseEntity<>("O seu usuário não é um usuário administrador", HttpStatus.UNAUTHORIZED);
    }

    public UsuarioModel alterarTipoDeUsuario(Long codigo){
        isUsuario(codigo);
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);
        usuario.setAdmin(true);
        return  usuario;
    }

    public UsuarioModel alterarEndereco(Long codigo, UsuarioModel endereco){
        isUsuario(codigo);
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        usuario.setCep(endereco.getCep());
        usuario.setEstado(endereco.getEstado());
        usuario.setCidade(endereco.getCidade());
        usuario.setBairro(endereco.getBairro());
        usuario.setRua(endereco.getRua());
        usuario.setNumero(endereco.getNumero());
        usuario.setComplemento(endereco.getComplemento());

        return usuarioRepository.save(usuario);
    }

    public boolean alterarSenha(Long codigo, String senhaAtual, String senhaNova){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        if(validarSenha(usuario.getEmail(), senhaAtual).getBody()){
            usuario.setSenha(senhaNova);
            return true;
        }
        else return false;
    }

    public String excluirTodosUsuarios(){
        List<UsuarioModel> usuario = usuarioRepository.findAll();
        usuarioRepository.deleteAll();
        return "Usuários deletados com sucesso!!";
    }

    public String excluirUsuarioPorID(Long codigo){
        isUsuario(codigo);
        usuarioRepository.deleteById(codigo);
        return "Usuário deletado com sucesso!!";
    }

    //Validações
    private ResponseEntity<Boolean> validarSenha(String email,String senha){
        UsuarioModel usuario = usuarioRepository.buscarPorLogin(email);
        isUsuario(usuario.getCodigo());

        boolean valid = passwordEncoder.matches(senha, usuario.getSenha());
        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return new ResponseEntity<>(valid, status);
    }

    private void isUsuario(Long codigo){
        Optional<UsuarioModel> usuario = Optional.ofNullable(usuarioRepository.buscarPorID(codigo));
        if(usuario.isEmpty()) throw new RuntimeException();
    }
}
