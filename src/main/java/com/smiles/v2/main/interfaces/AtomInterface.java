
package com.smiles.v2.main.interfaces;
/**Interface for the Atom.
 * @author Cesar Gerardo Guzman Lopez
 */
public interface AtomInterface {
    /**
     * @return if the atom is selected.
    */
    boolean isSelected();
    /**
     * @return ID of the atom.
     */
    int getId();
    /**
     * Method get the symbol of the atom.
     * @return String
     */
    String getSymbol();
    /**
     * Method get the string of the atom.
     * @return String smile
     */
    String toString();

    /**
     * get the number of implicit hydrogens of the atom.
     * @return int
     */
    int getImplicitHydrogens();

    /**
     * set the number of implicit hydrogens of the atom.
     * @param implicitHydrogens to redifine the number of implicit hydrogens.
    */
    void setImplicitHydrogens(int implicitHydrogens);

    /**
     * decrease Hydrogen from implicit atoms.
     */
    void decreaseImplicitHydrogens();

}
