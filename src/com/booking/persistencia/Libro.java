package com.booking.persistencia;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.booking.modelo.Categoria;

/**********************************************
 * Define los libros que serán prestados.
 * @author Ismael Núñez
 *
 **********************************************/
@Entity
@Table(name="libro")
public class Libro extends ObjetoBookingGenerico implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* ==============XXX==============|  Atributos  |==============XXX==============  */
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idLibro;
	
	@Column
	@NotNull
	@Size(min = 3, max = 50)
	private String titulo;
	
	@Column
	@NotNull
	@Size(min = 3, max = 50)
	private String autor;
	
	@NotNull
	@Enumerated(EnumType.ORDINAL)
	private Categoria categoria;
	
	@Column
	@NotNull
	@Size(min = 3, max = 50)
	private String editorial;
	
	@Column
	@NotNull
	@Max(2018)
	private String anoPublicacion;

	/* ==============XXX==============|  Constructores  |==============XXX==============  */

	public Libro() { }

	public Libro(String titulo, String autor, String editorial, Categoria categoria, String anoPublicacion) {
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.categoria = categoria;
		this.anoPublicacion = anoPublicacion;
	}
	
	/* ==============XXX==============|  Getter & Setter  |==============XXX==============  */

	public int getId() {
		return idLibro;
	}

	public void setId(int idLibro) {
		this.idLibro = idLibro;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public String getEditorial() {
		return editorial;
	}

	public void setEditorial(String editorial) {
		this.editorial = editorial;
	}

	public String getAnoPublicacion() {
		return anoPublicacion;
	}

	public void setAnoPublicacion(String anoPublicacion) {
		this.anoPublicacion = anoPublicacion;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	/* ==============XXX==============|  Métodos  |==============XXX==============  */

	/**********************************************
	 * Muestra información en detalle del Libro.
	 * @return
	 **********************************************/
	@Override
	public String informacionDetalle() {
		StringBuilder cadena = new StringBuilder(150);
		
		cadena.append("---------> " + titulo + " <---------\n");
		cadena.append("\tAutor: " + autor + "\n");
		cadena.append("\tEditorial: "+ editorial + "\n");
		cadena.append("\tCategoría: " + categoria.name() + "\n");
		cadena.append("\tAño de publicación: " + anoPublicacion + "\n");
		cadena.append("-----------------------------------------");
		
		return cadena.toString();
	}
	
	/**********************************************
	 * Muestra información breve del libro
	 **********************************************/
	@Override
	public String toString() {
		return idLibro + " - " + titulo;
	}
}
