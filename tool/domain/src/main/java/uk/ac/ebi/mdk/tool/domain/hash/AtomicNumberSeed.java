/**
 * AtomicNumberSeed.java
 *
 * 2011.11.09
 *
 * This file is part of the CheMet library
 * 
 * The CheMet library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * CheMet is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with CheMet.  If not, see <http://www.gnu.org/licenses/>.
 */
package uk.ac.ebi.mdk.tool.domain.hash;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;

/**
 *          AtomicNumberSeed - 2011.11.09 <br>
 *          Atomic number seed used the atomic number to provide a seed
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 */
public class AtomicNumberSeed implements AtomSeed {

    protected AtomicNumberSeed(){

    }

    public int seed(IAtomContainer molecule, IAtom atom) {
        return atom.getAtomicNumber().hashCode();
    }

    @Override
    public String toString() {
        return "Atomic Number";
    }
}
