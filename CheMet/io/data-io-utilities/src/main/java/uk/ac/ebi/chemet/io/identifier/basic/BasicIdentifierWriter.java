package uk.ac.ebi.chemet.io.identifier.basic;

import org.apache.log4j.Logger;
import uk.ac.ebi.caf.utility.version.annotation.CompatibleSince;
import uk.ac.ebi.chemet.io.identifier.IdentifierWriter;
import uk.ac.ebi.interfaces.identifiers.Identifier;

import java.io.DataOutput;
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
public class BasicIdentifierWriter implements IdentifierWriter<Identifier> {

    private static final Logger LOGGER = Logger.getLogger(BasicIdentifierWriter.class);

    private DataOutput out;

    public BasicIdentifierWriter(DataOutput out) {
        this.out = out;
    }

    @Override
    public void write(Identifier identifier) throws IOException {
        out.writeUTF(identifier.getAccession());
    }
}
