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

package uk.ac.ebi.mdk.domain.annotation.primitive;

import uk.ac.ebi.mdk.domain.annotation.Annotation;

/**
 *          BasicStringAnnotation - 2011.12.08 <br>
 *          Interface specifies that annotation can be parsed as a simple string (simplifying editing and IO operations)
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 */
public interface DoubleAnnotation extends Annotation {

    public Double getValue();

    public void setValue(Double value);

}
