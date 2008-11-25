/*
 * Copyright 2007-2008 Georgina Stegmayer, Milagros Gutiérrez, Jorge Roa
 * y Milton Pividori.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package frsf.cidisi.faia.solver.planning;

import java.util.Hashtable;

import frsf.cidisi.faia.exceptions.PrologConnectorException;
import frsf.cidisi.faia.solver.ActionFactory;
import frsf.cidisi.faia.solver.PrologConnector;
import frsf.cidisi.faia.state.AgentState;

public abstract class PlanningBasedAgentState extends AgentState {

	protected PrologConnector prologConnector;
	
	public PlanningBasedAgentState(String prologFile) throws PrologConnectorException {
		this.prologConnector = new PrologConnector(prologFile);
	}
	
	public void addInitState(String state) {
		String query = "holds(" + state + ", init)";
		
		// Check if the new init state isn't already added.
		if (this.queryHasSolution(query))
			return;
		
		this.prologConnector.addPredicate(query);
	}
	
	public Hashtable[] query(String query) {
    	return this.prologConnector.query(this.prepareQuery(query));
    }
	
	public Hashtable[] plainQuery(String query) {
		return this.prologConnector.query(query);
	}
	
	public boolean queryHasSolution(String query) {
    	return this.prologConnector.queryHasSolution(this.prepareQuery(query));
    }
	
	private String prepareQuery(String query) {
		return "holds(" + query + ", init)";
	}
	
//	String getBestAction() {
//		String bestActionQuery = this.getBestActionPredicate() + "(X).";
//		
//		Hashtable[] result = this.prologConnector.query(bestActionQuery);
//		String bestAction = result[0].get("X").toString();
//		
//		return bestAction;
//	}
	
	/**
	 * Returns the best action and applies it on the agent state.
	 */
	String getBestActionAction() {
		String bestActionQuery = this.getBestActionPredicate() + "(X)";
		
		// Query for the best action.
		Hashtable[] result = this.prologConnector.query(bestActionQuery);
		String bestAction = result[0].get("X").toString();
		
		// Apply the best action's effects on the agent state.
		this.prologConnector.executeNonQuery(this.getExecuteActionPredicate() +
				"(" + bestAction + ")");
		
		return bestAction;
	}
	
	public abstract ActionFactory getActionFactory();
	
	public abstract String getBestActionPredicate();
	
	public abstract String getExecuteActionPredicate();
}
