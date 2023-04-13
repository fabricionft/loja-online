package com.futshop.futshop.service;

import com.futshop.futshop.dto.request.EnderecoRequestDTO;
import com.futshop.futshop.exception.RequestException;
import com.futshop.futshop.model.CarrinhoUsuarioModel;
import com.futshop.futshop.model.UsuarioModel;
import com.futshop.futshop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${senha.sistema}")
    private String senhaSistema;


    public List<UsuarioModel> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    public UsuarioModel buscarUsuarioPorEmail(String email){
        return isUserByEmail(email);
    }

    public  UsuarioModel salvarUsuario(UsuarioModel usuario) {
        if(usuarioRepository.buscarPorEmail(usuario.getEmail()).isPresent())
            throw new RequestException("O email digitado já foi cadastrado, por favor digite outro!");
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel atualizarUsuario(UsuarioModel usuario){
        Integer quantiadeTotal = 0;
        Double valorTotal = 0.0;

        for(CarrinhoUsuarioModel itens: usuario.getItens()){
            quantiadeTotal += itens.getQuantidade();
            valorTotal += itens.getPrecoFinal();
        }

        usuario.setQuantidadeItens(quantiadeTotal);
        usuario.setValorTotalItens(valorTotal);
        return  usuarioRepository.save(usuario);
    }

    public UsuarioModel fazerLogin(String email, String senha){
        if(validarSenha(email, senha)) return isUserByEmail(email);
        else throw new RequestException("Credenciais incorretas");
    }

    public UsuarioModel alterarTipoDeUsuario(Long codigo, String senha){
        UsuarioModel usuario = isUserByCode(codigo);
        if(passwordEncoder.matches(senha, senhaSistema)){
            usuario.setAdmin(true);
            usuario.setRole("ROLE_ADMIN");
            return  usuarioRepository.save(usuario);
        }
        throw  new RequestException("Senha do sistema incorreta!");
    }

    public UsuarioModel alterarEndereco(Long codigo, EnderecoRequestDTO endereco){
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

    public String alterarSenha(Long codigo, String senhaAtual, String senhaNova){
        UsuarioModel usuario = isUserByCode(codigo);
        if(validarSenha(usuario.getEmail(), senhaAtual)){
            usuario.setSenha(senhaNova);
            return "Senha alterada com sucesso!";
        }
        throw new RequestException("Senha atual incorreta");
    }

    public String excluirTodosUsuarios(){
        usuarioRepository.deleteAll();
        return "Usuários deletados com sucesso!!";
    }

    public String excluirUsuarioPorID(Long codigo){
        usuarioRepository.deleteById(isUserByCode(codigo).getCodigo());
        return "Usuário deletado com sucesso!!";
    }

    //Validações
    private Boolean validarSenha(String email,String senha){
       return   passwordEncoder.matches(senha, isUserByEmail(email).getSenha());
    }

    public UsuarioModel isUserByCode(Long codigo){
        Optional<UsuarioModel> usuario = usuarioRepository.buscarPorId(codigo);
        if(usuario.isEmpty()) throw new RequestException("Usuário inexistente");
        else return usuario.get();
    }

    private UsuarioModel isUserByEmail(String email){
        Optional<UsuarioModel> usuario = usuarioRepository.buscarPorEmail(email);
        if(usuario.isEmpty()) throw new RequestException("Usuário inexistente");
        else return usuario.get();
    }
}
