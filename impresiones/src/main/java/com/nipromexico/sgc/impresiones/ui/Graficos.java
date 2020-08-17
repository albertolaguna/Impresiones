/*
 * Autor: Alberto Laguna
 * 
 */

package com.nipromexico.sgc.impresiones.ui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import com.nipromexico.sgc.impresiones.business.ArchivosAction;
import com.nipromexico.sgc.impresiones.business.ConfImpresiones;
import com.nipromexico.sgc.impresiones.dao.Password;

public class Graficos extends JFrame {
	private static final long serialVersionUID = 1L;
	
	public Boolean todoSeleccionado = false;
	
	List<JCheckBox> jCheck = new ArrayList<JCheckBox>();

	ActionListener cerrar = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			System.exit(EXIT_ON_CLOSE);
		}
	};

	JFrame ventana = new JFrame();
	JLabel mensajeLbl;
	JLabel indicacionLbl;

	public void cerrar() {
		ventana.setVisible(false);
		ventana.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ventana.dispose();
	}

	public void seleccionarArchivos(String rutaPdf, String rutaReporte) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		List<String> files = new ArrayList<String>();
		final List<String> archivosSeleccionados = new ArrayList<String>();

		ArchivosAction archivos = new ArchivosAction();

		files = archivos.obtenerArchivos(rutaPdf);

		JCheckBox todosCbx;
		todosCbx = new JCheckBox("Seleccionar todos los archivos");
		todosCbx.setBounds(5, 10, 450, 15);
		todosCbx.setVisible(true);
		todosCbx.setFocusable(false);

		JCheckBox checkCbx;
		for (int i = 0; i < files.size(); i++) {
			checkCbx = new JCheckBox(files.get(i));
			jCheck.add(checkCbx);
			panel.add(jCheck.get(i));
			jCheck.get(i).setFocusable(false);
		}

		ActionListener selecTodo = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (todoSeleccionado == false) {
					for (int i = 0; i < jCheck.size(); i++) {
						jCheck.get(i).setSelected(true);
						todoSeleccionado = true;
						jCheck.get(i).setFocusable(false);
					}
				} else {
					for (int i = 0; i < jCheck.size(); i++) {
						jCheck.get(i).setSelected(false);
						todoSeleccionado = false;
						jCheck.get(i).setFocusable(false);
					}
				}
			}
		};

		todosCbx.addActionListener(selecTodo);

		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(5, 35, 440, 540);

		JPanel contentPane = new JPanel(null);
		contentPane.setPreferredSize(new Dimension(450, 570));
		contentPane.add(scrollPane);

		ActionListener continuarEvent = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < jCheck.size(); i++) {
					if (jCheck.get(i).isSelected()) {
						archivosSeleccionados.add(jCheck.get(i).getText());
					}
				}
				if(archivosSeleccionados.isEmpty()) {
					JOptionPane.showMessageDialog(null, "No seleccionaste ningún archivo.\nPor favor, selecciona al menos uno.", "No hay archivos seleccionados.", JOptionPane.WARNING_MESSAGE);
				} else {
					cerrar();
					System.out.println(archivosSeleccionados.get(0));
					ConfImpresiones impresion = new ConfImpresiones();
					impresion.confImpresion(archivosSeleccionados, rutaPdf, rutaReporte);
				}
			}
		};

		JButton continuarBtn = new JButton("Continuar");
		continuarBtn.setBounds(250, 583, 150, 30);
		continuarBtn.addActionListener(continuarEvent);
		continuarBtn.setFocusable(false);

		JButton cancelarBtn = new JButton("Calcelar");
		cancelarBtn.setBounds(50, 583, 150, 30);
		cancelarBtn.addActionListener(cerrar);
		cancelarBtn.setFocusable(false);
		
		ventana.setContentPane(contentPane);
		ventana.add(todosCbx);
		ventana.add(continuarBtn);
		ventana.add(cancelarBtn);
		ventana.pack();
		ventana.setSize(465, 660);
		ventana.setTitle("Seleccionar archivos para imprimir");
		ventana.setDefaultCloseOperation(EXIT_ON_CLOSE);
		ventana.setLayout(null);
		ventana.setLocationRelativeTo(null);
		ventana.setVisible(true);
		ventana.setResizable(false);
		ventana.setFocusable(true);
		keyListeners();
	}
	
	public void keyListeners() {
		ventana.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar() == 'c' || e.getKeyChar() == 'C') {
					/*PathChooser chooser = new PathChooser("segunda");
					chooser.setVisible(true);*/
					JPanel panel = new JPanel();
					JPasswordField pass = new JPasswordField(30);
					panel.add(pass);
					String[] options = new String[]{"Cancelar", "Aceptar"};
					int option = JOptionPane.showOptionDialog(null, panel, "Ingresar contraseña",
					                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
					                         null, options, options[1]);
					if(option == 1) // pressing OK button
					{
						Password password = new Password();
						if (password.verificarContraseña(pass.getPassword())) {
							ventana.setVisible(false);
							PathChooser chooser = new PathChooser("segunda");
							chooser.setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "La contraseña que ingresaste no es correcta.\nAsegurate de ingresar la contraseña definida la primera vez que se sejecutó\neste programa o la ultima vez que se modificó la configuración.", "Contraseña incorrecta", JOptionPane.ERROR_MESSAGE);
						}
					} else if(option == 0) {
						ventana.setVisible(true);
					}
				}
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
