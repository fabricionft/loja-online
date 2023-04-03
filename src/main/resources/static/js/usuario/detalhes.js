window.onload = () => {
    alterarSessao();
    mostrarProduto();
    renderizarQuantidade(localStorage.getItem('quantidadeItens'));
}

function mostrarProduto(){
    $.ajax({
        method: "GET",
        url: "/produtos/"+localStorage.getItem('codigoProduto'),
        success: function (dados){
           $('#imgCamisa').attr('src', dados.imagem);
           $("#quantidadeDesconto").html(dados.promocao+"%");
           $("#quantidadeEstoque").html(dados.quantidadeEstoque);
           $("#descricao").html(dados.descricao+" - "+dados.tamanho);
           $("#valorBase").html("R$"+dados.valorBase.toFixed(2));
           $("#valorComDesconto").html("R$"+dados.valorComDesconto.toFixed(2));
        }
    }).fail(function(err){
        alert("Erro ao exibir produto" +err.responseJSON.message);
    });
}

$('#adcionaItem').click(function(){
    adcionaItens(localStorage.getItem('codigoProduto'));
})