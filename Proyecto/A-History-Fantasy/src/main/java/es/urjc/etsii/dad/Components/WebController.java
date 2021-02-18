package es.urjc.etsii.dad.Components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	//public Personaje [] jugadores;
	@Autowired
	private UserService userService;
	@Autowired
	private BatallaService batallaService;
	@Autowired
	private FormacionService formacionService;
	@Autowired
	private MercadoService mercadoService;
	@Autowired
	private PersonajeService personajeService;
	
	private boolean errorUsuario= false;
	private boolean errorContra= false;
	@GetMapping("/newUsuario")
	public String NuevoUsuario(Model model) {
		
		model.addAttribute("errorUsuario", errorUsuario);
		model.addAttribute("errorContra", errorContra);
		return "newUsuario";
	}
	@GetMapping("/login")
	public String LogIn(Model model) {
		
		model.addAttribute("errorUsuario", errorUsuario);
		model.addAttribute("errorContra", errorContra);
		return "LogIn";
	}
	@GetMapping("/clasificacion")
	public String MostrarClasificacion(Model model) {
		
		
		return "HTML";
	}
	
	@GetMapping("/mercado")
	public String MostrarMercado(Model model) {
		
		
		return "HTML";
	}
	
	@GetMapping("/formacion")
	public String MostrarFormacion(Model model) {
	
		
		return "HTML";
	}
	@GetMapping("/")
	public String Inicio (Model model) {
		
		return "index";
	}
	@GetMapping("/menuPrincipal")
	public String GetMenuPrincipal() {
		return "menuPrincipal";
	}
	
	
}



/*
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
@Controller
public class WebControllerTesteando {

	private static final Path IMAGES_FOLDER = Paths.get(System.getProperty("user.dir"), "images");
	
	private Usuario usuario;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/hello")
	
	public String helloWorld() {
		
		return "hello-world.html";// html sin especificar
	}
	//imagenes en static
	@GetMapping("/hello-juana")
	public String helloJuana(Model model) {
		model.addAttribute("name","Juana de Arco");
		return "helloJuana";//Mustache no especificar
	}
	@PostMapping("/procesarFormulario")
	public String formulario(@RequestParam String nombre ,@RequestParam String contrasena,@RequestParam String imageName,@RequestParam MultipartFile image)throws IOException {
		usuario.setContrasena(contrasena);
		usuario.setNombre(nombre);
		usuario.setImage(imageName);
		Files.createDirectories(IMAGES_FOLDER);
		
		Path imagePath = IMAGES_FOLDER.resolve("image.jpg");
		
		image.transferTo(imagePath);
		return "formulario";
	}
	@GetMapping("/mostrarDatos")
	public String mostrarDatos(Model model) {

		String nombre = usuario.getNombre();
		String contrasena= usuario.getContrasena();

		model.addAttribute("nombre", nombre);
		model.addAttribute("contrasena", contrasena);
		model.addAttribute("imageName", usuario.getImage());
		return "mostrarDatos";
	}
	
	@PostMapping("/subir_imagenUser")
	public String uploadImage(@RequestParam String imageName,@RequestParam MultipartFile image) throws IOException{
		usuario.setImage(imageName);
		Files.createDirectories(IMAGES_FOLDER);
		
		Path imagePath = IMAGES_FOLDER.resolve("image.jpg");
		
		image.transferTo(imagePath);

		return "uploaded_image";
	}
	@GetMapping("/image")
	public String viewImage(Model model) {

		model.addAttribute("imageName", usuario.getImage());

		return "view_image";
	}

	@GetMapping("/download_image")	
	public ResponseEntity<Object> downloadImage(Model model) throws MalformedURLException {

		Path imagePath = IMAGES_FOLDER.resolve("image.jpg");
		
		Resource image = new UrlResource(imagePath.toUri());

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, "image/jpeg").body(image);		
	}
	
	
	@PostMapping("/newUser")
	public String newUser(@RequestParam String nombre ,@RequestParam String contrasena) {
		Usuario nuevo= new Usuario(nombre,contrasena);
		
		if(userService.newUser(nuevo)) {
			return "correct";
		}
		else {
			return "newuser";
		}

	}
	@GetMapping("/muro")
	public String muro(Model model) {
		if(usuario!=null) {
			model.addAttribute("welcome",true);
			model.addAttribute("name",usuario.getNombre());

		}
		else {
			model.addAttribute("welcome",false);
		}
	
		model.addAttribute("users",userService.getUsers());
		return "murousuarios";

	}
	@PostMapping("/logIn")
	public String login(@RequestParam String nombre ,@RequestParam String contrasena) {
		Usuario nuevo= new Usuario(nombre,contrasena);
		
		Usuario original=  userService.logIn(nuevo);
		usuario= original;
		if(original!=null) {
			return "correct";
		}
		else {
			return "login";
		}
		

	}
	
	@GetMapping("/logIn")
	public String getLogin(Model model) {
		return "login";
	}
	@GetMapping("/newUser")
	public String getnew(Model model) {
		return "newuser";
	}
	
	
	
	}
	*/