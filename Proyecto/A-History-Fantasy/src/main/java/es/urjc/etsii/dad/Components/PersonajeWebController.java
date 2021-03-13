package es.urjc.etsii.dad.Components;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PersonajeWebController extends WebController {

	@Autowired
	private ControlFormaciones controlFormacion;
	
	@Autowired
	private ControlPersonajes controlPersonajes;
	
	
	
	@GetMapping ("/newPersonaje")
	public String CreadorDePersonajes(Model model,HttpServletRequest request) {
	
		if(ActualizarEncabezado(model,request,true)) {
			return "personajes";
		}else {
			return "errorNoLogin";
		}
	}
	
	@PostMapping("/newPersonaje")
	public String FormularioPersonajes(Model model,@RequestParam String nombre,@RequestParam long rango,@RequestParam String tipo,@RequestParam long vMilitar,@RequestParam long vDiplo,@RequestParam long vCultu,@RequestParam long precio,HttpServletRequest request)  {
		Personaje p= new Personaje(nombre,rango,Enums.TipoBatalla.valueOf(tipo),precio,vMilitar,vDiplo,vCultu,false);

		if(controlPersonajes.newPersonaje(p)) {
			return GetMenuPrincipal(model,request);
		}
		else {
			if(ActualizarEncabezado(model,request,true)) {
				return "personajes";
			}else {
				return "errorNoLogin";
			}
		}
	}
	@GetMapping("/formacion")
	public String MostrarFormacion(Model model,HttpServletRequest request) {
	
		if(currentUser!=null) {
			
			Optional<User> current= controlUsuarios.findByNombre(currentUser.getCurrentName());
			if(current.isPresent()) {
				Formacion miFormacion= current.get().getFormacion();
				if(miFormacion!=null) {
					model.addAttribute("personajes",miFormacion.getPersonajes());
				}
			}
			
			if(ActualizarEncabezado(model,request,true)) {
				return "formacion";
			}else {
				return "errorNoLogin";
			}
		}
		return "errorNoLogin";
		
	}
	@PostMapping("/venderPersonaje/{id}")
	public String FormularioPersonajes(Model model,@PathVariable int id,HttpServletRequest request) {
		if(currentUser!=null) {
			Optional<User> current= controlUsuarios.findByNombre(currentUser.getCurrentName());
			if(current.isPresent()) {
				
				controlFormacion.VenderPersonaje((long)id,current.get(), controlPersonajes);
			}
			return MostrarFormacion(model,request);
			}
		return "errorNoLogin";
	}
	
	@GetMapping("/mostrarTodosPersonajes")
	public String mostrarPersonajes(Model model,HttpServletRequest request) throws SQLException, IOException {
		Page<Personaje> aMostrar=controlPersonajes.findNoDefaultWithPage(0);
		model.addAttribute("hasPrev", aMostrar.hasPrevious());
		model.addAttribute("hasNext", aMostrar.hasNext());
		model.addAttribute("nextPage", aMostrar.getNumber()+1);
		model.addAttribute("prevPage", aMostrar.getNumber()-1);
		model.addAttribute("personajes",aMostrar);
		
		if(ActualizarEncabezado(model,request,false)) {
			return "mostrarTodosPersonajes";
		}else {
			return "errorNoLogin";
		}
	}
	@GetMapping("/mostrarTodosPersonajes/{page}")
	public String mostrarPersonajes(Model model,@PathVariable int page,HttpServletRequest request) throws SQLException, IOException {
		Page<Personaje> aMostrar=controlPersonajes.findNoDefaultWithPage(page);
		model.addAttribute("hasPrev", aMostrar.hasPrevious());
		model.addAttribute("hasNext", aMostrar.hasNext());
		model.addAttribute("nextPage", aMostrar.getNumber()+1);
		model.addAttribute("prevPage", aMostrar.getNumber()-1);
		model.addAttribute("personajes",aMostrar);
		
		if(ActualizarEncabezado(model,request,false)) {
			return "mostrarTodosPersonajes";
		}else {
			return "errorNoLogin";
		}
	}
	

}
