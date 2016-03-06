package frsf.ia.grupo9.search.ventas.actions;

import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.Constantes;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;

public class GenerarPlanEventosProgramado extends SearchAction {
	/**
	 * @uml.property  name="postcond"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso postcond = new EstadoProceso(Constantes.GENERAR_PLAN_EVENTOS_PROGRAMADOS, Constantes.PLANNED_EVENTS, 30, 150);
	/**
	 * @uml.property  name="tiempoAct"
	 */
	private float tiempoAct=postcond.tiempoActividad();
    /**
     * This method is not important for a search based agent, but is essensial
     * when creating a calculus based one.
     */
    @Override
    public String toString() {
        return "GenerarPlanEventosProgramado";
    }

	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {
		VentasAgentState state = (VentasAgentState) s;
		if (Constantes.ENVIAR_FORECAST_REQUEST.equals(state.getEstadoProceso().getActividad())
				&& Constantes.AGREE_FORECAST_REQUEST_RESPONSE.equals(state.getEstadoProceso().getResultado())){ 
			VentasAgentState nuevoEstado = (VentasAgentState) state.clone();
			nuevoEstado.setEstadoProceso(postcond);
			float tiempoAcumulado = state.getTiempoAcumulado();
			tiempoAcumulado += tiempoAct;
			nuevoEstado.setTiempoAcumulado(tiempoAcumulado);
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
		estadoActual.setTiempoUltimaActividad(tiempoAct);
	return estadoActual;
	}

}