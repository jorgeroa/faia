/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem.simple;

import java.util.List;
import java.util.stream.Collectors;

import frsf.cidisi.faia.solver.productionsystem.Matcher;
import frsf.cidisi.faia.solver.productionsystem.ProductionMemory;
import frsf.cidisi.faia.solver.productionsystem.RuleMatchesPair;
import frsf.cidisi.faia.solver.productionsystem.WorkingMemory;

public class SimpleMatcher implements Matcher {

	/**
	 * Naive algorithm for matching.
	 *
	 * @param productionMemory
	 *            Production memory with the rules
	 * @param workingMemory
	 *            Working memory with the data
	 * @return List of pair of rules with their matches
	 */
	@Override
	public List<RuleMatchesPair> match(ProductionMemory productionMemory, WorkingMemory workingMemory) {
		return productionMemory.getRules()
				.parallelStream()
				.map(r -> ((SimpleRule) r).match().parallelStream().map(m -> new RuleMatchesPair(r, m)).collect(Collectors.toList()))
				.flatMap(List::stream)
				.collect(Collectors.toList());
	};

}
