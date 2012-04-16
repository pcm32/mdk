/**
 * TransportReactionClassifier.java
 *
 * 2011.12.12
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
package uk.ac.ebi.core.tools;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.util.AbstractMap.*;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.log4j.Logger;
import uk.ac.ebi.chemet.entities.reaction.AbstractReaction;
import uk.ac.ebi.interfaces.entities.Metabolite;
import uk.ac.ebi.interfaces.entities.Reaction;
import uk.ac.ebi.interfaces.reaction.Compartment;
import uk.ac.ebi.interfaces.reaction.CompartmentalisedParticipant;


/**
 *          TransportReactionClassifier - 2011.12.12 <br>
 *          Utility class currently only used to determine the class of
 *          transport reaction (i.e. SYMPORTER, ANTIPORTER, UNIPORTER, UNKNOWN)
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 */
public class TransportReactionUtil {

    private static final Logger LOGGER = Logger.getLogger(TransportReactionUtil.class);


    public enum Classification {

        SYMPORTER,
        ANTIPORTER,
        UNIPORTER,
        UNKNOWN;
    };


    /**
     * Uses a simple direct object reference comparison for mapping
     * @param <T>
     * @param transportReaction
     * @return
     */
    public static <T> BiMap<CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>, CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>> getMappings(
            Reaction<? extends CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>> transportReaction) {
        return getMappings(transportReaction, new Comparator<T>() {

            public int compare(T o1,
                               T o2) {
                return o1.equals(o2) ? 0 : -1;
            }
        });
    }


    public static <T> BiMap<CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>, CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>> getMappings(
            Reaction<? extends CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>> transportReaction,
            Comparator<T> comparator) {

        BiMap<CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>, CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>> mappings = HashBiMap.create();

        for (CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>> p1 : transportReaction.getReactants()) {
            for (CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>> p2 : transportReaction.getProducts()) {
                if (comparator.compare(p1.getMolecule(), p2.getMolecule()) == 0) {
                    mappings.put(p1, p2);
                }
            }
        }
        
        return mappings;
    }


    /**
     * Classifies the provided MetabolicReaction
     */
    public static Classification getClassification(Reaction<? extends CompartmentalisedParticipant<Metabolite, ?, Enum<? extends Compartment>>> rxn) {

        if (!isTransport(rxn)) {
            return Classification.UNKNOWN;
        }


        return getClassification(getMappings(rxn));


    }


    public static boolean isTransport(Reaction<? extends CompartmentalisedParticipant<?, ?, Enum<? extends Compartment>>> rxn) {
        Set uniqueCompartments = new HashSet();
        for (CompartmentalisedParticipant p : rxn.getReactants()) {
            uniqueCompartments.add(p.getCompartment());
        }
        for (CompartmentalisedParticipant p : rxn.getProducts()) {
            uniqueCompartments.add(p.getCompartment());
        }
        return uniqueCompartments.size() > 1;
    }


    private static <T> Classification getClassification(
            BiMap<CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>, CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>> mappings) {

        int total = 0;
        Set<Integer> movement = new HashSet<Integer>();

        for (Entry<CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>, CompartmentalisedParticipant<T, ?, Enum<? extends Compartment>>> entry : mappings.entrySet()) {

            CompartmentalisedParticipant<?, ?, Enum<? extends Compartment>> p1 = entry.getKey();
            CompartmentalisedParticipant<?, ?, Enum<? extends Compartment>> p2 = entry.getValue();

            Compartment c1 = (Compartment) p1.getCompartment();
            Compartment c2 = (Compartment) p2.getCompartment();

            int value = c1.getRanking() > c2.getRanking() ? -1
                        : c1.getRanking() < c2.getRanking() ? +1 : 0;
            
            if (value != 0) {
                movement.add(value);
                total++;
            }

        }

        if (total == 1) {
            return Classification.UNIPORTER;
        }
        if (total > 1 && movement.size() > 1) {
            return Classification.ANTIPORTER;
        }
        if (total > 1 && movement.size() == 1) {
            return Classification.SYMPORTER;
        }

        return Classification.UNKNOWN;

    }
}