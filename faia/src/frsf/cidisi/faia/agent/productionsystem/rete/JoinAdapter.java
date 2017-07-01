/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem.rete;

import java.util.List;

import frsf.cidisi.faia.solver.productionsystem.Matches;

public class JoinAdapter extends ReteNode {

	private Join join;
	private Integer placeIndex;

	public JoinAdapter(Integer placeIndex, Join join) {
		this.placeIndex = placeIndex;
		this.join = join;
	}

	@Override
	public void propagateFacts(List<Matches> facts) {
		join.unirEn(facts, placeIndex);
	}

}
