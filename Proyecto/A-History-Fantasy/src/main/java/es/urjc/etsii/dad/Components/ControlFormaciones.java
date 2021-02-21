package es.urjc.etsii.dad.Components;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
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
}
