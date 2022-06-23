package com.smiles.v2.main.interfaces;

import com.smiles.v2.main.domain.models.Molecule;
/**Interface for the MoleculeComparable.
 * @author Cesar Gerardo Guzman Lopez
 */
public interface MoleculeComparableInterface extends Comparable<Molecule> {
    /**
     * Compare this molecule with another molecule.
     * @param molecule molecule to compare.
     * @return the result of the comparison.
     */
    int compareTo(Molecule molecule);

}
