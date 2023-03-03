window.onload = () => {
    if(verificarLogin()){
        listarItens();
        $("#estadoUSuario").html("Olá "+localStorage.getItem('nome'))
    }
    else $("#estadoUSuario").html("Faça login");

    renderizarQuantidade(localStorage.getItem('quantidadeItens'));
}

function listarItens(){
    for(var i = 0; i<= 600; i++) $('#linha-carrinho').remove();
    $.ajax({
        method: "GET",
        url: "carrinho/"+localStorage.getItem('codigo'),
        success: function (dados){
            var lista = dados.itens;
            lista.forEach(item => {
                listaItens(item);
            });
            ini = lista.length;
            $('#totalCarrinho').html(dados.valorTotalItens.toFixed(2));
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function adcionaItens(id){
    if(localStorage.getItem('logado') == 'logado'){
        $.ajax({
            method: "POST",
            url: "carrinho/produto/"+id+"/usuario/"+localStorage.getItem('codigo'),
            success: function (dados){
                renderizarQuantidade(dados.quantidadeItens);
            }
        }).fail(function(xhr, status, errorThrown){
            alert("Erro ao salvar: " +xhr.responseText);
        });
    }
    else gerarMessageBox("rgb(253, 214, 214)", "É necessário fazer login para adcionar um item ao carrinho!!", "Ok");

}

var ini;
function alterar(codigo, acao){
    $.ajax({
        method: "PUT",
        url: "carrinho/produto/"+codigo+"/usuario/"+localStorage.getItem('codigo')+"/acao/"+acao,
        success: function (dados){
            renderizarQuantidade(dados.quantidadeItens)
            var lista = dados.itens;
            var fim = lista.length;
            if(ini > fim)  listarItens();

            lista.forEach(item => {
                $('#quantidadeItens-'+item.codigo).html(item.quantidade);
                $('#valorFinal-'+item.codigo).html("R$ "+item.precoFinal.toFixed(2));
            });

            $('#totalCarrinho').html(dados.valorTotalItens.toFixed(2));
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function excluir(){
    $.ajax({
        method: "DELETE",
        url: "carrinho/usuario/"+localStorage.getItem('codigo'),
        success: function (dados){
            listarItens();
            renderizarQuantidade(dados.quantidadeItens)
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function listaItens(dados){
    $('#itensCarrinho').append(
        '<tr class="linha-carrinho" id="linha-carrinho">'+
            '<td class="coluna-1">'+
                '<div class="img-carrinho"><img src="'+dados.imagem+'" class="imgs-carrinho"></div>'+
                '<div class="desc-carrinho">'+dados.descricaoProduto+' - '+dados.tamanho+'</div>'+
            '</td>'+
            '<td class="coluna-2">'+
                '<div class="texto-quantidade-carrinho"><p id="quantidadeItens-'+dados.codigo+'">'+dados.quantidade+'</p></div>'+
                '<div class="btns-quantidade-carrinho">'+
                    '<button class="btn-quantidade" onclick="alterar('+dados.codigo+","+1+')">+</button>'+
                    '<button class="btn-quantidade" onclick="alterar('+dados.codigo+","+2+')">-</button>'+
                '</div>'+
            '</td>'+
            '<td class="coluna-3">R$ '+dados.precoUnitario.toFixed(2)+'</td>'+
            '<td class="coluna-4" id="valorFinal-'+dados.codigo+'">R$ '+dados.precoFinal.toFixed(2)+'</td>'+
        '</tr>'
    );
}