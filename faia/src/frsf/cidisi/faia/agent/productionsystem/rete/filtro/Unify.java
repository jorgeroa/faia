/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem.rete.filtro;

import java.util.List;
import java.util.stream.Collectors;

import frsf.cidisi.faia.agent.productionsystem.rete.Filter;
import frsf.cidisi.faia.agent.productionsystem.rete.ReteMatches;
import frsf.cidisi.faia.solver.productionsystem.Matches;

public class Unify extends Filter {

	private Integer factIndex1;
	private Integer valueIndex1;
	private Integer factIndex2;
	private Integer valueIndex2;

	public Unify(Integer factIndex1, Integer valueIndex1, Integer factIndex2, Integer valueIndex2) {
		this.factIndex1 = factIndex1;
		this.valueIndex1 = valueIndex1;
		this.factIndex2 = factIndex2;
		this.valueIndex2 = valueIndex2;
	}

	@Override
	public List<Matches> filter(List<Matches> facts) {
		return facts.stream()
				.map(m -> ((ReteMatches) m))
				.filter(rm -> rm.getFact(factIndex1).get(valueIndex1).equals(rm.getFact(factIndex2).get(valueIndex2)))
				.collect(Collectors.toList());
	}
}
