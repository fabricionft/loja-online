window.onload = () => listar();

function listar(){
    $.ajax({
        method: "GET",
        url: "pegarImagem",
        data: "codigo="+1,
        success: function (dados){
            console.log(dados)
            criaImagem(dados);
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function criaImagem(img){
    $('#img').attr('src', `data:image/png;base64,${img}`);
}