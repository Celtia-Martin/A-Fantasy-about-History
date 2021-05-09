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
	public String newUser(@RequestParam String nombre ,@RequestParam String contrasena, Model model,HttpServletRequest request, HttpSession session) {
		
		if(nombre.trim().equals("")||contrasena.trim().equals("")) {
			currentUser.setDatosInsuficientes(true);
			model.addAttribute("datosInsuficientes",currentUser.isDatosInsuficientes());
			currentUser.setDatosInsuficientes(false);
			
			return NuevoUsuario(model,request);
		}
		else {
			
			if(controlUsuarios.newUser(nombre, contrasena,controlPersonajes,controlFormacion,controlMercado, controlBatalla,false)) {
			
				return Inicio(model, session);
			}
			else {
				currentUser.setErrorUsuario(true);
				
				return NuevoUsuario(model,request);
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
	public String AdministrarUsuariosPage(Model model,@PathVariable int page,HttpServletRequest request, HttpSession session) {
		if(ActualizarEncabezado(model,request,true, session)) {
		
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
	public String Banear(Model model,@PathVariable Long id,HttpServletRequest request, HttpSession session) {
		Optional<User> user= controlUsuarios.findById(id);
		if(user.isPresent()) {
			user.get().setBaneado(!user.get().isBaneado());
			controlUsuarios.Update(user.get());
			currentUser.setUsuarioBaneadoConExito(true);
			
		}
		else{
			currentUser.setErrorBaneo(true);
			
		}
		return AdministrarUsuarios(model,request, session);
	}
	@GetMapping("/BorrarUsuario")
	public String BorrarUsuario( Model model, HttpSession session) {
		if(currentUser!=null) {
			Optional<User> user= controlUsuarios.findByNombre(currentUser.getCurrentName());
			if(user.isPresent()) {
				
				Formacion f= user.get().getFormacion();
				controlFormacion.BorrarPersonajes(f.getId(), controlPersonajes);
				controlUsuarios.delete(user.get());
			}
			
			return Inicio(model, session);
		}
		return "errorNoLogin";
	}
	

}
