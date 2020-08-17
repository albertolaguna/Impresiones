/*
 * Autor: Alberto Laguna
 * 
 * Leer el archivo README.txt para saber las lineas modificables del proyecto
 * así como lo procedente para realizar su implementación en producción.
 * 
 * Descripción: Este es un programa OpenSource que se ha realizado para Nipro 
 * 				Medical de México con la intención de llevar un registro de las 
 *				impresiones de algunos documentos.
 */

package com.nipromexico.sgc.impresiones;

import java.util.ArrayList;
import com.nipromexico.sgc.impresiones.Exceptions.NoRutasException;
import com.nipromexico.sgc.impresiones.business.Impresor;
import com.nipromexico.sgc.impresiones.dao.Rutas;
import com.nipromexico.sgc.impresiones.ui.Graficos;
import com.nipromexico.sgc.impresiones.ui.PathChooser;

public class App extends Impresor {
	public static void main(String[] args) {
		Rutas rutas = new Rutas();
		ArrayList<String> r = new ArrayList<String>();
		try {
			if(rutas.verificarRutas()) {
				r = rutas.getRutas();
			} 
		} catch (NoRutasException e) {
			PathChooser chooser = new PathChooser("inicio");
			chooser.setVisible(true);
		} finally {
			try {
				Graficos ventana = new Graficos(); 
				ventana.seleccionarArchivos(r.get(0), r.get(1));
			} catch (Exception e2) {
			}
		}
	}
}
