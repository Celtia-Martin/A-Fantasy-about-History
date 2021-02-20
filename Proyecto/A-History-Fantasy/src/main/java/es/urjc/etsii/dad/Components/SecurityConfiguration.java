/*package es.urjc.etsii.dad.Components;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests().antMatchers("/").permitAll();
		http.authorizeRequests().antMatchers("/newUsuario").permitAll();
		
		http.authorizeRequests().anyRequest().authenticated();
		
		
		http.formLogin().loginPage("/login");
		http.formLogin().usernameParameter("nombre");
		http.formLogin().passwordParameter("contrasena");
		
		http.logout().logoutUrl("/logout");
		http.logout().logoutSuccessUrl("/");
		
		http.csrf().disable();
		
		//auth.inMemoryAuthentication().withUser("ADMIN").password("115").roles("ADMIN");
	}

}

*/