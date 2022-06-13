package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.MoleculeComparableInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class Molecule extends Smiles implements MoleculeComparableInterface {

    private MoleculeDataFactoryInterface moleculeDataFactory;
    private MoleculeDataInterface moleculeDataOfSmile;

    public Molecule(SmilesHInterface smile, SmileVerificationInterface smileVerification,
            MoleculeDataFactoryInterface moleculeFactory) {
        super(smile, smileVerification);
        this.moleculeDataFactory = moleculeFactory;
        initialize();
    }
    public Molecule(String name, String smiles, String message, boolean hydrogenImplicit,
                     SmileVerificationInterface smileVerification,
        MoleculeDataFactoryInterface moleculeDataOfSmile) {
        super(name, smiles, message, hydrogenImplicit, smileVerification);
        this.moleculeDataFactory = moleculeDataOfSmile;
        initialize();
     }

    public Molecule(Molecule molecule, SmileVerificationInterface smileVerification,
            MoleculeDataFactoryInterface moleculeDataOfSmile) {
        super(molecule, smileVerification);
        this.moleculeDataFactory = moleculeDataOfSmile;
        initialize();
    }

    void initialize() {
        moleculeDataOfSmile = moleculeDataFactory.getMoleculeDataOfSmile(this);
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
    public int compareTo(Molecule molecule) {
        return 0;
    }

}
