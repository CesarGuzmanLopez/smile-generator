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
    private List<Integer> moleculesAdded;

    MoleculeData(Molecule molecule) { // UNCHECK NOSONAR
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
        moleculesAdded = new ArrayList<>();
    }

    public MoleculeData(Molecule molecule, final MoleculeDataInterface moleculeData) { // UNCHECK NOSONAR
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
        moleculesAdded = new ArrayList<>();
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
        public void select() {
            super.select();
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
        ((AtomSelectable) atom).select();
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
        } catch (Exception e) {
            StringBuilder bld = new StringBuilder();
            for (AtomInterface a : listAtoms) {
                IAtom x = ((AtomCDK) a).getIAtom();
                bld.append(a.getSymbol() + " Implicit: " + a.getImplicitHydrogens() + " Bounds:" + x.getBondCount()
                        + "\n");
            }
            throw new UnsupportedOperationException("Atoms: \n" + bld.toString() + "\nError: " + e.getMessage());
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addMoleculeData(final Molecule moleculeSubstituent, final Integer selectedPrincipal,
            final Integer selectedSubstituent, final Integer numBond) {

        Integer selectedSubstituentN = selectedSubstituent;
        Order bond = selectBond(numBond);
        IAtom atom = null;
        boolean susR = false;
        if (selectedSubstituentN != null) {
            atom = ((AtomCDK) moleculeSubstituent.getAtom(selectedSubstituentN)).getIAtom();
        }
        if (atom != null && atom.getSymbol().equals("R")) {
            if (atom.getBondCount() != 1) {
                throw new IllegalArgumentException("Atom with * must have only one bond");
            }
            IBond boundAsterisk = atom.bonds().iterator().next();
            bond = boundAsterisk.getOrder();
            IAtom other = boundAsterisk.getOther(atom);
            ((MoleculeData) moleculeSubstituent.getMoleculeData()).moleculeContainer.removeAtom(atom);
            selectedSubstituentN = other.getIndex();
            susR = true;
        }

        int initNumAtoms = moleculeContainer.getAtomCount();
        int numAtomPrincipalSelected = (selectedPrincipal != null) ? selectedPrincipal : 0; // NOSONAR
        int numAtomSubstituentSelected = (selectedSubstituentN != null)
                ? moleculeContainer.getAtomCount() + selectedSubstituentN // NOSONAR
                : moleculeContainer.getAtomCount();

        MoleculeData moleculeDataSubstituent = (MoleculeData) moleculeSubstituent.getMoleculeData();
        moleculeContainer.add(moleculeDataSubstituent.moleculeContainer);
        int totalNumAtoms = moleculeContainer.getAtomCount();

        for (int i = initNumAtoms; i < totalNumAtoms; i++) {
            final AtomSelectable temporal = new AtomSelectable(moleculeContainer.getAtom(i), i);
            listAtoms.add(temporal);
        }
        if (molecule.hasHydrogenImplicit()) {
            numAtomPrincipalSelected = realSelectedAndDecrease(numAtomPrincipalSelected, bond.numeric());
        }
        if (moleculeSubstituent.hasHydrogenImplicit() && !susR) {
            numAtomSubstituentSelected = realSelectedAndDecrease(numAtomSubstituentSelected, bond.numeric());
        }

        moleculeContainer.addBond(numAtomPrincipalSelected, numAtomSubstituentSelected, bond);

        /* unselected IAtom */
        if (selectedPrincipal != null) {
            selectOrderAtom(getAtom(selectedPrincipal));
        }
        // Location of add molecule in the molecule
        moleculesAdded.add(initNumAtoms);
        try {
            molecule.resetSmile();
        } catch (UnsupportedOperationException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    /**
     *
     * @param numBond
     * @return The type bond selected
     */
    private Order selectBond(final Integer numBond) {
        Order bond;
        switch (numBond) {
        case 1:// UNCHECK
            bond = IBond.Order.SINGLE;
            break;
        case 2:// UNCHECK
            bond = IBond.Order.DOUBLE;
            break;
        case 3:// UNCHECK
            bond = IBond.Order.TRIPLE;
            break;
        case 4:// UNCHECK
            bond = IBond.Order.QUADRUPLE;
            break;
        case 5:// UNCHECK
            bond = IBond.Order.QUINTUPLE;
            break;
        case 6:// UNCHECK
            bond = IBond.Order.SEXTUPLE;
            break;
        default:
            bond = IBond.Order.SINGLE;
            break;
        }
        return bond;
    }

    /**
     * @param atom1P
     * @param atom2P
     * @return if Atoms connected atoms equals.
     */
    private boolean isEquivalent(final IAtom atom1P, final IAtom atom2P) {
        if (atom1P.getBondCount() != atom2P.getBondCount()) {
            return false;
        }
        List<IAtom> atom1List = new ArrayList<>();
        List<IAtom> atom2List = new ArrayList<>();
        for (IBond bond : atom1P.bonds()) {
            atom1List.add(bond.getOther(atom1P));
        }
        for (IBond bond : atom2P.bonds()) {
            atom2List.add(bond.getOther(atom2P));
        }
        for (IAtom atom : atom1List) {
            boolean contains = false;
            for (IAtom atom2 : atom2List) {
                if (atom.getSymbol().equals(atom2.getSymbol())) {
                    contains = true;
                    break;
                }
            }
            if (!contains) {
                return false;
            }
        }

        return true;
    }

    /**
     * Reselect. I look for the closest atom that contains the same number of
     * connections and the same number of atoms and that are of the same type but
     * that contains implicit hydrogens from which I can remove an element.
     *
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

                if (other.getSymbol().equals(atom.getSymbol()) && other.getImplicitHydrogenCount() >= numBond
                        && isEquivalent(atom.getIAtom(), other)) {
                    numReal = other.getIndex();
                }
            }
            if (numReal == selected) {
                return numReal;
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
            if (atom.getId() == number) {
                return atom;
            }
        }
        throw new IllegalStateException("Atom not found atomInterface getAtom.");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unselectedAllAtoms() {
        for (AtomInterface atom : selectedList) {
            ((AtomSelectable) atom).select();
        }
        selectedList.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectOrderAtom(final int id) {
        AtomInterface atom = getAtom(id);
        selectOrderAtom(atom);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumOfMolecules() {
        return moleculesAdded.size() + 1;
    }
}
