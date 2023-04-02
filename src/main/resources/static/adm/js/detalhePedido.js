function exibirDetalhe(){
    window.scrollTo(0, 0);
    $('#sectionDetalhePedido').css("display", "initial");
    if($(window).width() < 1000)$('#sectionPedido').css("display", "none");
}

function esconderDetalhe(){
    $('#sectionDetalhePedido').css("display", "none");
    $('#sectionPedido').css("display", "initial");
}

function renderizarDetalhamento(acao, codigo){
    if(acao == 1){
        $.ajax({
            method: "GET",
            url: "/pedidos/pedido/"+codigo,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
            }
        }).done(function (dados) {
            exibirDetalhe();
            preencherDetalhamento(dados)
        }).fail(function (err)  {
            tratarErro(err);
        });

    }
    if(acao == 2) esconderDetalhe();
}

function alterarStatusPedido(codigo, acao){
    let verbo = (acao == 1) ?  "confirmado" : "rejeitado";
    let motivo = ($('#motivo').val().length) ? $('#motivo').val() : "Confirmado";
    console.log(codigo + "  " + acao + "  " + motivo);

    $.ajax({
        method: "PUT",
        url: "/pedidos/pedido/"+codigo+"/acao/"+acao+"/motivo/"+motivo,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
        }
    }).done(function (response) {
        gerarMessageBox("rgb(214, 253, 226)", "Pedido "+verbo+" com sucesso!!", "Ok");
        listarPedidos();
    }).fail(function (err)  {
        tratarErro(err);
    });
}

function renderizarFormRejeicao(acao){
    if(acao == 1){
        $('#containerMotivoRejeicao').addClass('ativo');
        travarTela();
    }
    if(acao == 2){
        $('#containerMotivoRejeicao').removeClass('ativo');
        destravarTela();
    }
}

function rejeitar(codigo){
    renderizarFormRejeicao(1);
    $('#btnRejeicao').remove();
    $('#formMotivoRejeicao').append(
        '<button type="button" class="confirmar-rejeicao" id="btnRejeicao" onclick="alterarStatusPedido('+codigo+', 2)">Confirmar</button>'
    )
}

function preencherDetalhamento(dados){
    $('#nomeCliente').html(dados.nomeCliente);
    $('#cpfCliente').html(dados.cpfCliente);
    $('#emailCliente').html(dados.email);
    $('#celularCliente').html(dados.celular);
    $('#statusPedido').html(dados.status);
    $('#formaDePagamentoPedido').html(dados.pagamento);
    $('#parcelamentoPedido').html(dados.quantidaeParcelas+"X de R$ "+dados.valorParcela.toFixed(2));
    $('#enderecoEntrega').html(dados.endereco);

    while($("[name='linhaDetalhe']").length) $('#linhaDetalhe').remove();
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
        '<button class="btn-recusar" name="btnsDetalhe" onclick="rejeitar('+dados.codigo+')">Recusar</button>'
    );

    if(dados.status != "Aguardando confirmação")  $("[name='btnsDetalhe']").css('display', "none");
    else $("[name='btnsDetalhe']").css('display', "initial");
}