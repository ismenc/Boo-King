package com.booking.vista;


import com.booking.ejecutable.MainGui;
import com.booking.modelo.BookingException;
import com.booking.modelo.Utilidades;
import com.booking.persistencia.Libro;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControladorLibros {
	
	private MainGui mainApp;

	@FXML
    private TableView<Libro> tablaLibros;
	@FXML
    private TableColumn<Libro, String> id;
    @FXML
    private TableColumn<Libro, String> titulo;
    @FXML
    private TableColumn<Libro, String> autor;
    @FXML
    private TableColumn<Libro, String> categoria;
    @FXML
    private TableColumn<Libro, String> editorial;
    @FXML
    private TableColumn<Libro, String> anoPublicacion;
    
    public ControladorLibros() {
    	
	}
	
	/* ============================|  Métodos básicos  |============================  */
    
    @FXML
    private void initialize() {
        id.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(Integer.toString(cellData.getValue().getId())));
        titulo.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTitulo()));
        autor.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAutor()));
        categoria.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCategoria().name()));
        editorial.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getEditorial()));
        anoPublicacion.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getAnoPublicacion()));
    }

    public void setMainApp(MainGui mainApp) {
        this.mainApp = mainApp;
        tablaLibros.setItems( mainApp.getTablaLibros() );
    }
    
    
    
	/* ============================|  Gestión de opciones  |============================  */
    
    public void volverAlMenu() {
    	mainApp.mostrarMenuPrincipal();
    }
    
    @FXML
	private void nuevoLibro() {
		Libro libro = new Libro();
		boolean okClicked = mainApp.gestionarLibro(libro, true);
		if (okClicked) {
			// Habría que refrescar la tabla.
			mainApp.mostrarLibros();
		}
	}

	
	@FXML
	private void editarLibro() {
		Libro libro = tablaLibros.getSelectionModel().getSelectedItem();
		if (libro != null) {
			boolean okClicked = mainApp.gestionarLibro(libro, false);
			if (okClicked) {
				mainApp.mostrarLibros();
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falta información");
			alert.setHeaderText("No se ha seleccionado ningún libro");
			alert.setContentText("Por favor, para editar un libro debe seleccionarlo en la tabla.");

			alert.showAndWait();
		}
	}
	
	@FXML
	private void borrarLibro() {
		int idSeleccion = tablaLibros.getSelectionModel().getSelectedIndex();
		if (idSeleccion >= 0) {
			Libro libro = tablaLibros.getItems().get(idSeleccion);
			try {
				Utilidades.borrarGenerico(libro);
			} catch (BookingException e) {
				e.printStackTrace();
			}
			tablaLibros.getItems().remove(idSeleccion);
			
		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Falta información");
			alert.setHeaderText("No se ha seleccionado ningún libro");
			alert.setContentText("Por favor, para borrar un libro debe seleccionarlo en la tabla.");

			alert.showAndWait();
		}
	}
    
    
}
