package com.smiles.v2.main.interfaces;

import java.util.List;

public interface MoleculeDataInterface {
    public int getNumberAtoms();
    public void selectOrderAtom(AtomInterface atom);
    public List<AtomInterface> getListAtoms();
    public List<AtomInterface> getListAtomsSelected();
}
