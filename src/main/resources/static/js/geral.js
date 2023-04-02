function verificarLogin(){
    if(localStorage.getItem('logado') == 'logado') return true;
    else return false;
}

function renderizarQuantidade(valor){
    localStorage.setItem('quantidadeItens', valor);
    let quantidade = (verificarLogin()) ? localStorage.getItem('quantidadeItens') : 0;
    $('#quantidade-carrinho').html(quantidade);
}

function alterarSessao(){
    let mensagem = (verificarLogin()) ? "Olá "+localStorage.getItem('nome') : "Faça login";
    $("#estadoUSuario").html(mensagem);
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

function tratarErro(err){
    if(err.status == 403) gerarMessageBox(2, "Sem autorização:<br> Seu token expirou ou não existe!! Para conseguir um novo deslogue e faça login novamente!", "Ok");
    else gerarMessageBox(2, err.responseJSON.mensagem, "Ok");
}

function gerarMessageBox(cor, mensagem, textoBtn, acesso){
    let corDeFundo = (cor == 1) ? "rgb(214, 253, 226)" : "rgb(253, 214, 214)";

    $('#esconder').addClass('ativo')
    $('#mensagem').css("transform", "translateY(250px)");
    $('#mensagem').css("background", corDeFundo);

    $('#textoMensagem').html(mensagem);
    $('#btnMessage').html(textoBtn);

    travarTela();
}

function fecharMessageBox(){
    destravarTela();
    $('#esconder').removeClass('ativo')
    $('#mensagem'). css("transform", "translateY(-250px)");

    if($('#btnMessage').html() == "Prosseguir") location.reload();
}