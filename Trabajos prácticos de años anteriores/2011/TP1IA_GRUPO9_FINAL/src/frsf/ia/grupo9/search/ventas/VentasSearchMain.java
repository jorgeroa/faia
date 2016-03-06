package frsf.ia.grupo9.search.ventas;

import frsf.cidisi.faia.simulator.SearchBasedAgentSimulator;

/**
 * Clase principal de la aplicacion
 */
public class VentasSearchMain {
	
	public static void main(String[] args) {
        VentasAgent ventasAgent = new VentasAgent();
        VentasEnvironment ventasEnvironment = new VentasEnvironment();
        SearchBasedAgentSimulator simulator =
                new SearchBasedAgentSimulator(ventasEnvironment, ventasAgent);
        simulator.start();
	}
}
