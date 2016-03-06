package frsf.ia.grupo9.search.ventas.actions;

import static frsf.ia.grupo9.search.ventas.Constantes.GENERAR_SOLICITUD_PRONOSTICO_DEMANDA;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.Constantes;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;


public class EnviarMensajeForecastRequest extends SearchAction {
	
	/**
	 * @uml.property  name="postcondConveniente"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso postcondConveniente = new EstadoProceso(
			Constantes.ENVIAR_FORECAST_REQUEST, 
			Constantes.AGREE_FORECAST_REQUEST_RESPONSE, 2, 3);
    
	/**
	 * @uml.property  name="postcondNoConveniente"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso postcondNoConveniente = new EstadoProceso(
			Constantes.ENVIAR_FORECAST_REQUEST, 
			Constantes.REFUSE_FORECAST_REQUEST_RESPONSE, 2, 4);
	
    /**
	 * @uml.property  name="tiempoActConv"
	 */
    private float tiempoActConv=postcondConveniente.tiempoAleatorio();
    /**
	 * @uml.property  name="tiempoActNoConv"
	 */
    private float tiempoActNoConv=postcondNoConveniente.tiempoAleatorio();
    
	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {
		VentasAgentState state = (VentasAgentState) s;
		if (GENERAR_SOLICITUD_PRONOSTICO_DEMANDA.equals(state.getEstadoProceso().getActividad())){ 
			VentasAgentState nuevoEstado = (VentasAgentState) state.clone();
			nuevoEstado.setEstadoProceso(postcondConveniente);
			float tiempoAcumulado = state.getTiempoAcumulado();
			tiempoAcumulado += tiempoActConv;
			nuevoEstado.setTiempoAcumulado(tiempoAcumulado);
			return nuevoEstado;
		}
		return null;
	}

	@Override
	public EnvironmentState execute(AgentState ast, EnvironmentState est) {
		VentasEnvironmentState estadoActual = (VentasEnvironmentState) est;
		VentasAgentState estadoActualAgente = (VentasAgentState) ast;
		if (EstadoProceso.RND.nextInt(2) == 0) { 
			estadoActual.setNuevoEstado(postcondNoConveniente);
			estadoActual.setTiempoUltimaActividad(tiempoActNoConv);
		} else {
			estadoActual.setNuevoEstado(postcondConveniente);
			estadoActual.setTiempoUltimaActividad(tiempoActConv);
		}
		estadoActual.setTiempoEnvioForecastRequest(
				estadoActualAgente.getTiempoAcumulado() + 
				estadoActual.getTiempoUltimaActividad());
		return estadoActual;
	}

	/**
     * This method returns the action cost.
     */
    @Override
    public Double getCost() {
        return new Double(postcondConveniente.tiempoPromedio());
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