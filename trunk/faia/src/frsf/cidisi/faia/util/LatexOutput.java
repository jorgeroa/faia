/*
 * Copyright 2007-2009 Georgina Stegmayer, Milagros Gutiérrez, Jorge Roa,
 * Luis Ignacio Larrateguy y Milton Pividori.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package frsf.cidisi.faia.util;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Vector;

import frsf.cidisi.faia.exceptions.LatexOutputException;
import frsf.cidisi.faia.simulator.SimulatorEventHandler;
import frsf.cidisi.faia.solver.search.NTree;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class LatexOutput implements SimulatorEventHandler {

    private static LatexOutput instance;
    private static final String lineSeparator = System.getProperty("line.separator");
    private final String faiaPdflatexDir = "../faia/pdflatex/";
    private final String pdflatexDir = "pdflatex/";
    private int fileIdx = 0;

    LatexOutput() {
    }

    public static LatexOutput getInstance() {
        if (instance == null) {
            instance = new LatexOutput();
        }
        return instance;
    }

    private void copyFiles(String faiaPdflatex) throws IOException {
        FileOperations.CopyFile(faiaPdflatex + "a0poster.cls", pdflatexDir + "a0poster.cls");
        FileOperations.CopyFile(faiaPdflatex + "a0size.sty", pdflatexDir + "a0size.sty");
        FileOperations.CopyFile(faiaPdflatex + "nodo.sty", pdflatexDir + "nodo.sty");
        FileOperations.CopyFile(faiaPdflatex + "qtree.sty", pdflatexDir + "qtree.sty");
    }

    public void compileLatexFiles(boolean removeTexFiles) throws LatexOutputException {
        // Copio los archivos necesarios para poder compilar con pdflatex
        // FIXME: Estoy suponiendo acá que FAIA se encuentra en la carpeta "..faia"
        try {
            this.copyFiles(this.faiaPdflatexDir);
        } catch (FileNotFoundException e1) {
            try {
                // Probamos a buscar un niver mas arriba
                this.copyFiles("../" + this.faiaPdflatexDir);
            } catch (IOException ex) {
            	throw new LatexOutputException("LaTeX files not found: "
                        + e1.getMessage());
            }
        } catch (Exception e2) {
        	throw new LatexOutputException("LaTeX files not found: "
                    + e2.getMessage());
        }

        Process p;
        String[] comando;

        // Creo el objeto que representa la carpeta pdfLatex
        File carpetaPdflatex = new File(pdflatexDir);

        System.out.println("Compilando archivos Latex...");
        
        // Por cada archivo .tex compilo a PDF
        for (File archivoTex : carpetaPdflatex.listFiles(new TexFilter())) {

            System.out.print("  " + archivoTex.getName());
            comando = new String[]{"pdflatex", "-quiet", "-halt-on-error", archivoTex.getName()};

            try {
                // Ejecuto el comando para la compilación
                p = Runtime.getRuntime().exec(comando, null, carpetaPdflatex);

                // Espero que termine de compilar y muestro el estado de la ejecución (si
                // fue exitoso o no)
                p.waitFor();
                if (p.exitValue() == 0) {
                    System.out.print(" -> Ok");
                } else {
                    System.out.print(" -> Error");
                }
            } catch (IOException e) {
            	throw new LatexOutputException("LaTeX/MiKTeX is not installed: " + e.getMessage());
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println();
        }

        System.out.println();
        System.out.println("Compilación finalizada.");

        // Ahora elimino los archivos temporales que creó pdflatex
        for (File archivoTemporal : carpetaPdflatex.listFiles(new TempFilesFilter())) {
            archivoTemporal.delete();        // Si se ha indicado borrar tmabién los archivos tex, los borro
        }
        for (File archivoTex : carpetaPdflatex.listFiles(new TexFilter())) {
            archivoTex.delete();
        }
        System.out.println("Archivos temporales eliminados.");
    }

    public void printFile(NTree tree) {
        printFile(tree, "ESTRATEGIA NO SETEADA");
    }

    public void printFile(NTree tree, String strategyName) {
        StringBuffer str = new StringBuffer();

        // Clase del documento y opciones generales
        str.append("\\documentclass[a0,landscale]{a0poster}" + lineSeparator);

        // Paquetes utilizados
        str.append("\\usepackage{mathptmx}" + lineSeparator);
        str.append("\\usepackage{qtree}" + lineSeparator);
        str.append("\\usepackage{nodo}" + lineSeparator);
        str.append("\\usepackage[spanish]{babel}" + lineSeparator);
        str.append("\\usepackage[utf8]{inputenc}" + lineSeparator);

        str.append("\\title{Árbol de ejecución - Ejecución Nro: " + fileIdx + " - Estrategia: " +
                strategyName + "}" + lineSeparator);
        str.append("\\author{}" + lineSeparator);
        str.append("\\begin{document}" + lineSeparator);
        str.append("\\maketitle" + lineSeparator);

        //StringBuffer sf = new StringBuffer();
        int cuentaArboles = 0;
        //int nivelesProcesados = 0;

        Vector<NTree> nodosAProcesar = new Vector<NTree>();
        nodosAProcesar.add(tree);
        nodosAProcesar.addAll(tree.getSonsTotal());

        for (int i = 0; i < nodosAProcesar.size(); i++) {
            NTree nodo = nodosAProcesar.elementAt(i);

            if (nodo.getSons().isEmpty()) {
                continue;
            }
            if (cuentaArboles == 0) {
                str.append("\\begin{figure}[!h]" + lineSeparator);
            }
            str.append("\\Tree " + nodo.toQtree() + lineSeparator);
            //str.append(tree.getSonsTotal().elementAt(i).toQtree() + "\n");
            cuentaArboles++;

            if (cuentaArboles == 4) {
                cuentaArboles = 0;
                str.append("\\end{figure}" + lineSeparator);
            }

        //nivelesProcesados++;

//			if (nivelesProcesados >= niveles)
//				break;
        }

        if (cuentaArboles > 0) {
            str.append("\\end{figure}" + lineSeparator);
        }
        str.append(lineSeparator);
        //Busqueda.logLatex.debug(sf.toString());		
        str.append("\\end{document}" + lineSeparator);

        // Ahora creo el archivo
        try {
            // Si la carpeta que necesito no existe, la creo.
            File f = new File(pdflatexDir);
            if (!f.exists()) {
                f.mkdir();
            }
            
            Writer out = new BufferedWriter(
                    new OutputStreamWriter(
                    new FileOutputStream(pdflatexDir + fileIdx + ".tex"),
                    "UTF-8"));
            
            out.write(str.toString());
            out.close();

            fileIdx++;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void simulationFinished() {
        try {
			this.compileLatexFiles(true);
		} catch (LatexOutputException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
