package com.smiles.v2.main.domain.models;

import java.util.ArrayList;
import java.util.List;

import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.MoleculeListInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;

public abstract class MoleculesListAbstract implements MoleculeListInterface {
    private SmileVerificationInterface smileVerifier;
    private List<Molecule> moleculeList = new ArrayList<>();
    private MoleculeDataFactoryInterface factoryMol;

    protected MoleculesListAbstract(final SmileVerificationInterface verificationSmile,
            final MoleculeDataFactoryInterface factory) {
        this.smileVerifier = verificationSmile;
        this.factoryMol = factory;
    }

    /**
     * @return the smileVerifier.
     */
    public final SmileVerificationInterface getSmileVerifier() {
        if (smileVerifier == null) throw new NullPointerException("SmileVerificationInterface is null");
        return this.smileVerifier;
    }

    /**
     * @return the factoryMolecule.
     */
    public final MoleculeDataFactoryInterface getFactoryMol() {
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
     * Returns if name is unique in the molecule list.
     *
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
     * Returns if name is unique in the molecule list.
     *
     * @param smile name to be checked.
     * @return true if name is unique, false otherwise.
     */
    public boolean isUniqueSmile(final String smile) {
        for (Molecule molecule : moleculeList) {
            if (molecule.getSmile().equals(smile)) return false;
        }
        return true;
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
}
