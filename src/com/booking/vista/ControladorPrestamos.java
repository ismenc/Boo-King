package com.booking.vista;


import com.booking.ejecutable.MainGui;
import com.booking.modelo.BookingException;
import com.booking.modelo.Utilidades;
import com.booking.persistencia.Prestamo;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControladorPrestamos {
	
	private MainGui mainApp;

	@FXML
    private TableView<Prestamo> tablaPrestamo;
	@FXML
    private TableColumn<Prestamo, String> id;
    @FXML
    private TableColumn<Prestamo, String> fecha;
    @FXML
    private TableColumn<Prestamo, String> duracion;
    @FXML
    private TableColumn<Prestamo, String> arrendador;
    
    public ControladorPrestamos() {
    	
	}
	
	/* ============================|  Métodos básicos  |============================  */
    
    @FXML
    private void initialize() {
        id.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getIdPrestamo())));
        fecha.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getFecha().toString()));
        duracion.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getDuracionDias())));
        arrendador.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getArrendador().getNombre()));
    }

    public void setMainApp(MainGui mainApp) {
        this.mainApp = mainApp;
        tablaPrestamo.setItems( mainApp.getTablaPrestamos() );
    }
    
    
    
	/* ============================|  Gestión de opciones  |============================  */
    
    public void volverAlMenu() {
    	mainApp.mostrarMenuPrincipal();
    }
    
    @FXML
	private void nuevoPrestamo() {
		Prestamo prestamo = new Prestamo();
		boolean okClicked = mainApp.gestionarPrestamo(prestamo, true);
		if (okClicked) {
			// Habría que refrescar la tabla.
			mainApp.mostrarPrestamos();
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit
	 * details for the selected person.
	 */
	@FXML
	private void editarPrestamo() {
		Prestamo prestamo = tablaPrestamo.getSelectionModel().getSelectedItem();
		if (prestamo != null) {
			boolean okClicked = mainApp.gestionarPrestamo(prestamo, false);
			if (okClicked) {
				mainApp.mostarArrendadores();
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falta información");
			alert.setHeaderText("No se ha seleccionado ningún préstamo");
			alert.setContentText("Por favor, para editar un préstamo debe seleccionarlo en la tabla.");

			alert.showAndWait();
		}
	}
	
	@FXML
	private void borrarPrestamo() {
		int idSeleccion = tablaPrestamo.getSelectionModel().getSelectedIndex();
		if (idSeleccion >= 0) {
			Prestamo prestamo = tablaPrestamo.getItems().get(idSeleccion);
			try {
				Utilidades.borrarGenerico(prestamo);
			} catch (BookingException e) {
				e.printStackTrace();
			}
			tablaPrestamo.getItems().remove(idSeleccion);
			
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falta información");
			alert.setHeaderText("No se ha seleccionado ningún préstamo");
			alert.setContentText("Por favor, para borrar un préstamo debe seleccionarlo en la tabla.");

			alert.showAndWait();
		}
	}
    
    
}
