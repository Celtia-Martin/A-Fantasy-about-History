package es.urjc.etsii.dad.Components;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jdk.internal.org.jline.utils.Log;
/*
 * <dependency>
		<groupId> org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
 */
@Controller
public class WebController {

	
	private boolean errorUsuario = false;
	private boolean errorContra = false;
	private boolean datosInsuficientes = false;
	
	@Autowired
	private ControlUsuarios controlUsuarios;

	@Autowired
	private ControlPersonajes controlPersonajes;
	
	@Autowired
	private ControlFormaciones controlFormacion;
	
	private String currentUser;
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
			datosInsuficientes=true;
			model.addAttribute("datosInsuficientes",datosInsuficientes);
			datosInsuficientes=false;
			return "newUsuario";
			
		}
		else {
			User nuevo= new User(nombre,contrasena);
			
			if(controlUsuarios.newUser(nombre,contrasena,controlPersonajes,controlFormacion)) {
				currentUser= nuevo.getNombre();	
				//controlFormacion.NewFormacion(nuevaFormacion, nuevo);
				model.addAttribute("name",currentUser);
				return "menuPrincipal";
			}
			else {
				errorUsuario=true;
				model.addAttribute("errorUsuario", errorUsuario);
				return "newUsuario";
			}
		}
	}
	
	@GetMapping("/mostrarTodosPersonajes")
	public String mostrarPersonajes(Model model) {
		List<Personaje> aMostrar=controlPersonajes.findAll();
		model.addAttribute("personajes",aMostrar);
		return "mostrarTodosPersonajes";
		
	}
	
	@GetMapping("/login")
	public String LogIn(Model model) {
		
		model.addAttribute("errorUsuario", errorUsuario);
		model.addAttribute("errorContra", errorContra);
		errorUsuario= false;
		errorContra= false;
		datosInsuficientes=false;
		return "login";
	}
	
	@PostMapping("/login")
	public String LoginPost(@RequestParam String nombre ,@RequestParam String contrasena, Model model) {
		if(nombre.trim().equals("")||contrasena.trim().equals("")) {
			datosInsuficientes=true;
			model.addAttribute("datosInsuficientes",datosInsuficientes);
			datosInsuficientes=false;
			
			return "login";
		}
		else {
			User nuevo= new User(nombre,contrasena);
			if(controlUsuarios.LogIn(nombre,contrasena)) {
				currentUser= nuevo.getNombre();
				model.addAttribute("name",currentUser);
				return "menuPrincipal";
			}
			else {
				errorUsuario=true;
				model.addAttribute("errorUsuario", errorUsuario);
				errorUsuario=false;
				return "login";
			}
		}
	}
	
	@GetMapping ("/newPersonaje")
	public String CreadorDePersonajes(Model model) {
		model.addAttribute("name",currentUser);
		return "personajes";
	}
	
	@PostMapping("/newPersonaje")
	public String FormularioPersonajes(Model model,@RequestParam String nombre,@RequestParam long rango,@RequestParam String tipo,@RequestParam long vMilitar,@RequestParam long vDiplo,@RequestParam long vCultu,@RequestParam long precio,@RequestParam MultipartFile image) throws IOException {
		Personaje p= new Personaje(nombre,rango,Enums.TipoBatalla.valueOf(tipo),precio,vMilitar,vDiplo,vCultu,false);
		//Personaje p= new Personaje(nombre,1,Enums.TipoBatalla.DIPLOMATICO,200,2,2,2,false);
		URI location= fromCurrentRequest().build().toUri();
		p.setImage(location.toString());
		p.setImageFile(BlobProxy.generateProxy(image.getInputStream(), image.getSize()));
		
		if(controlPersonajes.newPersonaje(p)) {
			model.addAttribute("name",currentUser);
			return "menuPrincipal";
		}
		else {
			model.addAttribute("name",currentUser);
			return "personajes";
		}
		
	}
	@GetMapping("/clasificacion")
	public String MostrarClasificacion(Model model) {
		model.addAttribute("clasificacion", controlUsuarios.findTop10ByPuntosDesc());
		
		return "clasificacion";
	}
	
	@GetMapping("/mercado")
	public String MostrarMercado(Model model) {
		
		
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
		
		
		return "formacion";
	}
	@GetMapping("/")
	public String Inicio (Model model) {
		
		return "index";
	}
	@GetMapping("/menuPrincipal")
	public String GetMenuPrincipal(Model model) {
		model.addAttribute("name",currentUser);
		return "menuPrincipal";
	}
	
	
}

/*
@GetMapping("/{id}/image")
public ResponseEntity<Object> downloadImage(@PathVariable long id)
 throws SQLException {
 Post post = posts.findById(id).orElseThrow();
 if (post.getImageFile() != null) {
 Resource file = new InputStreamResource(
 post.getImageFile().getBinaryStream());
 return ResponseEntity.ok()
 .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
 .contentLength(post.getImageFile().length())
 .body(file);
 } else {
 return ResponseEntity.notFound().build();
 }
}
*/
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