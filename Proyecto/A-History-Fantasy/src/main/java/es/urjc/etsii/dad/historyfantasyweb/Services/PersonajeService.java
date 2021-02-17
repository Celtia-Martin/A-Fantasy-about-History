package es.urjc.etsii.dad.historyfantasyweb.Services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import es.urjc.etsii.dad.Entities.Personaje;
import es.urjc.etsii.dad.Entities.User;
import es.urjc.etsii.dad.historyfantasyweb.Repositories.PersonajeRepository;
import es.urjc.etsii.dad.historyfantasyweb.Repositories.UserRepository;

//@Service
@Component
@SessionScope
public class PersonajeService {

	private PersonajeRepository personajes;
	
	//Hacer consultas
	
	
	public Collection<Personaje> findAll() {
		return personajes.findAll();
	}
	
	public Page<Personaje> findAll(Pageable pageable) {
		return personajes.findAll(pageable);
	}

	public Optional<Personaje> findById(long id) {
		return personajes.findById(id);
	}

	public void save(Personaje post) {

		personajes.save(post);
	}

	public void deleteById(long id) {
		this.personajes.deleteById(id);
	}

}
