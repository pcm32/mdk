package uk.ac.ebi.mdk.io.identifier;

import org.apache.log4j.Logger;
import uk.ac.ebi.caf.utility.version.annotation.CompatibleSince;
import uk.ac.ebi.mdk.io.EnumReader;
import uk.ac.ebi.mdk.io.IdentifierReader;
import uk.ac.ebi.mdk.domain.DefaultIdentifierFactory;
import uk.ac.ebi.mdk.domain.identifier.Taxonomy;

import java.io.DataInput;
import java.io.IOException;

/**
 * TaxonomyWriter - 13.03.2012 <br/>
 * <p/>
 * Class descriptions.
 *
 * @author johnmay
 * @author $Author$ (this version)
 * @version $Rev$
 */
@CompatibleSince("0.9")
public class TaxonomyReader implements IdentifierReader<Taxonomy> {

    private static final Logger LOGGER = Logger.getLogger(TaxonomyReader.class);

    private static final DefaultIdentifierFactory FACTORY = DefaultIdentifierFactory.getInstance();

    private DataInput in;
    private EnumReader enumReader;

    public TaxonomyReader(DataInput in) {
        this.in = in;
        this.enumReader = new EnumReader(in);
    }

    @Override
    public Taxonomy readIdentifier() throws IOException, ClassNotFoundException {

        Taxonomy identifier   = (Taxonomy) FACTORY.ofClass(Taxonomy.class);

        identifier.setTaxon(in.readInt());
        identifier.setCode(in.readUTF());
        identifier.setCommonName(in.readUTF());
        identifier.setOfficialName(in.readUTF());
        identifier.setKingdom((Taxonomy.Kingdom) enumReader.readEnum());

        return identifier;

    }
}
