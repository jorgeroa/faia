/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem.rete;

import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

import frsf.cidisi.faia.solver.productionsystem.Matches;

public abstract class ReteNode {

	protected List<ReteNode> outputs = new Vector<>();

	public void propagateFacts(List<Matches> facts) {
		outputs.parallelStream().forEach(s -> s.propagateFacts(
				facts.parallelStream()
						.map(m -> ((ReteMatches) m).clone())
						.collect(Collectors.toList())));
	}

	public void addOutput(ReteNode output) {
		outputs.add(output);
	}

}
