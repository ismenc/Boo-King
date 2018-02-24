package com.booking.vista;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.booking.ejecutable.MainGui;

import javafx.fxml.FXML;

public class ControladorPrincipal {
	
	private MainGui mainApp;
	
	@FXML
    private void initialize() {
    }

	public ControladorPrincipal() {
	}
	
	public void setMainApp(MainGui main) {
		this.mainApp = main;
	}
	
	public void abrirVistaArrendador() {
		mainApp.mostarArrendadores();
	}
	
	public void salir() {
		System.exit(0);
	}
	
	public void sobreBooking() {
		if (Desktop.isDesktopSupported()) {
		    try {
				Desktop.getDesktop().browse(new URI("https://github.com/ismenc/Boo-King"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void gitHubAutor() {
		if (Desktop.isDesktopSupported()) {
		    try {
				Desktop.getDesktop().browse(new URI("https://github.com/ismenc"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void linkedinAutor() {
		if (Desktop.isDesktopSupported()) {
		    try {
				Desktop.getDesktop().browse(new URI("http://www.linkedin.com/in/ismenc"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

	}
	
}
