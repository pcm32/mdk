package uk.ac.ebi.mdk.io.annotation.task;

import org.apache.log4j.Logger;
import uk.ac.ebi.mdk.domain.annotation.task.FileParameter;
import uk.ac.ebi.caf.utility.version.annotation.CompatibleSince;
import uk.ac.ebi.mdk.io.AnnotationReader;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;

/**
 * ParameterWriter - 10.03.2012 <br/>
 * <p/>
 * Class descriptions.
 *
 * @author johnmay
 * @author $Author$ (this version)
 * @version $Rev$
 */
@CompatibleSince("0.9")
public class FileParameterReader implements AnnotationReader<FileParameter> {

    private static final Logger LOGGER = Logger.getLogger(FileParameterReader.class);

    DataInput in;

    public FileParameterReader(DataInput in) {
        this.in = in;
    }

    @Override
    public FileParameter readAnnotation() throws IOException {

        return new FileParameter(in.readUTF(),
                                 in.readUTF(),
                                 in.readUTF(),
                                 new File(in.readUTF()));


    }
}
