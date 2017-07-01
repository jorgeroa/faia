/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem.rete;

import java.util.ArrayList;
import java.util.List;

import frsf.cidisi.faia.solver.productionsystem.Matches;

public class ReteMatches implements Matches, Cloneable {

	private List<Fact> facts = new ArrayList<>();

	public ReteMatches() {
		super();
	}

	public Fact getFact(Integer index) {
		return facts.get(index);
	}

	public void addFact(Fact hecho) {
		this.facts.add(hecho);
	}

	public List<Fact> getFacts() {
		return facts;
	}

	@Override
	public ReteMatches clone() {
		ReteMatches rm = new ReteMatches();
		rm.facts = new ArrayList<>(this.facts);
		return rm;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((facts == null) ? 0 : facts.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj){
			return true;
		}
		if(obj == null){
			return false;
		}
		if(getClass() != obj.getClass()){
			return false;
		}
		ReteMatches other = (ReteMatches) obj;
		if(facts == null){
			if(other.facts != null){
				return false;
			}
		}
		else if(!facts.equals(other.facts)){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		String result = "{";
		for(int i = 0; i < facts.size(); i++){
			result += facts.get(i);
			if(i != facts.size() - 1){
				result += ", ";
			}
			else{
				result += "}";
			}
		}
		return result;
	}
}
