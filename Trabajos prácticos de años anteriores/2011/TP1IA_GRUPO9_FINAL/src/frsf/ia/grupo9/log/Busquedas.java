package frsf.ia.grupo9.log;

import java.util.ArrayList;
import java.util.List;

import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;
import frsf.cidisi.faia.simulator.SearchBasedAgentSimulator;
import frsf.ia.grupo9.search.ventas.VentasAgent;
import frsf.ia.grupo9.search.ventas.VentasAgent2;
import frsf.ia.grupo9.search.ventas.VentasEnvironment;
import frsf.ia.grupo9.search.ventas.simulator.SearchBasedVentasAgentSimulator;

public class Busquedas {
	
	protected List<String> agImp1;
	protected List<String> world1;
	protected List<String> acciones1;
	protected List<String> percepciones1;
	
	protected List<String> agImp2;
	protected List<String> world2;
	protected List<String> acciones2;
	protected List<String> percepciones2;
	
	protected String resultado;
	
	public Busquedas(){
		
		agImp1 = agImp2 = new ArrayList<String>();
		world1 = world2 = new ArrayList<String>();
		acciones1 = acciones2 = new ArrayList<String>();
		percepciones1 = percepciones2 = new ArrayList<String>();
		resultado = new String();
//		busqueda1();
//		busqueda2();
	}
	
	public void realizarBusqueda(int nroBusqueda){
		if(nroBusqueda == 1)
			busqueda1();
		else
			busqueda2();
	}
	
	/**
	 * BUSQUEDAS COSTO DE UNIFORME;
	 */
	
	private void busqueda1(){
		VentasAgent ventasAgent = new VentasAgent();
        VentasEnvironment ventasEnvironment = new VentasEnvironment();
        SearchBasedVentasAgentSimulator simulator =
                new SearchBasedVentasAgentSimulator(ventasEnvironment, ventasAgent);
        simulator.start();
        
        agImp1 = simulator.getAgImp();
        world1 = simulator.getWorld();
        acciones1 = simulator.getAcciones();
        percepciones1 = simulator.getPercepciones();
        resultado = simulator.getResultado();
	}
	
	/**
	 * BUSQUEDAS PRIMERO EN AMPLITUD;
	 */
	
	private void busqueda2(){
		VentasAgent2 ventasAgent = new VentasAgent2();
        VentasEnvironment ventasEnvironment = new VentasEnvironment();
        SearchBasedVentasAgentSimulator simulator =
                new SearchBasedVentasAgentSimulator(ventasEnvironment, ventasAgent);
        simulator.start();
        
        agImp2 = simulator.getAgImp();
        world2 = simulator.getWorld();
        acciones2 = simulator.getAcciones();
        percepciones2 = simulator.getPercepciones();
        resultado = simulator.getResultado();
	}


	public List<String> getAgImp(int nroBusq) {
		if(nroBusq == 1)
			return agImp1;
		else
			return agImp2;
	}


	public List<String> getWorld(int nroBusq) {
		if(nroBusq == 1)
			return world1;
		else
			return world2;
	}


	public List<String> getAcciones(int nroBusq) {
		if(nroBusq == 1)
			return acciones1;
		else
			return acciones2;
	}


	public List<String> getPercepciones(int nroBusq) {
		if(nroBusq == 1)
			return percepciones1;
		else
			return percepciones2;
	}
	
	public String getResultado(){
		return resultado;
	}
}
