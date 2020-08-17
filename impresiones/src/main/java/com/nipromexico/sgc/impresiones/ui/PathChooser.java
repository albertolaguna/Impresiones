package com.nipromexico.sgc.impresiones.ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import com.nipromexico.sgc.impresiones.Exceptions.NoRutasException;
import com.nipromexico.sgc.impresiones.dao.Password;
import com.nipromexico.sgc.impresiones.dao.Reporte;
import com.nipromexico.sgc.impresiones.dao.Rutas;

public class PathChooser extends JFrame {

	private static final long serialVersionUID = 1L;
	private Container pane;
	private GridBagLayout layout;
	private GridBagConstraints c;
	private JLabel titulo1;
	private JLabel titulo2;
	private JLabel titulo3;
	private JLabel rutaPdfLbl;
	private JTextField rutaPdfTxt;
	private JButton rutaPdfBtn;
	private JLabel rutaReporteLbl;
	private JTextField rutaReporteTxt;
	private JButton rutaReporteBtn;
	private JLabel passLbl;
	private JLabel comfirmLbl;
	private JPasswordField passTxt;
	private JPasswordField comfirmTxt;
	private JButton aceptarBtn;

	public PathChooser(String opcion) {
		initComponents();
		agregarComponentes();
		iniciarListeners(opcion);
		setTitle("Escoger rutas");
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(700, 450);
		setLocationRelativeTo(null);

	}

	private void initComponents() {
		titulo1 = new JLabel("Antes de poder usar este software, debes definir la ruta donde se encuentran los");
		titulo2 = new JLabel("archivos imprimibles y la ruta donde se va a generar el reporte, además de una");
		titulo3 = new JLabel("contraseña de administrador para poder modificar dichas rutas.");
		rutaPdfLbl = new JLabel("Ruta de los imprimibles:");
		rutaPdfTxt = new JTextField();
		rutaPdfTxt.setEditable(false);
		rutaPdfBtn = new JButton("Elegir ruta");
		rutaPdfBtn.setFocusable(false);
		rutaReporteLbl = new JLabel("Ruta del reporte:");
		rutaReporteTxt = new JTextField();
		rutaReporteTxt.setEditable(false);
		rutaReporteBtn = new JButton("Elegir ruta");
		rutaReporteBtn.setFocusable(false);
		passLbl = new JLabel("Contraseña:");
		comfirmLbl = new JLabel("Repetir contraseña:");
		passTxt = new JPasswordField();
		comfirmTxt = new JPasswordField();
		aceptarBtn = new JButton("Aceptar");
		pane = getContentPane();
		layout = new GridBagLayout();
		pane.setLayout(layout);
		c = new GridBagConstraints();
	}
	
	private void agregarComponentes() {
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		pane.add(titulo1, c);
		c.gridy = 1;
		pane.add(titulo2, c);
		c.gridy = 2;
		pane.add(titulo3, c);
		c.insets = new Insets(40, 10, 20, 1);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		c.gridy = 3;
		pane.add(rutaPdfLbl, c);
		c.gridx = 1;
		c.ipadx = 350;
		c.ipady = 10;
		pane.add(rutaPdfTxt, c);
		c.ipady = 1;
		c.ipadx = 30;
		c.gridx = 2;
		pane.add(rutaPdfBtn, c);
		c.insets = new Insets(0, 10, 20, 1);
		c.gridwidth = 1;
		c.gridy = 4;
		c.gridx = 0;
		pane.add(rutaReporteLbl, c);
		c.gridx = 1;
		c.ipadx = 350;
		c.ipady = 10;
		pane.add(rutaReporteTxt, c);
		c.ipady = 1;
		c.ipadx = 30;
		c.gridx = 2;
		pane.add(rutaReporteBtn, c);
		c.insets = new Insets(30, 10, 0, 1);
		c.gridx = 0;
		c.gridy = 5;
		pane.add(passLbl, c);
		c.ipady = 10;
		c.gridx = 1;
		c.gridwidth = 2;
		pane.add(passTxt, c);
		c.ipady = 1;
		c.gridx = 0;
		c.gridy = 6;
		pane.add(comfirmLbl, c);
		c.ipady = 10;
		c.gridx = 1;
		c.gridwidth = 2;
		pane.add(comfirmTxt, c);
		c.insets = new Insets(50, 00, 0, 1);
		c.ipady = 10;
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 3;
		pane.add(aceptarBtn, c);
	}
	
