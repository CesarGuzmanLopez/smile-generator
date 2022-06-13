package com.smiles.v2.main.framework.cdk;

import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.interfaces.MoleculeGraphPainterInterface;
//uso del  CDK
public class MoleculeGraphPainter implements MoleculeGraphPainterInterface {
    @Override
    public void paintMolecule(JPanel moleculePanel, Molecule molecule) {
        if (molecule == null) {
            throw new IllegalArgumentException("Molecule is null");
        }
        moleculePanel.removeAll();
        moleculePanel.setLayout(new BoxLayout(moleculePanel, BoxLayout.Y_AXIS));
        moleculePanel.setForeground(Color.GREEN);
        JLabel moleculeLabel = new JLabel( molecule.getName());
        moleculeLabel.setForeground(Color.BLACK);
        moleculePanel.add(moleculeLabel);
        JpanelDrawMoleculeSelectedAtoms panelDraw = new JpanelDrawMoleculeSelectedAtoms(molecule);
        moleculePanel.add(panelDraw);
        JLabel smileJLabel = new JLabel("smile: " + molecule.getSmile());
        smileJLabel.setForeground(Color.BLACK);
        moleculePanel.add(smileJLabel);
        JLabel description = new JLabel(molecule.getMessage());
        description.setForeground(Color.GREEN);
        moleculePanel.add(description);
        moleculePanel.revalidate();
        moleculePanel.repaint();
    }
}
