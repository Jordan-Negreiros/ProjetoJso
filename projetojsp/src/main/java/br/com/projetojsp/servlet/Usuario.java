package br.com.projetojsp.servlet;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import br.com.projetojsp.beans.BeanCursoJsp;
import br.com.projetojsp.dao.DaoUsuario;

@WebServlet("/salvarUsuario")
@MultipartConfig
public class Usuario extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private DaoUsuario daoUsuario = new DaoUsuario();

    /*
     * Construtor Usuario()
     * Recebe o Construtor da Classe HttpServlet
     */
    public Usuario() {
        super();
    }

    /*
     * (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String acao = request.getParameter("acao");
            String user = request.getParameter("user");
            if ( acao != null && acao.equalsIgnoreCase("reset") && user != null) {
                recarregarListaDeUsuarios(request, response);
            } else if ( acao != null && acao.equalsIgnoreCase("delete") && user != null) {
                daoUsuario.delete(user);
                recarregarListaDeUsuarios(request, response);
            } else if (acao != null && acao.equalsIgnoreCase("editar")) {
                /* Consultar usuário e mostras os atributos em seus respectivos campos */
                BeanCursoJsp beanCursoJsp = daoUsuario.consultar(user);
                RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
                request.setAttribute("user", beanCursoJsp);
                view.forward(request, response);
            } else if ( acao != null && acao.equalsIgnoreCase("listartodos")) {
                daoUsuario.delete(user);
                recarregarListaDeUsuarios(request, response);
            } else if (acao != null && acao.equalsIgnoreCase("download")) {
                downloadImagem(request, response, user);
            } else {
                recarregarListaDeUsuarios(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String acao = request.getParameter("acao");
        if (acao != null && acao.equalsIgnoreCase("reset")) {
            try {
                recarregarListaDeUsuarios(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            String id = request.getParameter("id");
            String login = request.getParameter("login");
            String senha = request.getParameter("senha");
            String nome = request.getParameter("nome");
            String cep = request.getParameter("cep");
            String rua = request.getParameter("rua");
            String bairro = request.getParameter("bairro");
            String cidade = request.getParameter("cidade");
            String estado = request.getParameter("estado");
            String ibge = request.getParameter("ibge");
            String sexo = request.getParameter("sexo");
            String perfil = request.getParameter("perfil");

            BeanCursoJsp usuario = new BeanCursoJsp();
            usuario.setId((id != null && !id.isEmpty()) ? Long.parseLong(id) : null);
            usuario.setLogin(login);
            usuario.setSenha(senha);
            usuario.setNome(nome);
            usuario.setCep(cep);
            usuario.setRua(rua);
            usuario.setBairro(bairro);
            usuario.setCidade(cidade);
            usuario.setEstado(estado);
            usuario.setIbge(ibge);
            usuario.setSexo(sexo);
            usuario.setPerfil(perfil);

            if (request.getParameter("ativo") != null && request.getParameter("ativo").equalsIgnoreCase("on")) {
                usuario.setAtivo(true);
            } else {
                usuario.setAtivo(false);
            }

            try {
                /*Inicio File upload de imagems e pdf*/
                if (ServletFileUpload.isMultipartContent(request)) {
                    processamentoDeImagem(request, usuario);
                    processamentoDePdf(request, usuario);
                }
                /*FIM File upload de imagems e pdf*/

                String msg = null;
                boolean podeInserir = true;

                if (login == null || login.isEmpty()) {
                    msg = "Login Deve Ser Informado!";
                    podeInserir = false;
                } else if (senha == null || senha.isEmpty()) {
                    msg = "Senha Deve Ser Informada!";
                    podeInserir = false;
                } else if (nome == null || nome.isEmpty()) {
                    msg = "Nome Deve Ser Informado!";
                    podeInserir = false;
                } else if (id == null || id.isEmpty() && !daoUsuario.validarLogin(login)) {
                    request.setAttribute("msg", "Este Login Pertence a Um Usuário!");
                    podeInserir = false;
                } else if (id == null || id.isEmpty() && !daoUsuario.validarSenha(senha)) {
                    request.setAttribute("msg", "Esta Senha Pertence a Um Usuário!");
                    podeInserir = false;
                }
                if (msg != null) {
                    request.setAttribute("msg", msg);
                } else if (id == null || id.isEmpty() && daoUsuario.validarLogin(login) && daoUsuario.validarSenha(senha) && podeInserir) {
                    request.setAttribute("msg", "Salvo Com Sucesso!");
                    daoUsuario.salvar(usuario);
                }
                if (id != null && !id.isEmpty() && podeInserir) {
                    request.setAttribute("msg", "Atualizado com sucesso");
                    daoUsuario.atualizar(usuario);
                }
                if (!podeInserir) {
                    request.setAttribute("user", usuario);
                }

                recarregarListaDeUsuarios(request, response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void processamentoDePdf(HttpServletRequest request, BeanCursoJsp usuario) throws Exception {
        /* Pocessamento de PDF */
        Part curriculoPdf = request.getPart("curriculo");
        if (curriculoPdf != null && curriculoPdf.getInputStream().available() > 0) {
            String curriculoBase64 = new Base64().encodeBase64String(converteStremParabyte(curriculoPdf.getInputStream()));

            usuario.setCurriculoBase64(curriculoBase64);
            usuario.setContentTypeCurriculo(curriculoPdf.getContentType());
        } else {
            usuario.setAtualiarPdf(false);
        }
    }
    private void processamentoDeImagem(HttpServletRequest request, BeanCursoJsp usuario) throws Exception {
        /* Processamento de Imagem */
        Part imagemFoto = request.getPart("foto");
        if (imagemFoto != null && imagemFoto.getInputStream().available() > 0) {

            String fotoBase64 = new Base64()
                    .encodeBase64String(converteStremParabyte(imagemFoto.getInputStream()));

            usuario.setFotoBase64(fotoBase64);
            usuario.setContentType(imagemFoto.getContentType());

            /* INICIO MINIATURA IMAGEM*/
            /* DECODIFICANDO A IMAGEM */
            byte[] imageByteDecode = new Base64().decodeBase64(fotoBase64);
            /* TRANSFORMAR EM UM BUFFERED IMAGE*/
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageByteDecode));
            /* PEGA O TIPO DA IMAGEM*/
            int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
            /*CRIA IMAGEM EM MINIATURA*/
            BufferedImage resizedImage = new BufferedImage(100, 100, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(bufferedImage, 0, 0, 100, 100, null);
            g.dispose();
            /*ESCREVER IMAGEM NOVAMENTE*/
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "png", baos);

            String miniaturaBase64 = "data:image/png;base64," + DatatypeConverter.printBase64Binary(baos.toByteArray());

            usuario.setFotoBase64Miniatura(miniaturaBase64);
            /* FIM MINIATURA IMAGEM*/
        } else {
            usuario.setAtualiarImagem(false);
        }
    }
    private void downloadImagem(HttpServletRequest request, HttpServletResponse response, String user) throws Exception {
        BeanCursoJsp usuario = daoUsuario.consultar(user);
        if (usuario != null) {
            String contentType = "";
            byte[] fileBytes = null;
            String tipo = request.getParameter("tipo");

            if (tipo.equalsIgnoreCase("imagem")) {

                contentType = usuario.getContentType();
                /*Converte a base64 da imagem do banco para byte[]*/
                fileBytes = new Base64().decodeBase64(usuario.getFotoBase64());

            } else if (tipo.equalsIgnoreCase("curriculo")) {
                contentType = usuario.getContentTypeCurriculo();
                /*Converte a base64 da imagem do banco para byte[]*/
                fileBytes = new Base64().decodeBase64(usuario.getCurriculoBase64());
            }

            response.setHeader("Content-Disposition", "attachment;filename=arquivo."
                    + contentType.split("\\/")[1]);

            /*Coloca os bytes em um objeto de entrada para processar*/
            InputStream is = new ByteArrayInputStream(fileBytes);

            /*inicio da resposta para o navegador*/
            int read = 0;
            byte[] bytes = new byte[1024];
            OutputStream os = response.getOutputStream();


            while ((read = is.read(bytes)) != -1) {
                os.write(bytes, 0, read);
            }

            os.flush();
            os.close();

        }
    }
    private void recarregarListaDeUsuarios(HttpServletRequest request, HttpServletResponse response) throws Exception {
        RequestDispatcher view = request.getRequestDispatcher("/cadastroUsuario.jsp");
        request.setAttribute("usuarios", daoUsuario.listar());
        view.forward(request, response);
    }
    private byte[] converteStremParabyte(InputStream imagem) throws Exception {
        /*Converte a entrada de fluxo de dados da imagem para byte[]*/
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int reads = imagem.read();
        while (reads != -1) {
            baos.write(reads);
            reads = imagem.read();
        }

        return baos.toByteArray();

    }
}