package com.booking.reportes;

import java.util.ArrayList;

import javax.swing.JFrame;

import com.booking.modelo.Utilidades;
import com.booking.persistencia.Libro;

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

public class ReporteLibros extends JFrame {

	private static final long serialVersionUID = 1L;

	public ReporteLibros() throws JRException{
		
		ArrayList<Libro> libros = (ArrayList<Libro>) Utilidades.obtenerTablaLibro();
		JasperReport reporte = JasperCompileManager.compileReport("reportes/reporte_libros.jrxml");
		JasperPrint escritor = JasperFillManager.fillReport(reporte, null, new JRBeanCollectionDataSource(libros));
		
		JRViewer visor = new JRViewer(escritor);
		
		JRPdfExporter exportador = new JRPdfExporter();
		exportador.setExporterInput(new SimpleExporterInput(escritor));
		exportador.setExporterOutput(new SimpleOutputStreamExporterOutput("booking-libros.pdf"));
		exportador.exportReport();
		
		visor.setOpaque(true);
		visor.setVisible(true);
		visor.setZoomRatio(0.7f);
		add(visor);
		setSize(600, 900);
		setVisible(true);
		setTitle("Reporte de libros");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
