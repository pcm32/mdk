package uk.ac.ebi.chemet.io.domain.data;

import org.apache.log4j.Logger;
import uk.ac.ebi.caf.utility.version.annotation.CompatibleSince;
import uk.ac.ebi.chemet.io.core.EnumReader;
import uk.ac.ebi.chemet.io.domain.EntityInput;
import uk.ac.ebi.chemet.io.domain.EntityReader;
import uk.ac.ebi.chemet.io.identifier.IdentifierInput;
import uk.ac.ebi.interfaces.Genome;
import uk.ac.ebi.interfaces.entities.*;

import java.io.DataInput;
import java.io.File;
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
public class ReconstructionDataReader
        implements EntityReader<Reconstruction> {

    private static final Logger LOGGER = Logger.getLogger(ReconstructionDataReader.class);

    private DataInput in;
    private EntityInput entityIn;
    private IdentifierInput identifierInput;
    private EnumReader enumReader;
    private EntityFactory factory;

    public ReconstructionDataReader(DataInput in,
                                    EntityFactory factory,
                                    IdentifierInput identifierInput,
                                    EntityInput entityIn) {
        this.in = in;
        this.identifierInput = identifierInput;
        this.entityIn = entityIn;
        this.enumReader = new EnumReader(in);
        this.factory = factory;
    }

    public Reconstruction readEntity() throws IOException, ClassNotFoundException {

        Reconstruction recon = factory.newInstance(Reconstruction.class);

        recon.setIdentifier(identifierInput.read());

        // container
        recon.setContainer(new File(in.readUTF())); // set container

        // GENOME
        Genome genome = entityIn.read(Genome.class);
        recon.setGenome(genome);

        // METABOLOME
        int metabolites = in.readInt();
        for (int i = 0; i < metabolites; i++) {
            recon.addMetabolite(entityIn.read(Metabolite.class));
        }

        // PROTEOME
        int products = in.readInt();
        for (int i = 0; i < products; i++) {
            recon.addProduct((GeneProduct) entityIn.read());
        }

        // REACTOME
        int reactions = in.readInt();
        for (int i = 0; i < reactions; i++) {
            recon.addReaction(entityIn.read(MetabolicReaction.class));
        }

        return recon;

    }

}
