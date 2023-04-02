function travarTela() {
    document.documentElement.style.overflow = 'hidden';
    document.body.scroll = "no";
}

function destravarTela() {
    document.documentElement.style.overflow = 'auto';
    document.body.scroll = "yes";
}

function tratarErro(err){
    if(err.status == 403) gerarMessageBox(2, "Sem autorização:<br> Seu token expirou ou não existe!! Para conseguir um novo deslogue e faça login novamente!", "Ok");
    else gerarMessageBox(2, err.responseJSON.mensagem, "Ok");
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
}