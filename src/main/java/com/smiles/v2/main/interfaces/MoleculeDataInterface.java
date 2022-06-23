package com.smiles.v2.main.interfaces;

import java.util.List;

import com.smiles.v2.main.domain.models.Molecule;
/**Interface for the MoleculeComparablee.
 * @author Cesar Gerardo Guzman Lopez
 */
public interface MoleculeDataInterface extends MoleculeComparableInterface {
    /**
     * @return the number of molecule
     */
    int getNumberAtoms();
    /**
     * Selected the Atom selected by the Atom interface.
     * @param atom Atom to select.
    */
    void selectOrderAtom(AtomInterface atom);
    /**
     * @return the list of the all atoms.
     */
    List<AtomInterface> getListAtoms();
        /**
     * @return the list of the selected atoms.
     */
    List<AtomInterface> getListAtomsSelected();
    /**
     * @return the Isomeric Smile
     */
    String isomericSmile();
    /** fusion two molecules.
     * @param mol
     * @param selectedIn the selected atom in the molecule
     * @param selectedOut the selected atom in the substituted molecule.
     * @return true if the molecules are fusioned.
    */
    boolean  addMoleculeDataInterface(Molecule mol, AtomInterface selectedIn, AtomInterface selectedOut);
}
