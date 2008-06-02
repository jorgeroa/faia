package frsf.cidisi.faia.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Vector;

import frsf.cidisi.faia.simulator.SimulatorEventHandler;
import frsf.cidisi.faia.solver.search.NTree;

public class LatexOutput implements SimulatorEventHandler {
	private final String faiaPdflatexDir = "../faia/pdflatex/";
	private final String pdflatexDir = "pdflatex/";
	
	private int fileIdx = 0;
	private static LatexOutput instance;
	
	LatexOutput() {
	}
	
	public static LatexOutput getInstance() {
		if (instance == null)
			instance = new LatexOutput();
		
		return instance;
	}
	
	public void compileLatexFiles(boolean removeTexFiles) {
		// Copio los archivos necesarios para poder compilar con pdflatex
		// FIXME: Estoy suponiendo acá que FAIA se encuentra en la carpeta "..faia"
		try {
			FileOperations.CopyFile("../faia/pdflatex/a0poster.cls", pdflatexDir + "a0poster.cls");
			FileOperations.CopyFile("../faia/pdflatex/a0size.sty", pdflatexDir + "a0size.sty");
			FileOperations.CopyFile("../faia/pdflatex/nodo.sty", pdflatexDir + "nodo.sty");
			FileOperations.CopyFile("../faia/pdflatex/qtree.sty", pdflatexDir + "qtree.sty");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		Process p;
		String[] comando;
		
		// Creo el objeto que representa la carpeta pdfLatex
		File carpetaPdflatex = new File(pdflatexDir);
		
		System.out.println("Compilando archivos Latex...");
		
		// Por cada archivo .tex compilo a PDF
		for (File archivoTex : carpetaPdflatex.listFiles(new TexFilter())) {
			
			System.out.print("  " + archivoTex.getName());
			comando = new String[] {"pdflatex", "-file-line-error", "-halt-on-error", archivoTex.getName()};
			
			try {
				// Ejecuto el comando para la compilación
				p = Runtime.getRuntime().exec(comando, null, carpetaPdflatex);
				
				// Espero que termine de compilar y muestro el estado de la ejecución (si
				// fue exitoso o no)
				p.waitFor();
				if (p.exitValue() == 0)
					System.out.print(" -> Ok");
				else
					System.out.print(" -> Error");
					
					
			} catch (Exception e) {
				System.out.print(" -> Error: excepción arrojada");
				e.printStackTrace();
			}
			
			System.out.println();
		}
		
		// Ahora elimino los archivos temporales que creó pdflatex
		for (File archivoTemporal : carpetaPdflatex.listFiles(new TempFilesFilter()))
			archivoTemporal.delete();
		
		// Si se ha indicado borrar tmabién los archivos tex, los borro
		for (File archivoTex : carpetaPdflatex.listFiles(new TexFilter()))
			archivoTex.delete();
	}
	
	public void printFile(NTree tree) {
		printFile(tree, "ESTRATEGIA NO SETEADA");
	}
	
	public void printFile(NTree tree, String strategyName) {
		StringBuffer str = new StringBuffer();
		
	    // Clase del documento y opciones generales
	    str.append("\\documentclass[a0,landscale]{a0poster}\n");
	   
	    // Paquetes utilizados
	    str.append("\\usepackage{mathptmx}\n");
	    str.append("\\usepackage{colortbl}\n");
	    str.append("\\usepackage[scaled=.90]{helvet}\n");
	    str.append("\\usepackage{courier}\n");
	    str.append("\\usepackage{qtree}\n");
	    str.append("\\usepackage{nodo}\n");
	    str.append("\\usepackage[spanish]{babel}\n");
	    str.append("\\usepackage[utf8]{inputenc}\n");
	   
	    str.append("\\title{Ejecución Nº " + fileIdx + " Árbol de ejecución - Estrategia: " +
	        strategyName + "}\n");
	    str.append("\\author{}\n");
	    str.append("\\begin{document}\n");
	    str.append("\\maketitle\n");
	    
	    //StringBuffer sf = new StringBuffer();
		int cuentaArboles = 0;
		//int nivelesProcesados = 0;
		
		Vector<NTree> nodosAProcesar = new Vector<NTree>();
		nodosAProcesar.add(tree);
		nodosAProcesar.addAll(tree.getSonsTotal());
		
		for (int i=0;i<nodosAProcesar.size();i++) {
			NTree nodo = nodosAProcesar.elementAt(i);
			
			if (nodo.getSons().isEmpty())
				continue;
			
			if (cuentaArboles == 0)
				str.append("\\begin{figure}[!h]\n");
			
			str.append("\\Tree " + nodo.toQtree() + "\n");
			//str.append(tree.getSonsTotal().elementAt(i).toQtree() + "\n");
			cuentaArboles++;

			if (cuentaArboles == 4) {
				cuentaArboles = 0;
				str.append("\\end{figure}\n");
			}
			
			//nivelesProcesados++;
			
//			if (nivelesProcesados >= niveles)
//				break;
		}
		
		if (cuentaArboles > 0)
			str.append("\\end{figure}");
		
		str.append("\n");
		//Busqueda.logLatex.debug(sf.toString());		
		str.append("\\end{document}");
		
		// Ahora creo el archivo
		try {
			// Si la carpeta que necesito no existe, la creo.
			File f = new File(pdflatexDir);
			if (!f.exists())
				f.mkdir();
			
			PrintOut print = new PrintOut(pdflatexDir + fileIdx + ".tex", false);
			print.write(str.toString());
			print.close();
			
			fileIdx++;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void simulationFinished() {
		this.compileLatexFiles(true);
	}
}

class TexFilter implements FilenameFilter {

	@Override
	public boolean accept(File arg0, String arg1) {
		return (arg1.endsWith(".tex"));
	}
	
}

class TempFilesFilter implements FilenameFilter {

	@Override
	public boolean accept(File arg0, String arg1) {
		return (arg1.endsWith(".aux") || arg1.endsWith(".log"));
	}
	
}