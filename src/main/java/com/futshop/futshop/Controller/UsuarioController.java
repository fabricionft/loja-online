package com.futshop.futshop.Controller;

import com.futshop.futshop.Model.CarrinhoModel;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Model.UsuarioModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import com.futshop.futshop.Repository.UsuarioRepository;
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
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @PostMapping
    public ResponseEntity salvarUsuario(@RequestBody UsuarioModel usuario) throws AlreadyBoundException {

        for(UsuarioModel user: usuarioRepository.findAll()){
            if(user.getEmail().equals(usuario.getEmail())) throw new AlreadyBoundException();
        }

        usuarioRepository.save(usuario);
        return new ResponseEntity("Usuário cadastrado com sucesso", HttpStatus.OK);
    }

    @PutMapping(path = "/{codigo}")
    public UsuarioModel alterarEndereco(@PathVariable Long codigo,
                                        @RequestBody UsuarioModel endereco){
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

    @GetMapping
    public List<UsuarioModel> listarUsuarios(){
        return usuarioRepository.findAll();
    }

    @GetMapping(path = "/{codigo}")
    public UsuarioModel buscarUsuarioPorID(@PathVariable Long codigo){
        return usuarioRepository.buscarPorID(codigo);
    }

    @GetMapping(path = "/email/{email}/senha/{senha}")
    public UsuarioModel fazerLogin(@PathVariable String email,
                                   @PathVariable String senha){
        return usuarioRepository.fazerlogin(email, senha);
    }

    @GetMapping(path = "/produtos/{codigo}")
    public List<CarrinhoModel> listarProdutosDeUmUsuario(@PathVariable Long codigo){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);
        List<CarrinhoModel> lista = new ArrayList<CarrinhoModel>();

        for(CarrinhoModel produtos: usuario.getItens()){
            lista.add(produtos);
        }

        return lista;
    }

    @DeleteMapping
    public void excluirTodosUsuarios(){
        List<UsuarioModel> usuario = usuarioRepository.findAll();

        for(UsuarioModel user: usuario){
            for(CarrinhoModel item: user.getItens()){
                ProdutoModel prod = produtoRepository.buscarPorID(item.getCodigo());
                prod.setQuantidadeEstoque(prod.getQuantidadeEstoque() + item.getQuantidade());
                produtoRepository.save(prod);
            }
        }

        usuarioRepository.deleteAll();
    }

    @DeleteMapping(path = "/usuario/{codigo}")
    public void excluirUsuario(@PathVariable Long codigo){
        UsuarioModel usuario = usuarioRepository.buscarPorID(codigo);

        for(CarrinhoModel item: usuario.getItens()){
            ProdutoModel prod = produtoRepository.buscarPorID(item.getCodigo());
            prod.setQuantidadeEstoque(prod.getQuantidadeEstoque() + item.getQuantidade());
            produtoRepository.save(prod);
        }

        usuarioRepository.deleteById(codigo);
    }
}