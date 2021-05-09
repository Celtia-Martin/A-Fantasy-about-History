package es.urjc.etsii.dad.Components;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class UsserSession implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String currentName;
	
	private boolean errorUsuario = false;
	private boolean errorContra = false;
	private boolean datosInsuficientes = false;
	private boolean errorPuja= false;
	private boolean pujaRealizada= false;
	private boolean baneado=false;
	private boolean usuarioBaneadoConExito= false;
	private boolean errorBaneo= false;
	
	public UsserSession() {
		
	}

	public String getCurrentName() {
		return currentName;
	}

	public void setCurrentName(String currentName) {
		this.currentName = currentName;
	}

	public boolean isErrorUsuario() {
		return errorUsuario;
	}

	public void setErrorUsuario(boolean errorUsuario) {
		this.errorUsuario = errorUsuario;
	}

	public boolean isErrorContra() {
		return errorContra;
	}

	public void setErrorContra(boolean errorContra) {
		this.errorContra = errorContra;
	}

	public boolean isDatosInsuficientes() {
		return datosInsuficientes;
	}

	public void setDatosInsuficientes(boolean datosInsuficientes) {
		this.datosInsuficientes = datosInsuficientes;
	}

	public boolean isErrorPuja() {
		return errorPuja;
	}

	public void setErrorPuja(boolean errorPuja) {
		this.errorPuja = errorPuja;
	}

	public boolean isPujaRealizada() {
		return pujaRealizada;
	}

	public void setPujaRealizada(boolean pujaRealizada) {
		this.pujaRealizada = pujaRealizada;
	}

	public boolean isBaneado() {
		return baneado;
	}

	public void setBaneado(boolean baneado) {
		this.baneado = baneado;
	}

	public boolean isUsuarioBaneadoConExito() {
		return usuarioBaneadoConExito;
	}

	public void setUsuarioBaneadoConExito(boolean usuarioBaneadoConExito) {
		this.usuarioBaneadoConExito = usuarioBaneadoConExito;
	}

	public boolean isErrorBaneo() {
		return errorBaneo;
	}

	public void setErrorBaneo(boolean errorBaneo) {
		this.errorBaneo = errorBaneo;
	}

}
