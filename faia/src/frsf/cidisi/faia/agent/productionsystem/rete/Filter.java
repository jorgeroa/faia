/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem.rete;

import java.util.List;

import frsf.cidisi.faia.solver.productionsystem.Matches;

public abstract class Filter extends ReteNode {

	public Filter() {

	}

	@Override
	public void propagateFacts(List<Matches> facts) {
		super.propagateFacts(this.filter(facts));
	}

	public abstract List<Matches> filter(List<Matches> facts);
}
