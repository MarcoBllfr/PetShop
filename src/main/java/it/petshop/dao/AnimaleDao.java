package it.petshop.dao;

import java.util.List;


import it.petshop.model.Animale;

public interface AnimaleDao {
	List<Animale> getAll();
	Animale getAllbyId();
	List<Animale>getByCliente(int ntelefono);
	 void insert(Animale animale);
	 void update(Animale animale);
	 void delete(Animale animale);
	
}
