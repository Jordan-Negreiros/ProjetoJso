<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<center style="padding-top: 5%">
	<h1 style="font-size: 50px">Seja Bem Vindo ao sistema em JSP</h1>
	<table>
		<tr>
			<td>
				<a href="salvarUsuario?acao=listartodos">
					<img alt="Cadastro de Usuario" title="Cadastro de Usuario" src="resources/img/usuario.png" width="250px" height="180px">
				</a>
			</td>
			<td>
				<a href="salvarProduto?acao=listartodos">
					<img alt="Cadastro de Produtos" title="Cadastro de Produtos" src="resources/img/produto2.png" width="250px" height="180px">
				</a>
			</td>
		</tr>
		<tr>
			<td align="center">Cadastro Usuário</td>
			<td align="center">Cadastro Produto</td>
		</tr>
	</table>
</center>
</body>
</html>