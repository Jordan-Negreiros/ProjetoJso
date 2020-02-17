package br.com.projetojsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.projetojsp.beans.BeanCategoria;
import br.com.projetojsp.beans.BeansProduto;
import br.com.projetojsp.connection.SingleConnection;

public class DaoProduto {

    private Connection connection;

    public DaoProduto(){
        connection = SingleConnection.getConnection();
    }

    public void salvar(BeansProduto produto) throws Exception {
        try {
            String sql = "INSERT INTO produto(nome, quantidade, preco, categoria_id) VALUES (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, produto.getNome());
            statement.setDouble(2, produto.getQuantidade());
            statement.setDouble(3,produto.getPreco());
            statement.setLong(4,produto.getCategoria_id());
            statement.execute();
            connection.commit();
        }catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    public List<BeansProduto> listar() throws Exception {

        List<BeansProduto> listar = new ArrayList<>();

        String sql = "select * from produto";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            BeansProduto beansProduto = new BeansProduto();
            beansProduto.setId(resultSet.getLong("id"));
            beansProduto.setNome(resultSet.getString("nome"));
            beansProduto.setQuantidade(resultSet.getInt("quantidade"));
            beansProduto.setPreco(resultSet.getDouble("preco"));
            beansProduto.setCategoria_id(resultSet.getLong("categoria_id"));

            listar.add(beansProduto);
        }
        return listar;
    }

    public List<BeanCategoria> listarCategoria() throws  Exception{

        List<BeanCategoria> listar = new ArrayList<BeanCategoria>();

        String sql = "select * from categoria";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            BeanCategoria beanCategoria = new BeanCategoria();
            beanCategoria.setId(resultSet.getLong("id"));
            beanCategoria.setNome(resultSet.getString("nome"));

            listar.add(beanCategoria);
        }

        return listar;
    }

    public void atualizar(BeansProduto produto) {

        try {
            String sql = "update produto set nome = ?, quantidade = ?, preco = ?, categoria_id = ? where id = " + produto.getId();

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, produto.getNome());
            statement.setInt(2, produto.getQuantidade());
            statement.setDouble(3, produto.getPreco());
            statement.setLong(4, produto.getCategoria_id());
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (Exception e2) {
                e.printStackTrace();
            }
        }
    }

    public void delete(String id) {

        try {
            String sql = "delete from produto where id = '" + id + "'";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();

            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (Exception e2) {
            }
        }
    }

    public BeansProduto consultar(String id) throws Exception {

        String sql = "select * from produto where id='" + id + "'";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            BeansProduto beansProduto = new BeansProduto();
            beansProduto.setId(resultSet.getLong("id"));
            beansProduto.setNome(resultSet.getString("nome"));
            beansProduto.setQuantidade(resultSet.getInt("quantidade"));
            beansProduto.setPreco(resultSet.getDouble("preco"));
            beansProduto.setCategoria_id(resultSet.getLong("categoria_id"));

            return beansProduto;
        }
        return null;
    }

    public boolean validarNome(String nome) throws Exception {
        String sql = "select count(1) as qtd from produto where nome='" + nome + "'";

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {

            return resultSet.getInt("qtd") <= 0;/*Return true*/
        }

        return false;
    }

    public boolean validarNomeUpdate(String nome, String id) throws SQLException {
        String sql = "SELECT COUNT(1) AS qtde FROM produto WHERE nome = '" + nome + "' AND id <> '" + id + "'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    return result.getInt("qtde") <= 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
