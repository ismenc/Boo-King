package com.booking.vista;

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

}
