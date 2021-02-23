package es.urjc.etsii.dad.Components;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class ControlUsuarios implements CommandLineRunner {

	@Autowired
	private UserRepository repository;
	
	public boolean newUser (String nombre, String contra,ControlPersonajes controlPersonajes,ControlFormaciones controlFormaciones,ControlMercado controlMercado) {
		Optional<User> mismoNombre= repository.findByNombre(nombre);
		if(repository.count()==0) {
			controlMercado.newMercado(controlPersonajes);
		}
		if(mismoNombre.isPresent()) {
			return false;
		}
		else {
			Formacion nuevaFormacion= new Formacion();
			nuevaFormacion.initFormacion(controlPersonajes);
			User nuevo=new User(nombre,contra);
			nuevo.setFormacion(nuevaFormacion);
			repository.save(nuevo);
			controlFormaciones.NewFormacion(nuevaFormacion);
			nuevaFormacion.SetFormationToPersonaje();
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

	public Optional<User> findByNombre(String nombre)  {
		return repository.findByNombre(nombre);
	}
	
	public List<User> findTop10ByPuntosDesc(){
		return repository.findTop10ByOrderByPuntosDesc();
	}
	
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
	}
}
