window.onload = () => {
    alterarSessao();
    renderizarQuantidade(localStorage.getItem('quantidadeItens'));
    if(verificarLogin()) listarItens();
}

function listarItens(){
    while($("[name='linha']").length) $('#linhaCarrinho').remove();
    $.ajax({
        method: "GET",
        url: "carrinhos/"+localStorage.getItem('codigo'),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
        }
    }).done(function (dados) {
        dados.itens.forEach(item => {
            listaItens(item);
        });
        $('#totalCarrinho').html(" "+dados.valorTotalItens.toFixed(2));
    }).fail(function (err)  {
        tratarErro(err);
    });
}

function adcionaItens(id){
    if(localStorage.getItem('logado') == 'logado'){
        $.ajax({
            method: "POST",
            url: "carrinhos/produto/"+id+"/usuario/"+localStorage.getItem('codigo'),
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
            }
        }).done(function (dados) {
            renderizarQuantidade(dados.quantidadeItens);
        }).fail(function (err) {
            tratarErro(err);
        });
    }
    else gerarMessageBox(2, "É necessário fazer login para adcionar um item ao carrinho!!", "Ok");
}

function alterar(codigo, acao){
    $.ajax({
        method: "PUT",
        url: "carrinhos/produto/"+codigo+"/usuario/"+localStorage.getItem('codigo')+"/acao/"+acao,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
        }
    }).done(function (dados) {
         if($('#quantidadeItens-'+codigo).html() == 1 && acao == 2) listarItens();

         dados.itens.forEach(item => {
             $('#quantidadeItens-'+item.codigo).html(item.quantidade);
             $('#valorFinal-'+item.codigo).html(item.precoFinal.toFixed(2));
         });

         $('#totalCarrinho').html(" "+dados.valorTotalItens.toFixed(2));
         renderizarQuantidade(dados.quantidadeItens);
    }).fail(function (err)  {
        tratarErro(err);
    });
}

function excluir(){
    $.ajax({
        method: "DELETE",
        url: "carrinhos/usuario/"+localStorage.getItem('codigo'),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
        }
    }).done(function (dados) {
        listarItens();
        renderizarQuantidade(dados.quantidadeItens);
    }).fail(function (err)  {
        tratarErro(err);
    });
}

function listaItens(dados){
    $('#itensCarrinho').append(
        '<tr class="linha-carrinho" name="linha" id="linhaCarrinho">'+
            '<td class="coluna-1">'+
                '<div class="img-carrinho"><img src="'+dados.imagem+'" class="imgs-carrinho"></div>'+
                '<div class="desc-carrinho"><p>'+dados.descricaoProduto+' - '+dados.tamanho+'</p></div>'+
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