<jsp:useBean id="calcula" class="br.com.projetojsp.beans.BeanCursoJsp" type="br.com.projetojsp.beans.BeanCursoJsp" scope="page"></jsp:useBean>


<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>




<!DOCTYPE html>
<html>

<head>
	<meta charset="ISO-8859-1">
	<title>Insert title here</title>
	<link rel="stylesheet" href="resources/css/estilo.css">
</head>

<body>
	<div class="login-page" style="padding-top: 3%">
		<center><span><b>USUÁRIO:</b> admin <br/><b>SENHA:</b> admin</span></center><br/>
		<div class="form">
			<form action="LoginServlet" method="post" class="login-form">
				Login:
				<input type="text" id="login" name="login">
				<br />
				Senha:
				<input type="password" id="senha" name="senha">
				<br />
				<button type="submit" value="Logar">LOGAR</button>
			</form>
		</div>
	</div>
</body>

</html>