window.onload = () => {verificarAutorizacaoAdmin()}

function deslogarAdmin(){
    localStorage.admin="";
    location.href="loginAdmin.html";
}