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

import uk.ac.ebi.mdk.domain.identifier.Identifier;

import java.io.IOException;

/**
 * IdentifierInput - 11.03.2012 <br/>
 * <p/>
 * The IdentifierInput interface describes a class that can read identifiers
 * from a stream.
 *
 * @author johnmay
 * @author $Author$ (this version)
 * @version $Rev$
 */
public interface IdentifierInput {

    /**
     * Read the next identifier from the stream. This method will read the class and the data
     * and return a new instance of the read identifier.
     *
     * @param <I> cast to this type (convenience)
     *
     * @return new instance of a read identifier
     *
     * @throws IOException            low-level io error
     * @throws ClassNotFoundException low-level io error
     */
    public <I extends Identifier> I read() throws IOException, ClassNotFoundException;

    /**
     * Read an identifier data of the provided class. This method is used when identifiers
     * were written with {@see IdentifierOutput#writeData(Identifier)}.
     *
     * @param c   type of identifier that will be read
     * @param <I> cast to this type (convenience)
     *
     * @return new instance of an identifier of class (I) with read data
     *
     * @throws IOException            low-level io error
     * @throws ClassNotFoundException low-level io error
     */
    public <I extends Identifier> I read(Class<I> c) throws IOException, ClassNotFoundException;

}
