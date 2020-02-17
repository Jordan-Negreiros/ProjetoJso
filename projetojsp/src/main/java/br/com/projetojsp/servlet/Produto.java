package br.com.projetojsp.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.projetojsp.beans.BeansProduto;
import br.com.projetojsp.dao.DaoProduto;

@WebServlet("/salvarProduto")
public class Produto extends HttpServlet {

    private DaoProduto daoProduto = new DaoProduto();

    public Produto() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {
            String acao = req.getParameter("acao") != null ? req.getParameter("acao") : "listartodos";
            String product = req.getParameter("product");

            if (acao.equalsIgnoreCase("delete")) {

                daoProduto.delete(product);
                recarregarPaginaProdutos(req, resp);

            } else if (acao.equalsIgnoreCase("editar")) {

                BeansProduto beansProduto = daoProduto.consultar(product);

                RequestDispatcher view = req.getRequestDispatcher("/cadastroProduto.jsp");
                req.setAttribute("product", beansProduto);
                req.setAttribute("categorias", daoProduto.listarCategoria());
                view.forward(req, resp);
            } else if (acao.equalsIgnoreCase("listartodos")) {

                recarregarPaginaProdutos(req, resp);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String acao = req.getParameter("acao");

        if (acao != null && acao.equalsIgnoreCase("reset")) {
            try {
                recarregarPaginaProdutos(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String id = req.getParameter("id");
            String nome = req.getParameter("nome");
            String quantidade = req.getParameter("quantidade");
            String preco = req.getParameter("preco");
            String categorias = req.getParameter("categorias_id");

            BeansProduto produto = new BeansProduto();
            produto.setId(!id.isEmpty() ? Long.parseLong(id) : null);
            produto.setNome(nome);
            produto.setCategoria_id(Long.parseLong(categorias));

            if (quantidade != null && !quantidade.isEmpty()) {
                produto.setQuantidade(Integer.parseInt(quantidade));
            }
            if (preco != null && !preco.isEmpty()) {
                String valorParse = preco.replaceAll("\\.", "");

                valorParse = valorParse.replaceAll("\\,",".");
                produto.setPreco(Double.parseDouble(valorParse));
            }

            try {

                String msgErro = null;
                String msgSalvo = null;
                boolean podeInserir = true;

                if (nome == null || nome.isEmpty()) {
                    msgErro = "Nome do Produto deve ser inserido";
                    podeInserir = false;
                } else if (quantidade == null || quantidade.isEmpty()) {
                    msgErro = "Quantidade deve ser inserida";
                    podeInserir = false;
                } else if (quantidade.equalsIgnoreCase("0")) {
                    msgErro = "Quantidade deve ser maior que 0";
                    podeInserir = false;
                } else if (preco == null || preco.isEmpty()) {
                    msgErro = "Preço deve ser inserido";
                    podeInserir = false;
                } else if (preco.equalsIgnoreCase("0") || preco.equalsIgnoreCase("0.0")){
                    msgErro = "Preço deve ser maior que 0";
                    podeInserir = false;
                }
                else if (id == null || id.isEmpty() && !daoProduto.validarNome(nome)) {
                    msgErro = "Produto já existente";
                    podeInserir = false;
                } else if (id == null || id.isEmpty()
                        && daoProduto.validarNome(nome)) {
                    msgSalvo = "Produto salvo com sucesso";
                    daoProduto.salvar(produto);

                } else if (id != null && !id.isEmpty()) {
                    if (!daoProduto.validarNomeUpdate(nome, id)) {
                        msgErro = "Produto já existente";
                        podeInserir = false;
                    } else {
                        msgSalvo = "Produto Atualizado com succeso";
                        daoProduto.atualizar(produto);
                    }
                }
                if (msgErro != null) {
                    req.setAttribute("msgErro", msgErro);
                }
                if (msgSalvo != null) {
                    req.setAttribute("msgSalvo", msgSalvo);
                }
                if (!podeInserir) {
                    req.setAttribute("product", produto);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                /* carrega os produtos e volta para mesma página*/
                recarregarPaginaProdutos(req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void recarregarPaginaProdutos(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        /*Carrega os produtos e volta para mesma página*/
        RequestDispatcher view = req.getRequestDispatcher("/cadastroProduto.jsp");
        req.setAttribute("products", daoProduto.listar());
        req.setAttribute("categorias", daoProduto.listarCategoria());
        view.forward(req, resp);

    }
}
