package com.futshop.futshop.Controller;

import java.util.List;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoRepository repository;

    @GetMapping
    public List<ProdutoModel> listar(){
        return repository.findAll();
    }

    @GetMapping(path = "/promocoes")
    public List<ProdutoModel> listarPromocoes(){
        return repository.buscarPromocoes();
    }

    @GetMapping(path = "/novidades")
    public List<ProdutoModel> listarNovidades(){
        return repository.buscarNovidades();
    }

    @GetMapping(path = "/{codigo}")
    public ProdutoModel buscarPorID(@PathVariable Long codigo){
        return repository.buscarPorID(codigo);
    }

    @PostMapping
    public void salvar(@RequestBody ProdutoModel produto) {
        repository.save(produto);
    }

    @DeleteMapping(path = "/{codigo}")
    public void deletar(@PathVariable Long codigo){
        repository.deleteById(codigo);
    }

    @DeleteMapping()
    public void deletarTodos(){
        repository.deleteAll();
    }
}
