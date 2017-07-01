/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem.rete;

import java.util.Collection;
import java.util.Map;

import frsf.cidisi.faia.solver.productionsystem.WorkingMemory;

/**
 * Represent the internal state of the Agent.
 */
public interface ReteWorkingMemory extends WorkingMemory {

	public boolean queryHasSolution(String query);

	public Collection<Map<String, String>> query(String query);

	public void addPredicate(String predicate);

	public void removePredicate(String predicate);

	public void subscribe(ReteWorkingMemoryChangeListener rwmcl);
}
