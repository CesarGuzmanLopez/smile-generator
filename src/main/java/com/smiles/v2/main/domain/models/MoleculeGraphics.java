package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.DrawMoleculeInterface;
import com.smiles.v2.main.interfaces.MoleculeDataOfSmileFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeGraphPainterInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class MoleculeGraphics extends Molecule implements DrawMoleculeInterface {
    private MoleculeGraphPainterInterface drawMolecule;

    public MoleculeGraphics(SmilesHInterface molecule, SmileVerificationInterface smileVerification,
            MoleculeGraphPainterInterface drawer, MoleculeDataOfSmileFactoryInterface moleculeDataOfSmileFactory) {
        super(molecule, smileVerification, moleculeDataOfSmileFactory);
        this.drawMolecule = drawer;
    }

    public MoleculeGraphics(Molecule molecule, SmileVerificationInterface smileVerification,
            MoleculeGraphPainterInterface drawer, MoleculeDataOfSmileFactoryInterface moleculeDataOfSmileFactory) {
        super(molecule, smileVerification, moleculeDataOfSmileFactory);
        this.drawMolecule = drawer;
    }

}
