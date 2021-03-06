package es.urjc.etsii.dad.Components;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
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
	
	public void BorrarPersonajes(Long idFormacion,ControlPersonajes control) {
		log.warn("Procedemos a borrar");
		Optional<Formacion> f=repository.findById(idFormacion);
		log.warn(idFormacion.toString());
		if(f.isPresent()) {
			List<Personaje> ps= f.get().getPersonajes();
			
			int size=ps.size();
			for(int i=0;i<size;i++) {
				Personaje p= ps.remove(0);
		
				if(p.isDefault()) {
					f.get().deletePersonaje(p.getId());
					repository.save(f.get());
					control.deleteById(p.getId());
					
				}
				else {
					p.setTieneFormacion(false);
					control.addDefault(p);
				}

			}
		}
		else {
			
		}
	
		
	
				
			
	}
	public boolean VenderPersonaje( Long idPersonaje,User user,ControlPersonajes controlPersonajes,ControlUsuarios controlUsuarios) {
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
					user.setFormacion(formacion.get());
					controlUsuarios.Update(user);
					controlPersonajes.InvalidarCache();
					return true;
				}
			}
			
		}
		return false;
	}
	
}
