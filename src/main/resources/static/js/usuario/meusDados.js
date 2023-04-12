window.onload = () => {
    alterarSessao();
    if(verificarAutorizacao()) listarDados();
}

function listarDados(){
    $.ajax({
        method: "GET",
        url: "/usuarios/"+localStorage.getItem('usuario'),
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Authorization", 'Bearer '+ localStorage.getItem('token'));
        }
    }).done(function (dados) {
        $("#nomeUsuarioExibir").html(dados.nome.split(" ")[0]);
        $("#nomeExibir").html(dados.nome);
        $("#dataExibir").html(dados.dataNascimento);
        $("#cpfExibir").html(dados.cpf);
        $("#emailExibir").html(dados.email);
        $("#celularExibir").html(dados.celular);
        $("#cepExibir").html(dados.cep);
        $("#estadoExibir").html(dados.estado);
        $("#cidadeExibir").html(dados.cidade);
        $("#bairroExibir").html(dados.bairro);
        $("#ruaExibir").html(dados.rua);
        $("#numeroExibir").html(dados.numero);
        $("#complementoExibir").html(dados.complemento);
    }).fail(function (err)  {
        tratarErro(err);
    });
}