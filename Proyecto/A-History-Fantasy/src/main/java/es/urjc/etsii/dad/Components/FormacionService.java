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

public class FormacionService {
	@Autowired
	private FormacionRepository formaciones;
	
	//Hacer consultas
	
	
	public Collection<Formacion> findAll() {
		return formaciones.findAll();
	}
	
	public Page<Formacion> findAll(Pageable pageable) {
		return formaciones.findAll(pageable);
	}

	public Optional<Formacion> findById(long id) {
		return formaciones.findById(id);
	}

	public void save(Formacion post) {

		formaciones.save(post);
	}

	public void deleteById(long id) {
		this.formaciones.deleteById(id);
	}

}
*/