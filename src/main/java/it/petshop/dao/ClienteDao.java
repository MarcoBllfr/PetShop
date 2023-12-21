package it.petshop.dao;

import java.util.List;

import it.petshop.model.Cliente;



public interface ClienteDao {
	List<Cliente> getAll();
	Cliente getAllbyId(int ntelefono);
	 void insert(Cliente cliente);
	 void update(Cliente cliente);
	 void delete(int ntelefono);
	
}
