/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.solver.productionsystem;

import javafx.util.Pair;

@SuppressWarnings("serial")
public class RuleMatchesPair extends Pair<Rule, Matches> {

	private Integer novelty;

	public RuleMatchesPair(Rule key, Matches value, Integer novelty) {
		super(key, value);
		this.novelty = novelty;
	}

	public RuleMatchesPair(Rule key, Matches value) {
		this(key, value, 0);
	}

	public Integer getNovelty() {
		return novelty;
	}

	public void setNovelty(Integer novelty) {
		this.novelty = novelty;
	}
}