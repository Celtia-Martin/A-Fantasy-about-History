package es.urjc.etsii.dad.Components;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
	
	public UserRepositoryAuthenticationProvider() {

	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		
		
		Optional<User> user= userRepository.findByNombre(auth.getName());
		
		if(!user.isPresent()) {

			throw new BadCredentialsException("User not found");
		}
		
		String password = (String) auth.getCredentials();
		
		if (!new BCryptPasswordEncoder().matches(password, user.get().getContrasena())) {

			throw new BadCredentialsException("Wrong password");
		}
		if(user.get().isBaneado()) {

			throw new BadCredentialsException("Banned");
		}
		List<GrantedAuthority> roles = new ArrayList<>();
			
		for (String role : user.get().getRoles()) {
			roles.add(new SimpleGrantedAuthority(role));
		}

		

		
		return new UsernamePasswordAuthenticationToken(user.get().getNombre(), password, roles);
	}
	
	@Override
	public boolean supports(Class<?> authenticationObject) {
		
		return true;
	}

}
