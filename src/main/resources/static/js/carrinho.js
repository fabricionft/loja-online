window.onload = () => {
    if(verificarLogin()){
        listarItens();
        $("#estadoUSuario").html("Olá "+localStorage.getItem('nome'))
    }
    else $("#estadoUSuario").html("Faça login");

    renderizarQuantidade(localStorage.getItem('quantidadeItens'));
}

function listarItens(){
    while($("[name='linha']").length) $('#linhaCarrinho').remove();
    $.ajax({
        method: "GET",
        url: "carrinho/"+localStorage.getItem('codigo'),
        success: function (dados){
            dados.itens.forEach(item => {
                listaItens(item);
            });
            $('#totalCarrinho').html(" "+dados.valorTotalItens.toFixed(2));
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

function alterar(codigo, acao){
    $.ajax({
        method: "PUT",
        url: "carrinho/produto/"+codigo+"/usuario/"+localStorage.getItem('codigo')+"/acao/"+acao,
        success: function (dados){
            if($('#quantidadeItens-'+codigo).html() == 1 && acao == 2) listarItens();

            dados.itens.forEach(item => {
                $('#quantidadeItens-'+item.codigo).html(item.quantidade);
                $('#valorFinal-'+item.codigo).html(item.precoFinal.toFixed(2));
            });

            $('#totalCarrinho').html(" "+dados.valorTotalItens.toFixed(2));
            renderizarQuantidade(dados.quantidadeItens);
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
        '<tr class="linha-carrinho" name="linha" id="linhaCarrinho">'+
            '<td class="coluna-1">'+
                '<div class="img-carrinho"><img src="'+dados.imagem+'" class="imgs-carrinho"></div>'+
                '<div class="desc-carrinho">'+dados.descricaoProduto+' - '+dados.tamanho+'</div>'+
            '</td>'+
            '<td class="coluna-2">'+
                '<p class="texto-quantidade-carrinho" id="quantidadeItens-'+dados.codigo+'">'+dados.quantidade+'</p>'+
                '<div class="btns-quantidade-carrinho">'+
                    '<button class="btn-quantidade" onclick="alterar('+dados.codigo+', 1)">+</button>'+
                    '<button class="btn-quantidade" onclick="alterar('+dados.codigo+', 2)">-</button>'+
                '</div>'+
            '</td>'+
            '<td class="coluna-3">'+dados.precoUnitario.toFixed(2)+'</td>'+
            '<td class="coluna-4" id="valorFinal-'+dados.codigo+'">'+dados.precoFinal.toFixed(2)+'</td>'+
        '</tr>'
    );
}