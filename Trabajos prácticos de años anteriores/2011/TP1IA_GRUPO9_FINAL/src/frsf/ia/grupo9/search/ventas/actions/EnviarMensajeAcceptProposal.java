package frsf.ia.grupo9.search.ventas.actions;

import static frsf.ia.grupo9.search.ventas.Constantes.*;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;

public class EnviarMensajeAcceptProposal extends SearchAction {
    
    /**
	 * @uml.property  name="postCond"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private EstadoProceso postCond = new EstadoProceso(ENVIAR_ACEPTACION_PROPUESTA, SUCCESS, 1,3);
    /**
	 * @uml.property  name="tiempoAct"
	 */
    private float tiempoAct=postCond.tiempoAleatorio();
    /**
     * This method is not important for a search based agent, but is essensial
     * when creating a calculus based one.
     */
    @Override
    public String toString() {
        return "EnviarMensajeAcceptProposal";
    }

	@Override
	public Double getCost() {
		return new Double(postCond.tiempoPromedio());
	}

	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {
		VentasAgentState state = (VentasAgentState) s;
		if((EVALUAR_PRONOSTICO_DEMANDA.equals(state.getEstadoProceso().getActividad())) &&
				(DEMAND_FORECAST_RESPONSE_YES.equals(state.getEstadoProceso().getResultado()))){
			VentasAgentState nuevoEstado = (VentasAgentState) state.clone();
			nuevoEstado.setEstadoProceso(postCond);
			float tiempoAcumulado = state.getTiempoAcumulado();
			tiempoAcumulado += tiempoAct;
			nuevoEstado.setTiempoAcumulado(tiempoAcumulado);
			nuevoEstado.setCiclosNegociacion(state.getCiclosNegociacion());
			return nuevoEstado;
		}
		return null;
	}

	@Override
	public EnvironmentState execute(AgentState ast, EnvironmentState est) {
		VentasEnvironmentState estadoActual = (VentasEnvironmentState) est;
		VentasAgentState estadoAgente = (VentasAgentState) ast;
		estadoActual.setEstadoActual(postCond);
		estadoActual.setTiempoUltimaActividad(tiempoAct);
		estadoActual.setCiclosNegociacion(estadoAgente.getCiclosNegociacion());
		return estadoActual;
	}
	
}