package uk.ac.ebi.chemet.render.reaction;

/**
 * ParticipantEditor.java
 *
 * 2012.02.13
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
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import javax.swing.*;
import org.apache.log4j.Logger;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.templates.MoleculeFactory;
import uk.ac.ebi.annotation.chemical.AtomContainerAnnotation;
import uk.ac.ebi.caf.component.factory.ComboBoxFactory;
import uk.ac.ebi.caf.component.factory.FieldFactory;
import uk.ac.ebi.caf.component.factory.LabelFactory;
import uk.ac.ebi.core.DefaultEntityFactory;
import uk.ac.ebi.core.Reconstruction;
import uk.ac.ebi.core.ReconstructionManager;
import uk.ac.ebi.core.reaction.Membrane;
import uk.ac.ebi.core.reaction.MetabolicParticipantImplementation;
import uk.ac.ebi.core.reaction.Tissue;
import uk.ac.ebi.core.reaction.compartment.CellType;
import uk.ac.ebi.core.reaction.compartment.Organ;
import uk.ac.ebi.core.reaction.compartment.Organelle;
import uk.ac.ebi.interfaces.entities.EntityFactory;
import uk.ac.ebi.interfaces.entities.MetabolicParticipant;
import uk.ac.ebi.interfaces.entities.Metabolite;
import uk.ac.ebi.interfaces.reaction.Compartment;
import uk.ac.ebi.render.molecule.MoleculeRenderer;
import uk.ac.ebi.resource.chemical.BasicChemicalIdentifier;


/**
 *
 *          ParticipantEditor 2012.02.13
 * @version $Rev$ : Last Changed $Date$
 * @author  johnmay
 * @author  $Author$ (this version)
 *
 *          Class defines an editor for reaction participants
 *
 */
public class ParticipantEditor extends JPanel {

    private static final Logger LOGGER = Logger.getLogger(ParticipantEditor.class);

    private MetabolicParticipant participant;

    // user-entry
    private JComboBox compartment;

    private JTextField metabolite;

    private JLabel structure;

    private JTextField stoichiometry;

    private static ReconstructionManager MANAGER = ReconstructionManager.getInstance();

    // factories
    private static final MoleculeRenderer RENDERER = MoleculeRenderer.getInstance();


    public ParticipantEditor() {


        setOpaque(false);
        setLayout(new FormLayout("right:p, 1dlu, p", "min, 2dlu, min, 2dlu, min"));

        compartment = ComboBoxFactory.newComboBox(getCompartments());
        compartment.setRenderer(new DefaultListCellRenderer() {

            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                String desc = ((Compartment) value).getDescription();
                this.setText(desc.substring(0, Math.min(desc.length(), 20)));
                this.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
                return this;
            }
        });
        metabolite = FieldFactory.newField(12);
        stoichiometry = FieldFactory.newField(3);
        structure = LabelFactory.newLabel("");

        CellConstraints cc = new CellConstraints();
        add(compartment, cc.xyw(1, 1, 3));
        add(structure, cc.xyw(1, 3, 3, CellConstraints.CENTER, cc.CENTER));
        add(stoichiometry, cc.xy(1, 5));
        add(metabolite, cc.xy(3, 5));

    }


    public void setParticipant(MetabolicParticipant participant) {
        this.participant = participant;

        metabolite.setText(participant.getMolecule().getName());
        compartment.setSelectedItem(participant.getCompartment().getDescription());
        stoichiometry.setText(participant.getCoefficient().toString());


        try {
            if (participant.getMolecule().hasStructure()) {
                structure.setIcon(new ImageIcon(RENDERER.getImage(participant.getMolecule().getStructures().iterator().next().getStructure(),
                                                                  new Rectangle(128, 128))));
                structure.setText("");
            } else {
                structure.setText("No structure");
            }
        } catch (CDKException ex) {
            LOGGER.warn("Unable to set structure: " + ex.getMessage());
        }

    }


    /**
     * 
     * Access the participant with the new edited values.
     * If the name is empty (i.e. no molecule) null is returned
     * 
     */
    public MetabolicParticipant getParticipant() {

        String name = metabolite.getText().trim();

        if (name.isEmpty()) {
            return null;
        }

        Double coef = 1d;

        try {
            coef = Double.parseDouble(stoichiometry.getText());
        } catch (NumberFormatException ex) {
            LOGGER.warn("Invalid coefficient");
        }

        if (participant == null) {
            participant = new MetabolicParticipantImplementation();
        }

        Reconstruction recon = MANAGER.getActive();
        Collection<Metabolite> candidates = recon.getMetabolome().get(metabolite.getText());

        Metabolite entity;
        if (candidates.iterator().hasNext()) {
            entity = candidates.iterator().next();
        } else {
            entity = DefaultEntityFactory.getInstance().newInstance(Metabolite.class, BasicChemicalIdentifier.nextIdentifier(),
                                                                    metabolite.getText().trim(),
                                                                    metabolite.getText().trim());
            recon.addMetabolite(entity);
        }

        Metabolite molecule = participant.getMolecule();
        if (molecule != entity) {
            participant.setMolecule(entity);
        }

        participant.setCoefficient(coef);

        participant.setCompartment((Compartment) compartment.getSelectedItem());


        return participant;

    }


    private static Compartment[] getCompartments() {

        List<Compartment> compartments = new ArrayList<Compartment>();

        compartments.addAll(Arrays.asList(Organelle.values()));
        compartments.addAll(Arrays.asList(Membrane.values()));
        compartments.addAll(Arrays.asList(CellType.values()));
        compartments.addAll(Arrays.asList(Tissue.values()));
        compartments.addAll(Arrays.asList(Organ.values()));

        return compartments.toArray(new Compartment[0]);

    }


    public static void main(String[] args) {

        EntityFactory ef = DefaultEntityFactory.getInstance();

        Metabolite m = ef.newInstance(Metabolite.class, BasicChemicalIdentifier.nextIdentifier(), "ATP", "atp");
        m.addAnnotation(new AtomContainerAnnotation(MoleculeFactory.make123Triazole()));
        MetabolicParticipant mp = new MetabolicParticipantImplementation(m, 2.5d, Organelle.CYTOPLASM);

        ParticipantEditor pe = new ParticipantEditor();
        pe.setParticipant(mp);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(pe);
        frame.pack();
        frame.setVisible(true);

    }
}