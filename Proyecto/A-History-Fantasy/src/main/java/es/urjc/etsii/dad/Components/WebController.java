package es.urjc.etsii.dad.Components;

import java.io.IOException;
import java.net.URI;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;
import org.hibernate.engine.jdbc.BlobProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.security.web.csrf.CsrfToken;
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

	

	
	@Autowired
	protected ControlUsuarios controlUsuarios;
	
	
	@Autowired
	private BatallaService controlBatalla;
	
	
	@Autowired
	protected UsserSession currentUser;
	
	
	public boolean ActualizarEncabezado(Model model,HttpServletRequest request,boolean formulario) {
		if(currentUser!=null) {
			if(formulario) {
				CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
				 model.addAttribute("token", token.getToken()); 
			}
			
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
	
	

	public String GetMenuPrincipal(Model model,HttpServletRequest request) {
		model.addAttribute("batalla", controlBatalla.getBatalla());
		model.addAttribute("esAdmin",request.isUserInRole("ADMIN"));
		if(ActualizarEncabezado(model,request,false)) {
			return "menuPrincipal";
		}else {
			return "errorNoLogin";
		}
	}

	public String Inicio (Model model) {
		currentUser.setCurrentName(null);
		return "index";
	}
	
	

	public String ErrorGeneral() {
		return "error";
	}
	
}
