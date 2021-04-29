package es.urjc.etsii.dad.Components;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MercadoWebController extends WebController {
	@Autowired
	private ControlMercado controlMercado;	

	@Autowired
	private ControlPersonajes controlPersonajes;
	
	@Autowired
	private ControlPuja controlPuja;
	@Autowired
	private ControlFormaciones controlFormacion;
	
	public MercadoWebController() {
		
	}

	@GetMapping("/mercado")
	public String MostrarMercado(Model model,HttpServletRequest request) {
		List<Personaje> oferta= controlMercado.findAllPersonajes();
		
		model.addAttribute("mercado",oferta);
		model.addAttribute("errorPuja",currentUser.isErrorPuja());
		model.addAttribute("pujaRealizada",currentUser.isPujaRealizada());
		
		currentUser.setErrorPuja(false);
		currentUser.setPujaRealizada(false);
	
		if(ActualizarEncabezado(model,request,true)) {
			return "mercado";
		}else {
			return "errorNoLogin";
		}
	}
	
	@PostMapping("/pujarPersonaje/{id}")
	public String PujandoPersonaje(Model model,@PathVariable int id,@RequestParam long valor,HttpServletRequest request) {
		if(currentUser!=null) {
			
			Optional<User>current= controlUsuarios.findByNombre(currentUser.getCurrentName());
			Optional<Personaje> personaje= controlPersonajes.findById((long)id);
			
			if(current.isPresent()&&personaje.isPresent()) {
				boolean completado= controlPuja.Pujar(current.get(), personaje.get(), (int) valor);
				currentUser.setErrorPuja(!completado);
				currentUser.setPujaRealizada(completado);
			}
			else {
				currentUser.setErrorPuja(true);
			}
			
			return MostrarMercado(model,request);
		}
		return "errorNoLogin";
	}
	
	@PostMapping("/refrescarMercado")
	public String RefrescarMercado(Model model,HttpServletRequest request) {
		controlMercado.refrescarMercado();
		return GetMenuPrincipal(model,request);
	}
}
