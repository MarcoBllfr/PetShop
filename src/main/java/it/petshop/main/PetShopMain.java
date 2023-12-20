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
		
		/*
		Servizi service = new Servizi();
		service.caricDati(emFactory , entityManager);
		*/
		ServizioStampa stampa = new ServizioStampa();
		//stampa.stampaVideo();
		System.out.println("inserisci il telefono del cliente");
		int ntelefono = scanner.nextInt();
		stampa.stampaFileCliente(ntelefono);
		stampa.stampaOrdinata();
		
	}

}
