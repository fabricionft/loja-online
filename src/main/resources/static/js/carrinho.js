window.onload = () => {
    if(localStorage.getItem('logado') == 'logado'){
        $("#nomeUsuario").append('<p class="usuario-logado">Olá '+localStorage.getItem('nome')+'</p>');
        listarItens();
    }
    else {
        $("#nomeUsuario").append('<p class="usuario-logado">Faça login</p>');
    }
}

function listarItens(){
    for(var i = 0; i<= 600; i++) $('#linha-carrinho').remove();
    var valor = 0;
    $('#total-carrinho').remove();
    $.ajax({
        method: "GET",
        url: "usuario/produtos/"+localStorage.getItem('codigo'),
        success: function (dados){
            dados.forEach(item => {
                listaItens(item);
                valor += item.precoFinal;
            });
            $('#texto-carrinho').append('<p class="total-carrinho" id="total-carrinho">R$ '+valor.toFixed(2)+'</p>');
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function adcionaItens(id){
    if(localStorage.getItem('logado') == 'logado'){
        $.ajax({
            method: "POST",
            url: "carrinho/produto/"+id+"/usuario/"+localStorage.getItem('codigo'),
            success: function (dados){
                console.log("Sucesso")
            }
        }).fail(function(xhr, status, errorThrown){
            alert("Erro ao salvar: " +xhr.responseText);
        });
    }
    else alert("Faça login para poder adcionar um produto ao carrinho!")
}

function alterar(codigo, acao){
    if(acao == 1) sinal = "+";
    if(acao == 2) sinal = "-";
    $.ajax({
        method: "PUT",
        url: "carrinho/produto/"+codigo+"/usuario/"+localStorage.getItem('codigo')+"/acao/"+sinal,
        success: function (dados){
            var valor = 0;
            dados.forEach(item => {
                valor += item.precoFinal;
                $('#quantidadeItens-'+item.codigo).html(item.quantidade);
                $('#valorFinal-'+item.codigo).html("R$ "+item.precoFinal.toFixed(2));
            });
            $('#total-carrinho').html("R$ "+valor.toFixed(2));
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function excluir(){
    $.ajax({
        method: "DELETE",
        url: "carrinho/usuario/"+localStorage.getItem('codigo'),
        success: function (dados){
            listarItens();
        }
    }).fail(function(xhr, status, errorThrown){
        alert("Erro ao salvar: " +xhr.responseText);
    });
}

function listaItens(dados){
    $('#itensCarrinho').append(
        '<tr class="linha-carrinho" id="linha-carrinho">'+
            '<td class="coluna-1">'+
                '<div class="img-carrinho"><img src="img/camisa-liverpool.png" class="imgs-carrinho"></div>'+
                '<div class="desc-carrinho">'+dados.descricaoProduto+' - '+dados.tamanho+'</div>'+
            '</td>'+
            '<td class="coluna-2">'+
                '<div class="texto-quantidade-carrinho"><p id="quantidadeItens-'+dados.codigo+'">'+dados.quantidade+'</p></div>'+
                '<div class="btns-quantidade-carrinho">'+
                    '<button class="btn-quantidade" onclick="alterar('+dados.codigo+","+1+')">+</button>'+
                    '<button class="btn-quantidade" onclick="alterar('+dados.codigo+","+2+')">-</button>'+
                '</div>'+
            '</td>'+
            '<td class="coluna-3">R$ '+dados.precoUnitario.toFixed(2)+'</td>'+
            '<td class="coluna-4" id="valorFinal-'+dados.codigo+'">R$ '+dados.precoFinal.toFixed(2)+'</td>'+
        '</tr>'
    );
}