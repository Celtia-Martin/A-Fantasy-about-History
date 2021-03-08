package es.urjc.etsii.dad.Components;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jdk.internal.org.jline.utils.Log;

@Controller
public class WebController {

	private boolean errorUsuario = false;
	private boolean errorContra = false;
	private boolean datosInsuficientes = false;
	private boolean errorPuja= false;
	private boolean pujaRealizada= false;
	private boolean baneado=false;
	private boolean usuarioBaneadoConExito= false;
	private boolean errorBaneo= false;

	
	@Autowired
	private ControlUsuarios controlUsuarios;

	@Autowired
	private ControlPersonajes controlPersonajes;
	
	@Autowired
	private ControlFormaciones controlFormacion;
	
	@Autowired
	private ControlMercado controlMercado;
	
	@Autowired
	private BatallaService controlBatalla;
	
	@Autowired
	private ControlPuja controlPuja;
	
	@Autowired
	private UsserSession currentUser;
	
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
	
	public boolean ActualizarEncabezado(Model model) {
		if(currentUser!=null) {
			
			if(currentUser.getCurrentName()!=null) {
				Optional<User> current= controlUsuarios.findByNombre(currentUser.getCurrentName());
				
				if(current.isPresent()) {
					long dinero = 0;
					long puntos = 0;
					
					if(current.isPresent()) {
						dinero= current.get().getDinero();
						puntos= current.get().getPuntos();
					}
					
					model.addAttribute("name",currentUser.getCurrentName());
					model.addAttribute("dinero",dinero);
					model.addAttribute("puntos",puntos);
					return true;
				}
				else {
					return false;
				}
			}
		}
		return false;
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
				currentUser= new UsserSession(nuevo.getNombre());
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
					currentUser= new UsserSession(nuevo.getNombre());
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
	
	@GetMapping ("/newPersonaje")
	public String CreadorDePersonajes(Model model) {
		if(ActualizarEncabezado(model)) {
			return "personajes";
		}else {
			return "errorNoLogin";
		}
	}
	
	@PostMapping("/newPersonaje")
	public String FormularioPersonajes(Model model,@RequestParam String nombre,@RequestParam long rango,@RequestParam String tipo,@RequestParam long vMilitar,@RequestParam long vDiplo,@RequestParam long vCultu,@RequestParam long precio)  {
		Personaje p= new Personaje(nombre,rango,Enums.TipoBatalla.valueOf(tipo),precio,vMilitar,vDiplo,vCultu,false);

		if(controlPersonajes.newPersonaje(p)) {
			return GetMenuPrincipal(model);
		}
		else {
			if(ActualizarEncabezado(model)) {
				return "personajes";
			}else {
				return "errorNoLogin";
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
	
	@GetMapping("/mercado")
	public String MostrarMercado(Model model) {
		List<Personaje> oferta= controlMercado.findAllPersonajes();
		
		model.addAttribute("mercado",oferta);
		model.addAttribute("errorPuja",errorPuja);
		model.addAttribute("pujaRealizada",pujaRealizada);
		
		errorPuja=false;
		pujaRealizada=false;
		
		if(ActualizarEncabezado(model)) {
			return "mercado";
		}else {
			return "errorNoLogin";
		}
	}
	
	@GetMapping("/formacion")
	public String MostrarFormacion(Model model) {
	
		if(currentUser!=null) {
			
			Optional<User> current= controlUsuarios.findByNombre(currentUser.getCurrentName());
			if(current.isPresent()) {
				Formacion miFormacion= current.get().getFormacion();
				if(miFormacion!=null) {
					model.addAttribute("personajes",miFormacion.getPersonajes());
				}
			}
			
			if(ActualizarEncabezado(model)) {
				return "formacion";
			}else {
				return "errorNoLogin";
			}
		}
		return "errorNoLogin";
		
	}
	
	@GetMapping("/")
	public String Inicio (Model model) {
		currentUser=null;
		return "index";
	}
	
	@GetMapping("/menuPrincipal")
	public String GetMenuPrincipal(Model model) {
		model.addAttribute("batalla", controlBatalla.getBatalla());
		
		if(ActualizarEncabezado(model)) {
			return "menuPrincipal";
		}else {
			return "errorNoLogin";
		}
	}
	
	@PostMapping("/venderPersonaje/{id}")
	public String FormularioPersonajes(Model model,@PathVariable int id) {
		if(currentUser!=null) {
			Optional<User> current= controlUsuarios.findByNombre(currentUser.getCurrentName());
			if(current.isPresent()) {
				
				controlFormacion.VenderPersonaje((long)id,current.get(), controlPersonajes);
			}
			return MostrarFormacion(model);
			}
		return "errorNoLogin";
	}
	
	@PostMapping("/pujarPersonaje/{id}")
	public String PujandoPersonaje(Model model,@PathVariable int id,@RequestParam long valor) {
		if(currentUser!=null) {
			
			Optional<User>current= controlUsuarios.findByNombre(currentUser.getCurrentName());
			Optional<Personaje> personaje= controlPersonajes.findById((long)id);
			
			if(current.isPresent()&&personaje.isPresent()) {
				boolean completado= controlPuja.Pujar(current.get(), personaje.get(), (int) valor);
				errorPuja= !completado;
				pujaRealizada= completado;
			}
			else {
				errorPuja= true;
			}
			
			return MostrarMercado(model);
		}
		return "errorNoLogin";
	}
	
	@PostMapping("/ejecutarBatalla")
	public String FormularioPersonajes(Model model) {
		controlBatalla.RealizarBatalla();
		
		return GetMenuPrincipal(model);
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
	@PostMapping("/refrescarMercado")
	public String RefrescarMercado(Model model) {
		controlPuja.ReiniciarMercado(controlMercado);
		controlMercado.newMercado(controlPersonajes);
		
		return GetMenuPrincipal(model);
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
	@GetMapping("/mostrarTodosPersonajes")
	public String mostrarPersonajes(Model model) throws SQLException, IOException {
		Page<Personaje> aMostrar=controlPersonajes.findNoDefaultWithPage(0);
		model.addAttribute("hasPrev", aMostrar.hasPrevious());
		model.addAttribute("hasNext", aMostrar.hasNext());
		model.addAttribute("nextPage", aMostrar.getNumber()+1);
		model.addAttribute("prevPage", aMostrar.getNumber()-1);
		model.addAttribute("personajes",aMostrar);
		
		if(ActualizarEncabezado(model)) {
			return "mostrarTodosPersonajes";
		}else {
			return "errorNoLogin";
		}
	}
	@GetMapping("/mostrarTodosPersonajes/{page}")
	public String mostrarPersonajes(Model model,@PathVariable int page) throws SQLException, IOException {
		Page<Personaje> aMostrar=controlPersonajes.findNoDefaultWithPage(page);
		model.addAttribute("hasPrev", aMostrar.hasPrevious());
		model.addAttribute("hasNext", aMostrar.hasNext());
		model.addAttribute("nextPage", aMostrar.getNumber()+1);
		model.addAttribute("prevPage", aMostrar.getNumber()-1);
		model.addAttribute("personajes",aMostrar);
		
		if(ActualizarEncabezado(model)) {
			return "mostrarTodosPersonajes";
		}else {
			return "errorNoLogin";
		}
	}
	
}
