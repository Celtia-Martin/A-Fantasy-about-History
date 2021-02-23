package es.urjc.etsii.dad.Components;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
public class Puja {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long ID;
	
	private int Valor;
	
	@ManyToOne
	private Personaje personajePujado;
	
	@ManyToOne
	private User pujante;
}
