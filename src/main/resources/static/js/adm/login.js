$("[name='inputLogin']").keyup(function(){
    ($('#email').val().length && $('#senha').val().length) ? $('#btnLogin').removeAttr("disabled") : $('#btnLogin').attr('disabled', "disabled");
});

function fazerLogin(){
    $.ajax({
        method: "POST",
        url: "usuarios/email/"+$('#email').val().trim()+"/senha/"+$('#senha').val().trim(),
        success: function (dados){
            if(dados.admin === true){
                localStorage.setItem('token', dados.token);
                localStorage.setItem('admin', dados.admin);
                location.href="menuGestao.html";
            }
            else gerarMessageBox(2, "Seu usuário não é um usuário administrador!", "Entendido");
        }
    }).fail(function(err){
        gerarMessageBox(2, err.responseJSON.message, "Tentar novamente");
    });
}