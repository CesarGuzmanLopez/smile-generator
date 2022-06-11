package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.MoleculeComparableInterface;
import com.smiles.v2.main.interfaces.MoleculeDataOfSmileInterface;
import com.smiles.v2.main.interfaces.MoleculeInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class Molecule extends Smiles implements MoleculeInterface,MoleculeComparableInterface{

    public Molecule(SmilesHInterface smile, SmileVerificationInterface smileVerification,MoleculeDataOfSmileInterface moleculeDataOfSmile) {
        super(smile, smileVerification);
        initialize();
    }
    public Molecule(Molecule molecule,SmileVerificationInterface smileVerification,MoleculeDataOfSmileInterface moleculeDataOfSmile) {
        super(molecule,smileVerification);
        initialize();
    }
    void initialize(){
        //TODO initialize
    }
    @Override
    public MoleculeInterface getMolecule() {
        return this;

    }

    @Override
    public int compareTo(SmilesHInterface arg0) {
        // TODO Auto-generated method stub
        return 0;
    }



}
