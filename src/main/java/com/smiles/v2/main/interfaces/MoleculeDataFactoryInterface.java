package com.smiles.v2.main.interfaces;

import com.smiles.v2.main.domain.models.Molecule;

public interface MoleculeDataFactoryInterface  {
    public MoleculeDataInterface getMoleculeDataOfSmile(Molecule molecule);
    public MoleculeDataInterface getMoleculeDataOfSmile(Molecule molecule, MoleculeDataInterface moleculeData);

}
