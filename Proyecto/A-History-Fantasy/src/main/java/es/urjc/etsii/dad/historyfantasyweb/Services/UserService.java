package es.urjc.etsii.dad.historyfantasyweb.Services;


import java.util.Collection;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import es.urjc.etsii.dad.Entities.User;
import es.urjc.etsii.dad.historyfantasyweb.Repositories.UserRepository;

//@Service
@Component
@SessionScope
public class UserService {
	
	
	private UserRepository users;
	
	//Hacer consultas
	
	
	public Collection<User> findAll() {
		return users.findAll();
	}
	
	public Page<User> findAll(Pageable pageable) {
		return users.findAll(pageable);
	}

	public Optional<User> findById(long id) {
		return users.findById(id);
	}

	public void save(User post) {

		users.save(post);
	}

	public void deleteById(long id) {
		this.users.deleteById(id);
	}

	/*
	public boolean newUser(Usuario user) {
		if(user.getNombre().trim().equals("")||user.getContrasena().trim().equals("")) {
			return false;
		}
		if(users.containsKey(user.getNombre())) {
			return false;
		}
		else {
			users.put(user.getNombre(), user);
			return true;
		}
	}
	public Usuario logIn(Usuario user) {
		if(!users.containsKey(user.getNombre())) {
			return null;
		}
		Usuario original= users.get(user.getNombre());
		if(original.getContrasena().equals(user.getContrasena())) {
			return original;
		}
		return null;
	}*/
}
