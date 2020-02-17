package br.com.projetojsp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projetojsp.beans.BeanCursoJsp;
import br.com.projetojsp.beans.BeansTelefone;
import br.com.projetojsp.dao.DaoTelefone;
import br.com.projetojsp.dao.DaoUsuario;

@WebServlet("/salvarTelefones")
public class Telefone extends HttpServlet {

    DaoUsuario daoUsuario = new DaoUsuario();
    DaoTelefone daoTelefone = new DaoTelefone();

    public Telefone() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            BeanCursoJsp usuario = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");
            String numero = request.getParameter("telefone");
            String tipo = request.getParameter("tipo");
            String acao = request.getParameter("acao");

            if (acao == null || (acao != null && !acao.equalsIgnoreCase("voltar"))) {

                if (numero == null || (numero != null && numero.isEmpty())) {

                    RequestDispatcher view = request
                            .getRequestDispatcher("/telefones.jsp");
                    request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
                    view.forward(request, response);

                } else {

                	BeansTelefone telefone = new BeansTelefone();
                    telefone.setNumero(numero);
                    telefone.setTipo(tipo);
                    telefone.setUsuario(usuario.getId());


                    daoTelefone.salvar(telefone);

                    request.getSession().setAttribute("userEscolhido", usuario); // pega o id do usúario da sessão
                    request.setAttribute("userEscolhido", usuario);

                    RequestDispatcher view = request
                            .getRequestDispatcher("/telefones.jsp");
                    request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
                    view.forward(request, response);
                }
            } else {
                RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
                request.setAttribute("usuarios", daoUsuario.listar());
                view.forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String acao = request.getParameter("acao");
            if (acao != null) {
                if (acao.equalsIgnoreCase("addFone")) {

                    String user = request.getParameter("user");
                    BeanCursoJsp usuario = daoUsuario.consultar(user);

                    request.getSession().setAttribute("userEscolhido", usuario); // pega o id do usúario da sessão
                    request.setAttribute("userEscolhido", usuario);


                    RequestDispatcher view = request
                            .getRequestDispatcher("/telefones.jsp");
                    request.setAttribute("telefones", daoTelefone.listar(usuario.getId()));
                    view.forward(request, response);
                } else if (acao.equalsIgnoreCase("deleteFone")) {

                    String foneId = request.getParameter("foneId");
                    daoTelefone.delete(foneId);

                    BeanCursoJsp beanCursoJsp = (BeanCursoJsp) request.getSession().getAttribute("userEscolhido");

                    RequestDispatcher view = request.getRequestDispatcher("/telefones.jsp");
                    request.setAttribute("telefones", daoTelefone.listar(beanCursoJsp.getId()));
                    view.forward(request, response);

                }
            } else {
                RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
                request.setAttribute("usuarios", daoUsuario.listar());
                view.forward(request, response);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
