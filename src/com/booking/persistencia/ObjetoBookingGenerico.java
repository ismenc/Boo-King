package com.booking.persistencia;

/************************************
 * Objeto padre de los objetos que se 
 * guardarán en la base de datos.
 * @author Ismael Núñez
 ************************************/
public abstract class ObjetoBookingGenerico {

	public String informacionDetalle() {
		return "Método que debería mostrar información detallada del objeto";
	}
}
