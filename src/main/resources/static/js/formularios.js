function renderizarFormulario(formulario){
    fecharFormulario();
    travarTela();
    if(formulario == "resumoPedido"){
        if($('#totalCarrinho').html() > 0){
            $('#containerFormulario').addClass('ativo');
            $("#"+formulario).show();
             $('#enderecoPedido').html(localStorage.getItem('endereco'));
        }
        else gerarMessageBox(2, "É necessário ter ao menos um item no carrinho para fazer um pedido!", "Ok");
    }
    else{
        $('#containerFormulario').addClass('ativo');
        $("#"+formulario).show();
    }
}

function fecharFormulario(){
    $('#containerFormulario').removeClass('ativo');
    $("[name='formulario']").hide();
    destravarTela();
}

$("[name='inputLogin']").keyup(function(){
    ($('#email').val().length && $('#senha').val().length) ? $('#btnLogin').removeAttr("disabled") : $('#btnLogin').attr('disabled', "disabled");
});

function fazerLogin(){
    $.ajax({
        method: "POST",
        url: "usuarios/email/"+$('#email').val().trim()+"/senha/"+$('#senha').val().trim(),
        success: function (dados){
            localStorage.setItem('token', dados);
            buscarDadosUsuario($('#email').val().trim());
        }
    }).fail(function(err){
        gerarMessageBox(2, err.responseJSON.mensagem, "Tentar novamente");
    });
}

function buscarDadosUsuario(email){
    $.ajax({
        method: "GET",
        url: "/usuarios/"+email,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
        }
    }).done(function (dados) {
        preencherUsuario(dados);
    }).fail(function (err)  {
        tratarErro(err);
    });
}


function preencherUsuario(dados){
    localStorage.setItem('logado', 'logado');
    localStorage.setItem('codigo', dados.codigo);
    localStorage.setItem('usuario', dados.email);
    localStorage.setItem('nome', dados.nome.split(" ")[0]);
    localStorage.setItem('quantidadeItens', dados.quantidadeItens);
    let endereco = dados.cep+", "+dados.cidade+" - "+dados.estado+", "+dados.bairro+", "+dados.rua+", "+dados.numero+", "+dados.complemento;
    localStorage.setItem('endereco', endereco);

    gerarMessageBox(1, "Usuário logado com sucesso", "Prosseguir");
}


function sair(){
    localStorage.logado="";
    localStorage.codigo="";
    localStorage.token="";
    localStorage.nome="";
    localStorage.quantidadeItens="";
    localStorage.endereco="";

    location.reload();
}

function buscarEnderecoPorCEP(){
    if($('#cep').val().length != 8) gerarMessageBox(2, "Por favor digite o CEP completo", "Tentar novamente");
    else{
        $.ajax({
            method: "GET",
            url: "https://viacep.com.br/ws/"+$('#cep').val()+"/json/",
            success: function (dados){
                if(dados.uf == null) gerarMessageBox(2, "CEP inexistente", "Ok");
                else{
                    $('#estado').val(dados.uf);
                    $('#cidade').val(dados.localidade);
                    $('#bairro').val(dados.bairro);
                    $('#rua').val(dados.logradouro);
                }
            }
        }).fail(function(err){
            alert("Erro ao buscra CEP: " +err.responseText);
        });
    }
}

function cadastrarUsuario(){
    if(validarEtapa2()){
        let dataNascimento = $('#diaDataNascimento').val()+"/"+$('#mesDataNascimento').val()+"/"+$('#anoDataNascimento').val();
        $.ajax({
            method: "POST",
            url: "/usuarios",
            data: JSON.stringify(
            {
                nome: $('#nome').val(),
                dataNascimento: dataNascimento,
                cpf: $('#cpf').val(),
                celular: $('#celular').val(),
                email: $('#emailCadastro').val().trim(),
                senha: $('#senhaCadastro').val().trim(),
                cep: $('#cep').val().trim(),
                estado: $('#estado').val(),
                cidade: $('#cidade').val(),
                bairro:  $('#bairro').val(),
                rua: $('#rua').val(),
                numero: $('#numero').val(),
                complemento: $('#complemento').val()
            }),
            contentType: "application/json; charset-utf8",
            success: function (dados){
                gerarMessageBox(1, "Cadastro concluído com sucesso!!", "Prosseguir", true);
            }
        }).fail(function(err){
            gerarMessageBox(2, err.responseJSON.mensagem, "Tentar novamente");
        });
    }
}

