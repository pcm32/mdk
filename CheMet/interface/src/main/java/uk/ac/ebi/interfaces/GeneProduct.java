/**
 * GeneProduct.java
 *
 * 2011.09.12
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
package uk.ac.ebi.interfaces;

import org.biojava3.core.sequence.template.AbstractCompound;
import org.biojava3.core.sequence.template.Sequence;

/**
 *          GeneProduct – 2011.09.12 <br>
 *          Class description
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 */
public interface GeneProduct extends AnnotatedEntity {

    /**
     *
     * Access the name of the gene product
     *
     * @return
     *
     */
    public String getName();

    /**
     * Returns the sequence of the gene product
     * @return a Sequence composed of either AminoAcidCompoundSet or RNACompoundSet
     */
    public Sequence<? extends AbstractCompound> getSequence();

    /**
     * Mutator for the sequence
     * @param sequence
     */
    public void setSequence(Sequence<? extends AbstractCompound> sequence);

    
}
