package pacman;

import frsf.cidisi.faia.agent.problem.GoalTest;
import frsf.cidisi.faia.state.AgentState;
public class MetaPacman extends GoalTest{

	public boolean isGoalState(AgentState agentState){
		if (((EstadoPacman)agentState).noHayMasComida() & ((EstadoPacman)agentState).todoConocido())
			return true;
			
		return false;
	}
	

}