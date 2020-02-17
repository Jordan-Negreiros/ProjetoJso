<%@ page import="br.com.projetojsp.beans.BeanCursoJsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="resources/css/cadastro.css"/>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>
    <title>Cadastro de Usuário</title>
</head>
<body>
<!-- Tela de Manipulação da Tabela usuario do BD -->
<a href="acessoliberado.jsp"><img src="resources/img/home.png" alt="Inicio" title="Inicio" width="30px"
                                  height="30px"></a>
<a href="index.jsp"><img src="resources/img/sair.png" alt="Sair" title="Sair" width="30px" height="30px"></a>
<center>
    <h1>Cadastro de Usuário</h1>
    <h3 style="color:orange">${msg}</h3>
</center>
<form action="salvarUsuario" method="post" id="formUser" name="formUser" enctype="multipart/form-data">
    <ul class="form-style-1">
        <li>
            <table>
                <tr>
                    <td>ID:</td>
                    <td><input type="text" readonly="readonly" id="id" name="id" value="${user.id}"/></td>
                    <td>CEP:</td>
                    <td><input type="text" id="cep" name="cep" value="" onblur="consultarCep()" value="${user.cep}"
                               maxlength="10" placeholder="Informe o CEP"/>
                    </td>
                </tr>
                <tr>
                    <td>Login:</td>
                    <td><input type="text" id="login" name="login" value="${user.login}" maxlength="10"
                               placeholder="Informe o login"/></td>
                    <td>Rua:</td>
                    <td><input type="text" id="rua" name="rua" value="${user.rua}" maxlength="20"
                               placeholder="Informe a rua"/></td>
                </tr>
                <tr>
                    <td>Senha:</td>
                    <td><input type="password" id="senha" name="senha" value="${user.senha}"
                               placeholder="Informe o senha" maxlength="10"/></td>
                    <td>Bairro:</td>
                    <td><input type="text" id="bairro" name="bairro" value="${user.bairro}"
                               placeholder="Informe o bairro" maxlength="25"/></td>
                </tr>
                <tr>
                    <td>Nome:</td>
                    <td><input type="text" id="nome" name="nome" value="${user.nome}" maxlength="20"
                               placeholder="Informe o nome"/>
                    </td>
                    <td>Cidade:</td>
                    <td><input type="text" id="cidade" name="cidade" value="${user.cidade}"
                               placeholder="Informe a cidade" maxlength="20"/></td>
                </tr>
                <tr>
                    <td>Estado:</td>
                    <td><input type="text" id="estado" name="estado" value="${user.estado}"
                               placeholder="Informe o estado" maxlength="20"/></td>
                    <td>IBGE:</td>
                    <td><input type="text" id="ibge" name="ibge" value="${user.ibge}" maxlength="20"
                               placeholder="Informe o IBEG"/></td>
                </tr>
                <tr>
                    <td>
                        Perfil:
                    </td>
                    <td>
                        <select id="perfil" name="perfil" style="height: 30px; width: 185px">
                            <option value="nao_informado">[--SELECIONE--]</option>
                            <option value="administrador"
                                    <%
                                        if (request.getAttribute("user") != null) {
                                            BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
                                            if (user.getPerfil().equalsIgnoreCase("administrador")) {
                                                out.print(" ");
                                                out.print("selected=\"selected\"");
                                                out.print(" ");
                                            }
                                        }
                                    %>
                            >Administrador(a)
                            </option>
                            <option value="secretario"
                                    <%
                                        if (request.getAttribute("user") != null) {
                                            BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
                                            if (user.getPerfil().equalsIgnoreCase("secretario")) {
                                                out.print(" ");
                                                out.print("selected=\"selected\"");
                                                out.print(" ");
                                            }
                                        }
                                    %>
                            >Secretário(a)
                            </option>
                            <option value="gerente"
                                    <%
                                        if (request.getAttribute("user") != null) {
                                            BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
                                            if (user.getPerfil().equalsIgnoreCase("gerente")) {
                                                out.print(" ");
                                                out.print("selected=\"selected\"");
                                                out.print(" ");
                                            }
                                        }
                                    %>
                            >Gerente
                            </option>
                            <option value="funcionario"
                                    <%
                                        if (request.getAttribute("user") != null) {
                                            BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
                                            if (user.getPerfil().equalsIgnoreCase("funcionario")) {
                                                out.print(" ");
                                                out.print("selected=\"selected\"");
                                                out.print(" ");
                                            }
                                        }
                                    %>
                            >Funcionário
                            </option>
                        </select>
                    </td>
                </tr>
                <tr style="height: 10px"></tr>
                <tr>
                    <td>
                        Sexo:
                    </td>
                    <td>
                        <input type="radio" name="sexo"
                            <%
                                if (request.getAttribute("user") != null) {
                                    BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
                                    if (user.getSexo().equalsIgnoreCase("masculino")) {
                                        out.print(" ");
                                        out.print("checked=\"checked\"");
                                        out.print(" ");
                                        }
                                }
                            %>
                               value="masculino">Masculino</input>
                        <input type="radio" name="sexo"
                            <%
                                if (request.getAttribute("user") != null) {
                                    BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
                                    if (user.getSexo().equalsIgnoreCase("feminino")) {
                                        out.print(" ");
                                        out.print("checked=\"checked\"");
                                        out.print(" ");
                                        }
                                }
                            %>
                               value="feminino">Feminino</input>
                    </td>
                    <td>
                        Ativo:
                    </td>
                    <td>
                        <input type="checkbox" id="ativo" name="ativo"
                            <%
                                if (request.getAttribute("user") != null) {
                                    BeanCursoJsp user = (BeanCursoJsp) request.getAttribute("user");
                                    if (user.isAtivo()) {
                                        out.print(" ");
                                        out.print("checked=\"checked\"");
                                        out.print(" ");
                                    }
                                }
                            %>
                        >

                    </td>
                </tr>
                <tr style="height: 10px"></tr>
                <tr>
                    <td>
                        Foto:
                    </td>
                    <td>
                        <input type="file" name="foto">
                    </td>
                </tr>
                <tr>
                    <td>
                        Curriculo:
                    </td>
                    <td>
                        <input type="file" name="curriculo" value="curriculo">
                    </td>
                <tr>
                    <td></td>
                </tr>
                <tr style="height: 20px">
                </tr>

                <tr>
                    <td></td>
                    <td><input type="submit" value="Salvar" id="salvar" onclick="return validarCampos()"/> <input
                            type="submit" id="cancelar" value="Cancelar"
                            onclick="document.getElementById('formUser').action = 'salvarUsuario?acao=reset'"/>
                    </td>
                </tr>

            </table>
        </li>
    </ul>
