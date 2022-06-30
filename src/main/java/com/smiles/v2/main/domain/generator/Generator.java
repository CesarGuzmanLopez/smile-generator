package com.smiles.v2.main.domain.generator;

import java.util.List;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.domain.models.MoleculesListAbstract;
import com.smiles.v2.main.domain.models.MoleculesListNotRepeat;
import com.smiles.v2.main.interfaces.AtomInterface;

/**
 * generator of smiles.
 *
 * @author Cesar G G L
 * @version 1.01
 * @since 1.0
 */
public class Generator {
    private final Molecule principal;
    private MoleculesList moleculeListSubstitutes;

    private int numBounds;
    /**
     * Contain all generated Molecule.
     */
    private MoleculesListNotRepeat generate;

    private MoleculesList listOfSubstitutes;

    /**
     * This Class is used to generate smiles permutes.
     *
     * @param principal               The molecule Principal.
     * @param moleculeListSubstitutes The list of molecules.
     * @param rSubstitutes            The number of substitutes.
     * @param numBounds               The number of bounds.
     */
    public Generator(final Molecule principal, final MoleculesList moleculeListSubstitutes, final int rSubstitutes,
            final int numBounds) {
        this.principal = principal;
        this.moleculeListSubstitutes = moleculeListSubstitutes;
        this.numBounds = numBounds;

        if (principal.getNumberAtoms() > 1
                && rSubstitutes > principal.getMoleculeData().getListAtomsSelected().size()) {
            throw new IllegalArgumentException(
                    "The number of substitutes must be less than the number of atoms of the principal molecule.");
        }
        generate = new MoleculesListNotRepeat(moleculeListSubstitutes.getSmileVerifier(),
                moleculeListSubstitutes.getFactoryMol());
        generateSubstitutes();

        // insert the molecule principal int the list of return.
        generate.addMolecule(this.principal);

        /*
         * I recursively generate all permuted smiles.
         */
        for (int i = 0; i < rSubstitutes; i++) {
            generatePermutes(generate);
        }
    }

    /**
     * This method is used to generate the list of Substitutes. selected only 1 atom
     */
    private void generateSubstitutes() {
        listOfSubstitutes = new MoleculesList(moleculeListSubstitutes.getSmileVerifier(),
                moleculeListSubstitutes.getFactoryMol());
        List<Molecule> substitutes = moleculeListSubstitutes.getListMolecule();
        for (Molecule substitute : substitutes) {
            List<AtomInterface> listSelected = substitute.getMoleculeData().getListAtomsSelected();
            if (listSelected == null || listSelected.isEmpty()) {
                listOfSubstitutes.addMolecule(substitute);
                continue;
            }
            for (AtomInterface atom : listSelected) {
                Molecule newSubstitute = new Molecule(substitute, substitute.getName() + "|" + atom.getId(), true);
                newSubstitute.getMoleculeData().unselectedAllAtoms();
                newSubstitute.getMoleculeData().selectOrderAtom(atom.getId());
                listOfSubstitutes.addMolecule(newSubstitute);
            }
        }

    }

    /**
     * @param atom1 The first Atom.
     * @param atom2 The second Atom.
     * @param bound The bond type.
     * @return if they have enough implicit Hydrogens for bound.
     */
    private Boolean haveEnoughImplicitHydrogens(AtomInterface atom1, AtomInterface atom2, int bound) { // UNCHECK
        return atom1.getImplicitHydrogens() >= bound && atom2.getImplicitHydrogens() >= bound;
    }

    /**
     * bound Allowed.
     *
     * @param principalP     The principal molecule.
     * @param substitute     The substitution molecule.
     * @param atomPrincipal  atom principal
     * @param atomSubstitute
     * @param bound          Number of bound.
     * @return if the bond is allowed.
     */
    private boolean boundAllowed(Molecule principalP, Molecule substitute, // UNCHECK
            int bound, AtomInterface atomPrincipal, AtomInterface atomSubstitute) { // UNCHECK
        return haveEnoughImplicitHydrogens(atomPrincipal, atomSubstitute, bound) || principalP.hasHydrogenImplicit()
                || substitute.hasHydrogenImplicit();
    }

    /**
     * Generate and add all permutations between Atom and a Atom.
     *
     * @param principalM The principal molecule.
     * @param substitute The substitution molecule.
     * @param bound      type of bond.
     */
    private void generatePermuteAtom1Atom(Molecule principalM, Molecule substitute, int bound) { // UNCHECK
        AtomInterface atomPrincipal = principalM.getMoleculeData().getListAtoms().get(0);
        AtomInterface atomSubstitute = substitute.getMoleculeData().getListAtoms().get(0);
        if (boundAllowed(principalM, substitute, bound, atomPrincipal, atomSubstitute)) {
            Molecule molecule = Molecule.fusionMolecule(principalM, substitute, null, null, bound);
            generate.addMolecule(molecule);
        }
    }

