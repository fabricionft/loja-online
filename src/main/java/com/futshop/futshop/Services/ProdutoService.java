package com.futshop.futshop.Services;

import com.futshop.futshop.Model.ProdutoModel;
import com.futshop.futshop.Repository.ProdutoRepository;
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

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;


    public List<ProdutoModel> listarProdutos(){
        return repository.findAll();
    }

    public List<ProdutoModel> filtrarPorTipo(@PathVariable String tipo){
        return repository.buscarPorTipo(tipo);
    }

    public List<ProdutoModel> ordenarPromocaoEmOrdemDecrescente(){
        return repository.promocaoDecrescente();
    }

    public List<ProdutoModel> ordenarValorEmOrdemCrescente(){
        return repository.valorCrescente();
    }

    public List<ProdutoModel> ordenarValorEmOrdemDecrescente(){
        return repository.valorDecrescente();
    }

    public List<ProdutoModel> buscarPorDescricao(String descricao){
        return repository.buscarPorDescricao(descricao);
    }

    public ProdutoModel buscarProdutoPorID(Long codigo){
        return repository.buscarPorID(codigo);
    }

    public ProdutoModel salvarProduto(ProdutoModel produto) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        produto.setValorComDesconto(produto.getValorBase() - produto.getValorBase() * produto.getPromocao() / 100);
        produto.setDataPostagem(formatter.format(calendar.getTime()));
        return repository.save(produto);
    }

    public void salvarImagem(Long codigo, MultipartFile imagem){
        if(! imagem.isEmpty()){
            ProdutoModel produto = repository.buscarPorID(codigo);
            String nomeImagem = imagem.getOriginalFilename();
            produto.setImagem("uploads/"+nomeImagem);
            repository.save(produto);

            try{
                String caminho = "C:\\Users\\fabri\\OneDrive\\Área de Trabalho\\FUTSHOP\\loja-online-main\\src\\main\\resources\\static\\uploads";
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
        repository.deleteById(codigo);
        return "Produto deletado com sucesso!!";
    }

    public String deletarTodosProdutos(){
        repository.deleteAll();
        return "Todos produtos deletados com sucesso!!";
    }
}
