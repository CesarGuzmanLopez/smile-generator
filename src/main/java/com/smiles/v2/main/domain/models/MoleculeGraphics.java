package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.DrawMoleculeInterface;
import com.smiles.v2.main.interfaces.MoleculeDataOfSmileInterface;
import com.smiles.v2.main.interfaces.MoleculeGraphPainterInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class MoleculeGraphics extends Molecule implements DrawMoleculeInterface{
    MoleculeGraphPainterInterface drawMolecule;
    public MoleculeGraphics(SmilesHInterface molecule, SmileVerificationInterface smileVerification,MoleculeGraphPainterInterface drawer,MoleculeDataOfSmileInterface moleculeDataOfSmile) {
        super(molecule, smileVerification,moleculeDataOfSmile);
        this.drawMolecule = drawer;
    }
    public MoleculeGraphics(Molecule molecule, SmileVerificationInterface smileVerification,MoleculeGraphPainterInterface drawer,MoleculeDataOfSmileInterface moleculeDataOfSmile) {
        super(molecule, smileVerification,moleculeDataOfSmile);
        this.drawMolecule = drawer;
    }

}
