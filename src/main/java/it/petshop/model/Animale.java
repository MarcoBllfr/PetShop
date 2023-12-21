package it.petshop.model;

import java.io.Serializable;


import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the animale database table.
 * 
 */
@Entity
@NamedQuery(name="Animale.findAll", query="SELECT a FROM Animale a")
public class Animale implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int matricola;

	@Temporal(TemporalType.DATE)
	@Column(name="data_acquisto")
	private Date dataAcquisto;

	private String nome;

	private Double prezzo;

	@Column(name="tipo_animale")
	private String tipoAnimale;

	//bi-directional many-to-one association to Cliente
	@ManyToOne
	@JoinColumn(name="ntelefono")
	private Cliente cliente;

	public Animale() {
	}

	public int getMatricola() {
		return this.matricola;
	}

	public void setMatricola(int matricola) {
		this.matricola = matricola;
	}

	public Date getDataAcquisto() {
		return this.dataAcquisto;
	}

	public void setDataAcquisto(Date dataAcq) {
		this.dataAcquisto = dataAcq;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Double getPrezzo() {
		return this.prezzo;
	}

	

	public String getTipoAnimale() {
		return this.tipoAnimale;
	}

	public void setTipoAnimale(String tipoAnimale) {
		this.tipoAnimale = tipoAnimale;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	@Override
	public String toString() {
		return "Animale [matricola=" + matricola + ", dataAcquisto=" + dataAcquisto + ", nome=" + nome + ", prezzo="
				+ prezzo + ", tipoAnimale=" + tipoAnimale + ", cliente=" + cliente + "]";
	}

	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}



	

}