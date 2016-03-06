package frsf.ia.grupo9.search.ventas.actions;

import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.Constantes;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;


public class ActualizarPronostico extends SearchAction{
	/**
	 * @uml.property  name="postcond"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso postcond; 
	/**
	 * @uml.property  name="tiempoAct"
	 */
	private float tiempoAct;
 
	
	public ActualizarPronostico(){
		postcond = new EstadoProceso(Constantes.ACTUALIZAR_PRONOSTICO, Constantes.UPDATED_DEMAND_FORECAST, 2, 4);
		tiempoAct = postcond.tiempoAleatorio();
	}
    
    /**
     * This method is not important for a search based agent, but is essensial
     * when creating a calculus based one.
     */
    @Override
    public String toString() {
        return "ActualizarPronostico";
    }



	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {
		VentasAgentState state = (VentasAgentState) s;
		if (Constantes.EVALUAR_PRONOSTICO_DEMANDA.equals(state.getEstadoProceso().getActividad())
				&& Constantes.DEMAND_FORECAST_RESPONSE_NO.equals(state.getEstadoProceso().getResultado())){ 
			VentasAgentState nuevoEstado = (VentasAgentState) state.clone();
			nuevoEstado.setEstadoProceso(postcond);
			float tiempoAcumulado = state.getTiempoAcumulado();
			tiempoAcumulado += tiempoAct;
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
		VentasAgentState estadoAgente = (VentasAgentState) ast;
		estadoActual.setNuevoEstado(postcond);
		estadoActual.setTiempoUltimaActividad(tiempoAct);
		estadoActual.setCiclosNegociacion(estadoAgente.getCiclosNegociacion());
		return estadoActual;
	}


}