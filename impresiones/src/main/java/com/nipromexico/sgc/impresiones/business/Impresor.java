/*
 * Autor: Alberto Laguna
 * 
 */

package com.nipromexico.sgc.impresiones.business;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;
import com.nipromexico.sgc.impresiones.dao.Reporte;

public class Impresor {

	protected final static Logger LOGGER = Logger.getLogger("mx.hash.impresionpdf.Impresor");

	public Boolean imprimir(List<String> archivos, String rutaPdf, String rutaReporte) throws PrinterException, IOException {
		Boolean exito = false;
		Reporte reporte = new Reporte();
		PrinterJob job = PrinterJob.getPrinterJob();
		String usuario = System.getProperty("user.name");
		int cont = 0;
		Boolean reporteActualizado = false;
		Boolean cancelado = false;
		String ruta = rutaPdf+"/";
		if(reporte.existeReporte(rutaReporte)) {
			for(int i=0;i<archivos.size();i++) {
				String archivo = ruta + archivos.get(i);
				// Indicamos el nombre del archivo PDF que deseamos imprimir
				PDDocument document = PDDocument.load(new File(archivo));

				LOGGER.log(Level.INFO, "Mostrando el dialogo de impresion");
				if(cont == 0) {
					if (job.printDialog() == true) {
						job.setPageable(new PDFPageable(document));
						LOGGER.log(Level.INFO, "Imprimiendo documento");
						job.print();
						System.out.println("Se han impreso satisfactoriamente " + job.getCopies() + " copias de este documento");
					}else {
						JOptionPane.showMessageDialog(null, "El proceso de impresión ha sido cancelado por el usuario.", "Proceso cancelado", JOptionPane.WARNING_MESSAGE);
						job.setCopies(0);
						cancelado = true;
					}
				}else {
					if(cancelado == true) {
						job.setCopies(0);
					}else {
						job.setPageable(new PDFPageable(document));
						LOGGER.log(Level.INFO, "Imprimiendo documento");
						job.print();
						System.out.println("Se han impreso satisfactoriamente " + job.getCopies() + " copias de este documento");
						exito = true;
					}
				}
				document.close();
				cont ++;
				reporteActualizado = reporte.insertarDatos(job.getCopies(), usuario, archivos.get(i), rutaReporte);
			}
			if(cancelado == false) {
				Palabras palabra = new Palabras(job.getCopies(), archivos.size());
				JOptionPane.showMessageDialog(null, "Se "+ palabra.getPalabraHa()+" impreso, con éxito, " + Integer.toString(job.getCopies()) + palabra.getPalabraCopia() + Integer.toString(archivos.size()) + palabra.getPalabraArchivo(), "Impresión exitosa", JOptionPane.INFORMATION_MESSAGE);
			}
			if(reporteActualizado) {
				JOptionPane.showMessageDialog(null, "El reporte de impresiones se ha actualizado con éxito.", "Actualización exitosa", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo de reporte en la ruta especificada.\nSi lo ha eliminado o movido, recuperelo lo antes posible.\nDe lo contrario, no se podrán imprimir más copias", "Error de reporte", JOptionPane.ERROR_MESSAGE);
		}
		return exito;
	}
}