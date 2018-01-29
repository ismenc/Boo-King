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

import com.booking.modelo.Categoria;

/**
 * Define los libros que seran prestados
 * @author Ismael Nunez
 *
 */
@Entity
@Table(name="libro")
public class Libro implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* ------------------- Atributos ------------------- */
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idLibro;
	
	@Column
	private String titulo;
	
	@Column
	private String autor;
	
	@Enumerated(EnumType.ORDINAL)
	private Categoria categoria;
	
	@Column
	private String editorial;
	
	@Column
	private String anoPublicacion;

	/* ------------------- Constructor ------------------- */

	public Libro() { }

	public Libro(String titulo, String autor, String editorial, Categoria categoria, String anoPublicacion) {
		this.titulo = titulo;
		this.autor = autor;
		this.editorial = editorial;
		this.categoria = categoria;
		this.anoPublicacion = anoPublicacion;
	}
	
	/* ----------------- Getter & Setter ----------------- */

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
	
	/* -------------------- Métodos -------------------- */

	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder(150);
		
		cadena.append("------> " + titulo + " <------\n");
		cadena.append("Autor: " + autor + "\n");
		cadena.append("Editorial: "+ editorial + "\n");
		cadena.append("Categoría: " + categoria.name() + "\n");
		cadena.append("Año de publicación: " + anoPublicacion + "\n");
		
		return cadena.toString();
	}
}
