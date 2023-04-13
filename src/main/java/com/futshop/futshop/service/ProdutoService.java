package com.futshop.futshop.service;

import com.futshop.futshop.exceptions.RequestException;
import com.futshop.futshop.model.ProdutoModel;
import com.futshop.futshop.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;


    public List<ProdutoModel> listarProdutos(){
        return produtoRepository.findAll();
    }

    public List<ProdutoModel> filtrarPorTipo(@PathVariable String tipo){
        return produtoRepository.buscarPorTipo(tipo);
    }

    public List<ProdutoModel> ordenarPromocaoEmOrdemDecrescente(){
        return produtoRepository.promocaoDecrescente();
    }

    public List<ProdutoModel> ordenarValorEmOrdemCrescente(){
        return produtoRepository.valorCrescente();
    }

    public List<ProdutoModel> ordenarValorEmOrdemDecrescente(){
        return produtoRepository.valorDecrescente();
    }

    public List<ProdutoModel> buscarPorDescricao(String descricao){
        return produtoRepository.buscarPorDescricao(descricao);
    }

    public ProdutoModel buscarProdutoPorID(Long codigo){
        return isProductByCode(codigo);
    }

    public ProdutoModel salvarProduto(ProdutoModel produto) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        produto.setValorComDesconto(produto.getValorBase() - produto.getValorBase() * produto.getPromocao() / 100);
        produto.setDataPostagem(formatter.format(calendar.getTime()));
        return produtoRepository.save(produto);
    }

    public ProdutoModel atualizarProduto(ProdutoModel produto){
        return  produtoRepository.save(produto);
    }

    public void salvarImagem(Long codigo, MultipartFile imagem){
        if(! imagem.isEmpty()){
            ProdutoModel produto = isProductByCode(codigo);
            String nomeImagem = imagem.getOriginalFilename();
            produto.setImagem("uploads/"+nomeImagem);
            produtoRepository.save(produto);

            try{
                String caminho = "C:\\Users\\fabri\\OneDrive\\Área de Trabalho\\FUTSHOP\\futshop\\src\\main\\resources\\static\\uploads";
                File diretorio = new File(caminho);
                if(! diretorio.exists()) diretorio.mkdirs();

                File serverFile = new File(diretorio.getAbsolutePath() + File.separator + nomeImagem);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(imagem.getBytes());
                stream.close();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    public String deletarProdutoPorID(Long codigo){
        produtoRepository.deleteById(isProductByCode(codigo).getCodigo());
        return "Produto deletado com sucesso!!";
    }

    public String deletarTodosProdutos(){
        produtoRepository.deleteAll();
        return "Todos produtos deletados com sucesso!!";
    }

    //Validações
    public ProdutoModel isProductByCode(Long codigo){
        Optional<ProdutoModel> produto = produtoRepository.buscarPorID(codigo);
        if(produto.isEmpty()) throw new RequestException("Produto inexistente");
        else return produto.get();
    }
}
