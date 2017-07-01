/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem.rete;

import java.util.List;

public class Fact {

	private List<Object> values;

	private Predicate predicate;

	public Fact(Predicate predicate, List<Object> values) {
		assert values != null;

		this.values = values;
		this.predicate = predicate;
	}

	public Object get(Integer index) {
		return values.get(index);
	}

	@Override
	public String toString() {
		String output = predicate + "(";
		for(int i = 0; i < values.size(); i++){
			output += values.get(i);
			if(i != values.size() - 1){
				output += ", ";
			}
			else{
				output += ")";
			}
		}
		return output;
	}
}
