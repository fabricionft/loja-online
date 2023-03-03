window.onload = () => {
    verificarEstadoDeLogin();
    lista();
}

function verificarEstadoDeLogin(){
    if(localStorage.getItem('logado') == 'logado'){
        document.getElementById('login').style.display="none";
        document.getElementById('sair').style.display="initial";
    }
    else{
        document.getElementById('sair').style.display="none";
        document.getElementById('login').style.display="initial";
    }
}

function lista(){
    var i = 0;
    //Promoções
    $.ajax({
        method: "GET",
        url: "/produtos/promocoes",
        success: function (dados){
            dados.forEach(item => {
                i++;
                if(item.quantidadeEstoque > 0 && i <= 4) adcionaProduto(item, "section-promocao");
                if(item.quantidadeEstoque > 0 && i <= 4) adcionaProduto(item, "section-novidades");
            });
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });

    //Produtos
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

function adcionaProduto(dados, local){
    $('#'+local).append(
        '<div class="produto">'+
            '<a type="button" class="link" onclick="pegarId('+dados.codigo+')" href="detalhes.html">'+
                '<header class="promo-produto">'+
                    '<p class="numero-promo">'+dados.promocao+'%</p><p class="texto-promo">OFF</p>'+
                '</header>'+
                '<div class="img-produto">'+
                    '<img src="img/camisa-liverpool.png" height="90%">'+
                '</div>'+
                '<div class="desc-produto" >'+dados.descricao+" - "+dados.tamanho+'</div>'+
                '<div class="valor-produto" >R$ '+dados.valor+'</div>'+
            '</a>'+

            '<button class="adciona-carrinho" onclick="adcionaItens('+dados.codigo+')">Adcionar ao carrinho</button>'+
        '</div>'
    );
}

function pegarId(id){
    localStorage.setItem('codigoProduto', id)
}