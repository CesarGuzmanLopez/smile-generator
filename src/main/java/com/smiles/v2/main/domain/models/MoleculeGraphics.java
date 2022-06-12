package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.DrawMoleculeInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class MoleculeGraphics extends Molecule implements DrawMoleculeInterface {


    public MoleculeGraphics(SmilesHInterface molecule, SmileVerificationInterface smileVerification,
            MoleculeDataFactoryInterface moleculeDataOfSmileFactory) {
        super(molecule, smileVerification, moleculeDataOfSmileFactory);
     }

    public MoleculeGraphics(Molecule molecule, SmileVerificationInterface smileVerification,
             MoleculeDataFactoryInterface moleculeDataOfSmileFactory) {
        super(molecule, smileVerification, moleculeDataOfSmileFactory);

    }

}
