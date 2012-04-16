/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.interfaces.services;

import java.util.Collection;
import uk.ac.ebi.interfaces.identifiers.ChemicalIdentifier;

/**
 *
 * @author pmoreno
 */
public interface ChemicalConnectivityQueryService<I extends ChemicalIdentifier> extends QueryService<I> {
    public Collection<I> getEntriesWithConnectivity(String connectivity);
    public String getInChIConnectivity(I identifier);
}