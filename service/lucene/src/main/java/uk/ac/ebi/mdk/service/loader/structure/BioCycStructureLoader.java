package uk.ac.ebi.mdk.service.loader.structure;

import org.apache.log4j.Logger;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.io.MDLV2000Reader;
import uk.ac.ebi.mdk.service.index.LuceneIndex;
import uk.ac.ebi.mdk.service.loader.AbstractSingleIndexResourceLoader;
import uk.ac.ebi.mdk.service.loader.writer.DefaultStructureIndexWriter;
import uk.ac.ebi.mdk.service.location.ResourceDirectoryLocation;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author John May
 */
public class BioCycStructureLoader extends AbstractSingleIndexResourceLoader {

    private static final Logger LOGGER = Logger.getLogger(BioCycStructureLoader.class);

    public BioCycStructureLoader(LuceneIndex index) {
        super(index);

        addRequiredResource("Mol Folder",
                            "Mol folder containing .mol files and the BioCyc identifier as the name of the file",
                            ResourceDirectoryLocation.class);

    }

    /**
     * @inheritDoc
     */
    @Override
    public void update() throws IOException {

        // get the directory of files
        ResourceDirectoryLocation location = getLocation("mol.folder");
        DefaultStructureIndexWriter writer = new DefaultStructureIndexWriter(getIndex());
        MDLV2000Reader mdlReader = new MDLV2000Reader();

        while (location.hasNext() && !isCancelled()) {

            InputStream in = location.next();
            String name = location.getEntryName();

            // skip non-mol files
            if (!name.endsWith(".mol"))
                continue;

            try {
                mdlReader.setReader(in);
                IAtomContainer molecule = mdlReader.read(new AtomContainer());
                writer.write(name.substring(0, name.indexOf(".mol")), molecule);
                mdlReader.close();
            } catch (Exception ex) {
                LOGGER.warn("Could not read entry: " + name + " reason: " + ex.getMessage());
            }
        }

        writer.close();
        location.close();

    }

}
