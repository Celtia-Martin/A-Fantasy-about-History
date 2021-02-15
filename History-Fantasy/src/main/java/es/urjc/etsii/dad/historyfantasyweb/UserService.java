package es.urjc.etsii.dad.historyfantasyweb;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class UserService {

	
	private ConcurrentMap<String,Usuario> users=  new ConcurrentHashMap<>();
	private AtomicLong index= new AtomicLong();
	
	public UserService(){
		users.put("Panumo", new Usuario ("Panumo","115"));
	}
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
	}
	
	public AtomicLong getIndex() {
		return index;
	}
	public void setIndex(AtomicLong index) {
		this.index = index;
	}
	public Collection<Usuario> getUsers() {
		return users.values();
	}
	public void setUsers(ConcurrentMap<String,Usuario> users) {
		this.users = users;
	}
}
