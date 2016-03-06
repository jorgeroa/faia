package frsf.ia.grupo9.search.ventas;

import frsf.cidisi.faia.solver.search.IStepCostFunction;
import frsf.cidisi.faia.solver.search.NTree;

/**
 * Clase que representa la funcion de costo. En este caso, se retorna el tiempo
 * total acumulado por el agente.
 */
public class CostFunction implements IStepCostFunction {

	@Override
	public double calculateCost(NTree node) {
		return ((VentasAgentState) node.getAgentState()).getEstadoProceso().tiempoPromedio();
	}

}
