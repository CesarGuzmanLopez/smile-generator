package com.smiles.v2.main.domain.models;

import java.util.ArrayList;
import java.util.List;

import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;
import com.smiles.v2.main.interfaces.MoleculeListInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;

public class MoleculesList implements MoleculeListInterface {
    private SmileVerificationInterface smileVerifier;
    private List<Molecule> moleculeList = new ArrayList<>();
    private MoleculeDataFactoryInterface factoryMol;

    public MoleculesList(final SmileVerificationInterface verificationSmile,
            final MoleculeDataFactoryInterface factory) {
        this.smileVerifier = verificationSmile;
        this.factoryMol = factory;
    }

    protected final  SmileVerificationInterface getSmileVerifier() {
        if (smileVerifier == null) throw new NullPointerException("SmileVerificationInterface is null");
        return this.smileVerifier;
    }

    protected final MoleculeDataFactoryInterface getFactoryMol() {
        if (factoryMol == null) throw new NullPointerException("MoleculeDataFactoryInterface is null");
        return this.factoryMol;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public List<Molecule> getListMolecule() {
        return moleculeList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int addSmiles(final String name, final String smile, final String message, final boolean hydrogen) {
        if (!isUniqueName(name)) throw new IllegalArgumentException("Name already exists");

        final SmilesHInterface smileH = new Smiles(name, smile, message, hydrogen, smileVerifier);
        moleculeList.add(new Molecule(smileH, smileVerifier, factoryMol));
        return moleculeList.size() - 1;
    }
    /** Returns if name is unique in the molecule list.
     * @param name name to be checked.
     * @return true if name is unique, false otherwise.
    */
    public boolean isUniqueName(final String name) {
        for (Molecule molecule : moleculeList) {
            if (molecule.getName().equals(name)) return false;
        }
        return true;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int addSmiles(final SmilesHInterface smile) {
        if (!isUniqueName(smile.getName())) throw new IllegalArgumentException("Name already exists");

        moleculeList.add(new Molecule(smile, smileVerifier, factoryMol));
        return moleculeList.size() - 1;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Molecule getMolecule(final String name) {
        for (Molecule molecule : moleculeList) {
            if (molecule.getName().equals(name)) return molecule;

        }
        return null;
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


}
