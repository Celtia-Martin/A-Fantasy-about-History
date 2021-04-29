package com.servidorInterno.HistoryFantasy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

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
		
	}
	
	public List<Personaje> findAll(){
		return repository.findAll();
	}
	
	public Page<Personaje> findNoDefaultWithPage(int page){
		return repository.findByIsDefault(false, PageRequest.of(page, 10));
	}
	public void UpdatePersonaje(Personaje p) {
		repository.save(p);
	}
}
