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
package uk.ac.ebi.mdk.domain.entity;

import uk.ac.ebi.mdk.domain.identifier.Identifier;


/**
 * @name    ReconstructionEntity - 2011.10.10 <br>
 *          Interface description
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 */
public interface Entity {

    public String getName();


    public String getAbbreviation();


    public Identifier getIdentifier();


    public void setIdentifier(Identifier identifier);


    public void setName(String name);


    public void setAbbreviation(String abbreviation);


    /**
     * Access a string representation of the entity's identifier
     */
    public String getAccession();


    /**
     * Create a new instance of this entity
     * @return e the new instance
     */
    public Entity newInstance();
}
