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
    File nameFileOutput;
    File nameFileDescription;
    public Generator(MoleculesList substitutes, Molecule principal,
            int rSubstitutes,
            File nameFileOutput,
            File nameFileDescription) {
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
            File nameFileOutput,
            File nameFileDescription) {
        if (substitutes == null || principal == null)
            throw new IllegalArgumentException("Null argument");
        if (rSubstitutes <= 0)
            throw new IllegalArgumentException("rSubstitutes <= 0");
        if(principal.getMoleculeData().getListAtomsSelected().size()>1 &&rSubstitutes>principal.getMoleculeData().getListAtomsSelected().size())
            throw new IllegalArgumentException("rSubstitutes cannot be greater than the selected atoms");
        if (nameFileOutput == null )
            throw new IllegalArgumentException("nameFileOutput == null");
        if(!aAtomOrSelected(principal))
            throw new IllegalArgumentException( principal.getName() +"Principal molecule has no atom or selected");
        List<Molecule> list = substitutes.getMoleculeListMolecule();
        if (list.isEmpty())
            throw new IllegalArgumentException("List of substitutes is empty");
        for (Molecule molecule : list) {
            if (!aAtomOrSelected(molecule))
                throw new IllegalArgumentException( molecule.getName() + ": is notSubstitute molecule has no atom or selected");
        }
        if(nameFileDescription!=null)
            nameFileDescription.getParentFile().mkdirs();
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
