package frsf.ia.grupo9.search.ventas.actions;

import static frsf.ia.grupo9.search.ventas.Constantes.*;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;

public class FinConExito extends SearchAction {

	/**
	 * @uml.property  name="precond"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso precond;
	/**
	 * @uml.property  name="postcond"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso postcond;
	
    public FinConExito() {
    	precond = new EstadoProceso(ENVIAR_ACEPTACION_PROPUESTA,SUCCESS);
    	postcond = new EstadoProceso(PROCESO_FINALIZADO_EXITO,SUCCESS);
	}

	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {
		VentasAgentState state = (VentasAgentState) s;
		if (precond.equals(state.getEstadoProceso())){ 
				VentasAgentState nuevoEstado = (VentasAgentState) state.clone();
				nuevoEstado.setEstadoProceso(postcond);
				float tiempoAcumulado = state.getTiempoAcumulado();
				tiempoAcumulado += postcond.tiempoActividad();
				nuevoEstado.setTiempoAcumulado(tiempoAcumulado);
				nuevoEstado.setCiclosNegociacion(state.getCiclosNegociacion());
				return nuevoEstado;
		}
		return null;
	}


	@Override
	public Double getCost() {
		return new Double(postcond.tiempoPromedio());
	}


	@Override
	public EnvironmentState execute(AgentState ast, EnvironmentState est) {
		VentasEnvironmentState estadoActual = (VentasEnvironmentState) est;
		estadoActual.setNuevoEstado(postcond);
		return estadoActual;
	}
    
    /**
     * This method is not important for a search based agent, but is essensial
     * when creating a calculus based one.
     */
    @Override
    public String toString() {
        return "FinConExito";
    }

}