</form>
<form action="servletPesquisa" method="post">
    <ul class="form-style-1">
        <li>
            <table>
                <tr>
                    <td>Descrição:</td>
                    <td><input type="text" id="descricaoconsulta" name="descricaoconsulta"></td>
                    <td><input type="submit" value="Pesquisar"></td>
                </tr>
            </table>
        </li>
    </ul>
</form>
<div class="container">
    <table class="responsive-table">
        <caption>Usuários Cadastrados</caption>
        <tr>
            <th>Id</th>
            <th>Foto</th>
            <th>Curriculo</th>
            <th>Nome</th>
            <th>Delete</th>
            <th>Update</th>
            <th>Telefone</th>
        </tr>
        <c:forEach items="${usuarios}" var="user">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <c:if test="${user.fotoBase64Miniatura.isEmpty() == false}">
                    <td><a href="salvarUsuario?acao=download&tipo=imagem&user=${user.id}"><img
                            src='<c:out value="${user.fotoBase64Miniatura}"/>' alt="Imagem User" title="Download Imagem"
                            width="32px" height="32px"/> </a></td>
                </c:if>
                <c:if test="${user.fotoBase64Miniatura.isEmpty() == true || user.fotoBase64Miniatura == null}">
                    <td><img src="resources/img/userpadrao.png" alt="Imagem User" title="Sem Imagem" width="32px"
                             height="32px" onclick="alert('Não possui Imagem')"></td>
                </c:if>
                <c:if test="${user.curriculoBase64.isEmpty() == false}">
                    <td><a href="salvarUsuario?acao=download&tipo=curriculo&user=${user.id}"><img
                            src="resources/img/comcurriculo.png" alt="Download Curriculo" title="Download Curriculo"
                            width="32px" height="32px" "></a></td>
                </c:if>
                <c:if test="${user.curriculoBase64.isEmpty() == true || user.curriculoBase64 == null}">
                    <td><img src="resources/img/semcurriculo.png" alt="Sem Curriculo" title="Sem Curriculo" width="32px"
                             height="32px" onclick="alert('não possui curriculo')"></td>
                </c:if>
                <td><c:out value="${user.nome}"/></td>
                <td><a href="salvarUsuario?acao=delete&user=${user.id}"><img src="resources/img/excluir.png"
                                                                             alt="Excluir" title="Excluir" width="32px"
                                                                             height="32px"
                                                                             onclick="return confirm('Confirmar a exclusão?')"/></a>
                </td>
                <td><a href="salvarUsuario?acao=editar&user=${user.id}"><img src="resources/img/editar.png" alt="Editar"
                                                                             title="Editar" width="32px" height="32px"/></a>
                </td>
                <td><a href="salvarTelefones?acao=addFone&user=${user.id}"><img src="resources/img/telefone.png"
                                                                                alt="Telefones" title="Telefones"
                                                                                width="32px" height="32px"/></a></td>
            </tr>
        </c:forEach>
    </table>
</div>
<script type="text/javascript">
    function validarCampos() {

        if (document.getElementById("login").value == '') {
            alert("Informe o Login!");
            return false;
        } else if (document.getElementById("senha").value == '') {
            alert("Informe a Senha!");
            return false;
        } else if (document.getElementById("nome").value == '') {
            alert("Informe o Nome!");
            return false;
        } else if (document.getElementById("telefone").value == '') {
            alert("Informe o Telefone!");
            return false;
        } else {
            return true;
        }
    }

    function consultarCep() {
        var cep = $("#cep").val();

        $.getJSON("https://viacep.com.br/ws/" + cep + "/json/?callback=?", function (dados) {

            if (!("erro" in dados)) {
                $("#rua").val(dados.logradouro);
                $("#bairro").val(dados.bairro);
                $("#cidade").val(dados.localidade);
                $("#estado").val(dados.uf);
                $("#ibge").val(dados.ibge);
            } else {
                $("#cep").val('');
                $("#rua").val('');
                $("#bairro").val('');
                $("#cidade").val('');
                $("#estado").val('');
                $("#ibge").val('');
                alert("CEP não encontrado.");
            }
        });
    }
</script>
</body>
</html>