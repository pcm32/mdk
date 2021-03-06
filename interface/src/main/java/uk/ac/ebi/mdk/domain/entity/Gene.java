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

import org.biojava3.core.sequence.Strand;
import org.biojava3.core.sequence.template.Sequence;
import uk.ac.ebi.mdk.domain.entity.collection.Chromosome;

/**
 *          Gene – 2011.09.12 <br>
 *          Interface describing a gene
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 */
public interface Gene extends AnnotatedEntity {

    /**
     *
     * Access the chromosome this gene is present on
     *
     * @return
     *
     */
    public Chromosome getChromosome();

    public void setChromosome(Chromosome chromosome);

    /**
     *
     * Access the sense the gene is sense (+)/3'-5'/watson or anti-sense (-)/5'-3'/crick
     *
     * @return
     *
     */
    public Strand getStrand();

    public void setStrand(Strand strand);

    /**
     *
     * Access the start location of the gene
     *
     * @return
     *
     */
    public int getStart();

    public void setStart(int end);

    /**
     *
     * Access the end location of the gene
     *
     * @return
     */
    public int getEnd();

    public void setEnd(int end);

    /**
     *
     * Access the length of the gene
     *
     * @return
     */
    public int getLength();

    /**
     * Access the sequence of the gene
     * @return
     */
    public Sequence getSequence();

    /**
     * Set the sequence of the gene (normally derived from the start and end
     * with the entire genome sequence
     * @param sequence
     */
    public void setSequence(Sequence sequence);
}
