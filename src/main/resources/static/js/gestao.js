function salvar(){
    $.ajax({
        method: "POST",
        url: "salvarProduto",
        data: JSON.stringify(
        {
            nomeProduto: $('#nomeProduto').val(),
            descricao: $('#descricaoProduto').val(),
            promocao: $('#promocaoProduto').val(),
            quantidadeEstoque: $('#quantidadeProduto').val(),
            tamanho: $('#tamanhoProduto').val(),
            valor: $('#valorProduto').val()
        }),
        contentType: "application/json; charset-utf8",
        success: function (dados){
            alert("Sucesso");
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}