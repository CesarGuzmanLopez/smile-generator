package com.smiles.v2.main.interfaces;

import java.util.List;

import com.smiles.v2.main.domain.models.Molecule;

public interface MoleculeDataInterface extends MoleculeComparableInterface {
    public int getNumberAtoms();
    public void selectOrderAtom(AtomInterface atom);
    public List<AtomInterface> getListAtoms();
    public List<AtomInterface> getListAtomsSelected();
    public String isomericSmile();
    public boolean  addMoleculeDataInterface(Molecule mol, AtomInterface selectedIn, AtomInterface selectedOut);
}
