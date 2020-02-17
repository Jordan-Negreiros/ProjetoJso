package br.com.projetojsp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.com.projetojsp.connection.SingleConnection;

public class DaoLogin {

	private Connection connection;
	
	public DaoLogin() {
		
		connection = SingleConnection.getConnection();
	}
	
	public boolean validarLogin(String login, String senha) throws Exception{
		
		String sql = "select * from usuario where login = '"+ login +"' and senha = '"+ senha +"'";
		PreparedStatement preparedStatement = connection.prepareStatement(sql);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		if (resultSet.next()) {
			return true; // possui usu�rio 
		}else {
			return false; // n�o validou usu�rio
		}
	}
}
