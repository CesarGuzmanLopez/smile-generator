package com.smiles.v2.main.framework.cdk;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.interfaces.MoleculeDataOfSmileFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeDataOfSmileInterface;

public class MoleculeDataOfSmileFactory implements MoleculeDataOfSmileFactoryInterface {

    @Override
    public MoleculeDataOfSmileInterface getMoleculeDataOfSmile(Molecule molecule) {
        return new MoleculeDataOfSmile(molecule);
    }

}
