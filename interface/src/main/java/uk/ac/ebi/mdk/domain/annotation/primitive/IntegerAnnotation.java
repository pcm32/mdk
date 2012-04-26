/**
 * BasicStringAnnotation.java
 *
 * 2011.12.08
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
package uk.ac.ebi.mdk.domain.annotation.primitive;

import uk.ac.ebi.mdk.domain.annotation.Annotation;

/**
 *          BasicStringAnnotation - 2011.12.08 <br>
 *          Interface specifies that annotation can be parsed as a simple string (simplifying editing and IO operations)
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 */
public interface IntegerAnnotation extends Annotation {

    public Integer getValue();

    public void setValue(Integer value);

    public Annotation getInstance(Integer value);

}