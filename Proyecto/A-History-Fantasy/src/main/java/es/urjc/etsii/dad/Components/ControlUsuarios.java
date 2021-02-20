package es.urjc.etsii.dad.Components;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import antlr.collections.List;

@Controller
public class ControlUsuarios implements CommandLineRunner {

	@Autowired
	private UserRepository repository;
	public boolean newUser (String nombre, String contra) {
		Optional<User> mismoNombre= repository.findByNombre(nombre);
		if(mismoNombre.isPresent()) {
			return false;
		}
		else {
			repository.save(new User(nombre,contra));
			return true;
		}
		
	}
	
	public boolean LogIn(String nombre, String contra) {
		Optional<User> mismoNombre= repository.findByNombre(nombre);
		if(!mismoNombre.isPresent()) {
			return false;
		}
		else {
			
			if(mismoNombre.get().getContrasena().equals(contra)) {
				return true;
			}
			else {
				
				return false;
			}
		}
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
