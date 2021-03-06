package uk.ac.ebi.mdk.io.domain;

import org.apache.log4j.Logger;
import uk.ac.ebi.caf.utility.version.annotation.CompatibleSince;
import uk.ac.ebi.mdk.io.EntityInput;
import uk.ac.ebi.mdk.io.EntityReader;
import uk.ac.ebi.mdk.domain.entity.collection.Chromosome;
import uk.ac.ebi.mdk.domain.entity.collection.Genome;
import uk.ac.ebi.mdk.domain.entity.EntityFactory;

import java.io.DataInput;
import java.io.IOException;

/**
 * ProteinProductDataWriter - 12.03.2012 <br/>
 * <p/>
 * Class descriptions.
 *
 * @author johnmay
 * @author $Author$ (this version)
 * @version $Rev$
 */
@CompatibleSince("0.9")
public class GenomeDataReader
        implements EntityReader<Genome> {

    private static final Logger LOGGER = Logger.getLogger(GenomeDataReader.class);

    private DataInput in;
    private EntityFactory factory;
    private EntityInput entityIn;

    public GenomeDataReader(DataInput in, EntityFactory factory, EntityInput entityIn) {
        this.in = in;
        this.factory = factory;
        this.entityIn = entityIn;
    }

    public Genome readEntity() throws IOException, ClassNotFoundException {

        Genome genome = factory.newInstance(Genome.class);

        int n = in.readInt(); // n chromosome
        for(int i = 0 ; i < n; i++){
            genome.add(entityIn.read(Chromosome.class));
        }
        
        return genome;

    }

}
