window.onload = () => {
    if(verificarLogin()){
        document.getElementById('login').style.display="none";
        document.getElementById('sair').style.display="initial";
    }
    else{
        document.getElementById('sair').style.display="none";
        document.getElementById('login').style.display="initial";
    }
    renderizarQuantidade(localStorage.getItem('quantidadeItens'));
    listarProdutos();
}

function listarProdutos(){
    $.ajax({
        method: "GET",
        url: "/produtos",
        success: function (dados){
            dados.forEach(item => {
                if (item.quantidadeEstoque > 0) adcionaProduto(item, "section-produto");
            });
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function buscar(caminho, valor){
    if(!valor.length) location.reload();
    $.ajax({
        method: "GET",
        url: "/produtos/"+caminho+valor,
        success: function (dados){
            limparPesquisa();
            dados.forEach(item => {
                if (item.quantidadeEstoque > 0) adcionaProduto(item, "section-pesquisa");
            });
            $('#titulo-pesquisa').append(
                '<button onclick="location.reload()" class="btn-limpar-pesquisa" id="btnLimparPesquisa">Limpar pesquisa</button>'
            );
            if (!dados.length){
                $('#section-pesquisa').append(
                    '<p class="texto-titulo-section" id="aviso">Sem resultados para sua pesquisa!!</p>'
                );
            }
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function adcionaProduto(dados, local){
    $('#'+local).append(
        '<div class="produto" id="produto" name="produto">'+
            '<a type="button" class="link" onclick="pegarId('+dados.codigo+')" href="detalhes.html">'+
                '<header class="promo-produto">'+
                    '<p class="numero-promo">'+dados.promocao+'%</p><p class="texto-promo">OFF</p>'+
                '</header>'+
                '<div class="img-produto">'+
                    '<img src="'+dados.imagem+'" height="90%">'+
                '</div>'+
                '<div class="desc-produto" >'+dados.descricao+" - "+dados.tamanho+'</div>'+
                '<div class="valor-produto">'+
                    '<span class="base">'+
                        '<p class="valor-base">De: R$ '+dados.valorBase.toFixed(2)+'</p>&nbsp;por'+
                    '</span>'+
                    '<p class="valor-desconto">R$ '+dados.valorComDesconto.toFixed(2)+'</p>'+
                '</div>'+
            '</a>'+

            '<button class="adciona-carrinho" onclick="adcionaItens('+dados.codigo+')">Adcionar ao carrinho</button>'+
        '</div>'
    );
}

function limparPesquisa(){
    $('#tituloSection').remove();
    $('#section-produto').remove();
    $('#btnLimparPesquisa').remove();
    $('#aviso').remove();

    while($("[name='produto']").length) $('#produto').remove();
}

function pegarId(id){
    localStorage.setItem('codigoProduto', id)
}