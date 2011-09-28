
package uk.ac.ebi.core;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Externalizable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import org.jboss.serial.io.JBossObjectInputStream;
import org.jboss.serial.io.JBossObjectOutputStream;
import uk.ac.ebi.chemet.entities.reaction.Reaction;
import uk.ac.ebi.chemet.entities.reaction.participant.Participant;
import uk.ac.ebi.core.reconstruction.ReconstructionContents;
import uk.ac.ebi.core.reconstruction.ReconstructionProperites;
import uk.ac.ebi.metabolomes.core.gene.GeneProduct;
import uk.ac.ebi.metabolomes.core.gene.GeneProductCollection;
import uk.ac.ebi.metabolomes.core.compound.MetaboliteCollection;
import uk.ac.ebi.metabolomes.identifier.AbstractIdentifier;
import uk.ac.ebi.metabolomes.identifier.GenericIdentifier;
import uk.ac.ebi.resource.organism.Taxonomy;


/**
 * Reconstruction.java
 * Object to represent a complete reconstruction with genes, reactions and metabolites
 * @author johnmay
 * @date Apr 13, 2011
 */
public class Reconstruction
  implements Externalizable {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(
      Reconstruction.class);
    public static final String PROJECT_FILE_EXTENSION = ".mnb";
    private static final String DATA_FOLDER_NAME = "data";
    private static final String TMP_FOLDER_NAME = "mnb-tmp";
    private static final String GENE_PRODUCTS_FILE_NAME = "serialized-gene-projects.java-bin";
    // main container for the project on the file system
    private File container;
    private HashSet<ReconstructionContents> contents;
    private ReconstructionProperites properties;
    private GenericIdentifier identifier;
    private Taxonomy organismIdentifier; // could be under a generic ReconstructionContents class but this is already used as an enum
    // component collections
    private GeneProductCollection products;
    private List<MetabolicReaction> reactions;
    private MetaboliteCollection metabolites;


    /**
     * Constructor mainly used for creating a new Reconstruction
     * @param id The identifier of the project
     * @param org The organism identifier
     */
    public Reconstruction(GenericIdentifier id, Taxonomy org) {
        identifier = id;
        organismIdentifier = org;
        products = new GeneProductCollection();
        reactions = new ArrayList();
        metabolites = new MetaboliteCollection();
        contents = new HashSet<ReconstructionContents>();
        properties = new ReconstructionProperites();
    }

    /*
     * Default constructor
     */

    private Reconstruction() {
    }


    public GeneProductCollection getGeneProducts() {
        return products;
    }


    public void setGeneProducts(GeneProductCollection newProducts) {
        if( products.numberOfProteinProducts() != 0 ) {
            contents.add(ReconstructionContents.PROTEIN_PRODUCTS);
        }
        products = newProducts;
    }


    /**
     * Add gene products to the project
     * @param otherProducts
     * @return any clashing identifiers (identifiers matching products already in the collection)
     */
    public AbstractIdentifier[] addGeneProducts(GeneProductCollection otherProducts) {

        // if there are protein products present add the contents flag
        if( otherProducts.numberOfProteinProducts() > 0 ) {
            contents.add(ReconstructionContents.PROTEIN_PRODUCTS);
        }

        return products.addAll(otherProducts);

    }


    public void addGeneProduct(GeneProduct proudct) {
        products.addProduct(proudct);
    }


    public List<MetabolicReaction> getReactions() {
        return reactions;
    }


    public void addReaction(MetabolicReaction r) {
        reactions.add(r);
        contents.add(ReconstructionContents.REACTIONS);

        for( Participant<Metabolite, ?, ?> p : r.getAllReactionParticipants() ) {
            if( metabolites.contains(p.getMolecule()) == false ) {
                addMetabolite(p.getMolecule());
            }
        }

    }


    public void addMetabolite(Metabolite entity) {
        metabolites.add(entity);
        contents.add(ReconstructionContents.METABOLITES);
    }


    public MetaboliteCollection getMetabolites() {
        return metabolites;
    }


    public void setContainer(File container) {
        this.container = container;
    }


    public File getContainer() {
        if( container == null ) {
            return new File(getIdentifier() + PROJECT_FILE_EXTENSION);
        }
        return container;
    }


    public AbstractIdentifier getIdentifier() {
        return identifier;
    }


    public void setIdentifier(GenericIdentifier identifier) {
        this.identifier = identifier;
    }


    /**
     * Loads a reconstruction from a given container
     */
    public static Reconstruction load(File container) throws IOException, ClassNotFoundException {

        File file = new File(container, "recon.extern");
        ObjectInput in = new ObjectInputStream(new FileInputStream(file));
        Reconstruction reconstruction = new Reconstruction();
        reconstruction.readExternal(in);

        return reconstruction;

    }


    /**
     * Saves the project and it's data
     */
    public void save() {
        if( container != null ) {
            try {
                ObjectOutput out = new ObjectOutputStream(new FileOutputStream(
                  new File(container, "recon.extern")));
                this.writeExternal(out);
                out.close();
            } catch( FileNotFoundException ex ) {
                logger.error("error saving project", ex);
            } catch( IOException ex ) {
                logger.error("error saving project", ex);
            }
        } else {
            // prompt
            JOptionPane.showMessageDialog(null,
                                          "No previous save found please use save as");
        }
    }


    public void saveAsProject(File projectRoot) {

        if( !projectRoot.getPath().endsWith("mnb") ) {
            projectRoot = new File(projectRoot.getPath() + ".mnb");
        }

        // create folder
        if( !projectRoot.exists() ) {
            logger.info("Saving project as " + projectRoot);
            setContainer(projectRoot);
            container.mkdir();
            getDataDirectory().mkdir();
            save();
            //  setTmpDir();
        } else if( projectRoot.equals(container) ) {
            save();
        } else {
            JOptionPane.showMessageDialog(null,
                                          "Cannot overwrite a different project");
        }
    }


    @Override
    public String toString() {
        return identifier.toString();
    }


    public void addContents(ReconstructionContents newContent) {
        contents.add(newContent);
    }


    public Set<ReconstructionContents> getContents() {
        return Collections.unmodifiableSet(contents);
    }


    private File getDataDirectory() {
        return new File(container, DATA_FOLDER_NAME);
    }


    public void writeExternal(ObjectOutput out) throws IOException {

        out.writeUTF(container.getAbsolutePath());

        identifier.writeExternal(out);
        organismIdentifier.writeExternal(out);

        properties.writeExternal(out);

        // products
        products.writeExternal(out);

        // metabolites
        out.writeInt(metabolites.size());
        for( Metabolite metabolite : metabolites ) {
            metabolite.writeExternal(out);
        }
        
        // reactions
        out.writeInt(reactions.size());
        for( MetabolicReaction reaction : reactions ) {
            reaction.writeExternal(out, metabolites);
            // already writen so don't need to write
        }



    }


    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

        container = new File(in.readUTF());

        // ids
        identifier = new GenericIdentifier();
        identifier.readExternal(in);
        organismIdentifier = new Taxonomy();
        organismIdentifier.readExternal(in);

        // properties
        properties = new ReconstructionProperites();
        properties.readExternal(in);
        contents = properties.getProjectContents();

        // products
        products = new GeneProductCollection();
        products.readExternal(in);



        // metabolites
        metabolites = new MetaboliteCollection();
        int nMets = in.readInt();
        for( int i = 0 ; i < nMets ; i++ ) {
            Metabolite m = new Metabolite();
            m.readExternal(in);
            metabolites.add(m);
        }

        // reactions
        reactions = new ArrayList();

        int nRxns = in.readInt();
        for( int i = 0 ; i < nRxns ; i++ ) {
            MetabolicReaction r = new MetabolicReaction();
            r.readExternal(in, metabolites);
            reactions.add(r);
        }

    }


}
