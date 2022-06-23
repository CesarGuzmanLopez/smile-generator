package com.smiles.v2.main.interfaces;

import java.util.List;

import com.smiles.v2.main.domain.models.Molecule;
/**Interface for the MoleculeList.
 * @author Cesar Gerardo Guzman Lopez
 */
public interface MoleculeListInterface  {
    /**
     * @return the list of molecules selected or complete.
    */
    List<Molecule> getListMolecule();
    /** Add Smile.
     * @param name name of the molecule.
     * @param smile smile of the molecule.
     * @param message The message to be displayed.
     * @param hasHydrogenImplicit true if the molecule has hydrogen implicit.
     * @return number in the insert
    */
    int addSmiles(String name, String smile, String message, boolean hasHydrogenImplicit);
    /** Add molecule.
     * @param smile molecule to be added.
     * @return number in the insert
    */
    int addSmiles(SmilesHInterface smile);
    /**
     * @param name name of the molecule.
     * @return Molecule with name name.
    */
    Molecule getMolecule(String name);
}
