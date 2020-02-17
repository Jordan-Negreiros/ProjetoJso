<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Cadastro de Usuário</title>
    <link rel="stylesheet" href="resources/css/cadastro.css">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery.maskedinput/1.4.1/jquery.maskedinput.min.js"></script>
</head>
<body>
<a href="acessoliberado.jsp"><img src="resources/img/home.png" alt="Inicio" title="Inicio" width="30px" height="30px"></a>
<a href="index.jsp"><img src="resources/img/sair.png" alt="Sair" title="Sair" width="30px" height="30px"></a>
<center>
    <h1>Cadastro de Telefones</h1>
</center>
<center>
    <h3 style="color: #EF3B3A">${msgErro}</h3>
    <h3 style="color: #4CAF50">${msgSalvo}</h3>
</center>
<form action="salvarTelefones" method="post" id="formUser" onsubmit="return validarCampos() ? true : false">
    <ul class="form-style-1">
        <li>
            <table>
                <tr>
                    <td>Usuário:</td>
                    <td><input type="text" readonly="readonly" id="id" name="id" class="field-long"
                               value="${userEscolhido.id}"></td>
                    <td><input type="text" readonly="readonly" id="nome" name="nome" class="field-long"
                               value="${userEscolhido.nome}"></td>
                </tr>
                <tr>
                    <td>Número</td>
                    <td><input type="text" id="telefone" name="telefone" class="telefone" placeholder="Insira um novo Telefone"></td>
                    <td>
                        <select id="tipo" name="tipo" style="width: 183px;">
                            <option>Casa</option>
                            <option>Contato</option>
                            <option>Celular</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <input type="submit" value="Salvar" name="salvar" id="salvar" style="width: 183px;">
                    </td>
                    <td>
                        <input type="submit" value="Voltar" name="voltar" id="voltar"
                               onclick="document.getElementById('formUser').action = 'salvarTelefones?acao=voltar'"
                               style="width: 183px;">
                    </td>
                </tr>
            </table>
        </li>
    </ul>
</form>
<div class="container">
    <table class="responsive-table">
        <caption>Usuários cadastrados</caption>
        <tr>
            <th>Id</th>
            <th>Número</th>
            <th>Tipo</th>
            <th>Excluir</th>
        </tr>
        <c:forEach items="${telefones}" var="fone">
            <tr>
                <td style="width: 150px"><c:out value="${fone.id}">
                </c:out></td>
                <td style="width: 150px"><c:out value="${fone.numero}">
                </c:out></td>
                <td><c:out value="${fone.tipo}"></c:out></td>

                <td><a href="salvarTelefones?acao=deleteFone&foneId=${fone.id}"><img
                        src="resources/img/excluir.png" alt="excluir" title="Excluir"
                        width="20px" height="20px" onclick="return confirm('Confirmar a exclusão?')"> </a></td>
            </tr>
        </c:forEach>
    </table>
</div>
<script type="text/javascript">
    function validarCampos() {

        if (document.getElementById("numero").value == '') {
            alert('Informe o numero');
            return false;
        } else if (document.getElementById("tipo").value == '') {
            alert('Informe o tipo')
            return false;
        }
        return true;
    }
    jQuery("input.telefone")
        .mask("(99) 9999-9999?9")
        .focusout(function (event) {
            var target, phone, element;
            target = (event.currentTarget) ? event.currentTarget : event.srcElement;
            phone = target.value.replace(/\D/g, '');
            element = $(target);
            element.unmask();
            if(phone.length > 10) {
                element.mask("(99) 99999-999?9");
            } else {
                element.mask("(99) 9999-9999?9");
            }
        });
</script>
</body>
</html>