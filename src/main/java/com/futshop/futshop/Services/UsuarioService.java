package com.futshop.futshop.Services;

import com.futshop.futshop.DTO.Request.AdminLoginDTO;
import com.futshop.futshop.Model.CarrinhoModelUsuario;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import com.futshop.futshop.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.rmi.AlreadyBoundException;
import java.util.List;

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

    public ResponseEntity<Boolean> validarSenha(String email,String senha){
        UsuarioModel usuario = usuarioRepository.buscarPorLogin(email);

        if(usuario == null) return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

        boolean valid = passwordEncoder.matches(senha, usuario.getSenha());
        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return new ResponseEntity<>(valid, status);
    }

    public UsuarioModel fazerLogin(String email, String senha){
        if(validarSenha(email, senha).getBody()){
            UsuarioModel usuario = usuarioRepository.buscarPorLogin(email);
            return  usuario;
        }
        return null;
    }

    public String fazerLogincomoAdministrador(AdminLoginDTO admin){
        UsuarioModel usuario = fazerLogin(admin.getEmail(), admin.getSenha());
        if(usuario == null) return null;
        else if(usuario != null & usuario.getAdmin().equals(true)) return tokenService.gerarToken(admin);
        else return "Não é um admin";
    }

    public UsuarioModel alterarEndereco(Long codigo, UsuarioModel endereco){
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

        for(UsuarioModel user: usuario){
            for(CarrinhoModelUsuario item: user.getItens()){
                ProdutoModel prod = produtoRepository.buscarPorID(item.getCodigo());
                prod.setQuantidadeEstoque(prod.getQuantidadeEstoque() + item.getQuantidade());
                produtoRepository.save(prod);
            }
        }

        usuarioRepository.deleteAll();
        return "Usuários deletados com sucesso!!";
    }

    public String excluirUsuarioPorID(Long codigo){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        for(CarrinhoModelUsuario item: usuario.getItens()){
            ProdutoModel prod = produtoRepository.buscarPorID(item.getCodigo());
            prod.setQuantidadeEstoque(prod.getQuantidadeEstoque() + item.getQuantidade());
            produtoRepository.save(prod);
        }

        usuarioRepository.deleteById(codigo);
        return "Usuário deletado com sucesso!!";
    }
}
