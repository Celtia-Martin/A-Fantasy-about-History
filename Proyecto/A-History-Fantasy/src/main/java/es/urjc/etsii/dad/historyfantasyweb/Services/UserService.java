package es.urjc.etsii.dad.historyfantasyweb.Services;


import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import es.urjc.etsii.dad.historyfantasyweb.User;
import es.urjc.etsii.dad.historyfantasyweb.Repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository users;
	
	
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

}
