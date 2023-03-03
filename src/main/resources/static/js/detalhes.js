window.onload = () => mostrarProduto();

function mostrarProduto(){
    $.ajax({
        method: "GET",
        url: "/produtos/"+localStorage.getItem('codigoProduto'),
        success: function (dados){
           console.log(dados)
           $("#quantidadeDesconto").html(dados.promocao+"%");
           $("#quantidadeEstoque").html(dados.quantidadeEstoque);
           $("#descricaoe").html(dados.descricao+" - "+dados.tamanho);
           $("#valor").html("R$"+dados.valor);
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function adcionaItem(id){
    if(localStorage.getItem('logado') == 'logado'){
        $.ajax({
            method: "POST",
            url: "carrinho/produto/"+localStorage.getItem('codigoProduto')+"/usuario/"+localStorage.getItem('codigo'),
            success: function (dados){
                console.log("Sucesso")
                mostrarProduto();
            }
        }).fail(function(xhr, status, errorThrown){
            alert("Erro ao salvar: " +xhr.responseText);
        });
    }
    else alert("Faça login para poder adcionar um produto ao carrinho!")
}