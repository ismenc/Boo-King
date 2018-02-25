package com.booking.vista;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.hibernate.exception.GenericJDBCException;

import com.booking.ejecutable.MainGui;
import com.booking.modelo.HibernateUtil;
import com.booking.reportes.ReporteArrendadores;
import com.booking.reportes.ReporteLibros;
import com.booking.reportes.ReportePrestamos;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import net.sf.jasperreports.engine.JRException;

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
	
	/* =============================== Menu Items ================================= */
	
	public void abrirVistaArrendador() {
		try {
			mainApp.mostarArrendadores();
		}catch (GenericJDBCException e) {
			mostrarErrorConexion();
		}
	}
	
	public void abrirVistaLibros() {
		try {
			mainApp.mostrarLibros();
		}catch (GenericJDBCException e) {
			mostrarErrorConexion();
		}
	}
	
	public void abrirVistaPrestamos() {
		try {
			mainApp.mostrarPrestamos();
		}catch (GenericJDBCException e) {
			mostrarErrorConexion();
		}
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
		
	public void abrirManual() {
		if (Desktop.isDesktopSupported()) {
		    try {
				Desktop.getDesktop().browse(new URI("https://github.com/ismenc/Boo-King#manual-del-proyecto"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void reportArrendadores() {
		try {
			@SuppressWarnings("unused")
			ReporteArrendadores ventanaReporte = new ReporteArrendadores();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (GenericJDBCException e) {
			mostrarErrorConexion();
		}
	}
	
	public void reportLibros() {
		try {
			@SuppressWarnings("unused")
			ReporteLibros ventanaReporte = new ReporteLibros();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (GenericJDBCException e) {
			mostrarErrorConexion();
		}
	}
	
	public void reportPrestamos() {
		try {
			@SuppressWarnings("unused")
			ReportePrestamos ventanaReporte = new ReportePrestamos();
		} catch (JRException e) {
			e.printStackTrace();
		} catch (GenericJDBCException e) {
			mostrarErrorConexion();
		}
	}
	
	private void mostrarErrorConexion() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Fallo en la conexión con la base de datos.");
		alert.setContentText("El error puede haberse producido por los siguientes casos:\n"
				+ "  • No existe la base de datos.\n"
				+ "  • Las credenciales de conexión no son correctas.\n"
				+ "  • La base de datos está apagada.\n"
				+ "Por favor comprueba las credenciales en 'Archivo' -> 'Configurar BBDD' e inténtalo de nuevo.");

		alert.showAndWait();
	}
	
	public void configurarBD() {
		// Create the custom dialog.
		Dialog<Pair<String, String>> dialog = new Dialog<>();
		dialog.setTitle("Datos de conexión");
		dialog.setHeaderText("Introduce las credenciales de la base de datos");

		// Set the button types.
		ButtonType loginButtonType = new ButtonType("Confirmar", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		// Create the username and password labels and fields.
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		TextField username = new TextField();
		username.setPromptText("Usuario");
		PasswordField password = new PasswordField();
		password.setPromptText("Contraseña");

		grid.add(new Label("Usuario:"), 0, 0);
		grid.add(username, 1, 0);
		grid.add(new Label("Contraseña:"), 0, 1);
		grid.add(password, 1, 1);

		// Enable/Disable login button depending on whether a username was entered.
		Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
		loginButton.setDisable(true);

		// Do some validation (using the Java 8 lambda syntax).
		username.textProperty().addListener((observable, oldValue, newValue) -> {
		    loginButton.setDisable(newValue.trim().isEmpty());
		});

		dialog.getDialogPane().setContent(grid);

		// Request focus on the username field by default.
		Platform.runLater(() -> username.requestFocus());

		// Convert the result to a username-password-pair when the login button is clicked.
		dialog.setResultConverter(dialogButton -> {
		    HibernateUtil.setUser(username.getText());
		    HibernateUtil.setPass(password.getText());
		    HibernateUtil.buildSessionFactory();
		    return null;
		});

		dialog.showAndWait();

	}
}
