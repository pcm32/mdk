package uk.ac.ebi.mdk.io.annotation.primitive;


import uk.ac.ebi.mdk.domain.annotation.DefaultAnnotationFactory;
import uk.ac.ebi.caf.utility.version.annotation.CompatibleSince;
import uk.ac.ebi.mdk.io.AnnotationReader;
import uk.ac.ebi.mdk.domain.annotation.primitive.StringAnnotation;

import java.io.DataInput;
import java.io.IOException;

/**
 * StringAnnotationReader - 09.03.2012 <br/>
 * <p/>
 * Read's a string annotation from {@see DataInput}
 *
 * @author johnmay
 * @author $Author$ (this version)
 * @version $Rev$
 */
@CompatibleSince("0.9")
public class StringAnnotationReader
    implements AnnotationReader<StringAnnotation> {

    private DataInput in;
    private Class<? extends StringAnnotation> c;
    private static final DefaultAnnotationFactory FACTORY = DefaultAnnotationFactory.getInstance();

    public StringAnnotationReader(Class<? extends StringAnnotation> c, DataInput in){
        this.in = in;
        this.c  = c;
    }

    public StringAnnotation readAnnotation() throws IOException {
        StringAnnotation annotation = FACTORY.ofClass(c);
        annotation.setValue(in.readUTF());
        return annotation;
    }

}
