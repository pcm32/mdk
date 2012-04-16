/**
 * ChEBINameService.java
 *
 * 2011.10.26
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
package uk.ac.ebi.io.service;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import org.apache.log4j.Logger;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import uk.ac.ebi.chemet.resource.chemical.BioCycChemicalIdentifier;
import uk.ac.ebi.interfaces.services.ChemicalConnectivityQueryService;
import uk.ac.ebi.io.remote.MoleculeCollectionConnectivity;
import uk.ac.ebi.io.remote.MoleculeCollectionConnectivity.MoleculeCollectionConnectivityLuceneFields;
import uk.ac.ebi.resource.IdentifierFactory;

/**
 *          ChEBIMoleculeConnectivityService - 2011.10.26 <br>
 *          Singleton description
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 */
public class MetaCycMoleculeConnectivityService
        extends MoleculeConnectivityQueryService implements ChemicalConnectivityQueryService<BioCycChemicalIdentifier> {

    private static final Logger LOGGER = Logger.getLogger(MetaCycMoleculeConnectivityService.class);
    private static final IdentifierFactory FACTORY = IdentifierFactory.getInstance();
    private final Query collectionQuery;
    private static final String COLLECTION = "BioCyc Chemical";

    private MetaCycMoleculeConnectivityService() {
        super(new MoleculeCollectionConnectivity(COLLECTION));
        collectionQuery = new TermQuery(new Term(MoleculeCollectionConnectivityLuceneFields.CollectionName.toString(), COLLECTION));
        try {
            searcher = new IndexSearcher(getDirectory(), true);
        } catch (IOException ex) {
            LOGGER.error("Problems initializing searcher", ex);
        }
    }

    public static MetaCycMoleculeConnectivityService getInstance() {
        return MetaCycMoleculeConnectivityServiceHolder.INSTANCE;
    }

    public Collection<BioCycChemicalIdentifier> getEntriesWithConnectivity(String connectivity) {
        Query queryConnectivity = new TermQuery(new Term(MoleculeCollectionConnectivityLuceneFields.Connectivity.toString(), connectivity));
        
        BooleanQuery query = new BooleanQuery();
        query.add(queryConnectivity, Occur.MUST);
        query.add(collectionQuery, Occur.MUST);
        
        return reverseSearch(query);
    }

    private static class MetaCycMoleculeConnectivityServiceHolder {

        private static final MetaCycMoleculeConnectivityService INSTANCE = new MetaCycMoleculeConnectivityService();
    }

    @Override
    Query getCollectionQuery() {
        return collectionQuery;
    }
    
    public String getInChIConnectivity(BioCycChemicalIdentifier identifier) {
        return super.getInChIConnectivity(identifier);
    }    
    
    private Collection<BioCycChemicalIdentifier> reverseSearch(Query query) {
        Collection<BioCycChemicalIdentifier> ids = new HashSet<BioCycChemicalIdentifier>();

        try {
            TopScoreDocCollector collector = TopScoreDocCollector.create(getMaxResults(), true);
            searcher.search(query, collector);
            ScoreDoc[] hits = collector.topDocs().scoreDocs;
            for (ScoreDoc scoreDoc : hits) {
                BioCycChemicalIdentifier ident = getIdentifier();
                ident.setAccession(getValue(scoreDoc, MoleculeCollectionConnectivityLuceneFields.Identifier.toString()));
                ids.add(ident);
            }
        } catch (IOException ex) {
            LOGGER.error("Error occur whilst searching with query " + query);
        }

        return ids;
    }
    
    @Override
    public BioCycChemicalIdentifier getIdentifier() {
        return new BioCycChemicalIdentifier();
    }

}