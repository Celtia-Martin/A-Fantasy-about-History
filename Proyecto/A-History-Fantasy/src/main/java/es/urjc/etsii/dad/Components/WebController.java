package es.urjc.etsii.dad.Components;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jdk.internal.org.jline.utils.Log;
/*
 * <dependency>
		<groupId> org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
	
spring.datasource.url=jdbc:mysql://localhost:3306/HistoryFantasy
spring.datasource.username=root
spring.datasource.password=GatoPato115
spring.jpa.hibernate.ddl-auto=create-drop
 */
@Controller
public class WebController {

	private boolean errorUsuario = false;
	private boolean errorContra = false;
	private boolean datosInsuficientes = false;
	private boolean errorPuja= false;
	private boolean pujaRealizada= false;
	
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
	
	private String currentUser;
	
	
	public void ActualizarEncabezado(Model model) {
		Optional<User> current= controlUsuarios.findByNombre(currentUser);
		
		long dinero = 0;
		long puntos = 0;
		
		if(current.isPresent()) {
			 dinero= current.get().getDinero();
			 puntos= current.get().getPuntos();
		}
			
		model.addAttribute("name",currentUser);
		model.addAttribute("dinero",dinero);
		model.addAttribute("puntos",puntos);
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
			
			if(controlUsuarios.newUser(nombre,contrasena,controlPersonajes,controlFormacion,controlMercado)) {
				currentUser = nuevo.getNombre();
				model.addAttribute("name",currentUser);
				
				return GetMenuPrincipal(model);
			}
			else {
				errorUsuario = true;
				model.addAttribute("errorUsuario", errorUsuario);
				
				return "newUsuario";
			}
		}
	}
	
	@GetMapping("/mostrarTodosPersonajes")
	public String mostrarPersonajes(Model model) throws SQLException, IOException {
		List<Personaje> aMostrar=controlPersonajes.findAll();
		model.addAttribute("personajes",aMostrar);
		
		return "mostrarTodosPersonajes";
	}
	
	@GetMapping("/login")
	public String LogIn(Model model) {
		model.addAttribute("errorUsuario", errorUsuario);
		model.addAttribute("errorContra", errorContra);
		
		errorUsuario = false;
		errorContra = false;
		datosInsuficientes = false;
		
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
				currentUser= nuevo.getNombre();
				
				return GetMenuPrincipal(model);
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
		ActualizarEncabezado(model);
		return "personajes";
	}
	
	@PostMapping("/newPersonaje")
	public String FormularioPersonajes(Model model,@RequestParam String nombre,@RequestParam long rango,@RequestParam String tipo,@RequestParam long vMilitar,@RequestParam long vDiplo,@RequestParam long vCultu,@RequestParam long precio)  {
		Personaje p= new Personaje(nombre,rango,Enums.TipoBatalla.valueOf(tipo),precio,vMilitar,vDiplo,vCultu,false);

		if(controlPersonajes.newPersonaje(p)) {
			return GetMenuPrincipal(model);
		}
		else {
			ActualizarEncabezado(model);
			return "personajes";
		}
	}
	
	@GetMapping("/clasificacion")
	public String MostrarClasificacion(Model model) {
		model.addAttribute("clasificacion", controlUsuarios.findTop10ByPuntosDesc());
		ActualizarEncabezado(model);
		
		return "clasificacion";
	}
	
	@GetMapping("/mercado")
	public String MostrarMercado(Model model) {
		List<Personaje> oferta= controlMercado.findAllPersonajes();
		
		model.addAttribute("mercado",oferta);
		model.addAttribute("errorPuja",errorPuja);
		model.addAttribute("pujaRealizada",pujaRealizada);
		
		errorPuja=false;
		pujaRealizada=false;
		ActualizarEncabezado(model);
		
		return "mercado";
	}
	
	@GetMapping("/formacion")
	public String MostrarFormacion(Model model) {
	
		Optional<User> current= controlUsuarios.findByNombre(currentUser);
		if(current.isPresent()) {
			Formacion miFormacion= current.get().getFormacion();
			if(miFormacion!=null) {
				model.addAttribute("personajes",miFormacion.getPersonajes());
			}
		}
		
		ActualizarEncabezado(model);
		
		return "formacion";
	}
	
	@GetMapping("/")
	public String Inicio (Model model) {
		return "index";
	}
	
	@GetMapping("/menuPrincipal")
	public String GetMenuPrincipal(Model model) {
		ActualizarEncabezado(model);
		return "menuPrincipal";
	}
	
	@PostMapping("/venderPersonaje/{id}")
	public String FormularioPersonajes(Model model,@PathVariable int id) {
		Optional<User> current= controlUsuarios.findByNombre(currentUser);
		
		if(current.isPresent()) {
			
			controlFormacion.VenderPersonaje((long)id,current.get(), controlPersonajes);
		}
		return MostrarFormacion(model);
	}
	
	@PostMapping("/pujarPersonaje/{id}")
	public String PujandoPersonaje(Model model,@PathVariable int id,@RequestParam long valor) {
		
		Optional<User>current= controlUsuarios.findByNombre(currentUser);
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
	
	@PostMapping("/ejecutarBatalla")
	public String FormularioPersonajes(Model model) {
		Batalla batalla= new Batalla();
		controlBatalla.save(batalla);
		controlBatalla.RealizarBatalla();
		
		return GetMenuPrincipal(model);
	}
	
	@PostMapping("/refrescarMercado")
	public String RefrescarMercado(Model model) {
		controlPuja.ReiniciarMercado(controlMercado);
		controlMercado.newMercado(controlPersonajes);
		
		return GetMenuPrincipal(model);
	}
	
}
