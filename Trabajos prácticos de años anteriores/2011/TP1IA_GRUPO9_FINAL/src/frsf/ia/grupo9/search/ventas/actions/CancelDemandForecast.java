package frsf.ia.grupo9.search.ventas.actions;

import static frsf.ia.grupo9.search.ventas.Constantes.*;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;

public class CancelDemandForecast extends SearchAction {

	/**
	 * @uml.property  name="postcond"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso postcond = new EstadoProceso(CANCELAR_DEMAND_FORECAST);
	
	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {
		VentasAgentState state = (VentasAgentState) s;
		// Si pasan mas de 7 dias desde el envio del ForecastRequest, cancelar
		if ((state.getTiempoEnvioInform() > 0) &&
				(state.getTiempoAcumulado() - state.getTiempoEnvioInform() > HS_DIA * 7)) {
			VentasAgentState nuevoEstado = (VentasAgentState) state.clone();
			nuevoEstado.setEstadoProceso(postcond);
			float tiempoAcumulado = state.getTiempoAcumulado();
			tiempoAcumulado += postcond.tiempoActividad();
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
		return estadoActual;
	}

	@Override
	public String toString() {
		return "CancelDemandForecast";
	}

}
