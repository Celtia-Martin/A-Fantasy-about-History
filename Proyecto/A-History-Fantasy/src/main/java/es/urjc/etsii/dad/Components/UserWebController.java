package es.urjc.etsii.dad.Components;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserWebController extends WebController{
	
	@Autowired
	private ControlPersonajes controlPersonajes;
	
	@Autowired
	private ControlFormaciones controlFormacion;
	
	@Autowired
	private ControlMercado controlMercado;
	
	@Autowired
	private BatallaService controlBatalla;
	
	@Autowired
	public UserWebController() {
		
	}
	
	@PostConstruct
	 void started() {
		//por ahora de testeo, al pasar mysql a update estas lineas deben comentarse
		/*
		System.setProperty("javax.net.ssl.trustStore", "myclientkeystore");
		System.setProperty("javax.net.ssl.trustStorePassword", "GatoPato");
		*/
		
		controlUsuarios.newUser("Celtia", "115", controlPersonajes, controlFormacion, controlMercado, controlBatalla,true);
		controlUsuarios.newUser("Daniel", "115", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		
		controlUsuarios.newUser("AristoGato", "Gato", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Paimon", "EmergencyFood", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Richtofen","hayquequemarlasconfire", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("M.Rajoy", "persianas", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Kala", "ffviii", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Panumo","115" , controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Joselito", "joselito", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Japi","115" , controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Musa","115" , controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Jaimito", "chiste", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Cactus",  "noAgua", controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		
		
		controlPersonajes.iniciar();
		controlMercado.newMercado(controlPersonajes);
		controlBatalla.nuevaBatalla();
	}
	
	@GetMapping("/newUsuario")
	public String NuevoUsuario(Model model, HttpServletRequest request) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		
		model.addAttribute("token", token.getToken());
		
		model.addAttribute("errorUsuario", currentUser.isErrorUsuario());
		model.addAttribute("errorContra", currentUser.isErrorContra());
		model.addAttribute("datosInsuficientes", currentUser.isDatosInsuficientes());
		currentUser.setErrorUsuario(false);
		currentUser.setErrorContra(false);
		currentUser.setDatosInsuficientes(false);

		return "newUsuario";
	}

	@PostMapping("/newUsuario")
	public String newUser(@RequestParam String nombre ,@RequestParam String contrasena, Model model,HttpServletRequest request) {
		
		if(nombre.trim().equals("")||contrasena.trim().equals("")) {
			currentUser.setDatosInsuficientes(true);
			model.addAttribute("datosInsuficientes",currentUser.isDatosInsuficientes());
			currentUser.setDatosInsuficientes(false);
			
			return "newUsuario";
		}
		else {
			
			if(controlUsuarios.newUser(nombre, contrasena,controlPersonajes,controlFormacion,controlMercado, controlBatalla,false)) {
			
				return Inicio(model);
			}
			else {
				currentUser.setErrorUsuario(true);
				model.addAttribute("errorUsuario", currentUser.isErrorUsuario());
				
				return "newUsuario";
			}
		}
	}
	
	@GetMapping("/login")
	public String LogIn(Model model, HttpServletRequest request) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		
		model.addAttribute("token", token.getToken()); 
		model.addAttribute("errorUsuario", currentUser.isErrorUsuario());
		model.addAttribute("errorContra",currentUser.isErrorContra());
		model.addAttribute("hasSidoBaneado",currentUser.isBaneado());
		currentUser.setErrorUsuario(false);
		currentUser.setErrorContra(false);
		currentUser.setDatosInsuficientes(false);
		currentUser.setBaneado(false);
		
		return "login";
	}
	
	@GetMapping("/clasificacion")
	public String MostrarClasificacion(Model model,HttpServletRequest request) {
		model.addAttribute("clasificacion", controlUsuarios.findTop10ByPuntosDesc());
		if(ActualizarEncabezado(model,request,false)) {
			return "clasificacion";
		}else {
			return "errorNoLogin";
		}
	}
	

	@GetMapping("/administrarUsuarios")
	public String AdministrarUsuarios(Model model,HttpServletRequest request) {
		if(ActualizarEncabezado(model,request,true)) {
			Page<User> users= controlUsuarios.findWithPage(0);
			model.addAttribute("hasPrev", users.hasPrevious());
			model.addAttribute("hasNext", users.hasNext());
			model.addAttribute("nextPage", users.getNumber()+1);
			model.addAttribute("prevPage", users.getNumber()-1);
			model.addAttribute("baneoExito", currentUser.isUsuarioBaneadoConExito());
			model.addAttribute("error",currentUser.isErrorBaneo());
			model.addAttribute("usuarios", users);
			currentUser.setErrorBaneo(false);
			currentUser.setUsuarioBaneadoConExito(false);
			return "administradorUsuarios";
		}
		else {
			return "errorNoLogin";
		}
	}
	@GetMapping("/administrarUsuarios/{page}")
	public String AdministrarUsuariosPage(Model model,@PathVariable int page,HttpServletRequest request) {
		if(ActualizarEncabezado(model,request,true)) {
		
			Page<User> users= controlUsuarios.findWithPage(page);
			model.addAttribute("hasPrev", users.hasPrevious());
			model.addAttribute("hasNext", users.hasNext());
			model.addAttribute("nextPage", users.getNumber()+1);
			model.addAttribute("prevPage", users.getNumber()-1);
			model.addAttribute("baneoExito", currentUser.isUsuarioBaneadoConExito());
			model.addAttribute("error",currentUser.isErrorBaneo());
			model.addAttribute("usuarios", users);
			currentUser.setErrorBaneo(false);
			currentUser.setUsuarioBaneadoConExito(false);
		return "administradorUsuarios";
		}
		else {
			return "errorNoLogin";
		}
	}
	@PostMapping("/banear/{id}")
	public String Banear(Model model,@PathVariable Long id,HttpServletRequest request) {
		Optional<User> user= controlUsuarios.findById(id);
		if(user.isPresent()) {
			user.get().setBaneado(!user.get().isBaneado());
			controlUsuarios.Update(user.get());
			currentUser.setUsuarioBaneadoConExito(true);
			
		}
		else{
			currentUser.setErrorBaneo(true);
			
		}
		return AdministrarUsuarios(model,request);
	}
	@GetMapping("/BorrarUsuario")
	public String BorrarUsuario( Model model) {
		if(currentUser!=null) {
			Optional<User> user= controlUsuarios.findByNombre(currentUser.getCurrentName());
			if(user.isPresent()) {
				
				Formacion f= user.get().getFormacion();
				controlFormacion.BorrarPersonajes(f.getId(), controlPersonajes);
				controlUsuarios.delete(user.get());
			}
			
			return Inicio(model);
		}
		return "errorNoLogin";
	}
	

}
