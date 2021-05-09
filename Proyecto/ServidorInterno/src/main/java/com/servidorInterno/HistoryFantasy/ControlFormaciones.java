package com.servidorInterno.HistoryFantasy;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class ControlFormaciones  implements CommandLineRunner{

	private Logger log = LoggerFactory.getLogger(ControlFormaciones.class);
	
	@Autowired
	private FormacionRepository repository;
	

	public ControlFormaciones() {
		
	}
	@Override
	public void run(String... args) throws Exception {
		
		
	}

	public void NewFormacion(Formacion f) {
		repository.save(f);
		
	}
	public Optional<Formacion> findByPropietario(User user){
	
		return repository.findByPropietario(user);
	}
	public void BorrarPersonajes(Long idFormacion,ControlPersonajes control) {
		log.warn("Procedemos a borrar");
		Optional<Formacion> f=repository.findById(idFormacion);
		log.warn(idFormacion.toString());
		if(f.isPresent()) {
			List<Personaje> ps= f.get().getPersonajes();
			log.warn("ES PRESENTE");
			int size=ps.size();
			for(int i=0;i<size;i++) {
				Personaje p= ps.remove(0);
				log.warn(p.getId().toString());
				if(p.isDefault()) {
					f.get().deletePersonaje(p.getId());
					repository.save(f.get());
					control.deleteById(p.getId());
					log.warn(p.getId().toString() +" borrado presuntamente");
				}
				else {
					p.setTieneFormacion(false);
					control.addDefault(p);
				}

			}
		}
		else {
			log.warn("NO PRESENTE");
		}
	
		
	
				
			
	}
	public boolean VenderPersonaje( Long idPersonaje,User user,ControlPersonajes controlPersonajes) {
		Optional<Personaje> personaje= controlPersonajes.findById((long)idPersonaje);
		if(personaje.isPresent()) {
			Personaje aVender= personaje.get();
			Formacion miFormacion= user.getFormacion();
			Long idFormacion= miFormacion.getId();
			Optional<Formacion> formacion= repository.findById(idFormacion);
			if(formacion.isPresent()) {
				if(formacion.get().deletePersonaje(idPersonaje) ) {
					
					long precio= aVender.getPrecio();
					user.setDinero(user.getDinero()+precio);
					aVender.setTieneFormacion(false);
					if(aVender.isDefault()) {
						controlPersonajes.deleteById((long)idPersonaje);
					}
					
					repository.save(formacion.get());
				
					
					
					return true;
				}
			}
			
		}
		return false;
	}
	
}
