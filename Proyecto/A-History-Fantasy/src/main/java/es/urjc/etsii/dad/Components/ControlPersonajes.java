package es.urjc.etsii.dad.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;


import es.urjc.etsii.dad.Components.Enums.*;
import javax.transaction.Transactional;
@Transactional
@Service
@CacheConfig(cacheNames="personajes")
public class ControlPersonajes implements CommandLineRunner {

	@Autowired
	private PersonajeRepository repository;
	@CacheEvict(allEntries=true)
	public void InvalidarCache() {
		
	}
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
		

	}
	public void iniciar() {
		repository.save(new Personaje("Juana de Arco",5,TipoBatalla.MILITAR,2000,800,600,100,false));
		repository.save(new Personaje("Mark Evans",1,TipoBatalla.MILITAR,100,200,100,150,false));
		repository.save(new Personaje("Rosalia de Castro",4,TipoBatalla.CULTURAL,1500,100,600,600,false));
		repository.save(new Personaje("Cleopatra",5,TipoBatalla.DIPLOMATICO,2000,300,300,650,false));
		repository.save(new Personaje("Napoleón",4,TipoBatalla.MILITAR,1300,400,100,300,false));
		repository.save(new Personaje("Cervantes",5,TipoBatalla.CULTURAL,2300,300,700,700,false));
		repository.save(new Personaje("Nobunaga",5,TipoBatalla.MILITAR,2000,650,500,100,false));
		repository.save(new Personaje("María Teresa de Calcuta",2,TipoBatalla.DIPLOMATICO,200,100,150,200,false));
		repository.save(new Personaje("Elvis",5,TipoBatalla.CULTURAL,2000,200,600,600,false));
		repository.save(new Personaje("Marie Curie",5,TipoBatalla.CULTURAL,2000,100,200,750,false));
		repository.save(new Personaje("Anne Bonny",4,TipoBatalla.MILITAR,1500,775,100,150,false));
		repository.save(new Personaje("María Pita",5,TipoBatalla.MILITAR,2000,800,600,300,false));
		repository.save(new Personaje("Las Marías",3,TipoBatalla.CULTURAL,750,100,500,400,false));
	}
	public List<Personaje> findAll(){
		return repository.findAll();
	}
	
	public Page<Personaje> findNoDefaultWithPage(int page){
		return repository.findByIsDefault(false, PageRequest.of(page, 10));
	}
	
}
