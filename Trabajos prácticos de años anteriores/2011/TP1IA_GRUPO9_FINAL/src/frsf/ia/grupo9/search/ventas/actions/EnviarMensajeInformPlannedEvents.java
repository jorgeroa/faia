package frsf.ia.grupo9.search.ventas.actions;

import static frsf.ia.grupo9.search.ventas.Constantes.ENVIAR_PLAN_EVENTOS_PROGRAMADOS;
import static frsf.ia.grupo9.search.ventas.Constantes.GENERAR_PLAN_EVENTOS_PROGRAMADOS;
import static frsf.ia.grupo9.search.ventas.Constantes.PLANNED_EVENTS;
import static frsf.ia.grupo9.search.ventas.Constantes.PROPOSE_DEMAND_FORECAST_1;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;

public class EnviarMensajeInformPlannedEvents extends SearchAction {
    
    /**
	 * @uml.property  name="postCond"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private EstadoProceso postCond = new EstadoProceso(ENVIAR_PLAN_EVENTOS_PROGRAMADOS, PROPOSE_DEMAND_FORECAST_1, 2, 4);
    /**
	 * @uml.property  name="tiempoAct"
	 */
    private float tiempoAct=postCond.tiempoAleatorio();
    /**
     * This method returns the action cost.
     */
    @Override
    public Double getCost() {
        return new Double(postCond.tiempoPromedio());
    }
    @Override
    public String toString() {
        return "EnviarMensajeInformPlannedEvents";
    }
	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {
		VentasAgentState state = (VentasAgentState) s;
		
		if(GENERAR_PLAN_EVENTOS_PROGRAMADOS.equals(state.getEstadoProceso().getActividad()) &&
				PLANNED_EVENTS.equals(state.getEstadoProceso().getResultado())){
			VentasAgentState nuevoEstado = (VentasAgentState) state.clone();
			nuevoEstado.setEstadoProceso(postCond);
			float tiempoAcumulado = state.getTiempoAcumulado();
			tiempoAcumulado += tiempoAct;
			nuevoEstado.setTiempoAcumulado(tiempoAcumulado);
			return nuevoEstado;
		}
		return null;
	}
	@Override
	public EnvironmentState execute(AgentState ast, EnvironmentState est) {
		VentasEnvironmentState estadoActual = (VentasEnvironmentState) est;
		estadoActual.setNuevoEstado(postCond);
		estadoActual.setTiempoUltimaActividad(tiempoAct);
		return estadoActual;
	}
}