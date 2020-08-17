/*
 * Autor: Alberto Laguna
 * 
 */

package com.nipromexico.sgc.impresiones.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

import javax.swing.JOptionPane;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Reporte {

	private XSSFWorkbook wb;
	
	public Boolean existeReporte(String rutaReporte) {
		Boolean existe = false;
		FileInputStream archivo = null;
		try {
			archivo = new FileInputStream(new File(rutaReporte + "/reporteImpresiones.xlsx"));
			existe = true;
		} catch (FileNotFoundException e) {
			existe = false;
		}
		try {
			archivo.close();
		} catch (IOException e) {
		}
		return existe;
	}

	public Boolean insertarDatos(Integer copias, String usuario, String nombre, String rutaReporte) throws IOException {
		Boolean exito = false;
		try {
			FileInputStream archivo = new FileInputStream(new File(rutaReporte + "/reporteImpresiones.xlsx"));
			
			wb = new XSSFWorkbook(archivo);
			XSSFSheet hoja = wb.getSheetAt(0);
			XSSFRow fila = hoja.getRow(hoja.getLastRowNum() + 1);

			if (fila == null) {
				fila = hoja.createRow(hoja.getLastRowNum() + 1);
			}

			XSSFCell celdaUsuario = fila.createCell(0);
			XSSFCell celdaCopias = fila.createCell(1);
			XSSFCell celdaFecha = fila.createCell(2);
			XSSFCell celdaHora = fila.createCell(3);
			XSSFCell celdaNota = fila.createCell(4);
			XSSFCell celdaArchivos = fila.createCell(5);

			if (copias == 0) {
				celdaNota.setCellValue("Cancelada por el usuario");
			}
			celdaCopias.setCellValue(copias);
			celdaUsuario.setCellValue(usuario);

			celdaFecha.setCellValue(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/"
					+ Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR));
			celdaHora.setCellValue(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + ":" + (Calendar.MINUTE) + ":"
					+ (Calendar.SECOND));
			celdaArchivos.setCellValue(nombre);

			archivo.close();

			FileOutputStream salida = new FileOutputStream(rutaReporte + "/reporteImpresiones.xlsx");
			wb.write(salida);
			salida.close();
			exito = true;
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo de reporte en la ruta especificada.\nSi lo ha eliminado, recuperelo lo antes posible.", "Error de reporte", JOptionPane.ERROR_MESSAGE);
		}
		return exito;
	}
	
	public Boolean copiarReporte(String destino,  String rutaReporte) {
		File origin = new File(destino);
        File destination = new File(rutaReporte+"/reporteImpresiones.xlsx");
        if (origin.exists()) {
            try {
                InputStream in = new FileInputStream(origin);
                OutputStream out = new FileOutputStream(destination);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                in.close();
                out.close();
                return true;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
	}
}
