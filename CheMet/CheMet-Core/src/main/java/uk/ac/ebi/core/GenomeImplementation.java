/**
 * GenomeImplementation.java
 *
 * 2011.10.18
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

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import uk.ac.ebi.interfaces.Chromosome;
import uk.ac.ebi.interfaces.Gene;
import uk.ac.ebi.interfaces.Genome;

/**
 *          GenomeImplementation - 2011.10.18 <br>
 *          Implementation of genome interface
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 */
public class GenomeImplementation implements Genome {

    private static final Logger LOGGER = Logger.getLogger(GenomeImplementation.class);
    private Map<Integer, Chromosome> chromosomes = new HashMap();

    public GenomeImplementation() {
    }

    /**
     * @inheritDoc
     */
    public Collection<Chromosome> getChromosomes() {
        return chromosomes.values();
    }

    /**
     * @inheritDoc
     */
    public Collection<Gene> getGenes() {

        List<Gene> genes = new ArrayList();

        for (Chromosome c : getChromosomes()) {
            genes.addAll(c.getGenes());
        }

        return genes;

    }

    /**
     * @inheritDoc
     */
    public Chromosome getChromosome(int number) {

        if (chromosomes.containsKey(number)) {
            return chromosomes.get(number);
        }

        throw new InvalidParameterException("Chromosome number "
                                            + number
                                            + " not pressent in genome");

    }

    /**
     * @inheritDoc
     */
    public boolean add(Chromosome chromosome) {
        return chromosomes.put(chromosome.getChromosomeNumber(), chromosome) != null;
    }

    /**
     * @inheritDoc
     */
    public boolean remove(Chromosome chromosome) {
        return chromosomes.remove(chromosome.getChromosomeNumber()) != null;
    }

    /**
     * @inheritDoc
     */
    public boolean add(int number, Gene gene) {
        return getChromosome(number).add(gene);
    }

    /**
     * @inheritDoc
     */
    public void read(ObjectInput in) throws IOException, ClassNotFoundException {
        int nChromsomes = in.readInt();
        for (int i = 0; i < nChromsomes; i++) {
            Chromosome c = new ChromosomeImplementation(in);
            add(c);
        }
    }

    /**
     * @inheritDoc
     */
    public void write(ObjectOutput out) throws IOException {

        out.writeInt(chromosomes.size());
        for (Chromosome c : getChromosomes()) {
            c.writeExternal(out);
        }

    }

    /**
     * Access a gene for the given index on the given chromosome
     */
    public Gene getGene(int number, int index) {
        if (chromosomes.containsKey(number)) {
            return chromosomes.get(number).getGenes().get(index);
        }
        return null;
    }

    public int[] getIndex(Gene gene) {
        for (Chromosome c : getChromosomes()) {
            int index = c.getGenes().indexOf(gene);
            if (index != -1);
            return new int[]{c.getChromosomeNumber(), index};
        }
        return new int[]{-1, -1};
    }
}
