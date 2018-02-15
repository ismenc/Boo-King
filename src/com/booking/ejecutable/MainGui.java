package com.booking.ejecutable;

import java.io.IOException;

import com.booking.vista.ControladorConsultaArrendadores;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainGui extends Application {
	
	private Stage primaryStage;
	private BorderPane capaRaiz;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle("BooKing");
        
		iniciarRaiz();
		mostrarVista("VistaPrincipal");
	}
	
	/**
     * Initializes the root layout.
     */
    public void iniciarRaiz() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGui.class.getResource("../vista/RootLayout.fxml"));
            capaRaiz = (BorderPane) loader.load();

            Scene scene = new Scene(capaRaiz);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the person overview inside the root layout.
     */
    public void mostrarVista(String vista) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGui.class.getResource("../vista/"+vista+".fxml"));
            AnchorPane interfaz = (AnchorPane) loader.load();

            capaRaiz.setCenter(interfaz);
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
	
	/**
     * Shows the person overview inside the root layout.
     */
    public void showPersonOverview() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGui.class.getResource("../vista/MostrarArrendador.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();
            
            // Set person overview into the center of root layout.
            capaRaiz.setCenter(personOverview);
            
            // Give the controller access to the main app.
            ControladorConsultaArrendadores controller = loader.getController();
            controller.setMainApp(this);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
}
