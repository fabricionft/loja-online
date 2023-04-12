window.onload = () =>{
    if(verificarAutorizacaoAdmin()) listarProdutos();
}

function listarProdutos(){
    $.ajax({
       method: "GET",
       url: "/produtos",
       success: function (dados){
           dados.forEach(item => criaLinha(item));
       }
    }).fail(function(err){
       alert("Erro ao listar: " +err.responseJSON.message);
    });
}

function criaLinha(dados){
    $('#tabelaProdutos').append(
        '<div class="linha-tabela-produtos" id="linha" name="linha">'+
            '<div class="coluna-1">'+
                '<p class="texto-tabela-produtos">'+dados.nomeProduto+'</p>'+
            '</div>'+

            '<div class="coluna-2">'+
                '<p class="texto-tabela-produtos">'+dados.descricao+'</p>'+
            '</div>'+

            '<div class="coluna-3">'+
                '<p class="texto-tabela-produtos">R$ '+dados.valorComDesconto.toFixed(2)+'</p>'+
            '</div>'+

            '<div class="coluna-4">'+
                '<p class="texto-tabela-produtos">'+dados.quantidadeEstoque+'</p>'+
            '</div>'+

            '<div class="coluna-5">'+
                '<p class="texto-tabela-produtos">'+dados.tamanho+'</p>'+
            '</div>'+

            '<div class="coluna-6">'+
                '<p class="texto-tabela-produtos">'+dados.tipo+'</p>'+
            '</div>'+

            '<div class="coluna-7">'+
                '<button class="btn-editar" onclick="editar('+dados.codigo+')">Editar</button>'+
                '<button class="btn-excluir" onclick="excluir('+dados.codigo+')">Excluir</button>'+
            '</div>'+
        '</div>'
    );
}

function renderizarFormProdutos(opc){
    if(opc == 1){
        $('#containerCadastroProdutos').addClass('ativo');
        travarTela();
    }
    if(opc == 2){
        $('#containerCadastroProdutos').removeClass('ativo');
        destravarTela();
    }
}

function salvar(){
    let imagem = ($('#imagemEsconder').val().length) ? $('#imagemEsconder').val() : "nulo";
    $.ajax({
        method: "POST",
        url: "/produtos",
        data: JSON.stringify(
        {
            codigo: $('#codigoProduto').val(),
            nomeProduto: $('#nomeProduto').val(),
            descricao: $('#descricaoProduto').val(),
            imagem: imagem,
            promocao: $('#promocaoProduto').val(),
            quantidadeEstoque: $('#quantidadeProduto').val(),
            tamanho: $('#tamanhoProduto').val(),
            tipo: $('#tipoProduto').val(),
            valorBase: $('#valorProduto').val()
        }),
        contentType: "application/json; charset-utf8",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
        }
    }).done(function (dados) {
        if($("#upload")[0].files.length){
            console.log("A")
            salvarImagem(dados.codigo)
        }
        else gerarMessageBox(1, "Produto salvo com sucesso!!", "Prosseguir");
    }).fail(function (err)  {
        tratarErro(err);
    });
}

let data = new FormData();
document.getElementById("upload").onchange = function(e){
    if(e.target.files != null && e.target.files.length != 0){
        var arquivo = e.target.files[0];
        data.append("imagem", arquivo)
    }
}

function salvarImagem(codigo){
    $.ajax({
        type: "POST",
        url: "/produtos/img/"+codigo,
        data: data,
        processData: false,
        contentType: false,
        cache: false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
        }
    }).done(function (response) {
        gerarMessageBox(1, "Produto salvo com sucesso!!", "Prosseguir");
    }).fail(function (err)  {
        tratarErro(err);
    });
}

function editar(codigo){
    $.ajax({
        method: "GET",
        url: "/produtos/"+codigo,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
        }
    }).done(function (dados) {
        renderizarFormProdutos(1);
        $('#imagemEsconder').val(dados.imagem);
        $('#codigoProduto').val(dados.codigo);
        $('#nomeProduto').val(dados.nomeProduto);
        $('#descricaoProduto').val(dados.descricao);
        $('#tipoProduto').val(dados.tipo);
        $('#promocaoProduto').val(dados.promocao);
        $('#quantidadeProduto').val(dados.quantidadeEstoque);
        $('#tamanhoProduto').val(dados.tamanho);
        $('#valorProduto').val(dados.valorBase);
    }).fail(function (err)  {
        tratarErro(err);
    });
}

function excluir(codigo){
    $.ajax({
        method: "DELETE",
        url: "/produtos/"+codigo,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
        }
    }).done(function (response) {
        gerarMessageBox(1, "Produto deletado com sucesso!!", "Prosseguir");
    }).fail(function (err)  {
        tratarErro(err);
    });
}

function pesquisar(){
    if(!$('#pesquisarProduto').val().length) location.reload();
    limpar();
    $.ajax({
        method: "GET",
        url: "/produtos/descricao/"+$('#pesquisarProduto').val(),
        success: function (dados){
            dados.forEach(item => criaLinha(item))
        }
    }).fail(function(err){
        alert("Erro ao listar: " +err.responseJSON.message);
    });
}

function limpar(){
    while($("[name='linha']").length) $('#linha').remove();
}
