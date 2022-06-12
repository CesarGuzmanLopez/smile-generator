package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.MoleculeComparableInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class Molecule extends Smiles implements MoleculeComparableInterface {

    private MoleculeDataFactoryInterface moleculeDataOfSmileFactory;
    private MoleculeDataInterface moleculeDataOfSmile;

    public Molecule(SmilesHInterface smile, SmileVerificationInterface smileVerification,
            MoleculeDataFactoryInterface moleculeDataOfSmile) {
        super(smile, smileVerification);
        this.moleculeDataOfSmileFactory = moleculeDataOfSmile;
        initialize();
    }

    public Molecule(Molecule molecule, SmileVerificationInterface smileVerification,
            MoleculeDataFactoryInterface moleculeDataOfSmile) {
        super(molecule, smileVerification);
        this.moleculeDataOfSmileFactory = moleculeDataOfSmile;
        initialize();
    }

    void initialize() {
        moleculeDataOfSmile = moleculeDataOfSmileFactory.getMoleculeDataOfSmile(this);
    }
    public int getNumberAtoms() {
        return moleculeDataOfSmile.getNumberAtoms();
    }
    public MoleculeDataInterface getMoleculeData() {
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
