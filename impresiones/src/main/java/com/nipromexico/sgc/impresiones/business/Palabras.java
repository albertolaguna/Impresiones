package com.nipromexico.sgc.impresiones.business;

public class Palabras {
	private String palabraCopia;
	private String palabraArchivo;
	private String palabraHa;

	public Palabras(int copias, int cantArchivos) {
		if (copias > 1) {
			setPalabraCopia(" copias de ");
			setPalabraHa("han");
		} else {
			setPalabraCopia(" copia de ");
			setPalabraHa("ha");
		}
		if (cantArchivos > 1) {
			setPalabraArchivo(" archivos.");
		} else {
			setPalabraArchivo(" archivo.");
		}
	}

	public String getPalabraHa() {
		return palabraHa;
	}

	public void setPalabraHa(String palabraHa) {
		this.palabraHa = palabraHa;
	}

	public String getPalabraCopia() {
		return palabraCopia;
	}

	public void setPalabraCopia(String palabraCopia) {
		this.palabraCopia = palabraCopia;
	}

	public String getPalabraArchivo() {
		return palabraArchivo;
	}

	public void setPalabraArchivo(String palabraArchivo) {
		this.palabraArchivo = palabraArchivo;
	}

}
