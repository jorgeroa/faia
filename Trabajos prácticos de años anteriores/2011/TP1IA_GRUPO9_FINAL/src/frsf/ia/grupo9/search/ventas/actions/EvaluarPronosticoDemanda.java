package frsf.ia.grupo9.search.ventas.actions;

import java.util.ArrayList;

import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgentState;
import frsf.cidisi.faia.state.AgentState;
import frsf.cidisi.faia.state.EnvironmentState;
import frsf.ia.grupo9.search.ventas.Constantes;
import frsf.ia.grupo9.search.ventas.EstadoProceso;
import frsf.ia.grupo9.search.ventas.VentasAgentState;
import frsf.ia.grupo9.search.ventas.VentasEnvironmentState;

public class EvaluarPronosticoDemanda extends SearchAction {
    
	/**
	 * @uml.property  name="preCond1"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso preCond1 = new EstadoProceso(Constantes.ENVIAR_INFORME_POSFORECAST, Constantes.PROPOSE_DEMAND_FORECAST_1);
	/**
	 * @uml.property  name="preCond2"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso preCond2 = new EstadoProceso(Constantes.ENVIAR_PLAN_EVENTOS_PROGRAMADOS, Constantes.PROPOSE_DEMAND_FORECAST_1);
	/**
	 * @uml.property  name="preCond3"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
	private EstadoProceso preCond3 = new EstadoProceso(Constantes.ENVIAR_PROPUESTA_PRONOSTICO_ACTUALIZADO, Constantes.PROPOSE_DEMAND_FORECAST_2);
	
    /**
	 * @uml.property  name="postCondSI"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private EstadoProceso postCondSI;
    /**
	 * @uml.property  name="postCondNO"
	 * @uml.associationEnd  multiplicity="(1 1)"
	 */
    private EstadoProceso postCondNO;
    /**
	 * @uml.property  name="tiempoActSI"
	 */
    private float tiempoActSI;
    /**
	 * @uml.property  name="tiempoActNO"
	 */
    private float tiempoActNO;
    private static final int ciclos = EstadoProceso.RND.nextInt(5);
    /**
	 * @uml.property  name="ciclosAg"
	 */
    private int ciclosAg = ciclos;
    /**
	 * @uml.property  name="ciclosAmb"
	 */
    private int ciclosAmb = ciclos;

    public EvaluarPronosticoDemanda(){
    	postCondSI = new EstadoProceso(Constantes.EVALUAR_PRONOSTICO_DEMANDA,Constantes.DEMAND_FORECAST_RESPONSE_YES,10,20);
        postCondNO = new EstadoProceso(Constantes.EVALUAR_PRONOSTICO_DEMANDA,Constantes.DEMAND_FORECAST_RESPONSE_NO,10,20);
        tiempoActSI = postCondSI.tiempoAleatorio();
        tiempoActNO = postCondNO.tiempoAleatorio();
    }
    /**
     * This method is not important for a search based agent, but is essensial
     * when creating a calculus based one.
     */
    @Override

    public String toString() {
        return "EvaluarPronosticoDemanda";
    }

	@Override
	public Double getCost() {
			return new Double(postCondSI.tiempoPromedio());
	}

	@Override
	public SearchBasedAgentState execute(SearchBasedAgentState s) {

		VentasAgentState state = (VentasAgentState) s;
		if(preCond1.equals(state.getEstadoProceso()) ||
				preCond2.equals(state.getEstadoProceso())||
				preCond3.equals(state.getEstadoProceso())){
			VentasAgentState nuevoEstado = (VentasAgentState) state.clone();
			float tiempoAcumulado = state.getTiempoAcumulado();
			if (ciclosAg == 0){
				nuevoEstado.setEstadoProceso(postCondSI);
				tiempoAcumulado += tiempoActSI;
			} else {
				nuevoEstado.setEstadoProceso(postCondNO);
				tiempoAcumulado += tiempoActNO;
				ciclosAg--;
			}			
			
			if(state.getCiclosNegociacion() == 0)
				nuevoEstado.setCiclosNegociacion(1);
				
			nuevoEstado.setTiempoEnvioForecastRequest(0.0f);
			nuevoEstado.setTiempoEnvioInform(0.0f);
			nuevoEstado.setTiempoAcumulado(tiempoAcumulado);
			return nuevoEstado;
			
		}
		return null;
	}

	@Override
	public EnvironmentState execute(AgentState ast, EnvironmentState est) {

		VentasEnvironmentState estadoActual = (VentasEnvironmentState) est;
		VentasAgentState estadoAgente = (VentasAgentState) ast;
		
		if (ciclosAmb == 0) {
			estadoActual.setNuevoEstado(postCondSI);
			estadoActual.setTiempoUltimaActividad(tiempoActSI);
		} else {
			estadoActual.setNuevoEstado(postCondNO);
			estadoActual.setTiempoUltimaActividad(tiempoActNO);
			ciclosAmb--;
		}
		
		if(estadoActual.getCiclosNegociacion()==0)
			estadoActual.setCiclosNegociacion(1);
		estadoActual.setTiempoEnvioInform(estadoAgente.getTiempoAcumulado() + 
				estadoActual.getTiempoUltimaActividad());
		estadoActual.setTiempoEnvioForecastRequest(0.0f);
		estadoActual.setTiempoEnvioInform(0.0f);
//		respuesta = EstadoProceso.RND.nextInt(2);
		return estadoActual;
	}

}