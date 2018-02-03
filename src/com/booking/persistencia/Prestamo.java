package com.booking.persistencia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**********************************************
 * Define la estructura de los préstamos.
 * @author Ismael Núñez
 *
 **********************************************/
@Entity
@Table(name="prestamo")
public class Prestamo extends ObjetoBookingGenerico implements Serializable {
	private static final long serialVersionUID = 1L;

	/* ==============XXX==============|  Atributos  |==============XXX==============  */

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idPrestamo;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column
	@NotNull
	@Min(1)
	@Max(365)
	private int duracionDias;
	
	@ManyToOne
	@JoinColumn(name="idArrendador")
	@Valid
	private Arrendador arrendador;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="idPrestamo")
	@Valid
	private List<Stack> listaStacks;
	
	/* ==============XXX==============|  Constructores  |==============XXX==============  */
	
	public Prestamo() {	}

	public Prestamo(Date fecha, int duracionDias, Arrendador arrendador) {
		this.fecha = fecha;
		this.duracionDias = duracionDias;
		this.arrendador = arrendador;
		this.listaStacks = new ArrayList<Stack>();
	}

	/* ==============XXX==============|  Getter & Setter  |==============XXX==============  */
	
	public int getIdPrestamo() {
		return idPrestamo;
	}

	public void setIdPrestamo(int idPrestamo) {
		this.idPrestamo = idPrestamo;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getDuracionDias() {
		return duracionDias;
	}

	public void setDuracionDias(int duracionDias) {
		this.duracionDias = duracionDias;
	}

	public Arrendador getArrendador() {
		return arrendador;
	}

	public void setArrendador(Arrendador arrendador) {
		this.arrendador = arrendador;
	}

	public List<Stack> getListaStacks() {
		return listaStacks;
	}

	public void setListaStacks(List<Stack> listaStacks) {
		this.listaStacks = listaStacks;
	}

	/* ==============XXX==============|  Métodos  |==============XXX==============  */

	/**********************************************
	 * Muestra información en detalle del préstamo
	 * @return
	 **********************************************/
	@Override
	public String informacionDetalle() {
		StringBuilder cadena = new StringBuilder(150);
		
		cadena.append("------> Préstamo " + idPrestamo + " <------\n");
		cadena.append("Arrendador: " + arrendador.getNombre() + "\n");
		cadena.append("Fecha: "+ fecha + "\n");
		cadena.append("Duración: "+ duracionDias + " días\n");
		cadena.append("Libros: ");
		
		@SuppressWarnings("rawtypes")
		Iterator iterator;
		for (iterator = listaStacks.iterator(); iterator.hasNext();) {
			Stack stack = (Stack) iterator.next();
			cadena.append(stack.getCantidad() + "x " + stack.getLibro().getTitulo() + "\n");
		}
		
		return cadena.toString();
	}
	
	/**********************************************
	 * Muestra información breve del préstamo.
	 **********************************************/
	@Override
	public String toString() {
		return idPrestamo + " - Préstamo de " + arrendador.getNombre()+ ", "+ fecha;
	}
}
