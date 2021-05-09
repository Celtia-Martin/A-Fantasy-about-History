package com.servidorInterno.HistoryFantasy;

import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
@Transactional
@Service
public class ControlUsuarios implements CommandLineRunner {

	@Autowired
	private UserRepository repository;
	

	public boolean newUser (String nombre, String contra, ControlPersonajes controlPersonajes,ControlFormaciones controlFormaciones,ControlMercado controlMercado, BatallaService controlBatalla,boolean esAdmin) {
		Optional<User> mismoNombre= repository.findByNombre(nombre);
	
		if(mismoNombre.isPresent()) {
			return false;
		}
		else {
			Formacion nuevaFormacion = new Formacion();
			nuevaFormacion.initFormacion(controlPersonajes);
			
			User nuevo = new User(nombre,contra);
			nuevo.addRol("ROLE_USER");
			
			if(esAdmin) {
				nuevo.addRol("ROLE_ADMIN");
			}
			
			nuevo.setFormacion(nuevaFormacion);
			repository.save(nuevo);
			
			controlFormaciones.NewFormacion(nuevaFormacion);
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
	
	public void deleteByNombre(String nombre) {
		repository.deleteByNombre(nombre);
	}
	public void delete(User user) {
		repository.delete(user);
	}
	public void Update(User user) {
		repository.save(user);
	}
	
	public Page<User> findWithPage(int page){
		Page<User> pagina= repository.findAll(PageRequest.of(page, 10));
		return pagina;
	}
	public 	Optional<User> findById(Long id){
		return repository.findById(id);
	}

	
	@Override
	public void run(String... args) throws Exception {
		
	}
}


