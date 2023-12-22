package it.petshop.main;

import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import it.petshop.service.Servizi;
import it.petshop.service.ServizioStampa;

public class PetShopMain {
	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("PetShop");
		EntityManager entityManager = emFactory.createEntityManager();
		
		Servizi service = new Servizi();
		
		
		ServizioStampa stampa = new ServizioStampa();
		//stampa.stampaVideo();

	boolean entrata = true;
	while(entrata) {	
		System.out.println("Che operazione desideri effettuare?\n"
				+ "1* Caricamento dati\n"
				+ "2* Stampa info di un cliente\n"
				+ "3* Stampare un report delle vendite\n"
				+ "inserisci qualsiasi altro numero per chiudere il programma");
		int opzione = scanner.nextInt();
		switch (opzione) {
		  case 1:
			service.caricDati(emFactory , entityManager);
		    break;
		  case 2:
			System.out.println("inserisci il telefono del cliente");
			int ntelefono = scanner.nextInt();
			 stampa.stampaFileClienteTxT(ntelefono);
			 stampa.stampaFileClientePdf(ntelefono);
		    break;
		  case 3:
			stampa.stampaOrdinataTxT();
			stampa.stampaReportPdf();
			break;
		  default:
		    System.out.println("Nesuna operazione scelta chiusura");
		    entrata=false;
		}
	}
		
		
	}

}
