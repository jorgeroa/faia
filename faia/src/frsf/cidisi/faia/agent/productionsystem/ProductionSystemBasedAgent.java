/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem;

import java.util.HashSet;
import java.util.Set;

import frsf.cidisi.faia.agent.Action;
import frsf.cidisi.faia.agent.Agent;
import frsf.cidisi.faia.agent.Perception;
import frsf.cidisi.faia.solver.productionsystem.InferenceEngine;
import frsf.cidisi.faia.solver.productionsystem.ProductionMemory;
import frsf.cidisi.faia.solver.productionsystem.RuleMatchesPair;
import frsf.cidisi.faia.solver.productionsystem.criterias.UsedRulesExpert;

public abstract class ProductionSystemBasedAgent extends Agent implements UsedRulesExpert {

	private InferenceEngine solver;
	private ProductionSystemBasedAgentState state;
	private ProductionMemory productionMemory;

	private Set<RuleMatchesPair> usedRules = new HashSet<>();

	public ProductionSystemBasedAgent() {
	}

	/**
	 * This method must be overrode by the agent to receive perceptions
	 * from the simulator.
	 *
	 * @param p
	 */
	public abstract void see(Perception p);

	public InferenceEngine getSolver() {
		return solver;
	}

	protected void setSolver(InferenceEngine solver) {
		this.solver = solver;
	}

	public ProductionSystemBasedAgentState getAgentState() {
		return state;
	}

	protected void setAgentState(ProductionSystemBasedAgentState agentState) {
		this.state = agentState;
	}

	public ProductionMemory getProductionMemory() {
		return productionMemory;
	}

	public void setProductionMemory(ProductionMemory productionMemory) {
		this.productionMemory = productionMemory;
	}

	@Override
	public Action selectAction() {
		ProductionSystemAction psa = (ProductionSystemAction) this.learn();
		if(psa != null){
			usedRules.add(psa.getPeerRuleData());
		}
		return psa;
	}

	@Override
	public boolean used(RuleMatchesPair prd) {
		return usedRules.contains(prd);
	}

	public Set<RuleMatchesPair> getUsedRules() {
		return usedRules;
	}

	public void setUsedRules(Set<RuleMatchesPair> usedRules) {
		this.usedRules = usedRules;
	}

	@Override
	public String toString() {
		return "ProductionSystemBasedAgent";
	}

	public abstract Action learn();

	public abstract boolean finish();
}
