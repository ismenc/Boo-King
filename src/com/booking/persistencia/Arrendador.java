package com.booking.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa la persona o entidad que hará préstamos.
 * @author Ismael Núñez
 *
 */
@Entity
@Table(name="arrendador")
public class Arrendador implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* ------------------- Atributos ------------------- */

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idArrendador;
	
	@Column
	@NotNull
	@Size(min = 3, max = 50)
	private String nombre;
	
	@Column
	@Size(min = 0, max = 50)
	private String entidad;
	
	@Column
	@NotNull
	@Size(min = 5, max = 100)
	private String direccion;
	
	@Column
	@NotNull
	@Digits(integer=5, fraction = 0)
	private String codigoPostal;
	
	@Column
	@NotNull
	@Min(99999999)
	private String telefono;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="idArrendador")
	//@IndexColumn(name="")
	private List<Prestamo> listaPrestamos;
	
	/* ------------------- Constructor ------------------- */
	
	public Arrendador() {}

	public Arrendador(String nombre, String direccion, String codigoPostal, String telefono) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.listaPrestamos = new ArrayList<Prestamo>();
	}

	public Arrendador(String nombre, String entidad, String direccion, String codigoPostal, String telefono) {
		this.nombre = nombre;
		this.entidad = entidad;
		this.direccion = direccion;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.listaPrestamos = new ArrayList<Prestamo>();
	}
	
	/* ----------------- Getter & Setter ----------------- */
	
	public int getId() {
		return idArrendador;
	}

	public void setId(int id) {
		this.idArrendador = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEntidad() {
		return entidad;
	}

	public void setEntidad(String entidad) {
		this.entidad = entidad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public List<Prestamo> getListaPrestamos () {
		return listaPrestamos;
	}

	public void setListaPrestamos(List<Prestamo> listaPrestamos) {
		this.listaPrestamos = listaPrestamos;
	}

	
	/* -------------------- Métodos -------------------- */

	/**
	 * Muestra información detallada del arrendador
	 * @return
	 */
	public String informacionDetalle() {
		StringBuilder cadena = new StringBuilder(150);
		
		cadena.append("------> " + nombre + " <------\n");
		if(!entidad.equals("") && !entidad.equals(" "))
			cadena.append("Organización: " + entidad + "\n");
		cadena.append("Dirección: "+ direccion + "\n");
		cadena.append("Código postal: " + codigoPostal + "\n");
		cadena.append("Teléfono: " + telefono + "\n");
		
		if(!listaPrestamos.isEmpty()) {
			cadena.append("Préstamos: ");
			@SuppressWarnings("rawtypes")
			Iterator iterator;
			for (iterator = listaPrestamos.iterator(); iterator.hasNext();) {
				Prestamo prestamo = (Prestamo) iterator.next();
				cadena.append(prestamo.getIdPrestamo() + ", ");
			}
			cadena.setLength(cadena.length()-2);
			cadena.append(".\n");
		}else
			cadena.append("No ha realizado ningún préstamo.");
		
		return cadena.toString();
	}
	
	@Override
	public String toString() {
		return idArrendador + " - " + nombre;
	}
	
}
