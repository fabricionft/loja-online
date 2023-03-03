window.onload = () =>{
    listarProdutos();
}

function listarProdutos(){
    //limpar();
    $.ajax({
       method: "GET",
       url: "/produtos",
       success: function (dados){
           dados.forEach(item => criaLinha(item));
       }
    }).fail(function(xhr, status, errorThrown){
       alert("Erro ao listar: " +xhr.responseText);
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
    let imagem = "";
    if($("#imagemProduto")[0].files.length) imagem = $("#imagemProduto")[0].files[0].name;
    else imagem = $('#imagemEsconder').val();

    $.ajax({
        method: "POST",
        url: "/produtos",
        data: JSON.stringify(
        {
            codigo: $('#codigoProduto').val(),
            nomeProduto: $('#nomeProduto').val(),
            descricao: $('#descricaoProduto').val(),
            promocao: $('#promocaoProduto').val(),
            quantidadeEstoque: $('#quantidadeProduto').val(),
            tamanho: $('#tamanhoProduto').val(),
            imagem: imagem,
            tipo: $('#tipoProduto').val(),
            valorBase: $('#valorProduto').val()
        }),
        contentType: "application/json; charset-utf8",
        success: function (dados){
            gerarMessageBox("rgb(214, 253, 226)", "Produto cadastrado/editado com sucesso!!", "Ok");
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function editar(codigo){
    $.ajax({
        method: "GET",
        url: "/produtos/"+codigo,
        success: function (dados){
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
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao listar: " +xhr.responseText);
    });
}

function excluir(codigo){
    $.ajax({
        method: "DELETE",
        url: "/produtos/"+codigo,
        success: function (dados){
            gerarMessageBox("rgb(214, 253, 226)", "Produto deletado com sucesso!!", "Ok");
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao listar: " +xhr.responseText);
    });
}

function pesquisar(){
    limpar();
    $.ajax({
        method: "GET",
        url: "/produtos/descricao/"+$('#pesquisarProduto').val(),
        success: function (dados){
            dados.forEach(item => criaLinha(item))
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao listar: " +xhr.responseText);
    });
}

function limpar(){
    while($("[name='linha']").length > 0) $('#linha').remove();
}
