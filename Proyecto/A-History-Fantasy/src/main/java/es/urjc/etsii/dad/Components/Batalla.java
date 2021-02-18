package es.urjc.etsii.dad.Components;

import javax.persistence.*;

@Entity
public class Batalla {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long tipo;
}
