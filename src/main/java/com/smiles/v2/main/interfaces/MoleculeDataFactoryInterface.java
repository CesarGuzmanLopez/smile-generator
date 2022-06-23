package com.smiles.v2.main.interfaces;

import com.smiles.v2.main.domain.models.Molecule;
/**Interface for the MoleculeDataFactory.
 * @author Cesar Gerardo Guzman Lopez
 */
public interface MoleculeDataFactoryInterface  {
    /**
     * Method create moleculeData.
     * @param molecule Molecule to extract or Create Data
     * @return MoleculeData
     */
    MoleculeDataInterface getMoleculeDataOfSmile(Molecule molecule);
     /**
     * Method create moleculeData.
     * @param molecule Molecule to extract or Create Data
     * @param moleculeData moleculeData to clone data.
     * @return MoleculeData
     */
    MoleculeDataInterface getMoleculeDataOfSmile(Molecule molecule, MoleculeDataInterface moleculeData);

}
