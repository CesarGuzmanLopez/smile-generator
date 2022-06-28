package com.smiles.v2.main.domain.models;

import java.util.List;

import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class MoleculesListNotRepeat extends MoleculesListAbstract {

    public MoleculesListNotRepeat(final SmileVerificationInterface verificationSmile,
            final MoleculeDataFactoryInterface factory) {
        super(verificationSmile, factory);
    }

    public MoleculesListNotRepeat(final MoleculesList moleculesList) {
        super(moleculesList.getSmileVerifier(), moleculesList.getFactoryMol());
        initialize(moleculesList.getListMolecule());
    }

    /**
     * initialize list.
     *
     * @param moleculeListMolecule list to be initialized.
     */
    private void initialize(final List<Molecule> moleculeListMolecule) {

        for (Molecule molecule : moleculeListMolecule) {
            if (!getListMolecule().contains(molecule)) {
                getListMolecule().add(molecule);
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addSmiles(final String name, final String smile, final String message,
            final boolean hasHydrogenImplicit) {
        if (!isUniqueName(name))  throw new IllegalArgumentException("Name already exists");
        final SmilesHInterface smileH = new Smiles(name, smile, message, hasHydrogenImplicit, getSmileVerifier());
        final Molecule molecule = new Molecule(smileH, getFactoryMol());
        if (!getListMolecule().contains(molecule)) {
            getListMolecule().add(molecule);
        }
        return getListMolecule().indexOf(molecule);
    }

    /**
     * {@inheritDoc}
     */

    @Override
    public int addSmiles(final SmilesHInterface smile) {
        if (!isUniqueName(smile.getName())) throw new IllegalArgumentException("Name already exists");
        final Molecule molecule = new Molecule(smile, getFactoryMol());
        if (!getListMolecule().contains(molecule)) {
            getListMolecule().add(molecule);
        }
        return getListMolecule().indexOf(molecule);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addMolecule(final Molecule molecule) {
        if (!getListMolecule().contains(molecule)) {
            getListMolecule().add(molecule);
        }
        return getListMolecule().indexOf(molecule);
    }

}
