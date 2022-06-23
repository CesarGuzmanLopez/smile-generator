package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.MoleculeComparableInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class Molecule extends Smiles implements MoleculeComparableInterface {
    private MoleculeDataFactoryInterface moleculeDataFactory;
    private MoleculeDataInterface moleculeDataOfSmile;

    public Molecule(final SmilesHInterface smile, final SmileVerificationInterface smileVerification,
            final MoleculeDataFactoryInterface moleculeFactory) {
        super(smile);
        this.moleculeDataFactory = moleculeFactory;
        initialize();
    }

    public Molecule(final String name, final String smiles, final String message, final boolean hydrogenImplicit,
            final SmileVerificationInterface smileVerification,
            final MoleculeDataFactoryInterface moleculeDataOfSmile) {
        super(name, smiles, message, hydrogenImplicit, smileVerification);
        this.moleculeDataFactory = moleculeDataOfSmile;
        initialize();
    }

    public Molecule(final Molecule molecule) {
        super(molecule);
        this.moleculeDataFactory = molecule.moleculeDataFactory;
        initialize();
    }

    /**
     * initialize the molecule data and reset smile.
     *
     */
    private void initialize() {
        moleculeDataOfSmile = moleculeDataFactory.getMoleculeDataOfSmile(this);
        resetSmile();
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
        return getMoleculeData().isomericSmile();
    }

    /**
     * Return a fusion molecule Principal wit substituent.
     *
     * @param principal          The molecule to fusion
     * @param substituent        The substituent to fusion
     * @param numAtomPrincipal   substitution of the principal molecule
     * @param numAtomSubstitute substitution of the substituent molecule
     * @return Molecule The fusion molecule
     */
    public static Molecule fusionMolecule(final Molecule principal, final Molecule substituent,
            final Integer numAtomPrincipal, final Integer numAtomSubstitute) {

        verifyToSubstitute(principal, substituent, numAtomPrincipal, numAtomSubstitute);
        // TODO make the substitution
        return null;
    }

    /**
     * Reset Smile input for isomericSmile.
     */
    public void resetSmile() {
        setStrSmiles(getMoleculeData().isomericSmile());
    }

    /**
     * Verify if the molecule can be substituted.
     *
     * @param principal          The molecule to fusion
     * @param substituent        The substituent to fusion
     * @param numAtomPrincipal   substitution of the principal molecule
     * @param numAtomSubstitute substitution of the substituent molecule
     */
    private static void verifyToSubstitute(final Molecule principal, final Molecule substituent,
            final Integer numAtomPrincipal, final Integer numAtomSubstitute) {
        if (principal == null || substituent == null) {
            throw new NullPointerException("Molecule is null");
        }
        if ((numAtomPrincipal < 0 || numAtomPrincipal == null) && principal.getNumberAtoms() > 1) {
            throw new NullPointerException("toSubstitute is null or 0 ");
        }
        if (numAtomPrincipal >= principal.getMoleculeData().getListAtomsSelected().size()
                && principal.getMoleculeData().getListAtomsSelected().size() > 1) {
            throw new NullPointerException("toSubstitute is greater than number of atoms selected");
        }
        if ((numAtomSubstitute < 0 || numAtomSubstitute == null) && substituent.getNumberAtoms() > 1) {
            throw new NullPointerException("numAtomSubstitute is null or 0 ");
        }
        if (numAtomSubstitute >= substituent.getMoleculeData().getListAtomsSelected().size()
                && substituent.getMoleculeData().getListAtomsSelected().size() > 1) {
            throw new NullPointerException("toSubstitute is greater than number of atoms selected");
        }
    }

}
