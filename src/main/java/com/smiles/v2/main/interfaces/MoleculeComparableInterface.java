package com.smiles.v2.main.interfaces;

import com.smiles.v2.main.domain.models.Molecule;

public interface MoleculeComparableInterface extends Comparable<Molecule> {
    public int compareTo(Molecule molecule);

}
