package es.urjc.etsii.dad.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	 public UserRepositoryAuthenticationProvider authenticationProvider;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bc= new BCryptPasswordEncoder();
		return bc;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/newUsuario").permitAll();
		http.authorizeRequests().antMatchers("/login").permitAll();
		
		http.authorizeRequests().antMatchers("/mostrarTodosPersonajes").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers("/newPersonaje").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers("/administrarUsuarios").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers("/ejecutarBatalla").hasAnyRole("ADMIN");
		http.authorizeRequests().antMatchers("/refrescarMercado").hasAnyRole("ADMIN");		
		
		http.authorizeRequests().anyRequest().authenticated();
		
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("nombre");
		http.formLogin().passwordParameter("contrasena");
		http.formLogin().failureUrl("/error");
		http.formLogin().defaultSuccessUrl("/menuPrincipal");
		
		http.logout().logoutUrl("/error");
		http.logout().logoutSuccessUrl("/");
		
		//http.csrf().disable();
		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.authenticationProvider(authenticationProvider);
		
		auth.inMemoryAuthentication().withUser("user").password("pass")
		 .roles("USER");

	}

}

