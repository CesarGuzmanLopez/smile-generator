package com.smiles.v2.main.interfaces;

import java.util.List;

import com.smiles.v2.main.domain.models.Molecule;

public interface MoleculeListInterface  {

    public List<Molecule>getMoleculeList();
    public int addSmiles(String name, String smi, String message, boolean hydrogen);
    public int addSmiles(SmilesHInterface smile);
}
