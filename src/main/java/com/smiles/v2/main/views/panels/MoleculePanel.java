/*
 * Licensed to the Apache Software Foundation (ASF)
 *  under one or more contributor license agreements.
 */

package com.smiles.v2.main.views.panels;

import java.awt.Color;

import javax.swing.JLabel;
import java.awt.FlowLayout;
import com.smiles.v2.main.domain.models.MoleculeGraphics;
import com.smiles.v2.main.interfaces.MoleculeGraphPainterInterface;

@SuppressWarnings("java:S1948")
public class MoleculePanel extends javax.swing.JPanel {
    protected MoleculeGraphics molecule;
    protected MoleculeGraphPainterInterface painter;
    JLabel nameMoleculeLabel;
    public MoleculePanel(MoleculeGraphPainterInterface painter) {
        super();
        setLayout(new FlowLayout());
        setBackground(Color.white);
        setForeground(Color.black);
        this.painter = painter;
        nameMoleculeLabel = new JLabel();
    }

    public void setMolecule(MoleculeGraphics molecule) {
        this.molecule = molecule;
        if (molecule == null) {
            //setBackground(Color.white);
            return;
        }


        //nameMoleculeLabel.setText(molecule.getName());
        //nameMoleculeLabel.setBounds(0, 0, 100, 20);
        nameMoleculeLabel.setForeground(Color.black);
        add(nameMoleculeLabel);
        painter.paintMolecule(this, molecule);

        revalidate();
        repaint();
    }

}
