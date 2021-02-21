package es.urjc.etsii.dad.Components;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import es.urjc.etsii.dad.Components.Enums.*;

@Controller
public class ControlPersonajes implements CommandLineRunner {

	@Autowired
	private PersonajeRepository repository;
	
	public boolean newPersonaje (Personaje p) {
		Optional<Personaje> mismoNombre= repository.findByNombre(p.getNombre());
		
		if(mismoNombre.isPresent()) {
			return false;
		}
		else {
			repository.save(p);
			return true;
		}
	}
	
	@Override
	public void run(String... args) throws Exception {
		repository.save(new Personaje("Juana de Arco",5,TipoBatalla.MILITAR,115,5,4,1));
		repository.save(new Personaje("Mark Evans",5,TipoBatalla.MILITAR,100,5,2,3));
		repository.save(new Personaje("Rosalia de Castro",4,TipoBatalla.CULTURAL,200,2,5,5));
	}
}
