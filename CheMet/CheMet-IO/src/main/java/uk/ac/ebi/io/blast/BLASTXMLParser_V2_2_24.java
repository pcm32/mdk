/**
 * BLASTXMLParser_V2_2_24.java
 *
 * 2011.10.10
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
package uk.ac.ebi.io.blast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import org.apache.log4j.Logger;
import org.codehaus.stax2.XMLStreamReader2;
import uk.ac.ebi.interfaces.AnnotatedEntity;
import uk.ac.ebi.interfaces.TaskOptions;
import uk.ac.ebi.io.blast.xml.setters.*;
import uk.ac.ebi.observation.sequence.LocalAlignment;

/**
 * @name    BLASTXMLParser_V2_2_24 - 2011.10.10 <br>
 *          Blast XML Parser for version 2 2 4
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 */
public class BLASTXMLParser_V2_2_24 implements BLASTXMLParser {

    private static final Logger LOGGER = Logger.getLogger(BLASTXMLParser_V2_2_24.class);
    private Map<String, AlignmentSetter> setters = new HashMap();

    public BLASTXMLParser_V2_2_24() {
        setters.put("Hit_id", new SubjectSetter());
        setters.put("Hit_len", new LengthSetter());
        setters.put("Hsp_bit-score", new BitScoreSetter());
        setters.put("Hsp_evalue", new ExpectedValueSetter());
        setters.put("Hsp_query-from", new QueryStartSetter());
        setters.put("Hsp_query-to", new QueryEndSetter());
        setters.put("Hsp_hit-from", new SubjectStartSetter());
        setters.put("Hsp_hit-to", new SubjectEndSetter());
        setters.put("Hsp_identity", new IdentitySetter());
        setters.put("Hsp_positive", new PositiveSetter());
        setters.put("Hsp_qseq", new QuerySequenceSetter());
        setters.put("Hsp_hseq", new SubjectSequenceSetter());
        setters.put("Hsp_midline", new AlignmentSequenceSetter());
    }

    public Collection<LocalAlignment> parse(Map<String, ? extends AnnotatedEntity> entities, TaskOptions options, XMLStreamReader2 xmlr) throws XMLStreamException {

        // System.out.println("Reading new iteration...");
        Collection<LocalAlignment> alignments = new ArrayList();
        String queryIdentifier = "";

        while (xmlr.hasNext()) {
            int eventtype = xmlr.next();
            String name = xmlr.hasName() ? xmlr.getName().toString() : "";
            switch (eventtype) {
                case XMLEvent.START_ELEMENT:
                    if (name.equals("Hit")) {
                        LocalAlignment alignment = getLocalAlignment(xmlr);

                        alignment.setQuery(queryIdentifier);
                        alignment.setTaskOptions(options);
                        // add to the entity if provided
                        if (entities.containsKey(queryIdentifier)) {
                            entities.get(queryIdentifier).addObservation(alignment);
                        }
                        alignments.add(alignment);
                    } else if (name.equals("Iteration_query-def")) {
                        xmlr.next();
                        queryIdentifier = xmlr.getText();
//                        queryIdentifier = queryIdentifier.substring(0, queryIdentifier.indexOf(' '));
                    }
                    break;

                case XMLEvent.END_ELEMENT:
                    if (name.equals("Iteration")) {
                        return alignments;
                    }
                    break;
            }
        }

        return alignments;

    }

    public LocalAlignment getLocalAlignment(XMLStreamReader2 xmlr) throws XMLStreamException {

        LocalAlignment alignment = new LocalAlignment();

        while (xmlr.hasNext()) {
            int eventtype = xmlr.next();
            String name = xmlr.hasName() ? xmlr.getName().toString() : "";
            switch (eventtype) {
                case XMLEvent.START_ELEMENT:
                    if (setters.containsKey(name)) {
                        setters.get(name).set(alignment, xmlr);
                    } else if (name.equals("Hsp_num")) {
                        xmlr.next();
                        if (xmlr.getText().equals("1") == false) {
                            LOGGER.error("Multiple HSPs detected. this is not handled");
                            return alignment;
                        }
                    }
                    break;
                case XMLEvent.END_ELEMENT:
                    if (name.equals("Hit")) {
                        return alignment;
                    }
                    break;
            }
        }
        return alignment;
    }
}