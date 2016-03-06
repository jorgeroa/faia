package frsf.ia.grupo9.search.ventas.simulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.GoalBasedAgent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.environment.Environment;
import frsf.cidisi.faia.simulator.SearchBasedAgentSimulator;
import frsf.cidisi.faia.simulator.events.EventType;
import frsf.cidisi.faia.simulator.events.SimulatorEventNotifier;

public class SearchBasedVentasAgentSimulator extends SearchBasedAgentSimulator {

	/**
	 * @uml.property  name="agImp"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	protected List<String> agImp;
	/**
	 * @uml.property  name="world"
	 */
	protected List<String> world;
	/**
	 * @uml.property  name="acciones"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	protected List<String> acciones;
	/**
	 * @uml.property  name="percepciones"
	 * @uml.associationEnd  multiplicity="(0 -1)" elementType="java.lang.String"
	 */
	protected List<String> percepciones;
	/**
	 * @uml.property  name="resultado"
	 */
	private String resultado = new String();
	
	
	public SearchBasedVentasAgentSimulator(Environment environment,
			Vector<Agent> agents) {
		super(environment, agents);
		agImp = new ArrayList<String>();
		world = new ArrayList<String>();
		acciones = new ArrayList<String>();
		percepciones = new ArrayList<String>();
	}

	public SearchBasedVentasAgentSimulator(Environment environment, Agent agent) {
		this(environment, new Vector<Agent>(Arrays.asList(agent)));
	}
	
	@Override
    public void start() {

        System.out.println("----------------------------------------------------");
        System.out.println("--- " + this.getSimulatorName() + " ---");
        System.out.println("----------------------------------------------------");
        System.out.println();

        Perception perception;
        Action action;
        GoalBasedAgent agent;

        agent = (GoalBasedAgent) this.getAgents().firstElement();

        /*
         * Simulation starts. The environment sends perceptions to the agent, and
         * it returns actions. The loop condition evaluation is placed at the end.
         * This works even when the agent starts with a goal state (see agentSucceeded
         * method in the SearchBasedAgentSimulator).
         */
        do {

            System.out.println("------------------------------------");

            System.out.println("Sending perception to agent...");
            perception = this.getPercept();
            agent.see(perception);
            percepciones.add(new String(perception.toString()));
            System.out.println("Perception: " + perception);

            agImp.add(new String(agent.getAgentState().toString()));
            System.out.println("Agent State: " + agent.getAgentState());
            world.add(new String(environment.toString()));
            System.out.println("Environment: " + environment);

            System.out.println("Asking the agent for an action...");
            action = agent.selectAction();

            if (action == null) {
                break;
            }

            acciones.add(new String(action.toString()));
            System.out.println("Action returned: " + action);
            System.out.println();

            this.actionReturned(agent, action);

        } while (!this.agentSucceeded(action) && !this.agentFailed(action));

        // Check what happened, if agent has reached the goal or not.
        if (this.agentSucceeded(action)) {
            System.out.println("Agent has reached the goal!");
            resultado = new String("El agente ha cumplido su Meta!");
        } else {
            System.out.println("ERROR: The simulation has finished, but the agent has not reached his goal.");
            resultado = new String("ERROR: La simulación ha finalizado, pero el agente no ha cumplido su meta.");
        }

        // Leave a blank line
        System.out.println();

        // FIXME: This call can be moved to the Simulator class
        this.environment.close();

        // Launch simulationFinished event
        SimulatorEventNotifier.runEventHandlers(EventType.SimulationFinished, null);
    }

	public List<String> getAgImp() {
		return agImp;
	}

	public List<String> getWorld() {
		return world;
	}

	public List<String> getAcciones() {
		return acciones;
	}

	public List<String> getPercepciones() {
		return percepciones;
	}
	
	/**
	 * @return
	 * @uml.property  name="resultado"
	 */
	public String getResultado(){
		return resultado;
	}
	
	

}
