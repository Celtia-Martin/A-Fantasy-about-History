/*
package es.urjc.etsii.dad.Components;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
//@Component
//@SessionScope

public class MercadoService {
	@Autowired
	private MercadoRepository mercados;
	
	//Hacer consultas
	
	
	public Collection<Mercado> findAll() {
		return mercados.findAll();
	}
	
	public Page<Mercado> findAll(Pageable pageable) {
		return mercados.findAll(pageable);
	}

	public Optional<Mercado> findById(long id) {
		return mercados.findById(id);
	}

	public void save(Mercado post) {

		mercados.save(post);
	}

	public void deleteById(long id) {
		this.mercados.deleteById(id);
	}

}
*/