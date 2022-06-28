package com.smiles.v2.main.framework.cdk;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;

public class MoleculeDataFactory implements MoleculeDataFactoryInterface {
    /**
     * {@inheritDoc}
     */
    @Override
    public MoleculeDataInterface getMoleculeDataOfSmile(final Molecule molecule) {
        return new MoleculeData(molecule);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public MoleculeDataInterface getMoleculeDataOfSmile(final Molecule molecule,
            final MoleculeDataInterface moleculeData) {

        return new MoleculeData(molecule, moleculeData);
    }




}
