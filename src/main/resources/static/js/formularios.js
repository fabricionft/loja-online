//Form cadastro
function renderizarFormCadastro(acao){
    if(acao == 1)document.getElementById('containerCadastro').classList.add('ativo');
    if(acao == 2)document.getElementById('containerCadastro').classList.remove('ativo');
}

//Form login
function renderizarFormLogin(acao){
    if(acao == 1)document.getElementById('containerLogin').classList.add('ativo');
    if(acao == 2)document.getElementById('containerLogin').classList.remove('ativo');
}

function fazerLogin(){
    $.ajax({
        method: "GET",
        url: "usuario/email/"+$('#email').val()+"/senha/"+$('#senha').val(),
        success: function (dados){
            if(dados.codigo != null){
                localStorage.setItem('logado', 'logado');
                localStorage.setItem('codigo', dados.codigo);
                localStorage.setItem('nome', dados.nome.split(" ")[0]);
                alert("Logado com sucesso");
                location.reload();
            }
            else alert("Credenciais erradas ou inexistentes, tente novamente!")
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function sair(){
    localStorage.logado="";
    location.reload();
}

function buscarEnderecoPorCEP(){
    $.ajax({
        method: "GET",
        url: "https://viacep.com.br/ws/"+$('#cep').val()+"/json/",
        success: function (dados){
            $('#estado').val(dados.uf);
            $('#cidade').val(dados.localidade);
            $('#bairro').val(dados.bairro);
            $('#rua').val(dados.logradouro);
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function cadastrarUsuario(){
    $.ajax({
        method: "POST",
        url: "/usuario",
        data: JSON.stringify(
        {
            nome: $('#nome').val(),
            dataNascimento: $('#dataNascimento').val(),
            cpf: $('#cpf').val(),
            celular: $('#celular').val(),
            email: $('#emailCadastro').val(),
            senha: $('#senhaCadastro').val(),
            cep: $('#cep').val(),
            estado: $('#estado').val(),
            cidade: $('#cidade').val(),
            bairro:  $('#bairro').val(),
            rua: $('#rua').val(),
            numero: $('#numero').val(),
            complemento: $('#complemento').val()
        }),
        contentType: "application/json; charset-utf8",
        success: function (dados){
            alert(dados)
            location.reload();
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
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
            alert("ENdereço alterado com sucesso!!")
            location.reload();
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao sallet: " +xhr.responseText);
    });
}

$(document).ready(function(){
    $('.step').hide();
    $('.step').first().show();
});

$('#next').click(function (){
    $('#step-1').hide().next().show();
});

$('#back').click(function (){
    $('#step-2').hide().prev().show();
    $('#nome').val("testo");
});