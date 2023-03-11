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

    travarTela();
}

function fecharMessageBox(){
    destravarTela();
    $('#esconder').removeClass('ativo')
    $('#mensagem'). css("transform", "translateY(-250px)");

    if($('#btnMessage').html() == "Ok") location.reload();
    if($('#btnMessage').html() == "Prosseguir") location.href="menuGestao.html";
}