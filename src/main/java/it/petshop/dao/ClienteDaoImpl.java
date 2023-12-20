package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import it.petshop.connection.DbHandler;
import it.petshop.model.Cliente;

public class ClienteDaoImpl implements ClienteDao {

	@Override
	public List<Cliente> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cliente getAllbyId(int ntelefono) {
		Cliente cliente = null;
		DbHandler dbHandler = DbHandler.getInstance();
		try {
			 Connection connection = dbHandler.getConnection();
			 String query = "SELECT *"
			 		+ "FROM cliente  "
			 		+ "WHERE ntelefono = ?";
	         PreparedStatement preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setInt(1, ntelefono);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	            	cliente = new Cliente();
	            	cliente.setCitta(resultSet.getString("citta"));
	            	cliente.setCognomeCliente(resultSet.getString("cognome_cliente"));
	            	cliente.setNomeCliente(resultSet.getString("nome_cliente"));
	            	cliente.setNtelefono(resultSet.getInt("ntelefono"));
	            	cliente.setIndirizzo(resultSet.getString("indirizzo"));
	            }else {
	            	System.out.println("Cliente non trovato");
	            }
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return cliente;
	}

	@Override
	public void insert(Cliente cliente) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Cliente cliente) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Cliente cliente) {
		// TODO Auto-generated method stub

	}

}
