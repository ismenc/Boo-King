package com.booking.vista;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import com.booking.modelo.BookingException;
import com.booking.modelo.Categoria;
import com.booking.modelo.Utilidades;
import com.booking.persistencia.Libro;


public class ControladorEditarLibro {

    @FXML
    private TextField titulo;
    @FXML
    private TextField autor;
    @FXML
    private TextField categoria;
    @FXML
    private TextField editorial;
    @FXML
    private TextField anoPublicacion;

    private Stage stage;
    private Libro libro;
    private boolean nuevo;
    private boolean finalizado = false;


    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.stage = dialogStage;
    }


    public void setLibro(Libro libro, boolean nuevo) {
    	this.nuevo = nuevo;
        this.libro = libro;

        titulo.setText(libro.getTitulo());
        autor.setText(libro.getAutor());
        if(libro.getCategoria() != null)
        	categoria.setText(libro.getCategoria().toString());
        else
        	categoria.setText("");
        editorial.setText(libro.getEditorial());
        anoPublicacion.setText(libro.getAnoPublicacion());
    }


    public boolean isOkClicked() {
        return finalizado;
    }


    @FXML
    private void aceptar() {
    	try {
            libro.setTitulo(titulo.getText());
            libro.setAutor(autor.getText());
            libro.setCategoria(Categoria.valueOf(categoria.getText()));
            libro.setEditorial(editorial.getText());
            libro.setAnoPublicacion(anoPublicacion.getText());
            
            try {
	            if(nuevo)
	            	finalizado = Utilidades.insertarObjeto(libro);
	            else
	            	finalizado = Utilidades.actualizarObjeto(libro);
	
	            if(finalizado)
	            	stage.close();
	            else {
	            	Alert alert = new Alert(AlertType.ERROR);
	            	alert.setTitle("Error al actualizar");
	            	alert.setHeaderText("No se pudo actualizar el libro");
	            	alert.setContentText("Por favor comprueba los datos introducidos e inténtalo de nuevo.");
	
	            	alert.showAndWait();
	            }
            }catch (BookingException e) {
            	Alert alert = new Alert(AlertType.ERROR);
            	alert.setTitle("Error al actualizar");
            	alert.setHeaderText("No se pudo actualizar el libro");
            	alert.setContentText(e.getMessage());

            	alert.showAndWait();
            } 
        
    	}catch (IllegalArgumentException e) {
        	Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error al actualizar");
        	alert.setHeaderText("Categoría no válida.");
        	alert.setContentText("Debe introducir una de las categorías válidas:\n"+java.util.Arrays.asList(Categoria.values()));

        	alert.showAndWait();
        }
    
    }

    @FXML
    private void cancelar() {
        stage.close();
    }

}