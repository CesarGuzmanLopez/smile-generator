package com.smiles.v2.main.framework.cdk;

import java.util.List;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.interfaces.AtomInterface;
import com.smiles.v2.main.interfaces.MoleculeDataOfSmileInterface;


public class MoleculeDataOfSmile implements MoleculeDataOfSmileInterface {
    Molecule molecule;
    IAtomContainer moleculeContainer;
    MoleculeDataOfSmile(Molecule molecule) {
        this.molecule = molecule;
        SmilesParser smileParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        try{
            moleculeContainer = smileParser.parseSmiles(molecule.getSmile());
        }catch(Exception e){
            throw new IllegalArgumentException("Molecule error");
        }
    }


    @Override
    public int getNumberAtoms() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<AtomInterface> getListAtoms() {
        // TODO Auto-generated method stub
        return null;
    }

}
