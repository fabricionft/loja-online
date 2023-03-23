package com.futshop.futshop.Services;

import com.futshop.futshop.Exceptions.UsuarioException;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import com.futshop.futshop.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    private TokenService tokenService;

    public List<UsuarioModel> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public UsuarioModel buscarUsuarioPorID(Long codigo){
        return isUserByCode(codigo);
    }

    public UsuarioModel salvarUsuario(UsuarioModel usuario) {
        for(UsuarioModel user: usuarioRepository.findAll()){
            if(usuario.getEmail().equals(user.getEmail())) throw new UsuarioException("O email digitado já foi cadastrado, por favor digite outro!");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel fazerLogin(String email, String senha){
        if(validarSenha(email, senha)){
            UsuarioModel usuario = usuarioRepository.buscarPorLogin(email);
            usuario.setToken(tokenService.gerarToken(usuario));
            return  usuarioRepository.save(usuario);
        }
        throw new UsuarioException("Credenciais incorretas");
    }

    public UsuarioModel alterarTipoDeUsuario(Long codigo, String senha){
        UsuarioModel usuario = isUserByCode(codigo);

        if(passwordEncoder.matches(senha, "$2a$10$KrjoAbn9LLaIZIRiQ21uGuErs5aKmAeuIqPaApWxKJ0IeEbssFDRm")){
            usuario.setAdmin(true);
            usuario.setRole("ROLE_ADMIN");
            return  usuarioRepository.save(usuario);
        }
        throw  new UsuarioException("Senha de auetnticação incorreta!");

    }

    public UsuarioModel alterarEndereco(Long codigo, UsuarioModel endereco){
        UsuarioModel usuario = isUserByCode(codigo);

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
        UsuarioModel usuario = isUserByCode(codigo);

        if(validarSenha(usuario.getEmail(), senhaAtual)){
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
        isUserByCode(codigo);
        usuarioRepository.deleteById(codigo);
        return "Usuário deletado com sucesso!!";
    }

    //Validações
    private Boolean validarSenha(String email,String senha){
        boolean valid = passwordEncoder.matches(senha, isUserByEmail(email).getSenha());
        return valid;
    }

    private UsuarioModel isUserByCode(Long codigo){
        Optional<UsuarioModel> usuario = usuarioRepository.buscarOPTPorID(codigo);
        if(usuario.isEmpty()) throw new UsuarioException("Usuário inexistente");
        else return usuario.get();
    }

    private UsuarioModel isUserByEmail(String email){
        Optional<UsuarioModel> usuario = usuarioRepository.buscarOPTPorEmail(email);
        if(usuario.isEmpty()) throw new UsuarioException("Usuário inexistente");
        else return usuario.get();
    }

}
