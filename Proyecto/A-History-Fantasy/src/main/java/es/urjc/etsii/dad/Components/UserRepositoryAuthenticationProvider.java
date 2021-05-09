package es.urjc.etsii.dad.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class UserRepositoryAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	protected UsserSession currentUser;
	
	@Autowired
	private HttpSession session;
	
	private Logger log = LoggerFactory.getLogger(UserRepositoryAuthenticationProvider.class);
	
	public UserRepositoryAuthenticationProvider() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		// TODO Auto-generated method stub
		
		Optional<User> user= userRepository.findByNombre(auth.getName());
		
		if(!user.isPresent()) {
			currentUser.setErrorUsuario(true);
			throw new BadCredentialsException("User not found");
		}
		
		String password = (String) auth.getCredentials();
		
		if (!new BCryptPasswordEncoder().matches(password, user.get().getContrasena())) {
			currentUser.setErrorContra(true);
			throw new BadCredentialsException("Wrong password");
		}
		if(user.get().isBaneado()) {
			currentUser.setBaneado(true);
			throw new BadCredentialsException("Banned");
		}
		List<GrantedAuthority> roles = new ArrayList<>();
			
		for (String role : user.get().getRoles()) {
			roles.add(new SimpleGrantedAuthority(role));
		}
			
		currentUser.setCurrentName(auth.getName());
		
		session.setAttribute("nombre", auth.getName());
		
		log.warn("Nombre de Usuario: " + currentUser.getCurrentName());
		
		return new UsernamePasswordAuthenticationToken(user.get().getNombre(), password, roles);
	}
	
	@Override
	public boolean supports(Class<?> authenticationObject) {
		// TODO Auto-generated method stub
		return true;
	}

}
