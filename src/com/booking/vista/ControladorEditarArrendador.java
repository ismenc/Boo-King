package com.booking.vista;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.booking.modelo.BookingException;
import com.booking.modelo.Utilidades;
import com.booking.persistencia.Arrendador;


public class ControladorEditarArrendador {

    @FXML
    private TextField nombre;
    @FXML
    private TextField entidad;
    @FXML
    private TextField direccion;
    @FXML
    private TextField codigoPostal;
    @FXML
    private TextField telefono;

    private Stage stage;
    private Arrendador arrendador;
    private boolean nuevo;
    private boolean finalizado = false;


    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.stage = dialogStage;
    }


    public void setArrendador(Arrendador arrendador, boolean nuevo) {
    	this.nuevo = nuevo;
        this.arrendador = arrendador;

        nombre.setText(arrendador.getNombre());
        entidad.setText(arrendador.getEntidad());
        direccion.setText(arrendador.getDireccion());
        codigoPostal.setText(arrendador.getCodigoPostal());
        telefono.setText(arrendador.getTelefono());
    }


    public boolean isOkClicked() {
        return finalizado;
    }


    @FXML
    private void aceptar() {
        if (isInputValid()) {
            arrendador.setNombre(nombre.getText());
            if(entidad.getText() != null)
            	arrendador.setEntidad(entidad.getText());
            arrendador.setDireccion(direccion.getText());
            arrendador.setCodigoPostal(codigoPostal.getText());
            arrendador.setTelefono(telefono.getText());
            
            try {
	            if(nuevo)
	            	finalizado = Utilidades.insertarObjeto(arrendador);
	            else
	            	finalizado = Utilidades.actualizarObjeto(arrendador);
	
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
            	alert.setHeaderText("No se pudo actualizar el arrendador");
            	alert.setContentText(e.getMessage());

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