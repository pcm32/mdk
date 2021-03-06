/**
 * BacterialCompartment.java
 *
 * 2012.02.06
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
package uk.ac.ebi.mdk.domain.entity.reaction.compartment;

import uk.ac.ebi.mdk.domain.entity.reaction.Compartment;
import uk.ac.ebi.mdk.domain.identifier.Identifier;
import uk.ac.ebi.mdk.domain.identifier.classification.GeneOntologyTerm;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


/**
 *
 *          BacterialCompartment 2012.02.06
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 *
 *          Tissue and Cell Type compartments (likely to be split in future)
 *
 */
public enum CellType implements Compartment {


    ADIPOCYTE("a", "Adibopcyte", (byte) -150, ""),
    MYOCYTE("m", "Myocyte", (byte) -149 , ""),
    HEPATOCYTE("h", "Hepatocyte", (byte) -148, "");

    private final String abbreviation;

    private final String description;

    private byte index;

    private Identifier identifier;

    private Set<String> synonyms = new HashSet<String>(4);


    private CellType(String abbreviation,
                     String description,
                     byte index,
                     String term,
                     String ... synonyms) {
        this.abbreviation = abbreviation;
        this.description = description;
        this.index = index;
        this.identifier = new GeneOntologyTerm(term);
        this.synonyms.addAll(Arrays.asList(synonyms));
    }




    public String getAbbreviation() {
        return abbreviation;
    }


    public String getDescription() {
        return description;
    }


    public Set<String> getSynonyms() {
        return Collections.unmodifiableSet(synonyms);
    }

    @Override
    public Identifier getIdentifier() {
        return identifier;
    }


    public byte getRanking() {
        return index;
    }
    
        @Override
    public String toString() {
        return getAbbreviation();
    }

}
