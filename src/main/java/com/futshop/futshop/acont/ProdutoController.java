package com.futshop.futshop.acont;

import com.futshop.futshop.adt.request.ProdutoRequestDTO;
import com.futshop.futshop.adt.response.ProdutoResponseDTO;
import com.futshop.futshop.amo.ProdutoModel;
import com.futshop.futshop.aserv.ProdutoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

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
        return new ResponseEntity<>(converterListaEmDTO(service.listarProdutos()), HttpStatus.OK);
    }

    @GetMapping(path = "/tipo/{tipo}")
    public ResponseEntity<?> filtrarPorTipo(@PathVariable String tipo){
        return new ResponseEntity<>(converterListaEmDTO(service.filtrarPorTipo(tipo)), HttpStatus.OK);
    }

    @GetMapping(path = "/promocaoDesc")
    public ResponseEntity<?> ordenarPromocaoEmOrdemDecrescente(){
        return new ResponseEntity<>(converterListaEmDTO(service.ordenarPromocaoEmOrdemDecrescente()), HttpStatus.OK);
    }

    @GetMapping(path = "/valorAsc")
    public ResponseEntity<?> ordenarValorEmOrdemCrescente(){
        return new ResponseEntity<>(converterListaEmDTO(service.ordenarValorEmOrdemCrescente()), HttpStatus.OK);
    }

    @GetMapping(path = "/valorDesc")
    public ResponseEntity<?> ordenarValorEmOrdemDecrescente(){
        return new ResponseEntity<>(converterListaEmDTO(service.ordenarValorEmOrdemDecrescente()), HttpStatus.OK);
    }

    @GetMapping(path = "descricao/{descricao}")
    public ResponseEntity<?> listarPesquisados(@PathVariable String descricao){
        return new ResponseEntity<>(converterListaEmDTO(service.buscarPorDescricao(descricao)), HttpStatus.OK);
    }

    @GetMapping(path = "/{codigo}")
    public ResponseEntity<?> buscarPorID(@PathVariable Long codigo){
        return new ResponseEntity(converterParaDTO(service.buscarProdutoPorID(codigo)), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> salvar(@RequestBody ProdutoRequestDTO produtoDTO) {
        ProdutoModel produto = service.salvarProduto(converterParaEntidade(produtoDTO));
        return new ResponseEntity<>(converterParaDTO(produto), HttpStatus.CREATED);
    }

    @PostMapping(path = "/img/{codigo}")
    public void img(@PathVariable Long codigo,
                    @RequestParam MultipartFile imagem){
        service.salvarImagem(codigo, imagem);
    }

    @DeleteMapping(path = "/{codigo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletar(@PathVariable Long codigo){
        return new ResponseEntity<>(service.deletarProdutoPorID(codigo), HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deletarTodos(){
        return new ResponseEntity<>(service.deletarTodosProdutos(), HttpStatus.OK);
    }
}
