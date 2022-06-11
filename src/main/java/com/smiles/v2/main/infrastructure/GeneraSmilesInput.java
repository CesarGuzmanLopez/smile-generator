package com.smiles.v2.main.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.smiles.v2.main.domain.models.Smiles;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;
import com.smiles.v2.main.interfaces.SmilesListInterface;

public class GeneraSmilesInput implements SmilesListInterface {
    SmileVerificationInterface verificationSmile;
    List<SmilesHInterface> smilesHList = new ArrayList<>();

    public GeneraSmilesInput(SmileVerificationInterface verificationSmile) {
        this.verificationSmile = verificationSmile;
    }

    public List<SmilesHInterface> getSmilesHList() {
        smilesHList.add(new Smiles("Alcohol", "[OH]", "Over Oxygen", false, verificationSmile));
        smilesHList.add(new Smiles("Thiol", "[SH]", "Over Sulfur", false, verificationSmile));
        smilesHList.add(new Smiles("Amine", "[NH2]", "Over Nitrogen", false, verificationSmile));
        smilesHList.add(new Smiles("Chlorine", "[Cl]", "Over Chloride", false, verificationSmile));
        smilesHList.add(new Smiles("CarboxylicAcid", "[C](=O)O", "Over Carbon", false, verificationSmile));
        smilesHList.add(new Smiles("Chloromethane", "[CH2]Cl", "Over CH2", false, verificationSmile));
        smilesHList.add(new Smiles("Aldehyde", "[CH]=O", "Over Carbon", false, verificationSmile));
        smilesHList.add(new Smiles("Benzene", "c1ccc[c]c1", "Over C", false, verificationSmile));
        smilesHList.add(new Smiles("Diichloromethane", "[CH](Cl)Cl", "Over Carbon", false, verificationSmile));
        smilesHList.add(new Smiles("Fluoromethane", "[CH2]F", "Over Carbon", false, verificationSmile));
        smilesHList.add(new Smiles("Difluoromethane", "[CH](F)F", "Over Carbon", false, verificationSmile));
        smilesHList.add(new Smiles("Nitro", "[N](=O)([O])", "Over Nitrogen", false, verificationSmile));
        smilesHList.add(new Smiles("CH2_SH", "[CH2]S", "Over CH2", false, verificationSmile));
        smilesHList.add(new Smiles("S_CH3", "C[S]", "Over Sulfur", false, verificationSmile));
        smilesHList.add(new Smiles("C2C", "[CH]=C", "Over CH", false, verificationSmile));
        smilesHList.add(new Smiles("C4ClH9", "CCCCCl", "   ASD", false, verificationSmile));
        return smilesHList;
    }

    @Override
    // return number of smiles added
    public int addSmiles(String name, String smi, String message, boolean hydrogen) {
        smilesHList.add(new Smiles(name, smi, message, hydrogen, verificationSmile));
        return smilesHList.size() - 1;
    }

    @Override
    public int addSmiles(SmilesHInterface smile) {
        smilesHList.add(new Smiles(smile, verificationSmile));
        return smilesHList.size() - 1;
    }

}
