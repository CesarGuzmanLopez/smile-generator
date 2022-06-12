package com.smiles.v2.main.framework.cdk;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;

public class MoleculeDataFactory implements MoleculeDataFactoryInterface {

    @Override
    public MoleculeDataInterface getMoleculeDataOfSmile(Molecule molecule) {
        return new MoleculeData(molecule);
    }

}
