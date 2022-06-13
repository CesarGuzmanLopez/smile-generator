package com.smiles.v2.main.domain.models;

import java.util.ArrayList;
import java.util.List;

import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;
import com.smiles.v2.main.interfaces.MoleculeListInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;

public class MoleculesList implements MoleculeListInterface {
    SmileVerificationInterface smileVerifier;
    List<Molecule> moleculeList = new ArrayList<>();
    MoleculeDataFactoryInterface factoryMol;

    public MoleculesList(SmileVerificationInterface verificationSmile,
            MoleculeDataFactoryInterface factory) {
        this.smileVerifier = verificationSmile;
        this.factoryMol = factory;
    }

    public List<Molecule> getMoleculeListMolecule() {
              return moleculeList;
    }

    @Override
    // return number of smiles added
    public int addSmiles(String name, String smile, String message, boolean hydrogen) {
        if(!isUniqueName(name))
            throw new IllegalArgumentException("Name already exists");
        SmilesHInterface smileH = new Smiles(name, smile, message, hydrogen, smileVerifier);
        moleculeList.add(new Molecule(smileH, smileVerifier, factoryMol));
        return moleculeList.size()-1;
    }

    public boolean isUniqueName(String name) {

        for (Molecule molecule : moleculeList) {
            if (molecule.getName().equals(name)) {
                return false;
            }
        }
        return true;
    }
    @Override
    public int addSmiles(SmilesHInterface smile) {
        if(!isUniqueName(smile.getName()))
            throw new IllegalArgumentException("Name already exists");
        moleculeList.add(new Molecule(smile, smileVerifier, factoryMol));
        return moleculeList.size()-1;
    }
    @Override
    public Molecule getMolecule(String name) {
        for (Molecule molecule : moleculeList) {
            if (molecule.getName().equals(name)) {
                return molecule;
            }
        }
        return null;
    }
    public static MoleculesList createMoleculesList(SmileVerificationInterface smileVerifier,
            MoleculeDataFactoryInterface factoryMol, List<Molecule> moleculeList) {
        MoleculesList moleculesList = new MoleculesList(smileVerifier, factoryMol);
        moleculesList.moleculeList = moleculeList;
        return moleculesList;
    }

    public MoleculeListInterface getMoleculeList() {
        return this;
    }

}
