package frsf.ia.grupo9.search.ventas;

import static frsf.ia.grupo9.search.ventas.Constantes.*;
import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;
import frsf.cidisi.faia.state.AgentState;

/**
 * Clase que representa el ambiente
 */
public class VentasEnvironment extends Environment {

    public VentasEnvironment() {
        this.environmentState = new VentasEnvironmentState();
    }

	@Override
	public Perception getPercept() {
        VentasEnvironmentState state = (VentasEnvironmentState) getEnvironmentState();
		VentasAgentPerception perception = new VentasAgentPerception();
        perception.setActividad(state.getEstadoActual().getActividad());
        perception.setResultado(state.getEstadoActual().getResultado());
        perception.setTiempoUltimaActividad(state.getTiempoUltimaActividad());
        perception.setCiclosNegociacion(state.getCiclosNegociacion());
        return perception;
	}

	@Override
	public void updateState(AgentState ast, Action action) {
		super.updateState(ast, action);
	}

	@Override
	public boolean agentFailed(Action actionReturned) {
		VentasEnvironmentState state = (VentasEnvironmentState)getEnvironmentState();
		EstadoProceso estadoFalla = new EstadoProceso(PROCESO_FINALIZADO_FRACASO,FAIL);
		return estadoFalla.equals(state.getEstadoActual());
	}

	@Override
	public String toString() {
		return getEnvironmentState() != null ? getEnvironmentState().toString() : 
			"Estado inicial";
	}

}
