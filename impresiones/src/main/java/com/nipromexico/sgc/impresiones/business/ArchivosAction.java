/*
 * Autor: Alberto Laguna
 * 
 */

package com.nipromexico.sgc.impresiones.business;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArchivosAction {
	public List<String> obtenerArchivos(String rutaPdf) {
		List<String> files = new ArrayList<String>();
		File carpeta = new File(rutaPdf);
		String[] archivos = carpeta.list();
		int j = 0;
		if (archivos == null)
			System.out.println("No hay ficheros en el directorio especificado");
		else {
			for (int i = 0; i < archivos.length; i++) {
				if (archivos[i].endsWith(".pdf")) {
					files.add(j, archivos[i]);
					j++;
				}
			}
		}
		return files;
	}
}
