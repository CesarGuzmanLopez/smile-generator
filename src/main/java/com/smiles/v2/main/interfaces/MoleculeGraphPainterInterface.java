package com.smiles.v2.main.interfaces;

import com.smiles.v2.main.domain.models.MoleculeGraphics;
import com.smiles.v2.main.views.panels.MoleculePanel;

public interface MoleculeGraphPainterInterface {

    void paintMolecule(MoleculePanel moleculePanel, MoleculeGraphics molecule);

}
