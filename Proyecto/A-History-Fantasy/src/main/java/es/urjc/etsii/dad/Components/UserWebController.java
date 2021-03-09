package es.urjc.etsii.dad.Components;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
		controlUsuarios.newUser("Celtia", "115", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("Daniel", "115", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("AristoGato", "Gato", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("Paimon", "EmergencyFood", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("Richtofen", "hayquequemarlasconfire", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("M.Rajoy", "persianas", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("Kala", "ffviii", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("Panumo", "115", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("Joselito", "joselito", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("Japi", "115", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("Musa", "115", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("Jaimito", "chiste", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlUsuarios.newUser("Cactus", "noAgua", controlPersonajes, controlFormacion, controlMercado, controlBatalla);
		controlPersonajes.iniciar();
		controlMercado.newMercado(controlPersonajes);
		controlBatalla.nuevaBatalla();
		
	}
	@GetMapping("/newUsuario")
	public String NuevoUsuario(Model model) {
		model.addAttribute("errorUsuario", errorUsuario);
		model.addAttribute("errorContra", errorContra);
		model.addAttribute("datosInsuficientes",datosInsuficientes);
		
		errorUsuario= false;
		errorContra= false;
		datosInsuficientes=false;

		return "newUsuario";
	}

	@PostMapping("/newUsuario")
	public String newUser(@RequestParam String nombre ,@RequestParam String contrasena, Model model) {
		
		if(nombre.trim().equals("")||contrasena.trim().equals("")) {
			datosInsuficientes = true;
			model.addAttribute("datosInsuficientes",datosInsuficientes);
			datosInsuficientes = false;
			
			return "newUsuario";
		}
		else {
			User nuevo= new User(nombre,contrasena);
			
			if(controlUsuarios.newUser(nombre,contrasena,controlPersonajes,controlFormacion,controlMercado, controlBatalla)) {
				currentUser.setCurrentName(nuevo.getNombre());
				return GetMenuPrincipal(model);
			}
			else {
				errorUsuario = true;
				model.addAttribute("errorUsuario", errorUsuario);
				
				return "newUsuario";
			}
		}
	}
	
	
	
	@GetMapping("/login")
	public String LogIn(Model model) {
		model.addAttribute("errorUsuario", errorUsuario);
		model.addAttribute("errorContra", errorContra);
		model.addAttribute("hasSidoBaneado",baneado);
		errorUsuario = false;
		errorContra = false;
		datosInsuficientes = false;
		baneado=false;

		
		return "login";
	}
	
	@PostMapping("/login")
	public String LoginPost(@RequestParam String nombre ,@RequestParam String contrasena, Model model) {
		if(nombre.trim().equals("")||contrasena.trim().equals("")) {
			datosInsuficientes = true;
			model.addAttribute("datosInsuficientes",datosInsuficientes);
			datosInsuficientes = false;
			
			return "login";
		}
		else {
			User nuevo = new User(nombre,contrasena);
			
			if(controlUsuarios.LogIn(nombre,contrasena)) {
				Optional<User> user= controlUsuarios.findByNombre(nombre);
				if(user.get().isBaneado()) {
					baneado=true;
					return LogIn(model);
				}
				else {
					currentUser.setCurrentName(nuevo.getNombre());
					return GetMenuPrincipal(model);
				}

			}
			else {
				errorUsuario = true;
				model.addAttribute("errorUsuario", errorUsuario);
				errorUsuario = false;
				
				return "login";
			}
		}
	}
	
	@GetMapping("/clasificacion")
	public String MostrarClasificacion(Model model) {
		model.addAttribute("clasificacion", controlUsuarios.findTop10ByPuntosDesc());
		if(ActualizarEncabezado(model)) {
			return "clasificacion";
		}else {
			return "errorNoLogin";
		}
	}
	

	@GetMapping("/administrarUsuarios")
	public String AdministrarUsuarios(Model model) {
		if(ActualizarEncabezado(model)) {
			Page<User> users= controlUsuarios.findWithPage(0);
			model.addAttribute("hasPrev", users.hasPrevious());
			model.addAttribute("hasNext", users.hasNext());
			model.addAttribute("nextPage", users.getNumber()+1);
			model.addAttribute("prevPage", users.getNumber()-1);
			model.addAttribute("baneoExito", usuarioBaneadoConExito);
			model.addAttribute("error",errorBaneo);
			model.addAttribute("usuarios", users);
			errorBaneo=false;
			usuarioBaneadoConExito=false;
			return "administradorUsuarios";
		}
		else {
			return "errorNoLogin";
		}
	}
	@GetMapping("/administrarUsuarios/{page}")
	public String AdministrarUsuariosPage(Model model,@PathVariable int page) {
		if(ActualizarEncabezado(model)) {
		
			Page<User> users= controlUsuarios.findWithPage(page);
			model.addAttribute("hasPrev", users.hasPrevious());
			model.addAttribute("hasNext", users.hasNext());
			model.addAttribute("nextPage", users.getNumber()+1);
			model.addAttribute("prevPage", users.getNumber()-1);
			model.addAttribute("baneoExito", usuarioBaneadoConExito);
			model.addAttribute("error",errorBaneo);
			model.addAttribute("usuarios", users);
			errorBaneo=false;
			usuarioBaneadoConExito=false;
		return "administradorUsuarios";
		}
		else {
			return "errorNoLogin";
		}
	}
	@PostMapping("/banear/{id}")
	public String Banear(Model model,@PathVariable Long id) {
		Optional<User> user= controlUsuarios.findById(id);
		if(user.isPresent()) {
			user.get().setBaneado(!user.get().isBaneado());
			controlUsuarios.Update(user.get());
			usuarioBaneadoConExito=true;
			
		}
		else{
			errorBaneo=true;
		}
		return AdministrarUsuarios(model);
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
