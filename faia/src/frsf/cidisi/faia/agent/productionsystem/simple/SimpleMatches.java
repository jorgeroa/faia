/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem.simple;

import java.util.HashMap;

import frsf.cidisi.faia.solver.productionsystem.Matches;

public class SimpleMatches implements Matches, Cloneable {

	private HashMap<Object, Object> matches;

	public SimpleMatches() {
		this.matches = new HashMap<>();
	}

	private SimpleMatches(SimpleMatches m) {
		this.matches = new HashMap<>(m.matches);
	}

	public Object matchValue(Object key) {
		return matches.get(key);
	}

	public void addMatch(Object key, Object value) {
		matches.put(key, value);
	}

	@Override
	public SimpleMatches clone() {
		return new SimpleMatches(this);
	}

	@Override
	public int hashCode() {
		return matches.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return matches.equals(obj);
	}
}
