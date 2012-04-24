package uk.ac.ebi.mdk.domain.entity.collection;

import uk.ac.ebi.interfaces.entities.MetabolicReaction;
import uk.ac.ebi.interfaces.entities.Metabolite;

import java.util.Collection;

/**
 * @version $Rev$
 */
public interface Reactome extends Collection<MetabolicReaction> {


    /**
     * Access reactions that contain the given metabolite
     * @param m
     * @return
     */
    public Collection<MetabolicReaction> getReactions(Metabolite m);

    // force rebuild of participant mapping
    public void rebuildParticipantMap();
}