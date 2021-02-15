package es.urjc.etsii.dad.Entities;

import javax.persistence.*;
import org.springframework.web.context.annotation.SessionScope;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String Nombre;
	private String Contrasena;
}

