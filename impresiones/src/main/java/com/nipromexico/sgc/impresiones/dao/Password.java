package com.nipromexico.sgc.impresiones.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Password {
	public void setPass(char[] pass) {
		FileWriter fichero = null;
        PrintWriter pw = null;
        String psswrd = new String(pass);
        try
        {
            fichero = new FileWriter(".files/password.txt");
            pw = new PrintWriter(fichero);
            pw.println(md5(psswrd));

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
	
	public Boolean verificarContraseña(char[] p) {
		Boolean exito = false;
		String nombreFichero = ".files/password.txt";
        BufferedReader br = null;
        String pass = new String(p);
        String encPass;
        try {
           br = new BufferedReader(new FileReader(nombreFichero));
           encPass = br.readLine();
           pass = md5(pass);
           if(pass.equals(encPass)) {
        	   exito = true;
           } else {
        	   exito = false;
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
	
	public static String getHash(String txt, String hashType) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance(hashType);
			byte[] array = md.digest(txt.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/* Retorna un hash MD5 a partir de un texto */
	public static String md5(String txt) {
		return getHash(txt, "MD5");
	}
}
