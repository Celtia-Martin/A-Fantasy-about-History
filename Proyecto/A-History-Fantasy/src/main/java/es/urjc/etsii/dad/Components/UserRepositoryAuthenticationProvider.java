package es.urjc.etsii.dad.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	protected UsserSession currentUser;
	
	private Logger log = LoggerFactory.getLogger(UserRepositoryAuthenticationProvider.class);
	
	public UserRepositoryAuthenticationProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		// TODO Auto-generated method stub
		log.warn("hola");
		Optional<User> user= userRepository.findByNombre(auth.getName());
		
		if(!user.isPresent()) {
			log.warn("NO ENCONTRADO");
			throw new BadCredentialsException("User not found");
		}
		
		String password = (String) auth.getCredentials();
		
		if (!new BCryptPasswordEncoder().matches(password, user.get().getContrasena())) {
			log.warn("MALA CONTRASENA");
			throw new BadCredentialsException("Wrong password");
		}
			
		List<GrantedAuthority> roles = new ArrayList<>();
			
		for (String role : user.get().getRoles()) {
			roles.add(new SimpleGrantedAuthority(role));
		}
			
		log.warn("TODO BIEN");
		currentUser.setCurrentName(auth.getName());
		
		return new UsernamePasswordAuthenticationToken(user.get().getNombre(), password, roles);
	}

	@Override
	public boolean supports(Class<?> authenticationObject) {
		// TODO Auto-generated method stub
		return true;
	}

}
