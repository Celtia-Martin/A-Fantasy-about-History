package com.servidorInterno.HistoryFantasy;


import javax.persistence.*;

@Entity
public class Puja {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private int valor;
	
	@ManyToOne
	private Personaje personajePujado;
	
	@ManyToOne
	private User pujante;
	
	public Puja() {
		
	}
	public Puja(Personaje personaje, User user, int valor) {
		
		pujante= user;
		personajePujado= personaje;
		this.setValor(valor);
	}
	public User getUser() {
		return pujante;
	}
	public int getValor() {
		return valor;
	}
	public void setValor(int valor) {
		this.valor = valor;
	}
	
}
