package frsf.ia.grupo9.search.ventas.actions;

/**
 * IMPORTANTE PROGRAMAR ENVIO DE MAS DE UN POSFORECAST
 */

import static frsf.ia.grupo9.search.ventas.Constantes.ENVIAR_INFORME_POSFORECAST;
import static frsf.ia.grupo9.search.ventas.Constantes.GENERAR_PRONOSTICO_VENTAS_POS;
import static frsf.ia.grupo9.search.ventas.Constantes.POS_FORECAST;
import static frsf.ia.grupo9.search.ventas.Constantes.PROPOSE_DEMAND_FORECAST_1;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;

public class EnviarMensajeInformPOSForecast extends SearchAction {
    
    /**
	 * @uml.property  name="postCond"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private EstadoProceso postCond = new EstadoProceso(ENVIAR_INFORME_POSFORECAST, PROPOSE_DEMAND_FORECAST_1, 1, 2);
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
        return "EnviarMensajeInformPOSForecast";
    }

	@Override
	public Double getCost() {
		return new Double(postCond.tiempoPromedio());
	}

	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {
		VentasAgentState state = (VentasAgentState) s;
		if(GENERAR_PRONOSTICO_VENTAS_POS.equals(state.getEstadoProceso().getActividad()) &&
				POS_FORECAST.equals(state.getEstadoProceso().getResultado())){
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