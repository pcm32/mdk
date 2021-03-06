package uk.ac.ebi.mdk.io;

import org.apache.log4j.Logger;
import org.junit.Test;
import uk.ac.ebi.caf.utility.version.Version;
import uk.ac.ebi.caf.utility.version.VersionMap;
import uk.ac.ebi.caf.utility.version.annotation.CompatibleSince;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;

/**
 * @author johnmay
 * @author $Author$ (this version)
 * @version $Rev$
 */
public class IdentifierDataOutputStreamTest {

    private static final Logger LOGGER = Logger.getLogger(IdentifierDataOutputStreamTest.class);

    @Test
    public void testVersionTagsExist() throws IOException {
        IdentifierDataOutputStream manager = new IdentifierDataOutputStream(new DataOutputStream(new ByteArrayOutputStream()),
                                                                            new Version("1.0"));
        for(VersionMap map : manager.getMarshals().values()){
            for(Object o : map.values()){
                assertNotNull(o.getClass().getAnnotation(CompatibleSince.class));
            }
        }

    }

}
