package com.smiles.v2.main.interfaces;

import java.util.List;

public interface SmilesListInterface  {

    public List<SmilesHInterface>getSmilesHList();
    public int addSmiles(String name, String smi, String message, boolean hydrogen);
    public int addSmiles(SmilesHInterface smile);
}
