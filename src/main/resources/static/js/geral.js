function verificarLogin(){
    if(localStorage.getItem('logado') == 'logado') return true;
}

function renderizarQuantidade(quantidade){
    if(verificarLogin()){
        localStorage.setItem('quantidadeItens', quantidade);
        $('#quantidade-carrinho').html(localStorage.getItem('quantidadeItens'));
    }
    else $('#quantidade-carrinho').html("0");
}

function abrirMeusDados(){
    (verificarLogin()) ? location.href="meusDados.html":
    gerarMessageBox("rgb(253, 214, 214)", "É necessário fazer login para acessar seus dados!!", "Ok");
}

function abrirMeusPedidos(){
    (verificarLogin()) ? location.href="meusPedidos.html":
    gerarMessageBox("rgb(253, 214, 214)", "É necessário fazer login para acessar seus pedidos!!", "Ok");
}

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

    if(acesso){
        renderizarFormCadastro(2);
        renderizarFormLogin(2);
    }

    travarTela();
}

function fecharMessageBox(){
    destravarTela();
    $('#esconder').removeClass('ativo')
    $('#mensagem'). css("transform", "translateY(-250px)");

    if($('#btnMessage').html() == "Prosseguir") location.reload();
}