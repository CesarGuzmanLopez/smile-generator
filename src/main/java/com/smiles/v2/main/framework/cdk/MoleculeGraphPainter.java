package com.smiles.v2.main.framework.cdk;

import javax.swing.JPanel;

import com.smiles.v2.main.domain.models.MoleculeGraphics;
import com.smiles.v2.main.interfaces.MoleculeGraphPainterInterface;
import com.smiles.v2.main.views.panels.MoleculePanel;

//uso del framwork CDK


public class MoleculeGraphPainter implements MoleculeGraphPainterInterface {

    @Override
    public void paintMolecule(MoleculePanel moleculePanel, MoleculeGraphics molecule) {
        if(molecule == null) {
            throw new IllegalArgumentException("Molecule is null");
        }
        MoleculeDataOfSmile moleculeData =(MoleculeDataOfSmile) molecule.getMoleculeDataOfSmile();
        JPanel panel = new JPanel();
        panel.setLayout(null);
        //moleculePanel.add(panel);

    }

}
