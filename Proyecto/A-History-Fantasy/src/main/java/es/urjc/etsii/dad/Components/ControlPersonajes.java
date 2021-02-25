package es.urjc.etsii.dad.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import es.urjc.etsii.dad.Components.Enums.*;

@Service
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
	
	public Optional<Personaje> findByNombre(String nombre){
		return repository.findByNombre(nombre);
	}
	public List<Personaje> getRandomMercado(){
		
		List<Personaje> result= new ArrayList<>();
		List<Personaje> query= repository.findByIsDefaultAndTieneFormacion(false, false );
		int cont=5;
		if(query.size()<cont) {
			return query;
		}
		for(int i =0; i<cont ;i ++) {
			int idx= (int)(Math.random()*(query.size()));
			result.add(query.remove(idx));
			
		}
		
		return result;
	}
	public void addDefault(Personaje p) {
		repository.save(p);
	}
	public Optional<Personaje> findById(Long id){
		return repository.findById(id);
	}
	public void deleteById(Long id){
		repository.deleteById(id);
	}
	@Override
	public void run(String... args) throws Exception {
		
		repository.save(new Personaje("Juana de Arco",5,TipoBatalla.MILITAR,115,5,4,1,false));
		repository.save(new Personaje("Mark Evans",5,TipoBatalla.MILITAR,100,5,2,3,false));
		repository.save(new Personaje("Rosalia de Castro",4,TipoBatalla.CULTURAL,200,2,5,5,false));
		repository.save(new Personaje("Cleopatra",5,TipoBatalla.DIPLOMATICO,115,3,3,5,false));
		repository.save(new Personaje("Napoleón",5,TipoBatalla.MILITAR,100,5,2,3,false));
		repository.save(new Personaje("Cervantes",4,TipoBatalla.CULTURAL,200,2,5,5,false));
		repository.save(new Personaje("Nobunaga",5,TipoBatalla.MILITAR,115,5,4,1,false));
		repository.save(new Personaje("María Teresa de Calcuta",5,TipoBatalla.DIPLOMATICO,100,1,2,3,false));
		repository.save(new Personaje("Elvis",4,TipoBatalla.CULTURAL,200,2,5,5,false));
	}
	public List<Personaje> findAll(){//Posteriormente mostrar paginas de personajes!
		return repository.findAll();
	}
}
