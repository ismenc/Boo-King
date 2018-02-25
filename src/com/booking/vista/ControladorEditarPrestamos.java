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
        if (isInputValid()) {
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
    }

    @FXML
    private void cancelar() {
        stage.close();
    }

    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        /*String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No valid street!\n"; 
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n"; 
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n"; 
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
        	Dialogs.create()
		        .title("Invalid Fields")
		        .masthead("Please correct invalid fields")
		        .message(errorMessage)
		        .showError();
            return false;
        }*/
        
        return true;
    }
}