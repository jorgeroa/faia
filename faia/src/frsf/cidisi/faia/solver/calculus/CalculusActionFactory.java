/*
 * Copyright 2007-2008 Georgina Stegmayer, Milagros Gutiérrez, Jorge Roa
 * y Milton Pividori.
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

package frsf.cidisi.faia.solver.calculus;

import frsf.cidisi.faia.agent.Action;

/**
 * Calculus (a Solver subclass) receives an string representation
 * of the best action when invoking the knowledge base, in the
 * solve() method. This string must be converted to an Action object.
 * This is class must be implemented by the user to carry out this
 * conversion.
 */
public abstract class CalculusActionFactory {

    /**
     * This method is executed before stringToAction, which is overrode
     * by the user. If no action was returned by the agent, then we return
     * a NoAction object.
     * 
     * @param stringAction
     * @return The Action represented by stringAction
     */
    public Action makeActionFromString(String stringAction) {
        if (stringAction.equals(this.noActionString()))
            return CalculusNoAction.getInstance();
        
        return this.stringToAction(stringAction);
    }
    
    /**
     * This method is overrode by the user.
     * @param stringAction
     * @return The Action represented by stringAction.
     */
    public abstract Action stringToAction(String stringAction);
    
    /**
     * This method must return the string representation of a NoAction,
     * used by the user in the prolog file.
     */
    public abstract String noActionString();
}
