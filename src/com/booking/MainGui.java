package com.booking;

import java.io.IOException;

import javafx.application.Application;
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
		mostrarVistaPrincipal();
	}
	
	/**
     * Initializes the root layout.
     */
    public void iniciarRaiz() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGui.class.getResource("vista/RootLayout.fxml"));
            capaRaiz = (BorderPane) loader.load();

            // Show the scene containing the root layout.
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
    public void mostrarVistaPrincipal() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainGui.class.getResource("vista/VistaPrincipal.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            capaRaiz.setCenter(personOverview);
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
}
