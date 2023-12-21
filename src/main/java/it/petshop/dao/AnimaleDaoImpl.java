package it.petshop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.petshop.connection.DbHandler;
import it.petshop.model.Animale;


public class AnimaleDaoImpl implements AnimaleDao {

	@Override
	public List<Animale> getAll() {
		List<Animale> animaliLista = new ArrayList<Animale>();
		ClienteDaoImpl clienteDao = new ClienteDaoImpl();
		
		DbHandler dbHandler = DbHandler.getInstance();
		try {
			 Connection connection = dbHandler.getConnection();
			 String query = "SELECT * "
			 		+ "FROM animale ";
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            while(resultSet.next()) {
	            	Animale animalel = new Animale();
					animalel.setDataAcquisto(resultSet.getDate("data_acquisto"));
					animalel.setMatricola(resultSet.getInt("matricola"));
					animalel.setNome(resultSet.getString("nome"));
					animalel.setPrezzo(resultSet.getDouble("prezzo"));
					animalel.setTipoAnimale(resultSet.getString("tipo_animale"));
					animalel.setCliente(clienteDao.getAllbyId(resultSet.getInt("ntelefono")));
					animaliLista.add(animalel);
				}

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return animaliLista;
	}

	@Override
	public Animale getAllbyId(int matricola) {
		 Animale animale = null;
		 ClienteDaoImpl clienteDao = new ClienteDaoImpl();
		
		DbHandler dbHandler = DbHandler.getInstance();
		try {
			 Connection connection = dbHandler.getConnection();
			 String query = "SELECT * "
			 		+ "FROM animale "
			 		+ "WHERE matricola = ?";
	            PreparedStatement preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setInt(1, matricola);
	            ResultSet resultSet = preparedStatement.executeQuery();

	            if (resultSet.next()) {
	            			animale = new Animale();
	                        animale.setMatricola(resultSet.getInt("matricola"));
	                        animale.setDataAcquisto(resultSet.getDate("data_acquisto"));
	                        animale.setNome(resultSet.getString("nome"));
	                        animale.setPrezzo(resultSet.getDouble("prezzo"));
	                        animale.setTipoAnimale(resultSet.getString("tipo_animale"));
	                        animale.setCliente(clienteDao.getAllbyId(resultSet.getInt("ntelefono")));
	            }

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return animale;
	}

	@Override
	public List<Animale> getByCliente(int ntelefono) {
		List<Animale> animaliLista = new ArrayList<Animale>();
		DbHandler dbHandler = DbHandler.getInstance();
		try {
			 Connection connection = dbHandler.getConnection();
			 String query = "SELECT c.nome_cliente, c.cognome_cliente, a.nome, a.tipo_animale , a.matricola , a.prezzo , a.data_acquisto "
			 		+ "FROM cliente c "
			 		+ "JOIN animale a ON c.ntelefono = a.ntelefono "
			 		+ "WHERE a.ntelefono = ?";
	         PreparedStatement preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setInt(1, ntelefono);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            while(resultSet.next()) {
	            	Animale animalel = new Animale();
					animalel.setDataAcquisto(resultSet.getDate("a.data_acquisto"));
					animalel.setMatricola(resultSet.getInt("a.matricola"));
					animalel.setNome(resultSet.getString("a.nome"));
					animalel.setPrezzo(resultSet.getDouble("a.prezzo"));
					animalel.setTipoAnimale(resultSet.getString("a.tipo_animale"));
					animaliLista.add(animalel);
				}

		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return animaliLista;
	}
	

	@Override
	public void insert(Animale animale) {
		DbHandler dbHandler = DbHandler.getInstance();

        try {
        	Connection connection = dbHandler.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO animale(matricola, data_acquisto, nome, prezzo, tipo_animale, ntelefono) " +
                             "VALUES (?, ?, ?, ?, ?, ?)"); 

            preparedStatement.setInt(1, animale.getMatricola());
            preparedStatement.setDate(2, new java.sql.Date(animale.getDataAcquisto().getTime()));
            preparedStatement.setString(3, animale.getNome());
            preparedStatement.setDouble(4, animale.getPrezzo());
            preparedStatement.setString(5, animale.getTipoAnimale());
            preparedStatement.setInt(6, animale.getCliente().getNtelefono());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

	}

	@Override
	public void update(Animale animale) {
	    DbHandler dbHandler = DbHandler.getInstance();

        try {Connection connection = dbHandler.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE animale SET data_acquisto = ?, nome = ?, prezzo = ?, tipo_animale = ?, ntelefono = ? " +
                             "WHERE matricola = ?");

            preparedStatement.setDate(1, new java.sql.Date(animale.getDataAcquisto().getTime()));
            preparedStatement.setString(2, animale.getNome());
            preparedStatement.setDouble(3, animale.getPrezzo());
            preparedStatement.setString(4, animale.getTipoAnimale());
            preparedStatement.setInt(5, animale.getCliente().getNtelefono());
            preparedStatement.setInt(6, animale.getMatricola());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

	}

	@Override
	public void delete(int matricola) {
		DbHandler dbHandler = DbHandler.getInstance();

        try {
        	Connection connection = dbHandler.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM animale WHERE matricola = ?");

            preparedStatement.setInt(1, matricola);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
	}

}
