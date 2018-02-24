package com.booking.ejecutable;

import java.io.IOException;
import java.util.ArrayList;

import com.booking.modelo.Utilidades;
import com.booking.persistencia.Arrendador;
import com.booking.vista.ControladorArrendadores;
import com.booking.vista.ControladorEditarArrendador;
import com.booking.vista.ControladorPrincipal;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainGui extends Application {
	
	private Stage primaryStage;
	private BorderPane capaRaiz;
	private AnchorPane menuPrincipal, vistaArrendadores;
	private ObservableList<Arrendador> tablaArrendadores;
	private ControladorPrincipal controladorMain, controladorMenu;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("BooKing");
        
        
        
		iniciarRaiz();
		mostrarMenuPrincipal();
	}
	
	/**
     * Inicializa la ventana
     */
    public void iniciarRaiz() {
        try {
            FXMLLoader loader = new FXMLLoader();
            
            loader.setLocation(MainGui.class.getResource("../vista/RootLayout.fxml"));
            capaRaiz = (BorderPane) loader.load();
            
            controladorMenu = loader.getController();
	        controladorMenu.setMainApp(this);

            Scene scene = new Scene(capaRaiz);
            primaryStage.setScene(scene);
            
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

    /**
     * Devuelve el stage principal.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

	public static void main(String[] args) {
		launch(args);
	}
	
	public ObservableList<Arrendador> getTablaArrendador(){
		return tablaArrendadores;
	}
	
	/**
     * Shows the person overview inside the root layout.
     */
    public void mostarArrendadores() {
        try {
        	refrescarTablaArrendadores();
        	
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
    
    public void refrescarTablaArrendadores() {
    	ArrayList<Arrendador> tabla = new ArrayList<Arrendador>(Utilidades.obtenerTablaArrendadores());
    	tablaArrendadores = FXCollections.observableArrayList(tabla);
    }
    
    
    public boolean editarArrendador(Arrendador arrendador, boolean nuevo) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainGui.class.getResource("../vista/EditarArrendador.fxml"));
			AnchorPane contenido = (AnchorPane) loader.load();

			// Creamos el dialogo de edicion
			Stage dialogo = new Stage();
			dialogo.setTitle("Editar arrendador");
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
	
}
