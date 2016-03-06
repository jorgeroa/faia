package frsf.ia.grupo9.search.ventas;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.agent.search.Problem;
import frsf.cidisi.faia.agent.search.SearchAction;
import frsf.cidisi.faia.agent.search.SearchBasedAgent;
import frsf.cidisi.faia.solver.search.Search;
import frsf.cidisi.faia.solver.search.UniformCostSearch;
import frsf.ia.grupo9.search.ventas.actions.ActualizarPronostico;
import frsf.ia.grupo9.search.ventas.actions.CancelDemandForecast;
import frsf.ia.grupo9.search.ventas.actions.EnviarMensajeAcceptProposal;
import frsf.ia.grupo9.search.ventas.actions.EnviarMensajeCancelDemandForecast;
import frsf.ia.grupo9.search.ventas.actions.EnviarMensajeForecastRequest;
import frsf.ia.grupo9.search.ventas.actions.EnviarMensajeInformPOSForecast;
import frsf.ia.grupo9.search.ventas.actions.EnviarMensajeInformPlannedEvents;
import frsf.ia.grupo9.search.ventas.actions.EnviarMensajeProposeUpdatedDemandForecast;
import frsf.ia.grupo9.search.ventas.actions.EvaluarPronosticoDemanda;
import frsf.ia.grupo9.search.ventas.actions.FinConExito;
import frsf.ia.grupo9.search.ventas.actions.FinConFalla;
import frsf.ia.grupo9.search.ventas.actions.GenerarPlanEventosProgramado;
import frsf.ia.grupo9.search.ventas.actions.GenerarPronosticoVentasPorPOS;
import frsf.ia.grupo9.search.ventas.actions.GenerarSolicitudPronosticoDemanda;

/**
 * Clase que representa el agente inteligente
 */
public class VentasAgent extends SearchBasedAgent {
	
    public VentasAgent() {

        // The Agent Goal
        VentasGoal agGoal = new VentasGoal();

        // The Agent State
        VentasAgentState agState = new VentasAgentState();
        this.setAgentState(agState);

        // Create the operators
        Vector<SearchAction> operators = new Vector<SearchAction>();
        operators.addElement(new GenerarSolicitudPronosticoDemanda());
        operators.addElement(new EnviarMensajeForecastRequest());
        operators.addElement(new GenerarPronosticoVentasPorPOS());
        operators.addElement(new GenerarPlanEventosProgramado());
        operators.addElement(new EnviarMensajeInformPlannedEvents());
        operators.addElement(new EnviarMensajeInformPOSForecast());
        operators.addElement(new EvaluarPronosticoDemanda());
        operators.addElement(new EnviarMensajeAcceptProposal());
        operators.addElement(new ActualizarPronostico());
        operators.addElement(new EnviarMensajeProposeUpdatedDemandForecast());
        operators.addElement(new FinConFalla());
        operators.addElement(new FinConExito());
        operators.addElement(new EnviarMensajeCancelDemandForecast());
        operators.addElement(new CancelDemandForecast());

        // Create the Problem which the agent will resolve
        Problem problem = new Problem(agGoal, agState, operators);
        this.setProblem(problem);
    }

    /**
     * This method is executed by the simulator to ask the agent for an action.
     */
    @Override
    public Action selectAction() {

        // Create the search strategy
        UniformCostSearch strategy = new UniformCostSearch(new CostFunction());
        
        // Create a Search object with the strategy
        Search searchSolver = new Search(strategy);

        /* Generate an XML file with the search tree. It can also be generated
         * in other formats like PDF with PDF_TREE */
        searchSolver.setVisibleTree(Search.GRAPHVIZ_TREE);

        // Set the Search searchSolver.
        this.setSolver(searchSolver);

        // Ask the solver for the best action
        Action selectedAction = null;
        try {
            selectedAction =
                    this.getSolver().solve(new Object[]{this.getProblem()});
        } catch (Exception ex) {
            Logger.getLogger(VentasAgent.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Return the selected action
        return selectedAction;

    }

    /**
     * This method is executed by the simulator to give the agent a perception.
     * Then it updates its state.
     * @param p
     */
    @Override
    public void see(Perception p) {
        this.getAgentState().updateState(p);
    }

}
