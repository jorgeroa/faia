package frsf.ia.grupo9.search.ventas.actions;

import static frsf.ia.grupo9.search.ventas.Constantes.ESTADO_INICIAL;

import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.Constantes;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;

public class GenerarSolicitudPronosticoDemanda extends SearchAction {

	/**
	 * @uml.property  name="postcond"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso postcond = new EstadoProceso(
			Constantes.GENERAR_SOLICITUD_PRONOSTICO_DEMANDA, 
			Constantes.FORECAST_REQUEST, 10, 15);
	/**
	 * @uml.property  name="tiempoAct"
	 */
	private float tiempoAct=postcond.tiempoAleatorio();

	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {
		VentasAgentState state = (VentasAgentState) s;
		if (ESTADO_INICIAL.equals(state.getEstadoProceso().getActividad())) {
			VentasAgentState nuevoEstado = (VentasAgentState) state.clone();
			nuevoEstado.setEstadoProceso(postcond);
			float tiempoAcumulado = state.getTiempoAcumulado();
			tiempoAcumulado += this.tiempoAct;
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
    
    /**
     * This method is not important for a search based agent, but is essensial
     * when creating a calculus based one.
     */
    @Override
    public String toString() {
        return "GenerarSolicitudPronosticoDemanda";
    }

}