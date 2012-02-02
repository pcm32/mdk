/**
 * DefaultEntityFactory.java
 *
 * 2012.02.02
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
package uk.ac.ebi.core;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import uk.ac.ebi.interfaces.entities.Entity;
import uk.ac.ebi.interfaces.entities.EntityFactory;
import uk.ac.ebi.interfaces.identifiers.Identifier;


/**
 *
 *          DefaultEntityFactory 2012.02.02
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 *
 *          Singleton description
 *
 */
public class DefaultEntityFactory
        implements EntityFactory {

    private static final Logger LOGGER = Logger.getLogger(DefaultEntityFactory.class);

    private Map<Class, Entity> entites = new HashMap<Class, Entity>();


    private DefaultEntityFactory() {

        //entites.put(uk.ac.ebi.interfaces.entities.Metabolite.class, new Metabolite());

        for (Entity entity : Arrays.asList(new MetaboliteImplementation(),
                                           new MetabolicReaction(),
                                           new ProteinProduct(),
                                           new RibosomalRNA(),
                                           new TransferRNA(),
                                           new GeneImplementation(),
                                           new ChromosomeImplementation(),
                                           new GenomeImplementation())) {

            for (Class c : entity.getClass().getInterfaces()) {
                if (Entity.class.isAssignableFrom(c)) {
                    entites.put(c, entity);
                }
            }
        }

    }


    public static DefaultEntityFactory getInstance() {
        return DefaultEntityFactoryHolder.INSTANCE;
    }


    public <E extends Entity> E newInstance(Class<E> c) {
        return (E) entites.get(c).newInstance();
    }


    public <E extends Entity> E newInstance(Class<E> c,
                                            Identifier identifier,
                                            String name,
                                            String abbr) {

        E entity = (E) entites.get(c).newInstance();

        entity.setIdentifier(identifier);
        entity.setName(name);
        entity.setAbbreviation(abbr);

        return entity;

    }


    private static class DefaultEntityFactoryHolder {

        private static final DefaultEntityFactory INSTANCE = new DefaultEntityFactory();
    }
}