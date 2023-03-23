function travarTela() {
    document.documentElement.style.overflow = 'hidden';
    document.body.scroll = "no";
}

function destravarTela() {
    document.documentElement.style.overflow = 'auto';
    document.body.scroll = "yes";
}

function gerarMessageBox(cor, mensagem, textoBtn, acesso){
    $('#esconder').addClass('ativo')
    $('#mensagem').css("transform", "translateY(250px)");
    $('#mensagem').css("background", cor);

    $('#textoMensagem').html(mensagem);
    $('#btnMessage').html(textoBtn);

    if(acesso) $('#btnMessage').hide();
    else $('#aMessage').hide();

    travarTela();
}

function fecharMessageBox(){
    destravarTela();
    $('#esconder').removeClass('ativo')
    $('#mensagem'). css("transform", "translateY(-250px)");

    if($('#btnMessage').html() == "Ok") location.reload();
    if($('#btnMessage').html() == "Prosseguir") location.href="menuGestao.html";
}

function ir(){
$('#pagina').html('<h1>Ola</h1>');
    //console.log("Oi")
    /*$.ajax({
        method: "GET",
        url: "/gestaoProdutos",
        success: function (dados){
            console.log(dados)
            $('#pagina').html(dados);
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao listar: " +xhr.responseText);
    });*/
}