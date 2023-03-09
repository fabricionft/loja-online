window.onload = () =>{
    if(verificarLogin()){
        listarPedidos();
        $("#estadoUSuario").html("Olá "+localStorage.getItem('nome'))
    }
    else $("#estadoUSuario").html("Faça login");
    renderizarQuantidade(localStorage.getItem('quantidadeItens'));
}

function renderizarFormResumoPedido(acao){
    if(acao == 1 && $('#totalCarrinho').html() > 0){
        $('#esconderPedido').addClass('ativo');
        $('#enderecoPedido').html(localStorage.getItem('endereco'));
        travarTela();
    }
    else if(acao == 2){
        $('#esconderPedido').removeClass('ativo');
        $('.select-pedido').val("escolha");
        destravarTela();
    }
    else gerarMessageBox("rgb(253, 214, 214)", "É necessário ter ao menos um item no carrinho para fazer um pedido!", "Ok");
}

function gerarParcelas(){
    let valorTotal = $('#totalCarrinho').html();

    if($('#tipoPagamento').val() == "PIX") tamanho = 1;
    else if(valorTotal > 0 && valorTotal < 300) tamanho = 3;
    else if(valorTotal >= 300 && valorTotal < 600) tamanho = 6;
    else if(valorTotal >= 600 && valorTotal < 1000) tamanho = 9;
    else tamanho = 12;

    for(i = 1; i <= 12; i++) $('#parcela').remove();
    for(i = 1; i <= tamanho; i++){
        const parcela = valorTotal / i;
        $('#quantidadeParcelas').append(
            '<option value="'+i+'" id="parcela">'+i+'x de R$ '+parcela.toFixed(2)+'</>'
        )
    }
}

function confirmarPedido(){
    let tipoPagamento = $('#tipoPagamento').val();
    let quantidadeParcelas = $('#quantidadeParcelas').val();

    if(tipoPagamento != "escolha" && quantidadeParcelas != "escolha"){
        $.ajax({
            method: "POST",
            url: "pedidos/cliente/"+localStorage.getItem('codigo')+"/formaPagamento/"+tipoPagamento+"/quantidadeParcelas/"+quantidadeParcelas,
            success: function (dados){
                if(dados.numero == null) gerarMessageBox("rgb(253, 214, 214)", "Pedido não autorizado. Algum(ns) item(ns) do carrinho estão sem unidades sufioientes no estoque!!", "Prosseguir");
                else{
                     renderizarQuantidade(0);
                     renderizarFormResumoPedido(2);
                     gerarMessageBox("rgb(214, 253, 226", "Pedido realizado com sucesso. Dentre 1-3 dias úteis o dono da página aceitará ou recusará seu pedido, caso aceite, entrará em contato lhe enviando a cobrança no formato de pagamento escolhido!!", "Prosseguir");
                }
           }
        }).fail(function(xhr, status, errorThrown){
            alert("Erro ao fazer pedido: " +xhr.responseText);
        });
    }
    else gerarMessageBox("rgb(253, 214, 214)", "Por favor selecione o tipo de pagamento e a quantidade de parcelas desejada!", "Tentar novamente");
}

function listarPedidos(){
    $.ajax({
        method: "GET",
        url: "/pedidos/cliente/"+localStorage.getItem('codigo'),
        success: function (dados){
            dados.slice().reverse().forEach(item => criarPedido(item));

            let listaStatus = document.getElementsByName('statusPedido');
            for(i = 0; i < listaStatus.length; i++){
                if(listaStatus[i].innerHTML == "Aguardando confirmação") listaStatus[i].style.color="orange"
                else if(listaStatus[i].innerHTML == "Pedido confirmado") listaStatus[i].style.color="green"
                else listaStatus[i].style.color="red"
            }
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function criarPedido(dados){
    $('#containerPedido').append(
        '<div class="pedido">'+
            '<div class="margem-pedidos">'+
                '<p class="subtitulo">Data pedido</p>'+
                '<p class="texto">'+dados.data+'</p>'+

                '<p class="subtitulo">Número pedido</p>'+
                '<p class="texto">#'+dados.numero+'</p>'+

                '<p class="subtitulo">Status pedido</p>'+
                '<p class="texto-status" name="statusPedido">'+dados.status+'</p>'+

                '<p class="subtitulo">Valor pedido</p>'+
                '<p class="texto">R$ '+dados.valor.toFixed(2)+'</p>'+

                '<button class="btn-detalhe" onclick="pegarIdPedido('+dados.codigo+')">Ver detalhamento</button>'+
            '</div>'+
        '</div>'
    );
}

function pegarIdPedido(id){
    localStorage.setItem('codigoPedido', id)
    location.href="detalhePedido.html";
}