package com.smiles.v2.main.framework.cdk;

import java.util.ArrayList;
import java.util.List;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.exception.InvalidSmilesException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IBond.Order;
import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.interfaces.AtomInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;

public class MoleculeData implements MoleculeDataInterface {
    private Molecule molecule;
    private IAtomContainer moleculeContainer;
    private List<AtomInterface> listAtoms;
    private List<AtomInterface> selectedList;

    MoleculeData( Molecule molecule) {//NOSONAR no necessary clone molecule
        this.molecule = molecule;
        final SmilesParser smileParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        try {
            moleculeContainer = smileParser.parseSmiles(molecule.getSmile());
        } catch (InvalidSmilesException e) {
            throw new IllegalArgumentException("Invalid Smiles: " + molecule.getSmile());
        }
        selectedList = new ArrayList<>();
        listAtoms = new ArrayList<>();
        for (int i = 0; i < moleculeContainer.getAtomCount(); i++) {
            listAtoms.add(new AtomSelectable(moleculeContainer.getAtom(i), i));
        }
    }

    public MoleculeData( Molecule molecule, final MoleculeDataInterface moleculeData) { //NOSONAR No clone
        this.molecule = molecule;
        try {
            moleculeContainer = ((MoleculeData) moleculeData).getMoleculeContainer().clone();
        } catch (Exception e) { // NOSONAR
            throw new IllegalArgumentException("Molecule clone error");
        }
        listAtoms = new ArrayList<>();
        selectedList = new ArrayList<>();
        for (int i = 0; i < moleculeContainer.getAtomCount(); i++) {
            final AtomSelectable temporal = new AtomSelectable(moleculeContainer.getAtom(i), i);
            listAtoms.add(temporal);
        }
        for (AtomInterface atom : moleculeData.getListAtomsSelected()) {
            AtomInterface atomToSelected = getAtom(atom.getId());
            selectedList.add(atomToSelected);
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
        final SmilesGenerator generator = new SmilesGenerator(SmiFlavor.Absolute);
        try {
            final String smiles1 = generator.create(moleculeContainer);
            final String smiles2 = generator.create(iAtomContainerToCompare);
            return smiles1.compareTo(smiles2);
        } catch (CDKException e) {
            throw new UnsupportedOperationException("Error in compareTo + SMILES could not be created");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final String absoluteSmile() {
        final SmilesGenerator generator = new SmilesGenerator(SmiFlavor.Absolute);
        try {
            return generator.create(moleculeContainer);
        } catch (CDKException e) {
            throw new UnsupportedOperationException("Error in CDKException Smile");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMoleculeData(final Molecule moleculeSubstituent, final Integer selectedPrincipal,
            final Integer selectedSubstituent, final Integer numBond) {
        int initNumAtoms = moleculeContainer.getAtomCount();
        Order bond = selectBond(numBond);
        int numAtomPrincipalSelected = (selectedPrincipal != null) ? selectedPrincipal : 0; // NOSONAR
        int numAtomSubstituentSelected = (selectedSubstituent != null)
                ? moleculeContainer.getAtomCount() + selectedSubstituent // NOSONAR
                : moleculeContainer.getAtomCount();
        MoleculeData moleculeDataSubstituent = (MoleculeData) moleculeSubstituent.getMoleculeData();
        moleculeContainer.add(moleculeDataSubstituent.moleculeContainer);
        int totalNumAtoms = moleculeContainer.getAtomCount();
        for (int i = initNumAtoms; i < totalNumAtoms; i++) {
            final AtomSelectable temporal = new AtomSelectable(moleculeContainer.getAtom(i), i);
            listAtoms.add(temporal);
        }
        if (molecule.hasHydrogenImplicit()) {
            numAtomPrincipalSelected = realSelectedAndDecrease(numAtomPrincipalSelected, numBond);
        }
        if (moleculeSubstituent.hasHydrogenImplicit()) {
            numAtomSubstituentSelected = realSelectedAndDecrease(numAtomSubstituentSelected, numBond);
        }
        moleculeContainer.addBond(numAtomPrincipalSelected, numAtomSubstituentSelected, bond);
        /* unselected IAtom */
        if (selectedPrincipal != null) selectOrderAtom(getAtom(selectedPrincipal));
    }

    /**
     *
     * @param numBond
     * @return The type bond selected
     */
    private Order selectBond(final Integer numBond) {
        Order bond;
        switch (numBond) {
        case 1:// NOSONAR
            bond = IBond.Order.SINGLE;
            break;
        case 2:// NOSONAR
            bond = IBond.Order.DOUBLE;
            break;
        case 3:// NOSONAR
            bond = IBond.Order.TRIPLE;
            break;
        case 4:// NOSONAR
            bond = IBond.Order.QUADRUPLE;
            break;
        case 5:// NOSONAR
            bond = IBond.Order.QUINTUPLE;
            break;
        case 6:// NOSONAR
            bond = IBond.Order.SEXTUPLE;
            break;
        default:
            bond = IBond.Order.SINGLE;
            break;
        }
        return bond;
    }

    /**
     * @param selected
     * @param numBond
     * @return the number of atom selected and decreased
     */
    private int realSelectedAndDecrease(final Integer selected, final Integer numBond) {
        int numReal = selected;
        if (getAtom(selected).getImplicitHydrogens() < numBond) {
            AtomCDK atom = (AtomCDK) getAtom(selected);
            for (IBond bond : atom.bonds()) {
                IAtom other = bond.getOther(atom.getIAtom());
                if (other.getSymbol().equals(atom.getSymbol()) && other.getImplicitHydrogenCount() > 0) {
                    numReal = other.getIndex();
                }
            }
        }
        AtomInterface atom = getAtom(numReal);
        for (int i = 0; i < numBond; i++) {
            atom.decreaseImplicitHydrogens();
        }
        return numReal;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AtomInterface getAtom(final int number) {
        for (AtomInterface atom : listAtoms) {
            if (atom.getId() == number) return atom;
        }
        throw new IllegalStateException("Atom not found atomInterface getAtom.");
    }
}
