package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.AtomInterface;
import com.smiles.v2.main.interfaces.MoleculeComparableInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class Molecule extends Smiles implements MoleculeComparableInterface {
    private MoleculeDataFactoryInterface moleculeDataFactory;
    private MoleculeDataInterface moleculeDataOfSmile;

    public Molecule(final SmilesHInterface smile, final MoleculeDataFactoryInterface moleculeFactory) {
        super(smile);
        this.moleculeDataFactory = moleculeFactory;
        moleculeDataOfSmile = moleculeDataFactory.getMoleculeDataOfSmile(this);
        resetSmile();
    }

    public Molecule(final String name, final String smiles, final String message, final boolean hydrogenImplicit,
            final SmileVerificationInterface smileVerification,
            final MoleculeDataFactoryInterface moleculeDataOfSmileFactory) {
        super(name, smiles, message, hydrogenImplicit, smileVerification);
        this.moleculeDataFactory = moleculeDataOfSmileFactory;
        moleculeDataOfSmile = this.moleculeDataFactory.getMoleculeDataOfSmile(this);
        resetSmile();
    }

    public Molecule(final Molecule molecule) {
        super(molecule);
        this.moleculeDataFactory = molecule.moleculeDataFactory;
        moleculeDataOfSmile = moleculeDataFactory.getMoleculeDataOfSmile(this);
        resetSmile();
    }

    public Molecule(final Molecule molecule, final boolean cloneData) {
        super(molecule);
        this.moleculeDataFactory = molecule.moleculeDataFactory;
        if (cloneData) {
            moleculeDataOfSmile = moleculeDataFactory.getMoleculeDataOfSmile(molecule, molecule.getMoleculeData());
        } else {
            moleculeDataOfSmile = moleculeDataFactory.getMoleculeDataOfSmile(this);
        }
        resetSmile();
    }

    public Molecule(final Molecule molecule, final String newName, final boolean cloneData) {
        super(molecule);
        this.moleculeDataFactory = molecule.moleculeDataFactory;
        if (cloneData) {
            moleculeDataOfSmile = moleculeDataFactory.getMoleculeDataOfSmile(molecule, molecule.getMoleculeData());
        } else {
            moleculeDataOfSmile = moleculeDataFactory.getMoleculeDataOfSmile(this);
        }
        resetSmile();
        setName(newName);
    }

    /**
     * @param index
     * @return Atom by index id
     */
    public AtomInterface getAtom(final int index) {
        return moleculeDataOfSmile.getAtom(index);
    }

    /**
     * Get number of Atoms in the molecule.
     *
     * @return The number of atoms in the molecule
     */
    public int getNumberAtoms() {
        return moleculeDataOfSmile.getNumberAtoms();
    }

    /**
     * Get the molecule data.
     *
     * @return The molecule data
     */
    public MoleculeDataInterface getMoleculeData() {
        if (moleculeDataOfSmile == null) {
            throw new NullPointerException("MoleculeDataOfSmile is null");
        }
        return moleculeDataOfSmile;
    }

    /**
     * compare two molecules.
     *
     * @return Integer The molecule data
     */
    @Override
    public int compareTo(final Molecule molecule) {
        return getMoleculeData().compareTo(molecule);
    }

    /**
     * toString.
     *
     * @return String The molecule isomeric smiles.
     */

    @Override
    public String toString() {
        return getMoleculeData().absoluteSmile();
    }
    /**
     * Return a fusion molecule Principal wit substituent.
     *
     * @param principal         The molecule to fusion
     * @param substituent       The substituent to fusion
     * @param numAtomPrincipal  substitution of the principal molecule
     * @param numAtomSubstitute substitution of the substituent molecule
     * @return Molecule The fusion molecule
     */
    public static Molecule fusionMolecule(final Molecule principal, final Molecule substituent,
            final Integer numAtomPrincipal, final Integer numAtomSubstitute) {
        return fusionMolecule(principal, substituent, numAtomPrincipal, numAtomSubstitute, 1);
    }

    /**
     * Return a fusion molecule Principal wit substituent.
     *
     * @param principal         The molecule to fusion
     * @param substituent       The substituent to fusion
     * @param numAtomPrincipal  substitution of the principal molecule
     * @param numAtomSubstitute substitution of the substituent molecule
     * @param numBond Number of bonds between the principal and substituent
     * @return Molecule The fusion molecule
     */
    public static Molecule fusionMolecule(final Molecule principal, final Molecule substituent,
            final Integer numAtomPrincipal, final Integer numAtomSubstitute, final Integer numBond) {
        verifyToSubstitute(principal, substituent, numAtomPrincipal, numAtomSubstitute);
        Molecule substituentClone = new Molecule(substituent, true);
        Molecule fusion = new Molecule(principal, principal.getName() + " - " + substituentClone.getName(), true);
        fusion.getMoleculeData().addMoleculeData(substituentClone, numAtomPrincipal, numAtomSubstitute, numBond);
        fusion.resetSmile();
        return fusion;
    }

    /**
     * select a atom id.
     *
     * @param index the atom id.
     */
    public void selectAtom(final int index) {
        moleculeDataOfSmile.selectOrderAtom(getAtom(index));
    }

    /**
     * Reset Smile input for isomericSmile.
     */
    public void resetSmile() {
        setStrSmiles(getMoleculeData().absoluteSmile());
    }

    /**
     * Verify if the molecule can be substituted.
     *
     * @param principal         The molecule to fusion
     * @param substituent       The substituent to fusion
     * @param numAtomPrincipal  substitution of the principal molecule
     * @param numAtomSubstitute substitution of the substituent molecule
     */
    private static void verifyToSubstitute(final Molecule principal, final Molecule substituent,
            final Integer numAtomPrincipal, final Integer numAtomSubstitute) {
        if (principal == null || substituent == null) {
            throw new NullPointerException("molecule principal or substituent is null");
        }
        if (principal.getNumberAtoms() > 1 && principal.getMoleculeData().getListAtomsSelected().isEmpty()
                || substituent.getNumberAtoms() > 1 && substituent.getMoleculeData().getListAtomsSelected().isEmpty()) {
            throw new NullPointerException("molecule has no selected atoms and are more than 1");
        }
        verifyEntryAtomSelected(principal, numAtomPrincipal);
        verifyEntryAtomSelected(substituent, numAtomSubstitute);

    }

    private static void verifyEntryAtomSelected(final Molecule principal, final Integer numAtomPrincipal) {
        if (numAtomPrincipal == null && principal.getNumberAtoms() > 1) {
            throw new NullPointerException("toSubstitute is null or 0 and principal has more than 1 atom");
        }
        if (numAtomPrincipal != null && principal.getNumberAtoms() > 1
                && !principal.getMoleculeData().getListAtomsSelected().contains(principal.getAtom(numAtomPrincipal))) {
            throw new NullPointerException("The atom no selected");
        }
        if (numAtomPrincipal != null && principal.getMoleculeData().getListAtomsSelected().isEmpty()) {
            throw new NullPointerException("molecule has no selected atoms");
        }
    }

}
