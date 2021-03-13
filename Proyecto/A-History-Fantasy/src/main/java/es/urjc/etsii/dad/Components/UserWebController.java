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
	private BCryptPasswordEncoder encoder;
	
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
		
		controlUsuarios.newUser("Celtia", encoder.encode("115"), controlPersonajes, controlFormacion, controlMercado, controlBatalla,true);
		
		controlUsuarios.newUser("Daniel", encoder.encode("115"), controlPersonajes, controlFormacion, controlMercado, controlBatalla,true);
		controlUsuarios.newUser("AristoGato", encoder.encode("Gato"), controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Paimon", encoder.encode("EmergencyFood"), controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Richtofen", encoder.encode("hayquequemarlasconfire"), controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("M.Rajoy",encoder.encode( "persianas"), controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Kala",encoder.encode( "ffviii"), controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Panumo",encoder.encode("115") , controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Joselito", encoder.encode("joselito"), controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Japi",encoder.encode("115") , controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Musa",encoder.encode("115") , controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Jaimito",encoder.encode( "chiste"), controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
		controlUsuarios.newUser("Cactus", encoder.encode( "noAgua"), controlPersonajes, controlFormacion, controlMercado, controlBatalla,false);
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
	public String newUser(@RequestParam String nombre ,@RequestParam String contrasena, Model model,HttpServletRequest request) {
		
		if(nombre.trim().equals("")||contrasena.trim().equals("")) {
			datosInsuficientes = true;
			model.addAttribute("datosInsuficientes",datosInsuficientes);
			datosInsuficientes = false;
			
			return "newUsuario";
		}
		else {
			
			
			if(controlUsuarios.newUser(nombre, encoder.encode(contrasena),controlPersonajes,controlFormacion,controlMercado, controlBatalla,false)) {
			
				return Inicio(model);
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
	/*
	@PostMapping("/login")
	public String LoginPost(@RequestParam String nombre ,@RequestParam String contrasena, Model model,HttpServletRequest request) {
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
					return LogIn(model,request);
				}
				else {
					currentUser.setCurrentName(nuevo.getNombre());
					return GetMenuPrincipal(model,request);
				}

			}
			else {
				errorUsuario = true;
				model.addAttribute("errorUsuario", errorUsuario);
				errorUsuario = false;
				
				return "login";
			}
		}
	}*/
	
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
	public String AdministrarUsuariosPage(Model model,@PathVariable int page,HttpServletRequest request) {
		if(ActualizarEncabezado(model,request,true)) {
		
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
	public String Banear(Model model,@PathVariable Long id,HttpServletRequest request) {
		Optional<User> user= controlUsuarios.findById(id);
		if(user.isPresent()) {
			user.get().setBaneado(!user.get().isBaneado());
			controlUsuarios.Update(user.get());
			usuarioBaneadoConExito=true;
			
		}
		else{
			errorBaneo=true;
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
