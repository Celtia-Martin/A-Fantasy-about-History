package es.urjc.etsii.dad.Components;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
		
		if (controlUsuarios.estaVacia()) {
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
		
		
	}
	
	
	@GetMapping("/newUsuario")
	public String NuevoUsuario(Model model, HttpServletRequest request,HttpSession http) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		
		model.addAttribute("token", token.getToken());
	
		model.addAttribute("errorUsuario",http.getAttribute("errorUsuario"));
		model.addAttribute("errorContra", http.getAttribute("errorContra"));
		model.addAttribute("datosInsuficientes", http.getAttribute("errorDatosInsu"));
		http.setAttribute("errorUsuario",false);
		http.setAttribute("errorContra",false);
		http.setAttribute("errorDatosInsu",false);

		return "newUsuario";
	}

	@PostMapping("/newUsuario")
	public String newUser(@RequestParam String nombre ,@RequestParam String contrasena, Model model,HttpServletRequest request, HttpSession http) {
		
		if(nombre.trim().equals("")||contrasena.trim().equals("")) {
			http.setAttribute("errorDatosInsu",true);
			model.addAttribute("datosInsuficientes",http.getAttribute("errorDatosInsu"));
			http.setAttribute("errorDatosInsu",false);
			
			return NuevoUsuario(model,request,http);
		}
		else {
			
			if(controlUsuarios.newUser(nombre, contrasena,controlPersonajes,controlFormacion,controlMercado, controlBatalla,false)) {
			
				return Inicio(model);
			}
			else {
				
				http.setAttribute("errorUsuario",true);
				return NuevoUsuario(model,request,http);
			}
		}
	}
	
	@GetMapping("/login")
	public String LogIn(Model model, HttpServletRequest request) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		
		model.addAttribute("token", token.getToken()); 
		/*
		model.addAttribute("errorUsuario", currentUser.isErrorUsuario());
		model.addAttribute("errorContra",currentUser.isErrorContra());
		model.addAttribute("hasSidoBaneado",currentUser.isBaneado());
		currentUser.setErrorUsuario(false);
		currentUser.setErrorContra(false);
		currentUser.setDatosInsuficientes(false);
		currentUser.setBaneado(false);*/
		
		return "login";
	}
	
	@GetMapping("/clasificacion")
	public String MostrarClasificacion(Model model,HttpServletRequest request, HttpSession session) {
		model.addAttribute("clasificacion", controlUsuarios.findTop10ByPuntosDesc());
		if(ActualizarEncabezado(model,request,false, session)) {
			return "clasificacion";
		}else {
			return "errorNoLogin";
		}
	}
	

	@GetMapping("/administrarUsuarios")
	public String AdministrarUsuarios(Model model,HttpServletRequest request, HttpSession session) {
		if(ActualizarEncabezado(model,request,true, session)) {
			Page<User> users= controlUsuarios.findWithPage(0);
			model.addAttribute("hasPrev", users.hasPrevious());
			model.addAttribute("hasNext", users.hasNext());
			model.addAttribute("nextPage", users.getNumber()+1);
			model.addAttribute("prevPage", users.getNumber()-1);
			model.addAttribute("baneoExito", session.getAttribute("baneoExito") );
			model.addAttribute("error",session.getAttribute("errorBaneo"));
			model.addAttribute("usuarios", users);
			session.setAttribute("errorBaneo",false);
			session.setAttribute("baneoExito",false);
	
			return "administradorUsuarios";
		}
		else {
			return "errorNoLogin";
		}
	}
	@GetMapping("/administrarUsuarios/{page}")
	public String AdministrarUsuariosPage(Model model,@PathVariable int page,HttpServletRequest request, HttpSession session) {
		if(ActualizarEncabezado(model,request,true, session)) {
		
			Page<User> users= controlUsuarios.findWithPage(page);
			model.addAttribute("hasPrev", users.hasPrevious());
			model.addAttribute("hasNext", users.hasNext());
			model.addAttribute("nextPage", users.getNumber()+1);
			model.addAttribute("prevPage", users.getNumber()-1);
			model.addAttribute("baneoExito", session.getAttribute("baneoExito"));
			model.addAttribute("error",session.getAttribute("errorBaneo"));
			model.addAttribute("usuarios", users);
			session.setAttribute("errorBaneo",false);
			session.setAttribute("baneoExito",false);
		return "administradorUsuarios";
		}
		else {
			return "errorNoLogin";
		}
	}
	@PostMapping("/banear/{id}")
	public String Banear(Model model,@PathVariable Long id,HttpServletRequest request, HttpSession session) {
		Optional<User> user= controlUsuarios.findById(id);
		if(user.isPresent()) {
			user.get().setBaneado(!user.get().isBaneado());
			controlUsuarios.Update(user.get());
			session.setAttribute("baneoExito",true);
			
		}
		else{
			session.setAttribute("errorBaneo",true);
			
		}
		return AdministrarUsuarios(model,request, session);
	}
	@GetMapping("/BorrarUsuario")
	public String BorrarUsuario( Model model, HttpSession session,HttpServletRequest request) {

			Optional<User> user= controlUsuarios.findByNombre((String) request.getUserPrincipal().getName());
			if(user.isPresent()) {
				
				Formacion f= user.get().getFormacion();
				controlFormacion.BorrarPersonajes(f.getId(), controlPersonajes);
				controlUsuarios.delete(user.get());
			}
			
			return Inicio(model);
	
	}
	

}
