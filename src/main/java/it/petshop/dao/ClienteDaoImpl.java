package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.petshop.connection.DbHandler;
import it.petshop.model.Cliente;

public class ClienteDaoImpl implements ClienteDao {

	@Override
	public List<Cliente> getAll() {
		List<Cliente> clientiLista = new ArrayList<>();
	    DbHandler dbHandler = DbHandler.getInstance();

	    try {Connection connection = dbHandler.getConnection();
	         PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM cliente");
	         ResultSet resultSet = preparedStatement.executeQuery(); 

	        while (resultSet.next()) {
	            Cliente cliente = new Cliente();
	            cliente.setNtelefono(resultSet.getInt("ntelefono"));
	            cliente.setCitta(resultSet.getString("citta"));
	            cliente.setCognomeCliente(resultSet.getString("cognome_cliente"));
	            cliente.setIndirizzo(resultSet.getString("indirizzo"));
	            cliente.setNomeCliente(resultSet.getString("nome_cliente"));

	            clientiLista.add(cliente);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return clientiLista;
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
		DbHandler dbHandler = DbHandler.getInstance();

        try {Connection connection = dbHandler.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO cliente(ntelefono, citta, cognome_cliente, indirizzo, nome_cliente) " +
                             "VALUES (?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, cliente.getNtelefono());
            preparedStatement.setString(2, cliente.getCitta());
            preparedStatement.setString(3, cliente.getCognomeCliente());
            preparedStatement.setString(4, cliente.getIndirizzo());
            preparedStatement.setString(5, cliente.getNomeCliente());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

	@Override
	public void update(Cliente cliente) {
		  DbHandler dbHandler = DbHandler.getInstance();

	        try {Connection connection = dbHandler.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(
	                     "UPDATE cliente SET citta = ?, cognome_cliente = ?, indirizzo = ?, nome_cliente = ? " +
	                             "WHERE ntelefono = ?");

	            preparedStatement.setString(1, cliente.getCitta());
	            preparedStatement.setString(2, cliente.getCognomeCliente());
	            preparedStatement.setString(3, cliente.getIndirizzo());
	            preparedStatement.setString(4, cliente.getNomeCliente());
	            preparedStatement.setInt(5, cliente.getNtelefono());

	            preparedStatement.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	}

	@Override
	public void delete(int ntelefono) {
		 DbHandler dbHandler = DbHandler.getInstance();

	        try {Connection connection = dbHandler.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM cliente WHERE ntelefono = ?");

	            preparedStatement.setInt(1, ntelefono);
	            preparedStatement.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}

}
