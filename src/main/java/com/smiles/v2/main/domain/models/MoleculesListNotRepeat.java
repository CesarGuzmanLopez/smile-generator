package com.smiles.v2.main.domain.models;

import java.util.List;

import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;

public class MoleculesListNotRepeat extends MoleculesList {

    public MoleculesListNotRepeat(final SmileVerificationInterface verificationSmile,
            final MoleculeDataFactoryInterface factory) {
        super(verificationSmile, factory);

    }

    public MoleculesListNotRepeat(final MoleculesList moleculesList) {
        super(moleculesList.getSmileVerifier(), moleculesList.getFactoryMol());
        initialize(moleculesList.getListMolecule());
    }
    /** initialize list.
     * @param moleculeListMolecule list to be initialized.
    */
    private void initialize(final List<Molecule> moleculeListMolecule) {

        for (Molecule molecule : moleculeListMolecule) {
            if (!getListMolecule().contains(molecule)) {
                getListMolecule().add(molecule);
            }
        }
    }
    /** add molecule without repeating.
     * @param molecule name of molecule.
    */
    void addMolecule(final Molecule molecule) {
        if (!getListMolecule().contains(molecule)) getListMolecule().add(molecule);
    }
}
