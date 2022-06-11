package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.MoleculeComparableInterface;
import com.smiles.v2.main.interfaces.MoleculeDataOfSmileFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeDataOfSmileInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class Molecule extends Smiles implements MoleculeComparableInterface {

    private MoleculeDataOfSmileFactoryInterface moleculeDataOfSmileFactory;
    private MoleculeDataOfSmileInterface moleculeDataOfSmile;

    public Molecule(SmilesHInterface smile, SmileVerificationInterface smileVerification,
            MoleculeDataOfSmileFactoryInterface moleculeDataOfSmile) {
        super(smile, smileVerification);
        this.moleculeDataOfSmileFactory = moleculeDataOfSmile;
        initialize();
    }

    public Molecule(Molecule molecule, SmileVerificationInterface smileVerification,
            MoleculeDataOfSmileFactoryInterface moleculeDataOfSmile) {
        super(molecule, smileVerification);
        this.moleculeDataOfSmileFactory = moleculeDataOfSmile;
        initialize();
    }

    void initialize() {
        moleculeDataOfSmile = moleculeDataOfSmileFactory.getMoleculeDataOfSmile(this);
    }

    public MoleculeDataOfSmileInterface getMoleculeDataOfSmile() {
        if (moleculeDataOfSmile == null)
            throw new NullPointerException("MoleculeDataOfSmile is null");
        return moleculeDataOfSmile;
    }

    @Override
    public int compareTo(SmilesHInterface arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

}
