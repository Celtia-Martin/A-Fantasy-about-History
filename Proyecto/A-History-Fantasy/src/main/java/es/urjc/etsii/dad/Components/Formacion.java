package es.urjc.etsii.dad.Components;

import javax.persistence.*;

@Entity
public class Formacion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	

}
