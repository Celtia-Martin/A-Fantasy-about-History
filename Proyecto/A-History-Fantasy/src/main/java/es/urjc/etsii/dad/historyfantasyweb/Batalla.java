package es.urjc.etsii.dad.historyfantasyweb;

import javax.persistence.*;

@Entity
public class Batalla {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private int Tipo;
}
