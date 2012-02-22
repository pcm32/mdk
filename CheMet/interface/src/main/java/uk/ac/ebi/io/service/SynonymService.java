package uk.ac.ebi.io.service;

import org.apache.lucene.index.Term;
import uk.ac.ebi.interfaces.identifiers.Identifier;

import java.util.Collection;

/**
 * SynonymService.java - 21.02.2012 <br/>
 * <p/>
 * Class descriptions.
 *
 * @author johnmay
 * @author $Author$ (this version)
 * @version $Rev$
 */
public interface SynonymService<I extends Identifier>
        extends QueryService<I> {
    
    public static Term SYNONYM = new Term("Synonym");

    public Collection<I> searchSynonyms(String name, boolean fuzzy);

    public Collection<String> getSynonyms(I identifier);

    
}
