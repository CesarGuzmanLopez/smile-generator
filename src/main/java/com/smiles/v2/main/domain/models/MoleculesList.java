package com.smiles.v2.main.domain.models;

import java.util.ArrayList;
import java.util.List;

import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;
import com.smiles.v2.main.interfaces.MoleculeListInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;

public class MoleculesList implements MoleculeListInterface {
    SmileVerificationInterface vS;
    List<Molecule> moleculeList = new ArrayList<>();
    MoleculeDataFactoryInterface factoryMol;

    public MoleculesList(SmileVerificationInterface verificationSmile,
            MoleculeDataFactoryInterface factory) {
        this.vS = verificationSmile;
        this.factoryMol = factory;
    }

    public List<Molecule> getMoleculeList() {
              return moleculeList;
    }

    @Override
    // return number of smiles added
    public int addSmiles(String name, String smi, String message, boolean hydrogen) {
        SmilesHInterface smile = new Smiles(name, smi, message, hydrogen, vS);
        moleculeList.add(new Molecule(smile, vS, factoryMol));
        return moleculeList.size()-1;
    }


    @Override
    public int addSmiles(SmilesHInterface smile) {
        moleculeList.add(new Molecule(smile, vS, factoryMol));
        return moleculeList.size()-1;
    }

}
