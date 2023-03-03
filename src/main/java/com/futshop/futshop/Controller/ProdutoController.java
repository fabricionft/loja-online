package com.futshop.futshop.Controller;

import java.util.ArrayList;
import java.util.List;

import com.futshop.futshop.DTO.Request.ProdutoRequestDTO;
import com.futshop.futshop.DTO.Response.ProdutoResponseDTO;
import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Services.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ModelMapper modelMapper;

    public ProdutoModel converterParaEntidade(ProdutoRequestDTO produtoDTO){
        return modelMapper.map(produtoDTO, ProdutoModel.class);
    }

    public ProdutoResponseDTO converterParaDTO(ProdutoModel produto){
        return modelMapper.map(produto, ProdutoResponseDTO.class);
    }

    public List<ProdutoResponseDTO> converterListaEmDTO(List<ProdutoModel> listaProdutos){
        List<ProdutoResponseDTO> listaProdutosDTO = new ArrayList<>();
        for(ProdutoModel prodto: listaProdutos) {
            listaProdutosDTO.add(modelMapper.map(prodto, ProdutoResponseDTO.class));
        }
        return  listaProdutosDTO;
    }


    @GetMapping
    public ResponseEntity<?> listar(){
        return new ResponseEntity<>(converterListaEmDTO(produtoService.listarProdutos()), HttpStatus.OK);
    }

    @GetMapping(path = "/promocoes")
    public ResponseEntity<?> listarPromocoes(){
        return new ResponseEntity<>(converterListaEmDTO(produtoService.listarPromocoes()), HttpStatus.OK);
    }

    @GetMapping(path = "/novidades")
    public ResponseEntity<?> listarNovidades(){
        return new ResponseEntity<>(converterListaEmDTO(produtoService.listarNovidades()), HttpStatus.OK);
    }

    @GetMapping(path = "descricao/{descricao}")
    public ResponseEntity<?> listarPesquisados(@PathVariable String descricao){
        return new ResponseEntity<>(converterListaEmDTO(produtoService.buscarPorDescricao(descricao)), HttpStatus.OK);
    }

    @GetMapping(path = "/{codigo}")
    public ResponseEntity<?> buscarPorID(@PathVariable Long codigo){
        return new ResponseEntity(converterParaDTO(produtoService.buscarProdutoPorID(codigo)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody ProdutoRequestDTO produtoDTO) {
        ProdutoModel produto = produtoService.salvarProduto(converterParaEntidade(produtoDTO));
        return new ResponseEntity<>(converterParaDTO(produto), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{codigo}")
    public ResponseEntity<?> deletar(@PathVariable Long codigo){
        return new ResponseEntity<>(produtoService.deletarProdutoPorID(codigo), HttpStatus.OK);
    }

    @DeleteMapping()
    public ResponseEntity<?> deletarTodos(){
        return new ResponseEntity<>(produtoService.deletarTodosProdutos(), HttpStatus.OK);
    }
}
