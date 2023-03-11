function fazerLoginComoAdmin(){
    $.ajax({
        method: "POST",
        url: "/usuario/login",
        data: JSON.stringify(
        {
            email: $('#email').val().trim(),
            senha: $('#senha').val().trim()
        }),
        contentType: "application/json; charset-utf8",
        success: function (dados){
            console.log(dados);
            if(dados != "Não é um admin"){
                gerarMessageBox("rgb(214, 253, 226)", "Autenticação bem sucedida!! Você recebeu um token de autenticação que funcionará pelos próximos 60 min's. Após tal intervalo o mesmo ira expirar e então será necessário fazer login novamente para receber outro!", "Prosseguir");
                localStorage.setItem('token', dados);
            }
            else {
                gerarMessageBox("rgb(253, 214, 214)", "Seu usuário não é um usuário administrador!!", "Ok");
            }
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}