package frsf.ia.grupo9.search.ventas.actions;

import static frsf.ia.grupo9.search.ventas.Constantes.ACTUALIZAR_PRONOSTICO;
import static frsf.ia.grupo9.search.ventas.Constantes.ENVIAR_PROPUESTA_PRONOSTICO_ACTUALIZADO;
import static frsf.ia.grupo9.search.ventas.Constantes.INFORM_ISSUES;
import static frsf.ia.grupo9.search.ventas.Constantes.PROPOSE_DEMAND_FORECAST_2;
import static frsf.ia.grupo9.search.ventas.Constantes.UPDATED_DEMAND_FORECAST;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.Constantes;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;

public class EnviarMensajeProposeUpdatedDemandForecast extends SearchAction {

    /**
	 * @uml.property  name="postCondAct"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private EstadoProceso postCondAct;
    /**
	 * @uml.property  name="postCondInf"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private EstadoProceso postCondInf;
    
    /**
	 * @uml.property  name="tiempoActAct"
	 */
    private float tiempoActAct;
    /**
	 * @uml.property  name="tiempoActInf"
	 */
    private float tiempoActInf;
    public EnviarMensajeProposeUpdatedDemandForecast(){
    	postCondAct = new EstadoProceso(ENVIAR_PROPUESTA_PRONOSTICO_ACTUALIZADO, PROPOSE_DEMAND_FORECAST_2, 1, 2);
        postCondInf = new EstadoProceso(ENVIAR_PROPUESTA_PRONOSTICO_ACTUALIZADO, INFORM_ISSUES, 1, 2);
        tiempoActAct = postCondAct.tiempoAleatorio();
        tiempoActInf = postCondInf.tiempoAleatorio();
    }
    /**
     * This method is not important for a search based agent, but is essensial
     * when creating a calculus based one.
     */
    @Override
    public String toString() {
        return "EnviarMensajeProposeUpdatedDemandForecast";
    }

	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {
		VentasAgentState state = (VentasAgentState) s;
		if((ACTUALIZAR_PRONOSTICO.equals(state.getEstadoProceso().getActividad()))&&
			(UPDATED_DEMAND_FORECAST.equals(state.getEstadoProceso().getResultado()))){
			VentasAgentState nuevoEstado = (VentasAgentState) state.clone();
			nuevoEstado.setCiclosNegociacion(state.getCiclosNegociacion() + 1);
			float tiempoActividad;
			if (state.getCiclosNegociacion() <= Constantes.CICLOS_NEGOCIACION){
				nuevoEstado.setEstadoProceso(postCondAct);
				tiempoActividad = tiempoActAct;
			} else {
				nuevoEstado.setEstadoProceso(postCondInf);
				tiempoActividad = tiempoActInf;
			}
			nuevoEstado.setTiempoAcumulado(nuevoEstado.getTiempoAcumulado() + tiempoActividad);
			nuevoEstado.setTiempoEnvioInform(0f);
			return nuevoEstado;
		}
		return null;
	}

	@Override
	public Double getCost() {
		return new Double(postCondAct.tiempoPromedio());
	}

	@Override
	public EnvironmentState execute(AgentState ast, EnvironmentState est) {
		VentasEnvironmentState estadoActual = (VentasEnvironmentState) est;
		estadoActual.setCiclosNegociacion(estadoActual.getCiclosNegociacion() + 1);
		if (estadoActual.getCiclosNegociacion() <= Constantes.CICLOS_NEGOCIACION) {
			estadoActual.setNuevoEstado(postCondAct);
			estadoActual.setTiempoUltimaActividad(tiempoActAct);
		} else {
			estadoActual.setNuevoEstado(postCondInf);
			estadoActual.setTiempoUltimaActividad(tiempoActInf);
		}
		estadoActual.setTiempoEnvioInform(0f);
		return estadoActual;
	}
}