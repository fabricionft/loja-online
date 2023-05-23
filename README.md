<h1 align="center">Futshop 🛒</h1>
<img src="assets/inicial.png" width="100%$">

<h2>Sobre o projeto 🔎</h2>
Futshop é um site de vendas de produtos, criado com o intuito de facilitar a vida de pequenos vendedores, de forma que assim não precisem se preocupar com a frequente atualização dos dados de seu estoque ou a coleta de informações do cliente (informações de contato e envio). Até o momento este é meu projeto mais bem trabalhado, tanto no back, como no front.
<br><br>

<h2>Tecnologias utilizadas 💻</h2>
<ul>
  <li>Java + Spring Boot;</li>
  <li>Javascript + JQuery;</li>
  <li>HTML + CSSS;</li>
  <li>Banco de dados MySQL.</li>
</ul>
<br>

<h2>Dependências Maven utilizadas ♦️</h2>
<ul>
  <li>Spring Web;</li>
  <li>Spring Data JPA;</li>
  <li>Spring Security;</li>
  <li>MySQL Driver;</li>
  <li>Lombok;</li>
  <li>DevTools;</li>
  <li>Java JWT;</li>
  <li>Validation;</li>
  <li>ModelMapper.</li>
</ul>
<br>
 
<h2>Mecânica de funcionamento do site :hammer: </h2> 

<strong>1</strong> - O cliente cria sua conta passando seus dados de contato e endereço;<br>
<img src="assets/camisa.png" width="300px">

<strong>2</strong> - Com sua conta criada, o cliente loga e adquire a possibilidade de adicionar/remover itens do seu carrinho;<br>
<img src="assets/carrinho.png" width="300px">

<strong>3</strong> - Com ao menos um item no carrinho, o cliente ganha direito a fazer um pedido, passando a forma de pagamento e o parcelamento;<br><br>
<img src="assets/form.png" width="300px">

<strong>4</strong> - Com um pedido confirmado, o mesmo é gerado, salvo e exibido instantaneamente no menu administrativo;<br>
<img src="assets/pedido.png" width="300px">

<strong>5</strong> - O administrador ao receber o pedido, pode aceitá-lo ou recusá-lo. Caso aceite, ele deverá através de algum dos dados de comunicação do cliente estabelecer contato com o mesmo, para lhe passar a cobrança selecionada. Caso rejeite, deverá devolver uma mensagem explicando o motivo.<br>
<img src="assets/pedido-adm.png" width="500px">
<br><br>
 
<h2>Ações de um usuário comum</h2> 
<ul>
  <li>Gerar carrinhos e pedidos;</li>
  <li>Visualizar e editar dados pessoais;</li>
  <li>Visualizar todos seus pedidos e seus detalhamentos. </li>
</ul>
<br>
 
<h2>Ações de um usuário administrador</h2>
<ul>
  <li>Manipular os produtos do site (adição, edição e exclusão) e os pedidos dos clientes (confirmação ou rejeição). </li>
</ul>
<br>
 
<h2>Segurança do site :lock:</h2> 
O projeto possui um sistema de tokens JWT, o que faz com que somente usuários logados possam acessar certas rotas e métodos, e somente usuários administradores possam acessar/controlar o menu administrativo. Além disso, o sitestema também conta com conceitos de DTO's e criptografia, para uma maior proteção às suas entidades.
<br><br>

<h2>Passo a passo para rodar em seu PC</h2>
<ul>
  <li>Possua uma JDK 17 ou superior instalada em seu PC;</li>
  <li> Baixe ou clone o projeto em seu PC;</li>
  <li>Configure algum banco de dados MySQL, seja ele local ou de algum serviço externo (recomendo os do site RailWay) dentro do applications.properties. Para isso     você precisa   do host, nome, porta, usuario e senha do banco de dados;</li>
  <li>Dê build ou compile no projeto usando alguma IDE (recomendo Intelij), para baixar todas as dependências;</li>
  <li>Por fim, dê start na classe principal do projeto (FutshopApplication) e pronto, ele já funcionará.</li>
</ul>
<br>
 
<h2>Link video 📺</h2>
https://youtu.be/ljKxzoam1VA
<br><br>

<h2>Link site :zap:</h2>
https://futshop-nft.up.railway.app/
<br><br>

<h2>Autor :raising_hand:</h2>

| [<img src="assets/perfil.jpg" width=115><br><sub>Fabrício Ferreira Tavares</sub>](https://github.com/fabricionft) | 
| :---: | 
