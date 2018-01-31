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
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Define la estructura de los préstamos.
 * @author Ismael Núñez
 *
 */
@Entity
@Table(name="prestamo")
public class Prestamo implements Serializable {
	private static final long serialVersionUID = 1L;

	/* ------------------- Atributos ------------------- */

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idPrestamo;
	
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@Column
	@Min(1)
	@Max(365)
	private int duracionDias;
	
	@ManyToOne
	@JoinColumn(name="idArrendador")
	private Arrendador arrendador;
	
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name="idPrestamo")
	//@IndexColumn(name="")
	private List<Stack> listaStacks;
	
	/* ------------------- Constructor ------------------- */
	
	public Prestamo() {	}

	public Prestamo(Date fecha, int duracionDias, Arrendador arrendador) {
		this.fecha = fecha;
		this.duracionDias = duracionDias;
		this.arrendador = arrendador;
		this.listaStacks = new ArrayList<Stack>();
	}

	/* ----------------- Getter & Setter ----------------- */
	
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

	/* -------------------- Métodos -------------------- */

	/**
	 * Muestra información en detalle del préstamo
	 * @return
	 */
	public String informacionDetalle() {
		StringBuilder cadena = new StringBuilder(150);
		
		cadena.append("------> Préstamo " + idPrestamo + " <------\n");
		cadena.append("Arrendador: " + arrendador.getNombre() + "\n");
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
	
	@Override
	public String toString() {
		return idPrestamo + " - " + arrendador.getNombre();
	}
}
