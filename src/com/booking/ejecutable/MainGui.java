package com.booking.ejecutable;

import java.io.IOException;
import java.util.ArrayList;

import com.booking.modelo.Utilidades;
import com.booking.persistencia.Arrendador;
import com.booking.persistencia.Libro;
import com.booking.persistencia.Prestamo;
import com.booking.vista.ControladorArrendadores;
import com.booking.vista.ControladorEditarArrendador;
import com.booking.vista.ControladorEditarLibro;
import com.booking.vista.ControladorEditarPrestamos;
import com.booking.vista.ControladorLibros;
import com.booking.vista.ControladorPrestamos;
import com.booking.vista.ControladorPrincipal;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainGui extends Application {
	
	private Stage primaryStage;
	private BorderPane capaRaiz;
	private AnchorPane menuPrincipal, vistaArrendadores, vistaLibros, vistaPrestamos;
	
	private ObservableList<Arrendador> tablaArrendadores;
	private ObservableList<Libro> tablaLibros;
	private ObservableList<Prestamo> tablaPrestamos;
	
	private ControladorPrincipal controladorMain, controladorMenu;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("BooKing");
        
		iniciarRaiz();
		mostrarMenuPrincipal();
	}
	
	/* ==============XXX==============|  Getter & Setter  |==============XXX==============  */
	
	public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
	
	public ObservableList<Arrendador> getTablaArrendador(){
		return tablaArrendadores;
	}
	
	public ObservableList<Libro> getTablaLibros(){
		return tablaLibros;
	}
	
	public ObservableList<Prestamo> getTablaPrestamos(){
		return tablaPrestamos;
	}
	
	/* ==============XXX==============|  Métodos de pantalla  |==============XXX==============  */
	
    public void iniciarRaiz() {
        try {
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(MainGui.class.getResource("../vista/RootLayout.fxml"));
            capaRaiz = (BorderPane) loader.load();
            
            controladorMenu = loader.getController();
	        controladorMenu.setMainApp(this);

            Scene scene = new Scene(capaRaiz);
            primaryStage.setScene(scene);
            
            primaryStage.getIcons().add(new Image(MainGui.class.getResourceAsStream("../../../resources/icon.png")));
            
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void mostrarMenuPrincipal() {
        try {
	        FXMLLoader loader = new FXMLLoader();
	
	    	loader.setLocation(MainGui.class.getResource("../vista/VistaPrincipal.fxml"));

			menuPrincipal = (AnchorPane) loader.load();
		
	        controladorMain = loader.getController();
	        controladorMain.setMainApp(this);
	        
	        capaRaiz.setCenter(menuPrincipal);
	        
        } catch (IOException e) {
			e.printStackTrace();
		}
    }
	
    public void mostarArrendadores() {
        try {
        	ArrayList<Arrendador> tabla = (ArrayList<Arrendador>) Utilidades.obtenerTablaArrendador();
        	tablaArrendadores = FXCollections.observableArrayList(tabla);
        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGui.class.getResource("../vista/MostrarArrendador.fxml"));
            
            vistaArrendadores = (AnchorPane) loader.load();
            
            capaRaiz.setCenter(vistaArrendadores);

            ControladorArrendadores controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void mostrarLibros() {
        try {
        	ArrayList<Libro> tabla = (ArrayList<Libro>) Utilidades.obtenerTablaLibro();
        	tablaLibros = FXCollections.observableArrayList(tabla);
        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGui.class.getResource("../vista/MostrarLibros.fxml"));
            
            vistaLibros = (AnchorPane) loader.load();
            
            capaRaiz.setCenter(vistaLibros);

            ControladorLibros controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void mostrarPrestamos() {
        try {
        	  ArrayList<Prestamo> tabla = (ArrayList<Prestamo>) Utilidades.obtenerTablaPrestamo();
    		  tablaPrestamos = FXCollections.observableArrayList(tabla);
        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGui.class.getResource("../vista/MostrarPrestamos.fxml"));
            
            vistaPrestamos = (AnchorPane) loader.load();
            
            capaRaiz.setCenter(vistaPrestamos);

            ControladorPrestamos controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
	/* ==============XXX==============|  Tratamiento de datos  |==============XXX==============  */
    
    public boolean gestionarArrendador(Arrendador arrendador, boolean nuevo) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainGui.class.getResource("../vista/EditarArrendador.fxml"));
			AnchorPane contenido = (AnchorPane) loader.load();

			// Creamos el dialogo de edicion
			Stage dialogo = new Stage();
			dialogo.setTitle("Información del arrendador");
			dialogo.initModality(Modality.WINDOW_MODAL);
			dialogo.initOwner(primaryStage);
			Scene scene = new Scene(contenido);
			dialogo.setScene(scene);

			// Le pasamos el arrendador a editar
			ControladorEditarArrendador controlador = loader.getController();
			controlador.setDialogStage(dialogo);
			controlador.setArrendador(arrendador, nuevo);

			// lanzamos
			dialogo.showAndWait();

			return controlador.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
    
    public boolean gestionarLibro(Libro libro, boolean nuevo) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainGui.class.getResource("../vista/EditarArrendador.fxml"));
			AnchorPane contenido = (AnchorPane) loader.load();

			// Creamos el dialogo de edicion
			Stage dialogo = new Stage();
			dialogo.setTitle("Información del libro");
			dialogo.initModality(Modality.WINDOW_MODAL);
			dialogo.initOwner(primaryStage);
			Scene scene = new Scene(contenido);
			dialogo.setScene(scene);

			// Le pasamos el libro a editar
			ControladorEditarLibro controlador = loader.getController();
			controlador.setDialogStage(dialogo);
			controlador.setLibro(libro, nuevo);

			// lanzamos
			dialogo.showAndWait();

			return controlador.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
    
    public boolean gestionarPrestamo(Prestamo prestamo, boolean nuevo) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainGui.class.getResource("../vista/EditarPrestamo.fxml"));
			AnchorPane contenido = (AnchorPane) loader.load();

			// Creamos el dialogo de edicion
			Stage dialogo = new Stage();
			dialogo.setTitle("Información del préstamo");
			dialogo.initModality(Modality.WINDOW_MODAL);
			dialogo.initOwner(primaryStage);
			Scene scene = new Scene(contenido);
			dialogo.setScene(scene);

			// Le pasamos el prestamo a editar
			ControladorEditarPrestamos controlador = loader.getController();
			controlador.setDialogStage(dialogo);
			controlador.setPrestamo(prestamo, nuevo);

			// lanzamos
			dialogo.showAndWait();

			return controlador.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
