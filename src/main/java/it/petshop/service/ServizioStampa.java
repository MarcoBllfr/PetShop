package it.petshop.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import it.petshop.connection.DbHandler;
import it.petshop.dao.AnimaleDaoImpl;
import it.petshop.dao.ClienteDaoImpl;
import it.petshop.model.Animale;
import it.petshop.model.Cliente;



public class ServizioStampa {
	private static final String PATHFILE = "./Report"; //destinazione del file creato
	AnimaleDaoImpl animaleDao = new AnimaleDaoImpl();
	ClienteDaoImpl clienteDao = new ClienteDaoImpl();
	public void stampaVideo() {
		 DbHandler dbHandler = DbHandler.getInstance();
			try {
					 Connection connection = dbHandler.getConnection();
		            Statement statement = connection.createStatement(); 
				ResultSet rs = statement.executeQuery
				("SELECT c.nome_cliente, c.cognome_cliente, a.nome, a.tipo_animale , a.matricola\r\n"
						+ "FROM cliente c\r\n"
						+ "JOIN animale a ON c.ntelefono = a.ntelefono");
			while(rs.next()) {
				System.out.println(
			" "+rs.getString("nome_cliente")+
			" "+ rs.getString("cognome_cliente")+
			" animale| "+rs.getString("a.nome")+""
			+ " "+ rs.getString("a.tipo_animale")+"  | " +rs.getInt("a.matricola")+""
			);
			}
				
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	}

	
	public void stampaFileCliente(int ntelefono) {
		System.out.println("----Avvio la stampa del TxT-----");
		
		List<Animale> animaliStampa = animaleDao.getByCliente(ntelefono);
		 	String nomeCliente = clienteDao.getAllbyId(ntelefono).getNomeCliente();
		 	String cognomeCliente = clienteDao.getAllbyId(ntelefono).getCognomeCliente();
		 	String nomeFile = "/"+nomeCliente+"_"+cognomeCliente+".txt";
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(PATHFILE+nomeFile));
			writer.write(nomeCliente + " "+cognomeCliente);
			writer.newLine();
			for(Animale animali : animaliStampa) {
			writer.write(animali.getMatricola() + " " + animali.getTipoAnimale() +" " + animali.getPrezzo()+" "+ animali.getDataAcquisto());
			writer.newLine();	
						 	}
							writer.close();
						} catch (IOException e) {
							System.out.println("Errore: "+ e);
						}
		System.out.println("----Fine stampa del TxT-----");
	}
	public void stampaOrdinata() {
		System.out.println("----Avvio stampa della lista ordinata-----");
		
		List<Animale> animaliStampa = getAnimaliOrdinati();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(PATHFILE+"/Report.txt"));
			for(Animale animali : animaliStampa) {
			writer.write(animali.getMatricola() + " " + animali.getTipoAnimale() +" " + animali.getPrezzo()+" "+ animali.getDataAcquisto()
			+" "+animali.getCliente().getNtelefono() + " " +animali.getCliente().getNomeCliente() );
			writer.newLine();	
						 	}
							writer.close();
						} catch (IOException e) {
							System.out.println("Errore: "+ e);
						}
		System.out.println("----Fine stampa della lista ordinata-----");
	}
	
	public List<Animale> getAnimaliOrdinati() {
		List<Animale> animaliLista = new ArrayList<Animale>();
		ClienteDaoImpl clienteDao = new ClienteDaoImpl();
		
		DbHandler dbHandler = DbHandler.getInstance();
		try {
			 Connection connection = dbHandler.getConnection();
			 String query = "SELECT * "
			 		+ "FROM animale "
			 		+ "ORDER BY data_acquisto ";
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
}
