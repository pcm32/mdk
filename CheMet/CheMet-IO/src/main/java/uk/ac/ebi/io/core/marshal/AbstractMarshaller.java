/**
 * AbstractMarshal.java
 *
 * 2012.01.31
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
package uk.ac.ebi.io.core.marshal;

import org.apache.log4j.Logger;
import uk.ac.ebi.caf.utility.version.Version;
import uk.ac.ebi.interfaces.entities.EntityFactory;
import uk.ac.ebi.interfaces.io.marshal.MarshallFactory;
import uk.ac.ebi.io.core.MarshallFactoryImplementation;


/**
 *
 *          AbstractMarshal 2012.01.31
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 *
 *          Class description
 *
 */
public abstract class AbstractMarshaller {

    private static final Logger LOGGER = Logger.getLogger(AbstractMarshaller.class);

    private Version v;

    private EntityFactory factory;

    private MarshallFactory marshallers;


    public void setMarshallFactory(MarshallFactory factory) {
        this.marshallers = factory;
    }


    public MarshallFactory getMarshallFactory() {
        return marshallers;
    }


    public void setEntityFactory(EntityFactory factory) {
        this.factory = factory;
    }


    public EntityFactory getEntityFactory() {
        return factory;
    }


    public AbstractMarshaller(Version v) {
        this.v = v;
    }


    public Version getVersion() {
        return v;
    }
}
