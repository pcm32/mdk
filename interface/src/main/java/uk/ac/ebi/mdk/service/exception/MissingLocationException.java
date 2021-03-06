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

package uk.ac.ebi.mdk.service.exception;

/**
 * MissingLocationException.java - 20.02.2012 <br/>
 * <p/>
 * Exception should thrown when a loader is missing a required {@see ResourceLocation}
 *
 * @author johnmay
 * @author $Author$ (this version)
 * @version $Rev$
 */
public class MissingLocationException extends RuntimeException {

    /**
     * Construct an instance of the exception with the specified message
     *
     * @param message details of what resource location is missing
     */
    public MissingLocationException(String message) {
        super(message);
    }


}
