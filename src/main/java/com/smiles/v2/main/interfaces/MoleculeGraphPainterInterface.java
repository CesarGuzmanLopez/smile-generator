package com.smiles.v2.main.interfaces;

import javax.swing.JPanel;

import com.smiles.v2.main.domain.models.Molecule ;

public interface MoleculeGraphPainterInterface {

    void paintMolecule(JPanel moleculePanel, Molecule molecule);

}
