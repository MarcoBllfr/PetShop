package it.petshop.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import it.petshop.model.Animale;
import it.petshop.model.Cliente;

public class Servizi {
	private static final String PATHCSV = "./DatiDaCaricare/PETSHOP.csv";
	
	
	public void caricDati(EntityManagerFactory emFactory ,EntityManager entityManager ){
	 
	List<Cliente> clienteLista = new ArrayList<Cliente>();
	List<Animale> animaleLista = new ArrayList<Animale>();
	
	try {	
		BufferedReader reader = new BufferedReader(new FileReader(PATHCSV));
		String line;
		int lineaCount = 0;
		int telControllo = 0;
		while ((line = reader.readLine()) != null) {
			lineaCount++;
			if(lineaCount <= 2) {
				System.out.println("salto la linea" + lineaCount);
			}else {
			String[] data = line.split(";");
			String nomeC = data[0];
			String cognomeC = data[1];
			String cittaC = data[2];
			int telC = Integer.parseInt(data[3]);
			String indirizzoC = data[4];
			System.out.println(nomeC+" " + cognomeC+" " + cittaC+" " + telC+" " + indirizzoC);
			if(telC != telControllo ) {
			telControllo = telC;
			Cliente cliente = new Cliente();
            cliente.setNomeCliente(nomeC);
            cliente.setCognomeCliente(cognomeC);
            cliente.setCitta(cittaC);
            cliente.setNtelefono(telC);
            cliente.setIndirizzo(indirizzoC);

            // Aggiunta dell'oggetto Cliente alla lista
            clienteLista.add(cliente);
			
			}else {
				System.out.println("Cliente gia inserito nella lista");
			}
			}
		}
	} catch (IOException e) {
		System.out.println("Problema nella lettura file" + e);
	}
	System.out.println("----Caricamento Clienti Terminato--------");
	
    try {
    	
    	  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		BufferedReader reader2 = new BufferedReader(new FileReader(PATHCSV));
		String line2;
		int lineaCount2 = 0;
		while ((line2 = reader2.readLine()) != null) {
			lineaCount2++;
			if(lineaCount2 <= 2) {
				System.out.println("salto la linea" + lineaCount2);
			}else {
			String[] data = line2.split(";");
			int telC = Integer.parseInt(data[3]);
			String tipoA = data[5];
			String nomeA = data[6];
			int matricolaA = Integer.parseInt(data[7]);
			String dataAcqStr = data[8];
			Date dataAcq = dateFormat.parse(dataAcqStr);
			Double prezzo = Double.parseDouble(data[9]);
			System.out.println(telC+" " + tipoA+" " + nomeA+" " + matricolaA+" " + dataAcqStr+" "+prezzo);
			Animale animale = new Animale();
			animale.setDataAcquisto(dataAcq);
			for (Cliente cliente : clienteLista) {
				if(cliente.getNtelefono() == telC) {
					boolean esiste=true;
					if(esiste == true) {
						animale.setCliente(cliente);
						break;
					}
				}
			}
			animale.setMatricola(matricolaA);
			animale.setPrezzo(prezzo);
			animale.setNome(nomeA);
			animale.setTipoAnimale(tipoA);
			animaleLista.add(animale);
			}
		}
	} catch (IOException | ParseException e) {
		System.out.println("Problema nella lettura file" + e);
	}
    System.out.println("----Caricamento Animali Terminato--------");
    
    
	 for (Cliente cliente : clienteLista) {
		 entityManager.getTransaction().begin();
            System.out.println("Inserimento nel database: " + cliente);
            Cliente clienteCaricamento = new Cliente();
            clienteCaricamento.setNtelefono(cliente.getNtelefono());
            clienteCaricamento.setNomeCliente(cliente.getNomeCliente());
            clienteCaricamento.setCognomeCliente(cliente.getCognomeCliente());
            clienteCaricamento.setCitta(cliente.getCitta());
            clienteCaricamento.setIndirizzo(cliente.getIndirizzo());
            
            // Creo una quer per controllare se gia è presente il cliente
            String jpqlQuery = "SELECT c.ntelefono FROM Cliente c WHERE c.ntelefono = :telcheck";
            Query query = entityManager.createQuery(jpqlQuery);
            query.setParameter("telcheck", cliente.getNtelefono());
            List<Cliente> risultati = query.getResultList();
            //eseguo un controllo
            if(risultati.isEmpty()) {
            	entityManager.persist(clienteCaricamento);
                entityManager.getTransaction().commit();
            }else {  
            	entityManager.getTransaction().commit();
            System.out.println("Cliente gia esistente sul db");
        }
	 }
	 for (Animale animale : animaleLista) {
		 entityManager.getTransaction().begin();
		 System.out.println("Inserimento nel database: " + animale);
		 Animale animaleCaricamento = new Animale();
		 animaleCaricamento.setMatricola(animale.getMatricola());
		 animaleCaricamento.setCliente(animale.getCliente());
		 animaleCaricamento.setNome(animale.getNome());
		 animaleCaricamento.setPrezzo(animale.getPrezzo());
		 animaleCaricamento.setTipoAnimale(animale.getTipoAnimale());
		 animaleCaricamento.setDataAcquisto(animale.getDataAcquisto());
		// stessa funzione per animali
	     String jpqlQuery2 = "SELECT a.matricola FROM Animale a WHERE a.matricola = :matricolacheck";
	     Query query2 = entityManager.createQuery(jpqlQuery2);
	     query2.setParameter("matricolacheck", animale.getMatricola());
	     List<Animale> risultati2 = query2.getResultList();
	     //eseguo un controllo
	     if(risultati2.isEmpty()) {
	    	 entityManager.persist(animaleCaricamento);
			 entityManager.getTransaction().commit();
	     }else {     
	    	 entityManager.getTransaction().commit();
	     System.out.println("Animale gia presente sul db");
	 }
		 
	 }
  		entityManager.close();
  		emFactory.close();
}
}