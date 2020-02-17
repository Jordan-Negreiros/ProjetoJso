package br.com.projetojsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.com.projetojsp.beans.BeansTelefone;
import br.com.projetojsp.connection.SingleConnection;

public class DaoTelefone {

    private Connection connection;

    public DaoTelefone(){
        connection = SingleConnection.getConnection();
    }

    public void salvar(BeansTelefone telefone) throws Exception {
        try {
            String sql = "insert into telefone(numero, tipo, usuario) values (?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, telefone.getNumero());
            statement.setString(2, telefone.getTipo());
            statement.setLong(3,telefone.getUsuario());
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

    public List<BeansTelefone> listar(Long usuario) throws Exception {
        List<BeansTelefone> listar = new ArrayList<>();

        String sql = "select * from telefone where usuario = " + usuario;

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {

            BeansTelefone telefone = new BeansTelefone();
            telefone.setId(resultSet.getLong("id"));
            telefone.setNumero(resultSet.getString("numero"));
            telefone.setTipo(resultSet.getString("tipo"));
            telefone.setUsuario(resultSet.getLong("usuario"));

            listar.add(telefone);
        }
        return listar;
    }

    public void delete(String id) {

        try {
            String sql = "DELETE FROM telefone WHERE id = '"+ id +"'";
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

}
