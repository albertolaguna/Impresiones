/*
 * Autor: Alberto Laguna
 * 
 */

package com.nipromexico.sgc.impresiones.business;

import java.awt.print.PrinterException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

public class ConfImpresiones extends Impresor {
	public void confImpresion(List<String> archivos, String rutaPdf, String rutaReporte) {
		Impresor impresor = new Impresor();
		try {
			JOptionPane.showMessageDialog(null, "El proceso de impresión ha comenzado.\nSe te notificará cuando haya terminado.", "Imprimir", JOptionPane.INFORMATION_MESSAGE);
			if (impresor.imprimir(archivos, rutaPdf, rutaReporte)) {
				JOptionPane.showMessageDialog(null, "El proceso de impresión ha concluido satisfactoriamente.", "Proceso de impresión terminado", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (PrinterException | IOException ex) {
			JOptionPane.showMessageDialog(null, "Error de impresion", "Error", JOptionPane.ERROR_MESSAGE);
			LOGGER.log(Level.SEVERE, null, ex);
		}
	}
}
