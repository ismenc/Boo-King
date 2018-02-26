package com.booking.vista;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import org.hibernate.HibernateException;

import com.booking.modelo.BookingException;
import com.booking.modelo.Utilidades;
import com.booking.persistencia.Prestamo;


public class ControladorEditarPrestamos {

    @FXML
    private TextField fecha;
    @FXML
    private TextField duracionDias;
    @FXML
    private TextField arrendador;

    private Stage stage;
    private Prestamo prestamo;
    private boolean nuevo;
    private boolean finalizado = false;


    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.stage = dialogStage;
    }


    public void setPrestamo(Prestamo prestamo, boolean nuevo) {
    	this.nuevo = nuevo;
        this.prestamo = prestamo;

        if(nuevo) {
        	fecha.setText("");
	        duracionDias.setText("");
	        arrendador.setText("");
        }else {
	        fecha.setText(prestamo.getFecha().toString());
	        duracionDias.setText(Integer.toString(prestamo.getDuracionDias()));
	        arrendador.setText(prestamo.getArrendador().getNombre());
        }
    }


    public boolean isOkClicked() {
        return finalizado;
    }


    @FXML
    private void aceptar() {
    	try {
            //prestamo.setFecha(new LocalDate(fecha.getText()));
            prestamo.setDuracionDias(Integer.parseInt(duracionDias.getText()));
            prestamo.setArrendador(Utilidades.obtenerArrendadorPorNombre(arrendador.getText()));
            
            try {
	            if(nuevo)
	            	finalizado = Utilidades.insertarObjeto(prestamo);
	            else
	            	finalizado = Utilidades.actualizarObjeto(prestamo);
	
	            if(finalizado)
	            	stage.close();
	            else {
	            	Alert alert = new Alert(AlertType.ERROR);
	            	alert.setTitle("Error al actualizar");
	            	alert.setHeaderText("No se pudo actualizar el arrendador");
	            	alert.setContentText("Por favor comprueba los datos introducidos e inténtalo de nuevo.");
	
	            	alert.showAndWait();
	            }
            }catch (BookingException e) {
            	Alert alert = new Alert(AlertType.ERROR);
            	alert.setTitle("Error al actualizar");
            	alert.setHeaderText("No se pudo actualizar el préstamo");
            	alert.setContentText(e.getMessage());

            	alert.showAndWait();
            }
    	}catch(NumberFormatException e) {
    		Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error al actualizar");
        	alert.setHeaderText("Duración errónea");
        	alert.setContentText("La duración del préstamo debe ser un número entero");

        	alert.showAndWait();
    	}catch(HibernateException e) {
    		Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error al actualizar");
        	alert.setHeaderText("Duración errónea");
        	alert.setContentText("La duración del préstamo debe ser un número entero");

        	alert.showAndWait();
    	}
        
    }

    @FXML
    private void cancelar() {
        stage.close();
    }

}