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
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**********************************************
 * Representa la persona o entidad que hará préstamos.
 * @author Ismael Núñez
 *
 **********************************************/
@Entity
@Table(name="arrendador")
public class Arrendador extends ObjetoBookingGenerico implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* ==============XXX==============|  Atributos  |==============XXX==============  */

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
	// Valida entre 2 y 5 dígitos
	@Pattern(regexp = ".*(^[0-9]{2,5}$)")
	private String codigoPostal;
	
	@Column
	@NotNull
	// Valida entre 9 y 11 dígitos (por prefijos internacionales)
	@Pattern(regexp = ".*(^[0-9]{9,11}$)")
	private String telefono;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="idArrendador")
	//@IndexColumn(name="")
	@Valid
	private List<Prestamo> listaPrestamos;
	
	/* ==============XXX==============|  Constructores  |==============XXX==============  */
	
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
	
	/* ==============XXX==============|  Getter & Setter  |==============XXX==============  */
	
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

	
	/* ==============XXX==============|  Métodos  |==============XXX==============  */

	/**********************************************
	 * Muestra información detallada del arrendador.
	 * @return Cadena de información
	 **********************************************/
	@Override
	public String informacionDetalle() {
		StringBuilder cadena = new StringBuilder(150);
		
		cadena.append("----------> " + nombre + " <----------\n");
		if(!(entidad==null) && !entidad.equals(" "))
			cadena.append("\tOrganización: " + entidad + "\n");
		cadena.append("\tDirección: "+ direccion + "\n");
		cadena.append("\tCódigo postal: " + codigoPostal + "\n");
		cadena.append("\tTeléfono: " + telefono + "\n");
		
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
		cadena.append("------------------------------");
		
		return cadena.toString();
	}

	/**********************************************
	 * Cadena que muestra información breve.
	 **********************************************/
	@Override
	public String toString() {
		return idArrendador + " - " + nombre;
	}
	
}
