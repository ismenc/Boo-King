package com.booking.persistencia;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/*********************************************************************************
 * Define la relacion préstamo-libro N-M. Es una pila de una cantidad de un libro.
 * Alguien puede prestar varias pilas en un prestamo.
 * @author Ismael Núñez
 *
 *********************************************************************************/
@Entity
@Table(name="stack")
public class Stack extends ObjetoBookingGenerico implements Serializable {
	private static final long serialVersionUID = 1L;

	/* ==============XXX==============|  Atributos  |==============XXX==============  */
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idStack;
	
	@Column
	@NotNull
	@Min(1)
	@Max(255)
	private int cantidad;
	
	@ManyToOne
	@JoinColumn(name="idPrestamo")
	@Valid
	private Prestamo prestamo;
	
	@ManyToOne
	@JoinColumn(name="idLibro")
	@Valid
	private Libro libro;
	
	/* ==============XXX==============|  Constructores  |==============XXX==============  */
	
	public Stack() { }

	public Stack(Libro libro, int cantidad, Prestamo prestamo) {
		this.cantidad = cantidad;
		this.prestamo = prestamo;
		this.libro = libro;
	}

	/* ==============XXX==============|  Getter & Setter  |==============XXX==============  */

	public int getIdStack() {
		return idStack;
	}

	public void setIdStack(int idStack) {
		this.idStack = idStack;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public Prestamo getPrestamo() {
		return prestamo;
	}

	public void setPrestamo(Prestamo prestamo) {
		this.prestamo = prestamo;
	}

	public Libro getLibro() {
		return libro;
	}

	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	
	/* ==============XXX==============|  Métodos  |==============XXX==============  */

	/**********************************************
	 * Muestra información en detalle del Stack
	 * @return
	 **********************************************/
	@Override
	public String informacionDetalle() {
		return idStack + " - " + cantidad+ "x" + libro.getTitulo();
	}
	
	/**********************************************
	 * Muestra información breve de la pila de libros.
	 **********************************************/
	@Override
	public String toString() {
		return idStack + " - " + cantidad+ "x" + libro.getTitulo();
	}
}
