package frsf.ia.grupo9.search.ventas;

import static frsf.ia.grupo9.search.ventas.Constantes.*;
import frsf.cidisi.faia.agent.search.GoalTest;
import frsf.cidisi.faia.state.AgentState;

/**
 * Clase que representa la condicion de exito (objetivo del agente) y finalizacion de simulacion.
 */
public class VentasGoal extends GoalTest {

	/**
	 * @uml.property  name="precond1"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso precond1 =  new EstadoProceso(PROCESO_FINALIZADO_EXITO,SUCCESS);
	/**
	 * @uml.property  name="precond2"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso precond2 =  new EstadoProceso(PROCESO_FINALIZADO_FRACASO,FAIL);
	
	@Override
	public boolean isGoalState(AgentState agentState) {
		VentasAgentState ventasAgentState = (VentasAgentState) agentState;
		return (precond1.equals(ventasAgentState // CONDICION DE EXITO
				.getEstadoProceso()) ||
				precond2.equals(ventasAgentState.getEstadoProceso())); // SE AGREGÓ PARA CORTAR LA SIMULACIÓN, 
																	   // YA QUE SINO, NO MUESTRA EL RECORRIDO FALLIDO.
	}                                                                  // ES FALLA YA QUE SE ENCUENTRA DEFINIDA DENTRO
	                                                                   // DEL METODO agentFailed(action) DE VentasEnvironment.java

}
