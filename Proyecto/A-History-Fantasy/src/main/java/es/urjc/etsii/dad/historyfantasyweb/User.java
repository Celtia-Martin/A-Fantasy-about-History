package es.urjc.etsii.dad.historyfantasyweb;

import javax.persistence.*;
import org.springframework.web.context.annotation.SessionScope;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)

	private String Nombre;
	private String Contrasena;
}

