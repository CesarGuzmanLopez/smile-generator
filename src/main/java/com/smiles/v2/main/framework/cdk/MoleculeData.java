package com.smiles.v2.main.framework.cdk;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.interfaces.IBond;
import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.interfaces.AtomInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;

public class MoleculeData implements MoleculeDataInterface {
    private Molecule molecule;
    private IAtomContainer moleculeContainer;
    private List<AtomInterface> listAtoms;
    private List<AtomInterface> selectedList;

    MoleculeData(final Molecule molecule) {
        this.molecule = molecule;
        final SmilesParser smileParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        try {
            moleculeContainer = smileParser.parseSmiles(molecule.getSmile());
        } catch (Exception e) {
            throw new IllegalArgumentException("Molecule error");
        }
        selectedList = new ArrayList<>();
        listAtoms = new ArrayList<>();
        for (int i = 0; i < moleculeContainer.getAtomCount(); i++) {
            listAtoms.add(new AtomSelectable(moleculeContainer.getAtom(i), i));
        }
    }

    public MoleculeData(final Molecule molecule, final MoleculeDataInterface moleculeData) {
        this.molecule = molecule;
        try {
            moleculeContainer = ((MoleculeData) moleculeData).getMoleculeContainer().clone();
        } catch (Exception e) {
            throw new IllegalArgumentException("Molecule clone error");
        }
        listAtoms = new ArrayList<>();
        selectedList = new ArrayList<>();
        for (int i = 0; i < moleculeContainer.getAtomCount(); i++) {
            final AtomSelectable temporal = new AtomSelectable(moleculeContainer.getAtom(i), i);
            listAtoms.add(temporal);
            for (AtomInterface atom : moleculeData.getListAtomsSelected()) {
                if (atom.getId() == i) {
                    selectOrderAtom(temporal);
                    break;
                }
            }
        }
    }

    /**
     * @return the molecule associated with this molecule data.
     */
    protected Molecule getMolecule() {
        return molecule;
    }

    class AtomSelectable extends AtomCDK {
        AtomSelectable(final IAtom atom, final int id) {
            super(atom, id);
        }

        @Override
        public void selected() {
            super.selected();
        }
    }

    /**
     * @return the molecule container associated with this molecule data.
     */
    public IAtomContainer getMoleculeContainer() {
        return moleculeContainer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberAtoms() {
        return moleculeContainer.getAtomCount();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AtomInterface> getListAtoms() {
        return listAtoms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectOrderAtom(final AtomInterface atom) {

        if (!selectedList.contains(atom)) {
            selectedList.add(atom);
        } else {
            selectedList.remove(atom);
        }
        ((AtomSelectable) atom).selected();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AtomInterface> getListAtomsSelected() {
        return selectedList;

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final Molecule moleculeToCompare) {
        final MoleculeData moleculeData = ((MoleculeData) moleculeToCompare.getMoleculeData());
        final IAtomContainer iAtomContainerToCompare = moleculeData.moleculeContainer;
        final SmilesGenerator generator = new SmilesGenerator(SmiFlavor.Isomeric);
        try {
            final String smiles1 = generator.create(moleculeContainer);
            final String smiles2 = generator.create(iAtomContainerToCompare);
            return smiles1.compareTo(smiles2);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Error in compareTo");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String isomericSmile() {
        final SmilesGenerator generator = new SmilesGenerator(SmiFlavor.Isomeric);
        try {
            return generator.create(moleculeContainer);
        } catch (Exception e) {
            throw new UnsupportedOperationException("Error in Isomeric Smile");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean addMoleculeDataInterface(final Molecule moleculeSubstituent, final AtomInterface selectedPrincipal,
            final AtomInterface selectedSubstituent) {
        final int numAtomInit = moleculeContainer.getAtomCount();
        final MoleculeData moleculeDataSubstituent = (MoleculeData) moleculeSubstituent.getMoleculeData();
        moleculeContainer.add(moleculeDataSubstituent.moleculeContainer);
        moleculeContainer.addBond(selectedPrincipal.getId(), numAtomInit + selectedSubstituent.getId(),
                IBond.Order.SINGLE);

        return false;

    }

}
