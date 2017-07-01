/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem.rete;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import frsf.cidisi.faia.solver.productionsystem.Matches;

public abstract class Predicate extends ReteNode implements ReteWorkingMemoryChangeListener {

	private ReteWorkingMemory rwm;
	private String name;
	private String consultName;

	public Predicate(String name, Integer numberOfParameters) {
		name = Character.toLowerCase(name.charAt(0)) + name.substring(1, name.length());
		this.name = name;
		this.consultName = name + getStringParametros(numberOfParameters);
	}

	public void setRWM(ReteWorkingMemory rwm) {
		this.rwm = rwm;
		rwm.subscribe(this);
	}

	@Override
	public void propagateFacts(List<Matches> facts) {
		List<Matches> newFacts = rwm.query(consultName).stream().map(map -> {
			List<Object> values = new ArrayList<>();
			for(Entry<String, String> fact: map.entrySet()){
				Integer index = this.number(fact.getKey()) - 1;
				while(index >= values.size()){
					values.add(null);
				}
				values.set(index, fact.getValue());
			}
			return new Fact(this, values);
		}).map(h -> {
			ReteMatches rm = new ReteMatches();
			rm.addFact(h);
			return rm;
		}).collect(Collectors.toList());
		facts.addAll(newFacts);

		super.propagateFacts(facts);
	}

	@Override
	public void change(String query) {
		if(query.startsWith(name + "(")){
			this.propagateFacts(new ArrayList<>());
		}
	}

	@Override
	public String toString() {
		return name;
	}

	private String getStringParametros(Integer numberOfParameters) {
		StringBuilder result = new StringBuilder("(");
		for(int i = 1; i < numberOfParameters; i++){
			result.append(alpha(i)).append(",");
		}
		if(numberOfParameters > 0){
			result.append(alpha(numberOfParameters));
		}
		return result.append(")").toString();
	}

	private static char[] chars;
	static{
		chars = new char['Z' - 'A' + 1];
		for(char i = 'A'; i <= 'Z'; i++){
			chars[i - 'A'] = i;
		}
	}

	private StringBuilder alpha(int i) {
		char r = chars[--i % chars.length];
		int n = i / chars.length;
		return n == 0 ? new StringBuilder().append(r) : alpha(n).append(r);
	}

	private Integer number(String s) {
		return numberAux(s.toCharArray(), s.length() - 1);
	}

	private Integer numberAux(char[] s, int i) {
		if(i < 0){
			return 0;
		}
		int n = s[i] - 'A' + 1;
		return numberAux(s, i - 1) * chars.length + n;
	}

}
