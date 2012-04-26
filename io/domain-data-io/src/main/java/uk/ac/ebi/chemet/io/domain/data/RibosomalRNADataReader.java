package uk.ac.ebi.chemet.io.domain.data;

import org.apache.log4j.Logger;
import uk.ac.ebi.caf.utility.version.annotation.CompatibleSince;
import uk.ac.ebi.mdk.io.EntityInput;
import uk.ac.ebi.mdk.io.EntityReader;
import uk.ac.ebi.chemet.io.util.SequenceSerializer;
import uk.ac.ebi.mdk.domain.entity.Gene;
import uk.ac.ebi.mdk.domain.entity.EntityFactory;
import uk.ac.ebi.mdk.domain.entity.RibosomalRNA;

import java.io.DataInput;
import java.io.IOException;

/**
 * ProteinProductDataReader - 12.03.2012 <br/>
 * <p/>
 * Class descriptions.
 *
 * @author johnmay
 * @author $Author$ (this version)
 * @version $Rev$
 */
@CompatibleSince("0.9")
public class RibosomalRNADataReader
        implements EntityReader<RibosomalRNA> {

    private static final Logger LOGGER = Logger.getLogger(RibosomalRNADataReader.class);

    private DataInput in;
    private EntityFactory factory;
    private EntityInput ein;

    public RibosomalRNADataReader(DataInput in, EntityFactory factory, EntityInput ein) {
        this.in = in;
        this.factory = factory;
        this.ein = ein;
    }

    public RibosomalRNA readEntity() throws IOException, ClassNotFoundException {

        RibosomalRNA rrna = factory.newInstance(RibosomalRNA.class);

        int n = in.readInt();
        
        for(int i = 0 ; i < n; i++)
            rrna.addSequence(SequenceSerializer.readRNASequence(in));


        int ngenes = in.readByte();
        for(int i = 0; i< ngenes; i++){
           rrna.addGene(ein.read(Gene.class));
        }

        return rrna;

    }

}
