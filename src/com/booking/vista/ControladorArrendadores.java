package com.booking.vista;


import com.booking.ejecutable.MainGui;
import com.booking.modelo.BookingException;
import com.booking.modelo.Utilidades;
import com.booking.persistencia.Arrendador;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControladorArrendadores {
	
	private MainGui mainApp;

	@FXML
    private TableView<Arrendador> tablaArrendador;
	@FXML
    private TableColumn<Arrendador, String> id;
    @FXML
    private TableColumn<Arrendador, String> nombre;
    @FXML
    private TableColumn<Arrendador, String> entidad;
    @FXML
    private TableColumn<Arrendador, String> direccion;
    @FXML
    private TableColumn<Arrendador, String> codigoPostal;
    @FXML
    private TableColumn<Arrendador, String> telefono;
    
    public ControladorArrendadores() {
    	
	}
	
	/**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        // Initialize the person table with the two columns.
        id.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getId())));
        nombre.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getNombre()));
        entidad.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEntidad()));
        direccion.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getDireccion()));
        codigoPostal.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCodigoPostal()));
        telefono.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTelefono()));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainGui mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        tablaArrendador.setItems( mainApp.getTablaArrendador() );
    }
    
    public void volverAlMenu() {
    	mainApp.mostrarMenuPrincipal();
    }
    
    
    @FXML
	private void nuevoArrendador() {
		Arrendador arrendador = new Arrendador();
		boolean okClicked = mainApp.editarArrendador(arrendador, true);
		if (okClicked) {
			// Habría que refrescar la tabla.
			mainApp.mostarArrendadores();
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void editarArrendador() {
		Arrendador arrendador = tablaArrendador.getSelectionModel().getSelectedItem();
		if (arrendador != null) {
			boolean okClicked = mainApp.editarArrendador(arrendador, false);
			if (okClicked) {
				mainApp.mostarArrendadores();
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falta información");
			alert.setHeaderText("No se ha seleccionado ningún arrendador");
			alert.setContentText("Por favor, para editar un arrendador debe seleccionarlo en la tabla.");

			alert.showAndWait();
		}
	}
	
	@FXML
	private void borrarArrendador() {
		int idSeleccion = tablaArrendador.getSelectionModel().getSelectedIndex();
		if (idSeleccion >= 0) {
			Arrendador arrendador = tablaArrendador.getItems().get(idSeleccion);
			try {
				Utilidades.borrarGenerico(arrendador);
			} catch (BookingException e) {
				e.printStackTrace();
			}
			tablaArrendador.getItems().remove(idSeleccion);
			
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falta información");
			alert.setHeaderText("No se ha seleccionado ningún arrendador");
			alert.setContentText("Por favor, para borrar un arrendador debe seleccionarlo en la tabla.");

			alert.showAndWait();
		}
	}
    
    
}