    /**
     * Generate and add all permutations between Molecule and a Atom.
     *
     * @param principalM The principal molecule.
     * @param substitute The atom.
     * @param bound      The number of bounds.
     */
    private void generatePermuteMolecule1Atom(Molecule principalM, Molecule substitute, int bound) { // UNCHECK
        AtomInterface atomSubstitute = substitute.getMoleculeData().getListAtoms().get(0);
        for (AtomInterface atomPrincipal : principalM.getMoleculeData().getListAtomsSelected()) {
            if (boundAllowed(principalM, substitute, bound, atomPrincipal, atomSubstitute)) {
                Molecule molecule = Molecule.fusionMolecule(principalM, substitute, atomPrincipal.getId(), null, bound);
                generate.addMolecule(molecule);
            }
        }
    }

    /**
     * Generate and add all permutations between Atom and a Molecule.
     *
     * @param principalM The principal molecule.
     * @param substitute The atom.
     * @param bound      The number of bounds.
     */
    private void generatePermuteAtom1Molecule(Molecule principalM, Molecule substitute, int bound) { // UNCHECK
        AtomInterface atomPrincipal = principalM.getMoleculeData().getListAtoms().get(0);
        for (AtomInterface atomSubstitute : substitute.getMoleculeData().getListAtomsSelected()) {
            if (boundAllowed(principalM, substitute, bound, atomPrincipal, atomSubstitute)) {
                Molecule molecule = Molecule.fusionMolecule(principalM, substitute, null, atomSubstitute.getId(),
                        bound);
                generate.addMolecule(molecule);
            }
        }
    }

    /**
     * Generate and add all permutations between Molecule and a Molecule.
     *
     * @param principalM The principal molecule.
     * @param substitute The atom.
     * @param bound      The number of bounds.
     */
    private void generatePermuteMolecule1Molecule(Molecule principalM, Molecule substitute, int bound) { // UNCHECK
        for (AtomInterface atomPrincipal : principalM.getMoleculeData().getListAtomsSelected()) {
            for (AtomInterface atomSubstitute : substitute.getMoleculeData().getListAtomsSelected()) {
                if (boundAllowed(principalM, substitute, bound, atomPrincipal, atomSubstitute)) {
                    Molecule molecule = Molecule.fusionMolecule(principalM, substitute, atomPrincipal.getId(),
                            atomSubstitute.getId(), bound);
                    generate.addMolecule(molecule);
                }
            }
        }
    }

    /**
     * Generate and add all permutations between molecule and a substitute.
     *
     * @param principalM The principal molecule.
     * @param substitute The substitution molecule.
     * @param bound      type of bond.
     * @return if substitution is correct.
     *
     */
    private boolean generatePermutes1Molecule1Substitute(Molecule principalM, Molecule substitute, int bound) {//UNCHECK
        if (principalM.getNumberAtoms() == 1 && substitute.getNumberAtoms() == 1) {
            generatePermuteAtom1Atom(principalM, substitute, bound);
            return true;
        }
        if (principalM.getNumberAtoms() == 1 && substitute.getNumberAtoms() > 1) {
            generatePermuteAtom1Molecule(principalM, substitute, bound);
            return true;
        }
        if (principalM.getNumberAtoms() > 1 && substitute.getNumberAtoms() == 1) {
            generatePermuteMolecule1Atom(principalM, substitute, bound);
            return true;
        }
        if (principalM.getNumberAtoms() > 1 && substitute.getNumberAtoms() > 1) {
            generatePermuteMolecule1Molecule(principalM, substitute, bound);
            return true;
        }
        return false;
    }

    /**
     * Generate permutations of the list of molecules with substitutes.
     *
     * @param moleculesToPermute the List of molecules to permuted with substitutes.
     */
    private void generatePermutes(MoleculesListAbstract moleculesToPermute) { // UNCHECK
        MoleculesListNotRepeat toPermute = new MoleculesListNotRepeat(moleculesToPermute);
        /* traverse main list */
        for (Molecule actualPrincipal : toPermute.getListMolecule()) {
            /* traverse substitute list */
            for (Molecule substitute : this.moleculeListSubstitutes.getListMolecule()) {
                /* generate permutations */
                for (int numBound = 1; numBound <= numBounds; numBound++) {
                    generatePermutes1Molecule1Substitute(actualPrincipal, substitute, numBound);
                }
            }
        }

    }

    /**
     * This method is used to generate the smiles permutes.
     *
     * @return The list of smiles permutes.
     */
    public MoleculesListAbstract getAllMolecules() {
        return generate;
    }

    /**
     * @return the listOfSubstitutes
     */
    public final MoleculesList getListOfSubstitutes() {
        return listOfSubstitutes;
    }
}
