/**
 * Copyright (c) 2017, Emiliano Gioria - Andres Leonel Rico - Esteban Javier Rebechi
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package frsf.cidisi.faia.agent.productionsystem.rete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import frsf.cidisi.faia.solver.productionsystem.Matches;

public class Join extends ReteNode {

	private Integer expectedUnions;
	private Integer placeIndex;
	private Map<Integer, List<Matches>> factsCache = new HashMap<>();

	public Join(Integer numberOfUnions) {
		this.expectedUnions = numberOfUnions;
	}

	@Override
	public void propagateFacts(List<Matches> facts) {
		throw new UnsupportedOperationException();
	}

	public synchronized void unirEn(List<Matches> facts, Integer placeIndex) {
		factsCache.put(placeIndex, facts);
		if(factsCache.keySet().size() != expectedUnions){
			return;
		}

		List<Matches> join = new ArrayList<>();
		Integer joinIndex = 0;
		for(Entry<Integer, List<Matches>> es: factsCache.entrySet()){
			if(joinIndex > 0){
				join = join.stream()
						.map(mu -> ((ReteMatches) mu))
						.map(rmu -> es.getValue().stream()
								.map(mh -> ((ReteMatches) mh).getFacts())
								.map(lhh -> {
									ReteMatches rmuClon = rmu.clone();
									this.placeIndex = es.getKey();
									lhh.forEach(h -> {
										Integer index = this.placeIndex;
										while(index >= rmuClon.getFacts().size()){
											rmuClon.getFacts().add(null);
										}
										rmuClon.getFacts().set(this.placeIndex++, h);
									});
									return rmuClon;
								}).collect(Collectors.toList()))
						.flatMap(List::stream)
						.collect(Collectors.toList());
			}
			else{
				join = es.getValue().stream()
						.map(m -> ((ReteMatches) m).getFacts())
						.map(lh -> {
							ReteMatches rmu = new ReteMatches();
							this.placeIndex = es.getKey();
							lh.forEach(h -> {
								Integer index = this.placeIndex;
								while(index >= rmu.getFacts().size()){
									rmu.getFacts().add(null);
								}
								rmu.getFacts().set(this.placeIndex++, h);
							});
							return rmu;
						}).collect(Collectors.toList());
			}
			joinIndex++;
		}

		super.propagateFacts(join);
	}

}
