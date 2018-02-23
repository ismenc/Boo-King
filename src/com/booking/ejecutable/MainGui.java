package com.booking.ejecutable;

import java.io.IOException;
import java.util.ArrayList;

import com.booking.modelo.Utilidades;
import com.booking.persistencia.Arrendador;
import com.booking.vista.ControladorArrendadores;
import com.booking.vista.ControladorPrincipal;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainGui extends Application {
	
	private Stage primaryStage;
	private BorderPane capaRaiz;
	private AnchorPane menuPrincipal, vistaArrendadores;
	private ObservableList<Arrendador> tablaArrendadores;
	private ControladorPrincipal controladorMain;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("BooKing");
        
        
        
		iniciarRaiz();
		
	}
	
	/**
     * Inicializa la ventana
     */
    public void iniciarRaiz() {
        try {
            FXMLLoader loader = new FXMLLoader();
            FXMLLoader loader2 = new FXMLLoader();
            
            loader.setLocation(MainGui.class.getResource("../vista/RootLayout.fxml"));
            capaRaiz = (BorderPane) loader.load();
            
            loader2.setLocation(MainGui.class.getResource("../vista/VistaPrincipal.fxml"));
            menuPrincipal = (AnchorPane) loader2.load();
            
            controladorMain = loader2.getController();
            controladorMain.setMainApp(this);
            
            capaRaiz.setCenter(menuPrincipal);

            Scene scene = new Scene(capaRaiz);
            primaryStage.setScene(scene);
            
            primaryStage.show();
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
        	// FIXME Falla por aquí
        	ArrayList<Arrendador> tabla = new ArrayList<Arrendador>(Utilidades.obtenerTablaArrendadores());
        	tablaArrendadores = FXCollections.observableArrayList(tabla);
        	System.out.println(1);
        	
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGui.class.getResource("../vista/MostrarArrendador.fxml"));
            
            System.out.println(1);
            vistaArrendadores = (AnchorPane) loader.load();
            
            capaRaiz.setCenter(vistaArrendadores);
            System.out.println(1);
            ControladorArrendadores controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
