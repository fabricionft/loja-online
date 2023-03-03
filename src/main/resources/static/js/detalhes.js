window.onload = () => {
    if(verificarLogin()) $("#estadoUSuario").html("Olá "+localStorage.getItem('nome'))
    else $("#estadoUSuario").html("Faça login");
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
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

$('#adcionaItem').click(function(){
    adcionaItens(localStorage.getItem('codigoProduto'));
    mostrarProduto();
})