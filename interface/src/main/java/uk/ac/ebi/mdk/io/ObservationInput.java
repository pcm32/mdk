/*
 * Copyright (C) 2012  John May and Pablo Moreno
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package uk.ac.ebi.mdk.io;

import uk.ac.ebi.mdk.domain.observation.Observation;

import java.io.IOException;
import java.util.Collection;

/**
 * ObservationInput - 11.03.2012 <br/>
 * <p/>
 * Describes input for multiple observations
 *
 * @author johnmay
 * @author $Author$ (this version)
 * @version $Rev$
 */
public interface ObservationInput {

    /**
     * Read the next observation in the input.
     * @param <O>
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public <O extends Observation> O read() throws IOException, ClassNotFoundException;

    public <O extends Observation> O read(Class<O> c) throws IOException, ClassNotFoundException;

    public Class readClass() throws IOException, ClassNotFoundException;

    public Collection<Observation> readCollection() throws IOException, ClassNotFoundException;
    
}
