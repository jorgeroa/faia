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

public class EqualFilter extends Filter {

	private Integer predicateIndex;
	private Integer factIndex;
	private Object filter;

	public EqualFilter(Integer predicateIndex, Integer factIndex, Object filter) {
		this.predicateIndex = predicateIndex;
		this.factIndex = factIndex;
		this.filter = filter;
	}

	public EqualFilter(Integer factIndex, Object filter) {
		this(0, factIndex, filter);
	}

	@Override
	public List<Matches> filter(List<Matches> facts) {
		return facts.stream()
				.map(h -> ((ReteMatches) h))
				.filter(rm -> rm.getFacts().get(predicateIndex).get(factIndex).equals(filter))
				.collect(Collectors.toList());
	}
}
