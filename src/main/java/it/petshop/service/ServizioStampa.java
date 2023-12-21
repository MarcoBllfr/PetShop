package it.petshop.service;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import it.petshop.connection.DbHandler;
import it.petshop.dao.AnimaleDaoImpl;
import it.petshop.dao.ClienteDaoImpl;
import it.petshop.model.Animale;
import it.petshop.model.Cliente;



public class ServizioStampa {
	private static final String PATHFILE = "./Report"; //destinazione del file creato
	AnimaleDaoImpl animaleDao = new AnimaleDaoImpl();
	ClienteDaoImpl clienteDao = new ClienteDaoImpl();
	Font font = new Font(Font.FontFamily.COURIER,11);
	
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

	//Qui stampo l'elenco degli animali di un determinato cliente
	public void stampaFileClienteTxT(int ntelefono) {
		try {
			if(controlloSuQuery(ntelefono)== true) {
			System.out.println("----Avvio la stampa del TxT-----");
			
			 
			List<Animale> animaliStampa = animaleDao.getByCliente(ntelefono);
			 	String nomeCliente = clienteDao.getAllbyId(ntelefono).getNomeCliente();
			 	String cognomeCliente = clienteDao.getAllbyId(ntelefono).getCognomeCliente();
			 	
			 	String nomeFile = "/"+nomeCliente+"_"+cognomeCliente+".txt";
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(PATHFILE+nomeFile));
				writer.write("*Cliente: "+nomeCliente + " "+cognomeCliente+"|"+"N telefono: "+ ntelefono+"*");
				writer.newLine();
				
				// mi creo un header per le informazioni da stampare
				String hDtata =StringUtils.rightPad("Data acquisto",15,"");
				String hMatricola =StringUtils.rightPad("Matricola",10,"");
				String hAnimale = StringUtils.rightPad("Animale",10,"");
				String hPrezzo =StringUtils.rightPad("Prezzo",10,"");
				writer.write(hMatricola+"|"+hDtata+"|"+hAnimale+"|"+hPrezzo+"|");
				writer.newLine();
				
				for(Animale animali : animaliStampa) {
					// mi preparo le string formattate
			    String stringData = animali.getDataAcquisto().toString();
				String dataFormattata = StringUtils.rightPad(stringData,15,"");
				String stringMatricola =Integer.toString(animali.getMatricola());
				String matricolaFormat = StringUtils.rightPad(stringMatricola,10,"");
				String stringTipo =animali.getTipoAnimale();
				String tipoAnimaleFormat = StringUtils.rightPad(stringTipo,10 ,"");
				String stringPrezzo = Double.toString(animali.getPrezzo());
				String prezzoFormat = StringUtils.rightPad(stringPrezzo,10 ,"");
					//stampo le string
				
				writer.write(
				matricolaFormat + "|"+
				dataFormattata+"|"+
				tipoAnimaleFormat +"|" +
				prezzoFormat+"|"
				);
				writer.newLine();	
							 	}
								writer.close();
							} catch (IOException e) {
								System.out.println("Errore: "+ e);
							}
			System.out.println("----Fine stampa del TxT-----");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//Qui stampo il repost di tutte le vendite ordinate per data
	public void stampaOrdinataTxT() {
		System.out.println("----Avvio stampa della lista ordinata-----");
		List<Animale> animaliStampa = getAnimaliOrdinati();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(PATHFILE+"/Report.txt"));
			String hDtata =StringUtils.rightPad("Data acquisto",15,"");
			String hMatricola =StringUtils.rightPad("Matricola",10,"");
			String hAnimale = StringUtils.rightPad("Animale",10,"");
			String hPrezzo =StringUtils.rightPad("Prezzo",10,"");
			String hNomeCliente=StringUtils.rightPad("Cliente",15,"");
			String hTelefono=StringUtils.rightPad("Telefono",10,"");
			writer.write(hDtata+"|"+hMatricola+"|"+hAnimale+"|"+hPrezzo+"|"+hTelefono+"|"+hNomeCliente);
			writer.newLine();
			for(Animale animali : animaliStampa) {
					String stringData = animali.getDataAcquisto().toString();
					String dataFormattata = StringUtils.rightPad(stringData,15,"");
					String stringMatricola =Integer.toString(animali.getMatricola());
					String matricolaFormat = StringUtils.rightPad(stringMatricola,10,"");
					String stringTipo =animali.getTipoAnimale();
					String tipoAnimaleFormat = StringUtils.rightPad(stringTipo,10 ,"");
					String stringPrezzo = Double.toString(animali.getPrezzo());
					String prezzoFormat = StringUtils.rightPad(stringPrezzo,10 ,"");
					String ntelefono =Integer.toString(animali.getCliente().getNtelefono());
					String nTelFormat = StringUtils.rightPad(ntelefono,10 ,"");
					String nomeCliente =animali.getCliente().getNomeCliente();
					String nomeClienteFormat = StringUtils.rightPad(nomeCliente,15,"");
			
			writer.write(
			dataFormattata+"|"
			+matricolaFormat+"|"
			+tipoAnimaleFormat+"|"
			+prezzoFormat+"|"
			+nTelFormat + "|" 
			+nomeClienteFormat);
			writer.newLine();	
						 	}
							writer.close();
							
						} catch (IOException e) {
							System.out.println("Errore: "+ e);
						}
		System.out.println("----Fine stampa della lista ordinata-----");
	}
	
	
	
	//operazioni di stampa in pdf
	public void stampaFileClientePdf(int ntelefono) {
		try {
			if(controlloSuQuery(ntelefono)== true) {
			System.out.println("----Avvio la stampa del file Pdf-----");
			
			 
			List<Animale> animaliStampa = animaleDao.getByCliente(ntelefono);
			 	String nomeCliente = clienteDao.getAllbyId(ntelefono).getNomeCliente();
			 	String cognomeCliente = clienteDao.getAllbyId(ntelefono).getCognomeCliente();
			 	
			 	
			try {
				String nomeFile = "/"+nomeCliente+"_"+cognomeCliente+".pdf";
			 	String destFile = PATHFILE+nomeFile;
			    Document document = new Document();
				   
			    FileOutputStream outputStream = new FileOutputStream(destFile);
			    PdfWriter.getInstance(document, outputStream);
			    
			    //Apro la scrittura del pdf
			    document.open();
			    document.add(new Paragraph("*Cliente: "+nomeCliente + " "+cognomeCliente+"|"+"N telefono: "+ ntelefono+"*" , font));
				
				// mi creo un header per le informazioni da stampare
				String hDtata =StringUtils.rightPad("Data acquisto",15,"");
				String hMatricola =StringUtils.rightPad("Matricola",10,"");
				String hAnimale = StringUtils.rightPad("Animale",10,"");
				String hPrezzo =StringUtils.rightPad("Prezzo",10,"");
			
				document.add(new Paragraph(hMatricola+"|"+hDtata+"|"+hAnimale+"|"+hPrezzo+"|" , font));
				 
				for(Animale animali : animaliStampa) {
					// mi preparo le string formattate
			    String stringData = animali.getDataAcquisto().toString();
				String dataFormattata = StringUtils.rightPad(stringData,15,"");
				String stringMatricola =Integer.toString(animali.getMatricola());
				String matricolaFormat = StringUtils.rightPad(stringMatricola,10,"");
				String stringTipo =animali.getTipoAnimale();
				String tipoAnimaleFormat = StringUtils.rightPad(stringTipo,10 ,"");
				String stringPrezzo = Double.toString(animali.getPrezzo());
				String prezzoFormat = StringUtils.rightPad(stringPrezzo,10 ,"");
					//stampo le string
				document.add(new Paragraph( 
						matricolaFormat + "|"+
								dataFormattata+"|"+
								tipoAnimaleFormat +"|" +
								prezzoFormat+"|"
						      ,font));
				}
				document.close();
			    outputStream.close();
							} catch (IOException | DocumentException e) {
								System.out.println("Errore: "+ e);
							}
			System.out.println("----Fine stampa del file Pdf-----");
			}else {
				System.out.println("Numero inesistente");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	public void stampaReportPdf() {
		System.out.println("----Avvio stampa del report pdf-----");
		List<Animale> animaliStampa = getAnimaliOrdinati();
		try {
			String destFile = PATHFILE+"/Report.pdf";
		    Document document = new Document();
			   
		    FileOutputStream outputStream = new FileOutputStream(destFile);
		    PdfWriter.getInstance(document, outputStream);
		   
		    //formatto una intestazione
		    String hDtata =StringUtils.rightPad("Data acquisto",15,"");
			String hMatricola =StringUtils.rightPad("Matricola",10,"");
			String hAnimale = StringUtils.rightPad("Animale",10,"");
			String hPrezzo =StringUtils.rightPad("Prezzo",10,"");
			String hNomeCliente=StringUtils.rightPad("Cliente",15,"");
			String hTelefono=StringUtils.rightPad("Telefono",10,"");
		    
		    
		    document.open();
		    document.add(new Paragraph(hDtata+"|"+hMatricola+"|"+hAnimale+"|"+hPrezzo+"|"+hTelefono+"|"+hNomeCliente, font));
			
			for(Animale animali : animaliStampa) {
					String stringData = animali.getDataAcquisto().toString();
					String dataFormattata = StringUtils.rightPad(stringData,15,"");
					String stringMatricola =Integer.toString(animali.getMatricola());
					String matricolaFormat = StringUtils.rightPad(stringMatricola,10,"");
					String stringTipo =animali.getTipoAnimale();
					String tipoAnimaleFormat = StringUtils.rightPad(stringTipo,10 ,"");
					String stringPrezzo = Double.toString(animali.getPrezzo());
					String prezzoFormat = StringUtils.rightPad(stringPrezzo,10 ,"");
					String ntelefono =Integer.toString(animali.getCliente().getNtelefono());
					String nTelFormat = StringUtils.rightPad(ntelefono,10 ,"");
					String nomeCliente =animali.getCliente().getNomeCliente();
					String nomeClienteFormat = StringUtils.rightPad(nomeCliente,15,"");
					
					
			document.add(new Paragraph(dataFormattata+"|"
					+matricolaFormat+"|"
					+tipoAnimaleFormat+"|"
					+prezzoFormat+"|"
					+nTelFormat + "|" 
					+nomeClienteFormat, font));
		
						 	}
			document.close();
	        outputStream.close();
						} catch (IOException | DocumentException e) {
							System.out.println("Errore: "+ e);
						}
		System.out.println("----Fine stampa del report pdf-----");
	}
	
	private boolean controlloSuQuery(int ntelefono) throws SQLException {
		boolean esiste = false;
		DbHandler dbHandler = DbHandler.getInstance();
			 Connection connection = dbHandler.getConnection();
			 String query = "SELECT c.nome_cliente, c.cognome_cliente, a.nome, a.tipo_animale , a.matricola , a.prezzo , a.data_acquisto "
			 		+ "FROM cliente c "
			 		+ "JOIN animale a ON c.ntelefono = a.ntelefono "
			 		+ "WHERE a.ntelefono = ?";
	         PreparedStatement preparedStatement = connection.prepareStatement(query);
	            preparedStatement.setInt(1, ntelefono);
	            ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			esiste=true;
		}else {
			esiste = false;
		}
		return esiste;
	}

	
	//Metodo per ottenere gli animali dal db (Da inserire nel Dao?)
	private List<Animale> getAnimaliOrdinati() {
		//aggiungere intestazione e data primo elemento 
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
}
