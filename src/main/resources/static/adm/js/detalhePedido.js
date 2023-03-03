function exibirDetalhe(){
    window.scrollTo(0, 0);
    $('#detalhePedido').css("display", "flex");
    if($(window).width() < 1000)$('#tabelaPedidos').css("display", "none");
}

function esconderDetallhe(){
    $('#detalhePedido').css("display", "none");
    $('#tabelaPedidos').css("display", "flex");
}

function renderizarDetalhamento(acao, codigo){
    if(acao == 1){
        exibirDetalhe();
        $.ajax({
            method: "GET",
            url: "/pedidos/pedido/"+codigo,
            success: function (dados){
               preencherDetalhamento(dados)
            }
        }).fail(function(xhr, status, errorThrown){
            alert("Erro ao buscar: " +xhr.responseText);
        });
    }
    if(acao == 2) esconderDetallhe();
}

function alterarStatusPedido(codigo, acao){
    if(acao == 1) verbo = "confirmado";
    else verbo = "negado";

    $.ajax({
        method: "PUT",
        url: "/pedidos/pedido/"+codigo+"/acao/"+acao,
        success: function (dados){
           gerarMessageBox("rgb(214, 253, 226)", "Pedido "+verbo+" com sucesso!!", "Ok");
           listarPedidos();
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao alterar: " +xhr.responseText);
    });
}

function preencherDetalhamento(dados){
    $('#nomeCliente').html(dados.nomeCliente);
    $('#emailCliente').html(dados.email);
    $('#celularCliente').html(dados.celular);
    $('#statusPedido').html(dados.status);
    $('#parcelamentoPedido').html(dados.quantidaeParcelas+"X de R$ "+dados.valorParcela.toFixed(2));
    $('#enderecoEntrega').html(dados.endereco);

    while($("[name='linhaDetalhe']").length > 0) $('#linhaDetalhe').remove();
    dados.itens.forEach(item => {
        $('#itensDetalhePedido').append(
            '<div class="linha-item-detalhe-pedido" name="linhaDetalhe" id="linhaDetalhe">'+
                '<div class="coluna-1-detalhe">'+
                    '<img src="'+item.imagem+'" width="50px">'+
                '</div>'+

                '<div class="coluna-2-detalhe">'+
                    '<p class="texto-item-detalhe-pedido">'+item.descricaoProduto+'</p>'+
                '</div>'+

                '<div class="coluna-3-detalhe">'+
                    '<p class="texto-item-detalhe-pedido">'+item.quantidade+'</p>'+
                '</div>'+

                '<div class="coluna-4-detalhe">'+
                    '<p class="texto-item-detalhe-pedido">'+item.precoFinal.toFixed(2)+'</p>'+
                '</div>'+
            '</div>'
        );
    })

    $("[name='btnsDetalhe']").remove();
    $('#footerBtnsDetalhePedido').append(
        '<button class="btn-confirmar" name="btnsDetalhe" onclick="alterarStatusPedido('+dados.codigo+', 1)">Confirmar</button>'+
        '<button class="btn-recusar" name="btnsDetalhe" onclick="alterarStatusPedido('+dados.codigo+', 2)">Recusar</button>'
    );

    if(dados.status != "Aguardando confirmação")  $("[name='btnsDetalhe']").css('display', "none");
    else $("[name='btnsDetalhe']").css('display', "initial");
}