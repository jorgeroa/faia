package frsf.ia.grupo9.search.ventas.actions;

import static frsf.ia.grupo9.search.ventas.Constantes.ENVIAR_CANCELACION_DEMANDA_FORECAST;
import static frsf.ia.grupo9.search.ventas.Constantes.HS_DIA;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;


public class EnviarMensajeCancelDemandForecast extends SearchAction {
	
	/**
	 * @uml.property  name="postcond"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso postcond = new EstadoProceso(ENVIAR_CANCELACION_DEMANDA_FORECAST);
	/**
	 * @uml.property  name="tiempoAct"
	 */
	private float tiempoAct=postcond.tiempoAleatorio();
	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {
		VentasAgentState state = (VentasAgentState) s;
		// Si pasan mas de 5 dias desde el envio del ForecastRequest, cancelar
		if ((state.getTiempoEnvioForecastRequest()) > 0 &&
				(state.getTiempoAcumulado() - state.getTiempoEnvioForecastRequest() > 
					HS_DIA * 5)) {
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
	public EnvironmentState execute(AgentState ast, EnvironmentState est) {
		VentasEnvironmentState estadoActual = (VentasEnvironmentState) est;
		estadoActual.setNuevoEstado(postcond);
		estadoActual.setTiempoUltimaActividad(tiempoAct);
		return estadoActual;
	}

	/**
     * This method returns the action cost.
     */
    @Override
    public Double getCost() {
        return new Double(postcond.tiempoPromedio());
    }

    /**
     * This method is not important for a search based agent, but is essensial
     * when creating a calculus based one.
     */
    @Override
    public String toString() {
        return "EnviarMensajeForecastRequest";
    }

}