function preencherEndereco(){
    renderizarFormCadastro(1);
    $.ajax({
        method: "GET",
        url: "/usuarios/"+localStorage.getItem('codigo'),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
        }
    }).done(function (dados) {
        $('#cep').val(dados.cep);
        $('#estado').val(dados.estado);
        $('#cidade').val(dados.cidade);
        $('#bairro').val(dados.bairro);
        $('#rua').val(dados.rua);
        $('#numero').val(dados.numero);
        $('#complemento').val(dados.complemento);
    }).fail(function (err)  {
        tratarErro(err);
    });
}

function alterarEndereco(){
    if(validarEtapa2()){
        $.ajax({
            method: "PUT",
            url: "/usuarios/"+localStorage.getItem('codigo'),
            data: JSON.stringify(
            {
                cep: $('#cep').val(),
                estado: $('#estado').val(),
                cidade: $('#cidade').val(),
                bairro: $('#bairro').val(),
                rua: $('#rua').val(),
                numero: $('#numero').val(),
                complemento: $('#complemento').val()
            }),
            contentType: "application/json; charset-utf8",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
            }
        }).done(function (dados) {
            let endereco = dados.cep+", "+dados.cidade+" - "+dados.estado+", "+dados.bairro+", "+dados.rua+", "+dados.numero+", "+dados.complemento;
            localStorage.setItem('endereco', endereco);
            gerarMessageBox(1, "Endereço alterado com sucesso!!", "Prosseguir");
        }).fail(function (err)  {
            tratarErro(err);
        });
    }
}

function alterarSenha(){
    if(validarCamposDeSenha()){
        $.ajax({
            method: "PUT",
            url: "/usuarios/1/senhaAtual/"+$('#senhaAtual').val()+"/senhaNova/"+$('#senhaNova').val(),
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
            }
        }).done(function (dados) {
            gerarMessageBox(1, dados, "Prosseguir");
        }).fail(function (err)  {
            tratarErro(err);
        });
    }
}

$(document).ready(function(){
    $('.step').hide();
    $('.step').first().show();
    gerarAnos();
});

$('#next').click(function (){
    if(validarEtapa1()) $('#step-1').hide().next().show();
});

$('#back').click(function (){
    $('#step-2').hide().prev().show();
});

function gerarAnos(){
    for(var i = 2004; i>= 1940; i--){
        $('#anoDataNascimento').append(
            '<option>'+i+'</option>'
        );
    }
}

function gerarDias(){
    let mes = $('#mesDataNascimento').val();
    let dias = (mes == 01 || mes == 03 || mes == 05 ||mes == 07 ||
                mes == 08 || mes == 10 || mes == 12) ? 31 : 30;
                if(mes == 2) dias = 28;

    while($("[name='dia']").length) $('#dia').remove();
    for(var i = 1; i <= dias; i++){
        let dia = (i <= 9) ? "0"+i : i;
        $('#diaDataNascimento').append(
            '<option id="dia" name="dia">'+dia+'</option>'
        );
    }
}

$('#trocarInputCadastro').click(function (){
    if($('#senhaCadastro').attr('type') == "password"){
        $('#senhaCadastro').attr('type', "text");
        $('#trocarInputCadastro').attr('src', "icon/olhoF.png");
    }
    else{
        $('#senhaCadastro').attr('type', "password");
        $('#trocarInputCadastro').attr('src', "icon/olho.png");
    }
});

$('#trocarInputLogin').click(function (){
    if($('#senha').attr('type') == "password"){
        $('#senha').attr('type', "text");
        $('#trocarInputLogin').attr('src', "icon/olhoF.png");
    }
    else{
        $('#senha').attr('type', "password")
        $('#trocarInputLogin').attr('src', "icon/olho.png");
    }
});

$('#check').click(function (){
    let senhas = $("[name='senhas']");

    if(senhas[0].type == "password")
        for(i = 0; i < senhas.length; i++) senhas[i].type = "text";
    else
        for(i = 0; i < senhas.length; i++) senhas[i].type = "password";
});