package es.urjc.etsii.dad.Components;

import javax.persistence.*;

@Entity
public class Mercado {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

}
