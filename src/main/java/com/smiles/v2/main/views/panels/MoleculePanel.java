/*
 * Licensed to the Apache Software Foundation (ASF)
 *  under one or more contributor license agreements.
 */

package com.smiles.v2.main.views.panels;

import java.awt.Color;

import javax.swing.JLabel;
import java.awt.FlowLayout;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.interfaces.MoleculeGraphPainterInterface;

@SuppressWarnings("java:S1948")
public class MoleculePanel extends javax.swing.JPanel {
    protected Molecule molecule;
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

    public void setMolecule(Molecule molecule) {
        this.molecule = molecule;
        if (molecule == null) {
            setBackground(Color.white);
            return;
        }


        nameMoleculeLabel.setForeground(Color.black);
        add(nameMoleculeLabel);
        painter.paintMolecule(this, molecule);

        revalidate();
        repaint();
    }
    public Molecule getMolecule() {
        return molecule;
    }
}
