package es.urjc.etsii.dad.Components;

import java.sql.Blob;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Personaje {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String nombre;
	private long rango;
	private Enums.TipoBatalla tipo;
	private long precio;
	
	private long vMilitar;
	private long vDiplomatico;
	private long vCultural;
	private boolean isDefault;
	private boolean tieneFormacion;

	
	public Personaje() {
		
	}
	
	public Personaje(String nombre, long rango, Enums.TipoBatalla tipo, long precio, long vMilitar,long vDiplomatico, long vCultural,boolean isDefault) {
		this.setDefault(isDefault);
		this.nombre=nombre;
		this.rango=rango;
		this.tipo=tipo;
		this.setPrecio(precio);
		this.vMilitar=vMilitar;
		this.vDiplomatico= vDiplomatico;
		this.vCultural= vCultural;
		setTieneFormacion(false);
	}

	public String getTipo() {
		return tipo.toString();
	}
	
	public int getVMilitar() {
		return (int) vMilitar;
	}
	
	public int getVDiplomatico() {
		return (int) vDiplomatico;
	}
	
	public int getVCultural() {
		return (int) vCultural;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Long getId() {
		return id;
	}
  

	public boolean isTieneFormacion() {
		return tieneFormacion;
	}

	public void setTieneFormacion(boolean tieneFormacion) {
		this.tieneFormacion = tieneFormacion;
	}

	public long getPrecio() {
		return precio;
	}

	public void setPrecio(long precio) {
		this.precio = precio;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
}
