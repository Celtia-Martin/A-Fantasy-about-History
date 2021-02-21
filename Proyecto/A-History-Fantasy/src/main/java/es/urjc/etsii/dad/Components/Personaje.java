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
	private String image;
	@Lob
	@JsonIgnore
	private Blob imageFile;
	
	@OneToOne
	private Formacion formacion;
	
	public Personaje() {
		
	}
	
	public Personaje(String nombre, long rango, Enums.TipoBatalla tipo, long precio, long vMilitar,long vDiplomatico, long vCultural) {
		this.nombre=nombre;
		this.rango=rango;
		this.tipo=tipo;
		this.precio=precio;
		this.vMilitar=vMilitar;
		this.vDiplomatico= vDiplomatico;
		this.vCultural= vCultural;
	}
	public void setImageFile(Blob imageFile,int size) {
		
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
