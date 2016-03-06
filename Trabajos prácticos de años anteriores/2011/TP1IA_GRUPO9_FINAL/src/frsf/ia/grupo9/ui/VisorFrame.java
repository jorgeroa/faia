package frsf.ia.grupo9.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import frsf.cidisi.faia.agent.GoalBasedAgent;
import frsf.ia.grupo9.log.Busquedas;
import frsf.ia.grupo9.log.RenderImage;

import java.awt.event.KeyAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VisorFrame extends JFrame {

	private Busquedas busq;
	RenderImage ri;
	private JArbolPanel arbolPane;
	private JButton btnInicio;
	private JButton btnAnterior;
	private JButton btnSiguiente;
	private JButton btnFinal;
	private JMenuItem mntmBsquedaCostoUniforme;
	private JPanel visorJP;

	private int indexBusq1 = 0;
	private int indexBusq2 = 0;
	private boolean busq1 = true;
	private JLabel lblPercepcin;
	private JTextField txtPercepcion;
	private JLabel lblAccin;
	private JTextField txtEstadoagente;
	private JTextField txtEstadoRealAmbiente;
	private JTextField txtAccin;
	private JMenuItem mntmBusquedaEnAmplitud;
	private JMenuItem mntmCostoUniforme;
	private JMenuItem mntmPrimeroEnAnchura;

	public VisorFrame() {
		super();
		initialize();
		busq = new Busquedas();
		ri = new RenderImage();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		this.setTitle("VisorFrame");
		this.setBounds(100, 100, 800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		// frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
		this.getContentPane().setLayout(new BorderLayout(0, 0));

		JPanel botoneraJP = new JPanel();
		botoneraJP.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"),
				"Secuencia Acci\u00F3n-Percepci\u00F3n", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(0, 0, 0)));
		botoneraJP.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		btnInicio = new JButton("Inicio");

		btnInicio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnInicio.setEnabled(false);
				btnAnterior.setEnabled(false);
				btnSiguiente.setEnabled(true);
				btnFinal.setEnabled(true);
				if (busq1) {
					indexBusq1 = 0;
					txtAccin.setText(busq.getAcciones(1).get(indexBusq1));
					txtPercepcion.setText(busq.getPercepciones(1).get(indexBusq1));
					txtEstadoagente
							.setText(busq.getAgImp(1).get(indexBusq1));
					txtEstadoRealAmbiente.setText(busq.getWorld(1).get(indexBusq1));
					((TitledBorder) visorJP.getBorder()).setTitle("Estado "
							+ (indexBusq1 + 1) + "/" + busq.getAgImp(1).size());
				} else {
					indexBusq2 = 0;
					txtAccin.setText(busq.getAcciones(2).get(indexBusq2));
					txtPercepcion.setText(busq.getPercepciones(2).get(indexBusq2));
					txtEstadoagente
							.setText(busq.getAgImp(2).get(indexBusq2));
					txtEstadoRealAmbiente.setText(busq.getWorld(2).get(indexBusq2));
					((TitledBorder) visorJP.getBorder()).setTitle("Estado "
							+ (indexBusq2 + 1) + "/" + busq.getAgImp(2).size());
				}
			}
		});
		btnInicio.setEnabled(false);
		botoneraJP.add(btnInicio);

		btnAnterior = new JButton("Anterior");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (busq1) {
					if (indexBusq1 > 0) {
						if (indexBusq1 == busq.getAgImp(1).size() - 1) {
							btnSiguiente.setEnabled(true);
							btnFinal.setEnabled(true);
						}
						indexBusq1--;
						txtAccin.setText(busq.getAcciones(1).get(indexBusq1));
						txtPercepcion.setText(busq.getPercepciones(1).get(indexBusq1));
						txtEstadoagente.setText(busq.getAgImp(1).get(
								indexBusq1));
						txtEstadoRealAmbiente.setText(busq.getWorld(1)
								.get(indexBusq1));
						if (indexBusq1 == 0) {
							btnAnterior.setEnabled(false);
							btnInicio.setEnabled(false);
						}
					}
				} else {
					if (indexBusq2 > 0) {
						if (indexBusq2 == busq.getAgImp(2).size() - 1) {
							btnSiguiente.setEnabled(true);
							btnFinal.setEnabled(true);
						}
						indexBusq2--;
						txtAccin.setText(busq.getAcciones(2).get(indexBusq2));
						txtPercepcion.setText(busq.getAcciones(2).get(indexBusq2));
						txtEstadoagente.setText(busq.getAgImp(2).get(
								indexBusq2));
						txtEstadoRealAmbiente.setText(busq.getWorld(2)
								.get(indexBusq2));
						if (indexBusq2 == 0) {
							btnAnterior.setEnabled(false);
							btnInicio.setEnabled(false);
						}
					}

				}

			}
		});
		btnAnterior.setEnabled(false);
		btnAnterior.setAlignmentX(Component.RIGHT_ALIGNMENT);
		botoneraJP.add(btnAnterior);

		btnSiguiente = new JButton("Siguiente");
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (busq1) {
					if (indexBusq1 < busq.getAgImp(1).size() - 1) {
						if (indexBusq1 == 0) {
							btnAnterior.setEnabled(true);
							btnInicio.setEnabled(true);
						}
						indexBusq1++;
						if((busq.getAcciones(1).size() == busq.getAgImp(1).size())
								||
								(indexBusq1 < busq.getAgImp(1).size() - 1))
							txtAccin.setText(busq.getAcciones(1).get(indexBusq1));
								
						
						txtPercepcion.setText(busq.getPercepciones(1).get(indexBusq1));
						txtEstadoagente.setText(busq.getAgImp(1).get(
								indexBusq1));
						txtEstadoRealAmbiente.setText(busq.getWorld(1)
								.get(indexBusq1));
						if (indexBusq1 == busq.getAgImp(1).size() - 1) {
							btnSiguiente.setEnabled(false);
							btnFinal.setEnabled(false);
						}
					}
				} else {
					if (indexBusq2 < busq.getAgImp(2).size() - 1) {
						if (indexBusq2 == 0) {
							btnAnterior.setEnabled(true);
							btnInicio.setEnabled(true);
						}
						indexBusq2++;
						txtAccin.setText(busq.getAcciones(2).get(indexBusq2));
						txtPercepcion.setText(busq.getPercepciones(2).get(indexBusq2));
						txtEstadoagente.setText(busq.getAgImp(2).get(
								indexBusq2));
						txtEstadoRealAmbiente.setText(busq.getWorld(2)
								.get(indexBusq2));
						if (indexBusq2 == busq.getAgImp(2).size() - 1) {
							btnSiguiente.setEnabled(false);
							btnFinal.setEnabled(false);
						}
					}
				}
			}
		});
		btnSiguiente.setEnabled(false);
		btnSiguiente.setHorizontalAlignment(SwingConstants.RIGHT);
		botoneraJP.add(btnSiguiente);

		btnFinal = new JButton("Final");
		btnFinal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnInicio.setEnabled(true);
				btnAnterior.setEnabled(true);
				btnSiguiente.setEnabled(false);
				btnFinal.setEnabled(false);
				if (busq1) {
					indexBusq1 = busq.getAgImp(1).size() - 1;
					if(busq.getAcciones(1).size() == busq.getAgImp(1).size())
						txtAccin.setText(busq.getAcciones(1).get(indexBusq1));
					else
						txtAccin.setText(busq.getAcciones(1).get(indexBusq1-1));
					
					txtPercepcion.setText(busq.getPercepciones(1).get(indexBusq1));
					txtEstadoagente
							.setText(busq.getAgImp(1).get(indexBusq1));
					txtEstadoRealAmbiente.setText(busq.getWorld(1).get(indexBusq1));
				} else {
					indexBusq2 = busq.getAgImp(2).size() - 1;
					txtAccin.setText(busq.getAcciones(2).get(indexBusq2-1));
					txtPercepcion.setText(busq.getPercepciones(2).get(indexBusq2));
					txtEstadoagente
							.setText(busq.getAgImp(2).get(indexBusq2));
					txtEstadoRealAmbiente.setText(busq.getWorld(2).get(indexBusq2));
				}
			}
		});
		btnFinal.setEnabled(false);
		botoneraJP.add(btnFinal);
		this.getContentPane().add(botoneraJP, BorderLayout.SOUTH);

		visorJP = new JPanel();
		this.getContentPane().add(visorJP, BorderLayout.CENTER);
		visorJP.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.GROWING_BUTTON_COLSPEC,},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(55dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.MIN_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(54dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("max(167dlu;default)"),
				FormFactory.RELATED_GAP_ROWSPEC,}));
		
				JLabel lblEstadoRealDel = new JLabel("Estado Real del Ambiente:");
				lblEstadoRealDel.setHorizontalAlignment(SwingConstants.CENTER);
				visorJP.add(lblEstadoRealDel, "2, 2");
		
		txtEstadoRealAmbiente = new JTextField();
		txtEstadoRealAmbiente.setEditable(false);
		txtEstadoRealAmbiente.setHorizontalAlignment(SwingConstants.CENTER);
		visorJP.add(txtEstadoRealAmbiente, "2, 4, fill, fill");
		txtEstadoRealAmbiente.setColumns(10);
		
		lblPercepcin = new JLabel("Percepci\u00F3n:");
		lblPercepcin.setHorizontalAlignment(SwingConstants.CENTER);
		visorJP.add(lblPercepcin, "2, 6");
		
		txtPercepcion = new JTextField();
		txtPercepcion.setEditable(false);
		txtPercepcion.setHorizontalAlignment(SwingConstants.CENTER);
		visorJP.add(txtPercepcion, "2, 8, fill, default");
		txtPercepcion.setColumns(10);

		JLabel lblEstadoInternoDel = new JLabel("Estado Interno del Agente:");
		lblEstadoInternoDel.setHorizontalAlignment(SwingConstants.CENTER);
		visorJP.add(lblEstadoInternoDel, "2, 9");
		
		
		txtEstadoagente = new JTextField();
		txtEstadoagente.setEditable(false);
		txtEstadoagente.setHorizontalAlignment(SwingConstants.CENTER);
		visorJP.add(txtEstadoagente, "2, 11, fill, fill");
		txtEstadoagente.setColumns(10);
		
		lblAccin = new JLabel("Acci\u00F3n:");
		lblAccin.setHorizontalAlignment(SwingConstants.CENTER);
		visorJP.add(lblAccin, "2, 13");
		
		txtAccin = new JTextField();
		txtAccin.setEditable(false);
		txtAccin.setHorizontalAlignment(SwingConstants.CENTER);
		visorJP.add(txtAccin, "2, 15, fill, default");
		txtAccin.setColumns(10);
		
				JLabel lblArbolDeBsqueda = new JLabel("Arbol de B\u00FAsqueda");
				lblArbolDeBsqueda.setHorizontalAlignment(SwingConstants.CENTER);
				visorJP.add(lblArbolDeBsqueda, "2, 17");

		arbolPane = new JArbolPanel();
		// arbolPane.setBackground(UIManager.getColor("Panel.background"));
		visorJP.add(arbolPane, "2, 19, fill, fill");

		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);

		JMenu mnBsqueda = new JMenu("B\u00FAsqueda");
		menuBar.add(mnBsqueda);

		mntmBsquedaCostoUniforme = new JMenuItem("B\u00FAsqueda Costo Uniforme");
		mntmBsquedaCostoUniforme.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				busq1 = true;
				indexBusq1 = 0;
				btnInicio.setEnabled(true);
				btnAnterior.setEnabled(true);
				btnSiguiente.setEnabled(true);
				btnFinal.setEnabled(true);
				
				busq.realizarBusqueda(1);

				txtAccin.setText(busq.getAcciones(1).get(indexBusq1));
				txtEstadoagente.setText(busq.getAgImp(1).get(indexBusq1));
				txtEstadoRealAmbiente.setText(busq.getWorld(1).get(indexBusq1));
				txtPercepcion.setText(busq.getPercepciones(1).get(indexBusq1));
				if(busq.getResultado().equals("El agente ha cumplido su Meta!")){
					ri.dotToJPG("./searchGVTrees\\0.dot", "./searchGVTrees\\Arbol.jpg");
					arbolPane.setImagen("./searchGVTrees\\Arbol.jpg");
				}
				
				JOptionPane.showMessageDialog(visorJP,busq.getResultado());
			}
		});
		mnBsqueda.add(mntmBsquedaCostoUniforme);
		
		mntmBusquedaEnAmplitud = new JMenuItem("Busqueda En Amplitud");
		mntmBusquedaEnAmplitud.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				busq1 = false;
				indexBusq2 = 0;
				busq.realizarBusqueda(2);
				btnInicio.setEnabled(false);
				btnAnterior.setEnabled(false);
				btnSiguiente.setEnabled(true);
				btnFinal.setEnabled(true);

				txtAccin.setText(busq.getAcciones(2).get(indexBusq2));
				txtEstadoagente.setText(busq.getAgImp(2).get(indexBusq2));
				txtEstadoRealAmbiente.setText(busq.getWorld(2).get(indexBusq2));
				txtPercepcion.setText(busq.getPercepciones(2).get(indexBusq2));
				if(busq.getResultado().equals("El agente ha cumplido su Meta!")){
					ri.dotToJPG("./searchGVTrees\\0.dot", "./searchGVTrees\\Arbol.jpg");
					arbolPane.setImagen("./searchGVTrees\\Arbol.jpg");
				}
				JOptionPane.showMessageDialog(visorJP,busq.getResultado());
			}
		});
		mnBsqueda.add(mntmBusquedaEnAmplitud);
	}


	public void cargarArbol(String path) {
		arbolPane.setImagen(path);
	}
	
	public void cargarArbol(BufferedImage image){
		arbolPane.setImagen(image);
	}

}
