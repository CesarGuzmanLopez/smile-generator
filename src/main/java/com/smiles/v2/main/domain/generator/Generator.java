package com.smiles.v2.main.domain.generator;
import java.io.File;
import java.util.List;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;

public class Generator {
    MoleculesList substitutes;
    Molecule principal;
    int rSubstitutes;
    String nameFileOutput;
    String nameFileDescription;
    public Generator(MoleculesList substitutes, Molecule principal,
            int rSubstitutes,
            String nameFileOutput,
            String nameFileDescription) {
        verifyEntry(substitutes, principal, rSubstitutes, nameFileOutput, nameFileDescription);

        this.substitutes = substitutes;
        this.principal = principal;
        this.rSubstitutes = rSubstitutes;
        this.nameFileOutput = nameFileOutput;
        this.nameFileDescription = nameFileDescription;

    }
    public static final boolean verifyEntry(
            MoleculesList substitutes,
            Molecule principal,
            int rSubstitutes,
            String nameFileOutput,
            String nameFileDescription) {
        if (substitutes == null || principal == null)
            throw new IllegalArgumentException("Null argument");
        if (rSubstitutes <= 0)
            throw new IllegalArgumentException("rSubstitutes <= 0");
        if (nameFileOutput == null || nameFileOutput.isEmpty())
            throw new IllegalArgumentException("nameFileOutput == null");
        if (nameFileDescription !=null && nameFileDescription.length() > 0){
             new File(nameFileDescription);
        }
        new File(nameFileOutput);
        if(!aAtomOrSelected(principal))
            throw new IllegalArgumentException( principal.getName() +"Principal molecule has no atom or selected");
        List<Molecule> list = substitutes.getMoleculeList();
        if (list.isEmpty())
            throw new IllegalArgumentException("List of substitutes is empty");
        for (Molecule molecule : list) {
            if (!aAtomOrSelected(molecule))
                throw new IllegalArgumentException( molecule.getName() + ": is notSubstitute molecule has no atom or selected");
        }
        return true;
    }
    static final boolean aAtomOrSelected(Molecule molecule){
        MoleculeDataInterface moleculeData = molecule.getMoleculeData();
        if(moleculeData == null){
            throw new IllegalArgumentException("moleculeData is null");
        }
        if(moleculeData.getNumberAtoms() ==1)
            return true;
        if(moleculeData.getNumberAtoms()<=0 )
            return false;

        return !moleculeData.getListAtoms().isEmpty();
    }




}
