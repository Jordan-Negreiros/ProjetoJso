<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Cadastro de Produto</title>
    <script src="resources/javascript/jquery.min.js" type="text/javascript"></script>
    <script src="resources/javascript/jquery.maskMoney.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="resources/css/cadastro.css">
</head>
<body>
<a href="acessoliberado.jsp"><img src="resources/img/home.png" alt="Inicio" title="Inicio" width="30px" height="30px"></a>
<a href="index.jsp"><img src="resources/img/sair.png" alt="Sair" title="Sair" width="30px" height="30px"></a>
<center>
    <h1>Cadastro de Produto</h1>
</center>
<center>
    <h3 style="color: #EF3B3A">${msgErro}</h3>
    <h3 style="color: #4CAF50">${msgSalvo}</h3>
</center>
<form action="salvarProduto" method="post" id="formProduto" onsubmit="return validarCampos() ? true : false">
    <ul class="form-style-1">
        <li>
            <table>
                <tr>
                    <td>Código:</td>
                    <td><input type="text" readonly="readonly" id="id" name="id"
                               value="${product.id}"></td>
                </tr>
                <tr>
                    <td>Nome:</td>
                    <td><input type="text" id="nome" name="nome"
                               value="${product.nome}" placeholder="Informe o nome" maxlength="100"></td>
                </tr>
                <tr>
                    <td>Quantidade:</td>
                    <td><input type="text" id="quantidade" name="quantidade"
                               value="${product.quantidade}" placeholder="Informe a quantidade" maxlength="9"></td>
                </tr>
                <tr>
                    <td>Preço:</td>
                    <td><input type="text" id="preco" name="preco"
                               value="${product.valorEmTexto}" placeholder="Informe o preço" maxlength="20"
                               data-thousands="." data-decimal=","></td>
                </tr>
                <tr>
                    <td>
                        Categoria:
                    </td>
                    <td>
                        <select id="categorias" name="categorias_id">
                            <c:forEach items="${categorias}" var="cat">
                                <option value="${cat.id}" id="${cat.id}"
                                        <c:if test="${cat.id == product.categoria_id}">
                                            <c:out value="selected=selected"/>
                                        </c:if>>
                                    ${cat.nome}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" value="Salvar">
                        <input type="submit" value="Cancelar"
                               onclick="document.getElementById('formProduto').action = 'salvarProduto?acao=reset'">
                    </td>
                </tr>
            </table>
        </li>
    </ul>
</form>
<div class="container">
    <table class="responsive-table">
        <caption>Produtos cadastrados</caption>
        <tr>
            <th>Id</th>
            <th>Nome</th>
            <th>Quantidade</th>
            <th>Preço</th>
            <th>Excluir</th>
            <th>Editar</th>
        </tr>
        <c:forEach items="${products}" var="product">
            <tr>
                <td style="width: 150px"><c:out value="${product.id}">
                </c:out></td>
                <td style="width: 150px"><c:out value="${product.nome}">
                </c:out></td>
                <td><c:out value="${product.quantidade}"></c:out></td>
                <fmt:setLocale value = "pt_BR"/>
                <td><fmt:formatNumber type="currency" maxFractionDigits="2" value="${product.preco}"/></td>

                <td><a href="salvarProduto?acao=delete&product=${product.id}"><img
                        src="resources/img/excluir.png" alt="excluir" title="Excluir"
                        width="20px" height="20px" onclick="return confirm('Confirmar a exclusão?')"> </a></td>
                <td><a href="salvarProduto?acao=editar&product=${product.id}"><img
                        alt="Editar" title="Editar" src="resources/img/editar.png"
                        width="20px" height="20px"></a></td>
            </tr>
        </c:forEach>
    </table>
</div>
<script type="text/javascript">
    function validarCampos() {
        if (document.getElementById('nome').value == '') {
            alert('informe o nome do produto');
            return false;
        } else if (document.getElementById('quantidade').value == '' || document.getElementById('quantidade').value == '0') {
            alert('informe a quantidade');
            return false;
        } else if (document.getElementById('preco').value == '') {
            alert('informe o preço');
            return false;
        }
        return true;
    }

    $(function() {
        $('#preco').maskMoney();
    });

    $(document).ready(function () {
        $("#quantidade").keyup(function () {
            $("#quantidade").val(this.value.match(/[0-9]*/));
        });
    });

</script>
</body>
</html>
