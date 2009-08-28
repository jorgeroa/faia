/*
 * Copyright 2007-2009 Georgina Stegmayer, Milagros Gutiérrez, Jorge Roa,
 * Luis Ignacio Larrateguy y Milton Pividori.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package frsf.cidisi.faia.examples.situationcalculus.pacman;

import frsf.cidisi.faia.exceptions.PrologConnectorException;
import frsf.cidisi.faia.simulator.SituationCalculusBasedAgentSimulator;

/**
 *
 * @author miltondp
 */
public class PacmanLogicoMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws PrologConnectorException {
        AgentePacmanLogico agente = new AgentePacmanLogico();
        AmbientePacmanLogico ambiente = new AmbientePacmanLogico();
        SituationCalculusBasedAgentSimulator simu = new SituationCalculusBasedAgentSimulator(ambiente, agente);
        simu.start();
    }
}
