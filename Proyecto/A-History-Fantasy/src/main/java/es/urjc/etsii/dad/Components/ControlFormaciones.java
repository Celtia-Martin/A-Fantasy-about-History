package es.urjc.etsii.dad.Components;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

@Service
public class ControlFormaciones  implements CommandLineRunner{

	@Autowired
	private FormacionRepository repository;
	public ControlFormaciones() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
	}

	public void NewFormacion(Formacion f) {
		repository.save(f);
		
	}
	public Optional<Formacion> findByPropietario(User user){
	
		return repository.findByPropietario(user);
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
					aVender.setFormacion(null);
					if(aVender.isDefault()) {
						controlPersonajes.deleteById((long)idPersonaje);
					}
					return true;
				}
			}
			
		}
		return false;
	}
}
