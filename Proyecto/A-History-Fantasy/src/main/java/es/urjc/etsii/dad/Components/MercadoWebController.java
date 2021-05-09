package es.urjc.etsii.dad.Components;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	public String MostrarMercado(Model model,HttpServletRequest request, HttpSession session) {
		List<Personaje> oferta= controlMercado.findAllPersonajes();
		
		model.addAttribute("mercado",oferta);
		model.addAttribute("errorPuja",session.getAttribute("errorPuja"));
		model.addAttribute("pujaRealizada",session.getAttribute("exitoPuja"));
		session.setAttribute("errorPuja",false);
		session.setAttribute("exitoPuja",false);
	
		if(ActualizarEncabezado(model,request,true, session)) {
			return "mercado";
		}else {
			return "errorNoLogin";
		}
	}
	
	@PostMapping("/pujarPersonaje/{id}")
	public String PujandoPersonaje(Model model,@PathVariable int id,@RequestParam long valor,HttpServletRequest request, HttpSession session) {
	
			Optional<User>current= controlUsuarios.findByNombre((String) request.getUserPrincipal().getName());
			Optional<Personaje> personaje= controlPersonajes.findById((long)id);
			
			if(current.isPresent()&&personaje.isPresent()) {
				boolean completado= controlPuja.Pujar(current.get(), personaje.get(), (int) valor);
				session.setAttribute("errorPuja",!completado);
				session.setAttribute("exitoPuja",completado);
			}
			else {
				session.setAttribute("errorPuja",true);
			
			}
			
			return MostrarMercado(model,request, session);
	
	}
	
	@PostMapping("/refrescarMercado")
	public String RefrescarMercado(Model model,HttpServletRequest request, HttpSession session) {
		controlMercado.refrescarMercado();
		return GetMenuPrincipal(model,request, session);
	}
}
