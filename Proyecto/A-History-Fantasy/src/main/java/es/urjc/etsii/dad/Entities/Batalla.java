package es.urjc.etsii.dad.Entities;

import javax.persistence.*;

@Entity
public class Batalla {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private int Tipo;
}
