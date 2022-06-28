package com.smiles.v2.main.domain.models;

import java.util.ArrayList;
import java.util.List;

import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;

public class MoleculesList extends MoleculesListAbstract {
    private SmileVerificationInterface smileVerifier;
    private List<Molecule> moleculeList = new ArrayList<>();
    private MoleculeDataFactoryInterface factoryMol;

    public MoleculesList(final SmileVerificationInterface verificationSmile,
            final MoleculeDataFactoryInterface factory) {
        super(verificationSmile, factory);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int addSmiles(final String name, final String smile, final String message, final boolean hydrogen) {
        if (!isUniqueName(name)) throw new IllegalArgumentException("Name already exists");

        final SmilesHInterface smileH = new Smiles(name, smile, message, hydrogen, smileVerifier);
        moleculeList.add(new Molecule(smileH, factoryMol));
        return moleculeList.size() - 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addSmiles(final SmilesHInterface smile) {
        if (!isUniqueName(smile.getName())) throw new IllegalArgumentException("Name already exists");

        moleculeList.add(new Molecule(smile, factoryMol));
        return moleculeList.size() - 1;
    }

    /** Returns of Molecule List.
     * @param smileVerifier smile verifier.
     * @param factoryMol factory mol.
     * @param moleculeList molecule list to construct.
     * @return moleculeList.
    */
    public static MoleculesList createMoleculesList(final SmileVerificationInterface smileVerifier,
            final MoleculeDataFactoryInterface factoryMol, final List<Molecule> moleculeList) {
        MoleculesList moleculesList = new MoleculesList(smileVerifier, factoryMol);
        moleculesList.moleculeList.addAll(moleculeList);
        return moleculesList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addMolecule(final Molecule molecule) {
        if (!isUniqueName(molecule.getName())) throw new IllegalArgumentException("Name already exists");
        Molecule cloneMolecule = new Molecule(molecule, true);
        moleculeList.add(cloneMolecule);
        return moleculeList.size() - 1;
    }


}
