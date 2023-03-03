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
    if(verificarLogin()) location.href="meusDados.html";
    else{
        renderizarFormLogin(1);
        gerarMessageBox("rgb(253, 214, 214)", "É necessário fazer login para acessar seus dados!!", "Ok");
    }
}

function gerarMessageBox(cor, mensagem, textobtn, acesso){
    $('#esconder').addClass('ativo')
    $('#mensagem').css("transform", "translateY(250px)");
    $('#mensagem').css("background", cor);

    $('#textoMensagem').html(mensagem);
    $('#btnMessage').html(textobtn);

    if(acesso){
        renderizarFormCadastro(2);
        renderizarFormLogin(2);
    }
}

function fecharMessageBox(){
    $('#esconder').removeClass('ativo')
    $('#mensagem'). css("transform", "translateY(-250px)");

    if($('#btnMessage').html() == "Prosseguir") location.reload();
}