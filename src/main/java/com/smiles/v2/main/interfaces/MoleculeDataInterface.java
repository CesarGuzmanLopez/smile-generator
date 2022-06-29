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
    String absoluteSmile();
    /**
     * @param id to search the atom.
     * @return the atom with ID specified.
    */
    AtomInterface getAtom(int id);

    /** fusion two molecules.
     * @param mol
     * @param selectedIn the selected atom in the molecule
     * @param selectedOut the selected atom in the substituted molecule.
     * @param numBond the number of bonds to connect the selected.
    */
    void  addMoleculeData(Molecule mol, Integer selectedIn, Integer selectedOut, Integer numBond);

    /**
     * unselect all the atoms.
    */
    void unselectedAllAtoms();
    /**
     * @param id to select the atom.
     */
    void selectOrderAtom(int id);

    /**
     * @return numOfMolecules added
     */
    int getNumOfMolecules();


}
