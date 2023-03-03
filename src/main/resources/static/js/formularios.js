//Form cadastro
function renderizarFormCadastro(acao){
    if(acao == 1){
        $('#containerCadastro').addClass('ativo');
        travarTela();
    }
    if(acao == 2){
        $('#containerCadastro').removeClass('ativo');
        destravarTela();
    }
}

//Form login
function renderizarFormLogin(acao){
    if(acao == 1){
        $('#containerLogin').addClass('ativo');
        travarTela();
    }
    if(acao == 2){
        $('#containerLogin').removeClass('ativo');
        destravarTela();
    }
}

function fazerLogin(){
    if($('#email').val().length == 0 || $('#senha').val().length == 0) gerarMessageBox("rgb(253, 214, 214)", "Por favor preencha os campos corretamente!!", "Tentar novamente");
    else{
        $.ajax({
            method: "POST",
            url: "usuario/email/"+$('#email').val().trim()+"/senha/"+$('#senha').val().trim(),
            success: function (dados){
                if(dados.nome != null){
                    localStorage.setItem('logado', 'logado');
                    localStorage.setItem('codigo', dados.codigo);
                    localStorage.setItem('nome', dados.nome.split(" ")[0]);
                    localStorage.setItem('quantidadeItens', dados.quantidadeItens);
                    let endereco = dados.cep+", "+dados.cidade+" - "+dados.estado+", "+dados.bairro+", "+dados.rua+", "+dados.numero+", "+dados.complemento;
                    localStorage.setItem('endereco', endereco);

                    gerarMessageBox("rgb(214, 253, 226)", "Logado com sucesso!!", "Prosseguir", true);
                }
                else gerarMessageBox("rgb(253, 214, 214)", "Credenciais incorretas!!", "Tentar novamente");
            }
        }).fail(function(xhr, status, errorThrown){
            alert("Erro ao salvar: " +xhr.responseText);
        });
    }
}

function sair(){
    localStorage.logado="";
    location.reload();
}

function buscarEnderecoPorCEP(){
    if($('#cep').val().length != 8) gerarMessageBox("rgb(253, 214, 214)", "Por favor digite o CEP completo", "Tentar novamente");
    else{
        $.ajax({
            method: "GET",
            url: "https://viacep.com.br/ws/"+$('#cep').val()+"/json/",
            success: function (dados){
                $('#estado').val(dados.uf);
                $('#cidade').val(dados.localidade);
                $('#bairro').val(dados.bairro);
                $('#rua').val(dados.logradouro);
                if(dados.uf == null) gerarMessageBox("rgb(253, 214, 214)", "CEP inexistente", "Ok");
            }
        }).fail(function(xhr, status, errorThrown){
            alert("Erro ao sallet: " +xhr.responseText);
        });
    }
}

function cadastrarUsuario(){
    if(validarEtapa2()){
        let dataNascimento = $('#diaDataNascimento').val()+"/"+$('#mesDataNascimento').val()+"/"+$('#anoDataNascimento').val();
        $.ajax({
            method: "POST",
            url: "/usuario",
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
                if(dados.nome != null) gerarMessageBox("rgb(214, 253, 226", "Cadastro concluído com sucesso!!", "Prosseguir", true);
                else gerarMessageBox("rgb(253, 214, 214)", "Este email já foi cadastrado!!", "Tentar novamente");
            }
        }).fail(function(xhr, status, errorThrown){
            alert("Erro ao salvar: " +xhr.responseText);
        });
    }
}

function preencherEndereco(){
    renderizarFormCadastro(1);
    $.ajax({
        method: "GET",
        url: "/usuario/"+localStorage.getItem('codigo'),
        success: function (dados){
            $('#cep').val(dados.cep);
            $('#estado').val(dados.estado);
            $('#cidade').val(dados.cidade);
            $('#bairro').val(dados.bairro);
            $('#rua').val(dados.rua);
            $('#numero').val(dados.numero);
            $('#complemento').val(dados.complemento);
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao sallet: " +xhr.responseText);
    });
}

function alterarEndereco(){
    $.ajax({
        method: "PUT",
        url: "/usuario/"+localStorage.getItem('codigo'),
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
        success: function (dados){
            let endereco = dados.cep+", "+dados.cidade+" - "+dados.estado+", "+dados.bairro+", "+dados.rua+", "+dados.numero+", "+dados.complemento;
            localStorage.setItem('endereco', endereco);
            gerarMessageBox("rgb(214, 253, 226", "Endereço alterado com sucesso!!", "Prosseguir");
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao sallet: " +xhr.responseText);
    });
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
    let dias;

    if(mes == 01 || mes == 03 || mes == 05 ||mes == 07 ||
       mes == 08 || mes == 10 || mes == 12) dias = 31;
    else if(mes == 04 || mes == 06 || mes == 09 ||mes == 11) dias = 30;
    else dias = 28;

    for(var i = 0; i <= 31; i++ )$('#dia').remove();
    for(var i = 1; i <= dias; i++){
        let dia = "0";
        if(i <= 9) dia += i;
        else dia = i;
        $('#diaDataNascimento').append(
            '<option id="dia" name="b">'+dia+'</option>'
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