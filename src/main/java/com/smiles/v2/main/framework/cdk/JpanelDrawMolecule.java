package com.smiles.v2.main.framework.cdk;

import java.awt.Graphics;
import com.smiles.v2.main.domain.models.Molecule;

public class JpanelDrawMolecule extends JpanelDrawMoleculeAbstract {

    public JpanelDrawMolecule(Molecule molecule) {
        super(molecule);
    }

    @Override
    void paintHerder(Graphics a) {
      // document why this method is empty
      //only DrawMolecule
    }

}
