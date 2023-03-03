window.onload = () =>{
    if(verificarLogin()){
        listarDados();
        $("#estadoUSuario").html("Olá "+localStorage.getItem('nome'))
    }
    else $("#estadoUSuario").html("Faça login");
    renderizarQuantidade(localStorage.getItem('quantidadeItens'));
}

function listarDados(){
    $.ajax({
        method: "GET",
        url: "/pedidos/pedido/"+localStorage.getItem('codigoPedido'),
        success: function (dados){
            $('#nomeCliente').html(dados.nomeCliente);
            $('#emailCliente').html(dados.email);
            $('#celularCliente').html(dados.celular);
            $('#dataPedido').html(dados.data);
            $('#numeroPedido').html("#"+dados.numero);
            $('#statusPedido').html(dados.status);
            $('#quantidadeItensPedido').html(dados.quantidadeItens);
            $('#metodoPagamentoPedido').html(dados.pagamento);
            $('#parcelamentoPedido').html(dados.quantidaeParcelas+"X de R$ "+dados.valorParcela.toFixed(2));
            $('#valorpedido').html("R$ "+dados.valor.toFixed(2));
            $('#enderecoPedido').html(dados.endereco);
            dados.itens.forEach(item => {criarLinha(item);});
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function criarLinha(dados){
    $('#itensPedido').append(
        '<div class="linha-item">'+
            '<div class="coluna-1">'+
                '<div class="img-item"><img src="'+dados.imagem+'" class="imgs-item"></div>'+
                '<div class="desc-item">'+dados.descricaoProduto+' - '+dados.tamanho+'</div>'+
            '</div>'+
            '<div class="coluna-2">'+
                '<div class="texto-quantidade-item">'+dados.quantidade+'</div>'+
            '</div>'+
            '<div class="coluna-3">'+dados.precoUnitario.toFixed(2)+'</div>'+
            '<div class="coluna-4">'+dados.precoFinal.toFixed(2)+'</div>'+
        '</div>'
    );
}