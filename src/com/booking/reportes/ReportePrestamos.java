package com.booking.reportes;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.booking.modelo.Utilidades;
import com.booking.persistencia.Prestamo;

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

public class ReportePrestamos extends JFrame {

	private static final long serialVersionUID = 1L;

	public ReportePrestamos() throws JRException{
		
		ArrayList<Prestamo> prestamos = (ArrayList<Prestamo>) Utilidades.obtenerTablaPrestamo();
		JasperReport reporte = JasperCompileManager.compileReport("src/com/booking/reportes/reporte_prestamos.jrxml");
		JasperPrint escritor = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(prestamos));
		
		JRViewer visor = new JRViewer(escritor);
		
		JRPdfExporter exportador = new JRPdfExporter();
		exportador.setExporterInput(new SimpleExporterInput(escritor));
		exportador.setExporterOutput(new SimpleOutputStreamExporterOutput("booking-prestamos.pdf"));
		exportador.exportReport();
		
		visor.setOpaque(true);
		visor.setVisible(true);
		visor.setZoomRatio(0.7f);
		add(visor);
		setSize(600, 900);
		setVisible(true);
		setTitle("Reporte de préstamos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
