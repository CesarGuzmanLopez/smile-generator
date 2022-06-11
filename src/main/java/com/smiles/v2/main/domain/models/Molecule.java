package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.MoleculeInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class Molecule extends Smiles implements MoleculeInterface{

    Molecule(SmilesHInterface smile, SmileVerificationInterface smileVerification) {
        super(smile, smileVerification);
    }

    @Override
    public MoleculeInterface getMolecule() {
        // TODO Auto-generated method stub
        return null;
    }




}
