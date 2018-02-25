package com.booking.reportes;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.booking.modelo.Utilidades;
import com.booking.persistencia.Arrendador;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.swing.JRViewer;

public class ReporteArrendadores extends JFrame {

	private static final long serialVersionUID = 1L;

	public ReporteArrendadores() throws JRException{
		
		ArrayList<Arrendador> arrendadores = (ArrayList<Arrendador>) Utilidades.obtenerTablaArrendador();
		JasperReport reporte = JasperCompileManager.compileReport("src/com/booking/reportes/reporte_arrendadores.jrxml");
		JasperPrint escritor = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(arrendadores));
		
		JRViewer visor = new JRViewer(escritor);
		
		JRPdfExporter exportador = new JRPdfExporter();
		exportador.setExporterInput(new SimpleExporterInput(escritor));
		exportador.setExporterOutput(new SimpleOutputStreamExporterOutput("booking-arrendadores.pdf"));
		exportador.exportReport();
		
		visor.setOpaque(true);
		visor.setVisible(true);
		visor.setZoomRatio(0.7f);
		add(visor);
		setSize(600, 900);
		setVisible(true);
		setTitle("Reporte de arrendadores");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