	private void iniciarListeners(String opcion) {
		rutaPdfBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rutaPdfTxt.setText(escogerDirectorio());
			}
		});
		rutaReporteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				rutaReporteTxt.setText(escogerDirectorio());
			}
		});
		aceptarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!rutaPdfTxt.getText().equals("") && !rutaReporteTxt.getText().equals("")) {
					if(passTxt.getPassword().length != 0 && comfirmTxt.getPassword().length != 0) {
						if(Arrays.equals(passTxt.getPassword(), comfirmTxt.getPassword())) {
							if(passTxt.getPassword().length >= 8 && comfirmTxt.getPassword().length >= 8) {
								Reporte reporte = new Reporte();
								if(opcion.equals("inicio")) {
									if (reporte.copiarReporte("resources/reporte.xlsx", rutaReporteTxt.getText())) {
										Rutas rutas = new Rutas();
										Password password = new Password();
										password.setPass(passTxt.getPassword());
										rutas.escribirArchivoDeRutas(rutaPdfTxt.getText(), rutaReporteTxt.getText());
										JOptionPane.showMessageDialog(null, "Rutas registradas con éxito!\nEl programa se cerrará.\nDeberá volver a ejectutarlo maualmente\npara comenzar a usar la función de impresión", "Listo", JOptionPane.INFORMATION_MESSAGE);
										System.exit(0);
									} else {
										JOptionPane.showMessageDialog(null, "No se ha podido crear el archivo de reporte en la ruta especificada. \nPor favor, verifique que el directorio no necesite permisos de administrador\no elija otra ruta", "Error de escriura", JOptionPane.ERROR_MESSAGE);
									}
								} else {
									Rutas rutas = new Rutas();
									ArrayList<String> r = new ArrayList<String>();
									try {
										rutas.verificarRutas();
										r = rutas.getRutas();
									} catch (NoRutasException e1) {
										
									}
									if (reporte.copiarReporte(r.get(1)+"/reporteImpresiones.xlsx", rutaReporteTxt.getText())) {
										Password password = new Password();
										password.setPass(passTxt.getPassword());
										rutas.escribirArchivoDeRutas(rutaPdfTxt.getText(), rutaReporteTxt.getText());
										File archivo = new File(r.get(1)+"/reporteImpresiones.xlsx");
										archivo.delete();
										JOptionPane.showMessageDialog(null, "Rutas registradas con éxito!\nEl programa se cerrará.\nDeberá volver a ejectutarlo maualmente\npara comenzar a usar la función de impresión", "Listo", JOptionPane.INFORMATION_MESSAGE);
										System.exit(0);
									} else {
										JOptionPane.showMessageDialog(null, "No se ha podido crear el archivo de reporte en la ruta especificada. \nPor favor, verifique que el directorio no necesite permisos de administrador\no elija otra ruta", "Error de escriura", JOptionPane.ERROR_MESSAGE);
									}
								}
								
							} else {
								JOptionPane.showMessageDialog(null, "La contraseña debe tener, al menos, 8 caracteres.", "Contraseña invalida", JOptionPane.ERROR_MESSAGE);
							}
							
						} else {
							JOptionPane.showMessageDialog(null, "Las contraseñas que ingresaste no coinciden.", "Contraseñas incorrectas", JOptionPane.ERROR_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "La contraseña es obligatoria.\nDebes ingresarla.", "Contraseña no ingresada", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Debes seleccionar las rutas.", "Rutas incompletas", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
	}

	private String escogerDirectorio() {
		String ruta = null;
		JFileChooser dir = new JFileChooser();
		dir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		Integer seleccion = dir.showOpenDialog(null);
		if(seleccion != JFileChooser.CANCEL_OPTION) {
			ruta = dir.getSelectedFile().getPath().toString();
		}
		return ruta;
	}
}
