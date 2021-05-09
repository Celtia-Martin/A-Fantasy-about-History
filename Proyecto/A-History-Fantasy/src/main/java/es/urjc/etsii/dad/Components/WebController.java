package es.urjc.etsii.dad.Components;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@Autowired
	protected ControlUsuarios controlUsuarios;
	
	@Autowired
	private BatallaService controlBatalla;
	

	
	private Logger log = LoggerFactory.getLogger(WebController.class);
	
	/*
	public boolean ActualizarEncabezado(Model model,HttpServletRequest request,boolean formulario) {
		if(currentUser!=null) {
			if(formulario) {
				CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
				model.addAttribute("token", token.getToken()); 
			}
			
			if(currentUser.getCurrentName()!=null) {
				log.warn("HAY NOMBRE");
				
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
					log.warn("NO ES PRESENT");
					return false;
				}
			}
			
			log.warn("EL NOMBRE ES NULL");
		}
		
		log.warn("ES NULL");
		
		return false;
	}
	*/
	
	public boolean ActualizarEncabezado(Model model,HttpServletRequest request,boolean formulario, HttpSession session) {
		
			if(formulario) {
				CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
				model.addAttribute("token", token.getToken()); 
			}
			
			if(request.getUserPrincipal().getName()!=null) {

				Optional<User> current= controlUsuarios.findByNombre((String) request.getUserPrincipal().getName());
				
				if(current.isPresent()) {
					long dinero = 0;
					long puntos = 0;
					
					if(current.isPresent()) {
						dinero= current.get().getDinero();
						puntos= current.get().getPuntos();
					}
					
					model.addAttribute("name",(String) request.getUserPrincipal().getName());
					model.addAttribute("dinero",dinero);
					model.addAttribute("puntos",puntos);
					return true;
				}
				else {
				
					return false;
				}
			}
		
		return false;
	}
	
	public String GetMenuPrincipal(Model model,HttpServletRequest request, HttpSession session) {
		
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		
		model.addAttribute("token", token.getToken()); 
		
		model.addAttribute("batalla", controlBatalla.getBatalla());
		model.addAttribute("esAdmin",request.isUserInRole("ADMIN"));
		
		if(ActualizarEncabezado(model,request,false, session)) {
			return "menuPrincipal";
		}else {
			return "errorNoLogin";
		}
	}

	public String Inicio (Model model) {

		return "index";
	}
	
	

	public String ErrorGeneral() {
		return "error";
	}
	
}
