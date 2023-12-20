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
					animalel.setPrezzo(resultSet.getInt("prezzo"));
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
	public Animale getAllbyId() {
		// TODO Auto-generated method stub
		return null;
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
					animalel.setPrezzo(resultSet.getInt("a.prezzo"));
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
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Animale animale) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Animale animale) {
		// TODO Auto-generated method stub

	}

}
