package com.futshop.futshop.Services;

import com.futshop.futshop.Model.CarrinhoModelUsuario;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import com.futshop.futshop.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;


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
        return usuarioRepository.save(usuario);
    }

    public UsuarioModel fazerLogin(String email, String senha){
        return usuarioRepository.fazerlogin(email, senha);
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
