package it.petshop.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the cliente database table.
 * 
 */
@Entity
@NamedQuery(name="Cliente.findAll", query="SELECT c FROM Cliente c")
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int ntelefono;

	private String citta;

	@Column(name="cognome_cliente")
	private String cognomeCliente;

	private String indirizzo;

	@Column(name="nome_cliente")
	private String nomeCliente;

	//bi-directional many-to-one association to Animale
	@OneToMany(mappedBy="cliente")
	private List<Animale> animales;

	public Cliente() {
	}

	public int getNtelefono() {
		return this.ntelefono;
	}

	public void setNtelefono(int ntelefono) {
		this.ntelefono = ntelefono;
	}

	public String getCitta() {
		return this.citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public String getCognomeCliente() {
		return this.cognomeCliente;
	}

	public void setCognomeCliente(String cognomeCliente) {
		this.cognomeCliente = cognomeCliente;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNomeCliente() {
		return this.nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public List<Animale> getAnimales() {
		return this.animales;
	}

	public void setAnimales(List<Animale> animales) {
		this.animales = animales;
	}

	public Animale addAnimale(Animale animale) {
		getAnimales().add(animale);
		animale.setCliente(this);

		return animale;
	}

	public Animale removeAnimale(Animale animale) {
		getAnimales().remove(animale);
		animale.setCliente(null);

		return animale;
	}

}