package es.urjc.etsii.dad.historyfantasyweb.Services;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import es.urjc.etsii.dad.Entities.Batalla;
import es.urjc.etsii.dad.Entities.User;
import es.urjc.etsii.dad.historyfantasyweb.Repositories.BatallaRepository;
import es.urjc.etsii.dad.historyfantasyweb.Repositories.UserRepository;

//@Service
@Component
@SessionScope
public class BatallaService {

private BatallaRepository batallas;
	
	//Hacer consultas
	
	
	public Collection<Batalla> findAll() {
		return batallas.findAll();
	}
	
	public Page<Batalla> findAll(Pageable pageable) {
		return batallas.findAll(pageable);
	}

	public Optional<Batalla> findById(long id) {
		return batallas.findById(id);
	}

	public void save(Batalla post) {

		batallas.save(post);
	}

	public void deleteById(long id) {
		this.batallas.deleteById(id);
	}

}
