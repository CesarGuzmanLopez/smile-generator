package com.smiles.v2.main.domain.models;

import java.util.List;

import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;

public class MoleculesListNotRepeat extends MoleculesList {

    public MoleculesListNotRepeat(SmileVerificationInterface verificationSmile,
    MoleculeDataFactoryInterface factory) {
        super(verificationSmile,factory);

    }

    public MoleculesListNotRepeat(MoleculesList moleculesList) {
        super(moleculesList.smileVerifier,moleculesList.factoryMol);
        initialize(moleculesList.getMoleculeListMolecule());
    }

    void initialize(List<Molecule> moleculeListMolecule){

        for (Molecule molecule : moleculeListMolecule) {
            if(!moleculeList.contains(molecule)){
                moleculeList.add(molecule);
            }
        }
    }
    void addMolecule(Molecule molecule){
        if(!moleculeList.contains(molecule)){
            moleculeList.add(molecule);
        }
    }
}
