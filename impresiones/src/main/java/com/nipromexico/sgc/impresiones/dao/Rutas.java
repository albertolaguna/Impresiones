package com.nipromexico.sgc.impresiones.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import com.nipromexico.sgc.impresiones.Exceptions.NoRutasException;

public class Rutas {
	public static final Integer RUTA_PDF = 0;
	public static final Integer RUTA_REPORTE = 1;
	
	private File directorio = new File(".files");
	private File archvoRutas = new File(".files/archivoRutas.txt");
	private ArrayList<String> rutas = new ArrayList<String>();
	
	public Rutas() {
		
	}
	
	public Boolean verificarRutas() throws NoRutasException {
		Boolean exito = false;
		crearCarpeta();
		crearArchivo();
		if(leerArchivoDeRutas()) {
			System.out.println("Se leyó");
			exito = true;
		} else {
			exito = false;
			throw new NoRutasException();
		}
		return exito;
	}
	
	private void crearCarpeta() {
		if (!directorio.exists()) {
			directorio.mkdirs();
		}
	}
	
	private Boolean crearArchivo() {
		Boolean exito = false;
		try {
			if (!archvoRutas.exists()) {
				archvoRutas.createNewFile();
				exito = true;
			}
		} catch (IOException e) {
			exito = false;
		} 
		return exito;
	}
	
	private Boolean leerArchivoDeRutas() {
		Boolean exito = false;
		String nombreFichero = ".files/archivoRutas.txt";
        BufferedReader br = null;
        try {
           br = new BufferedReader(new FileReader(nombreFichero));
           rutas.add(br.readLine());
           rutas.add(br.readLine());
           if(rutas.get(RUTA_PDF) != null && rutas.get(RUTA_REPORTE) != null) {
        	   exito = true;
           }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado");
            System.out.println(e.getMessage());
            exito = false;
        }
        catch(Exception e) {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
            exito = false;
        }
        finally {
            try {
                if(br != null)
                    br.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
        return exito;
	}
	
	public void escribirArchivoDeRutas(String rutaPdf, String rutaReporte) {
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(".files/archivoRutas.txt");
            pw = new PrintWriter(fichero);
            pw.println(rutaPdf);
            pw.println(rutaReporte);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}

	public ArrayList<String> getRutas() {
		return rutas;
	}

	public void setRutas(ArrayList<String> rutas) {
		this.rutas = rutas;
	}	
}
