package com.smiles.v2.main.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.smiles.v2.main.domain.models.Smiles;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;
import com.smiles.v2.main.interfaces.SmilesListInterface;

public class GeneraSmilesInput implements SmilesListInterface {
    SmileVerificationInterface verificarSmile;
    List<SmilesHInterface> smilesHList = new ArrayList<>();
    public GeneraSmilesInput(SmileVerificationInterface verificarSmile) {
        this.verificarSmile = verificarSmile;
    }

    public List<SmilesHInterface> getSmilesHList() {
        smilesHList.add(new Smiles("Alcohol", "[OH]", "Over Oxygen", false, verificarSmile));
        smilesHList.add(new Smiles("Thiol", "[SH]", "Over Sulfur", false, verificarSmile));
        smilesHList.add(new Smiles("Amine", "[NH2]", "Over Nitrogen", false, verificarSmile));
        smilesHList.add(new Smiles("Chlorine", "[Cl]", "Over Chloride", false, verificarSmile));
        smilesHList.add(new Smiles("CarboxylicAcid", "[C](=O)O", "Over Carbon", false, verificarSmile));
        smilesHList.add(new Smiles("Chloromethane", "[CH2]Cl", "Over CH2", false, verificarSmile));
        smilesHList.add(new Smiles("Aldehyde", "[CH]=O", "Over Carbon", false, verificarSmile));
        smilesHList.add(new Smiles("Benzene", "c1ccc[c]c1", "Over C", false, verificarSmile));
        smilesHList.add(new Smiles("Diichloromethane", "[CH](Cl)Cl", "Over Carbon", false, verificarSmile));
        smilesHList.add(new Smiles("FloroMethane", "[CH2]F", "Over Carbon", false, verificarSmile));
        smilesHList.add(new Smiles("DiFloroMethane", "[CH](F)F", "Over Carbon", false, verificarSmile));
        smilesHList.add(new Smiles("Nitro", "[N](=O)([O])", "Over Nitrogen", false, verificarSmile));
        smilesHList.add(new Smiles("CH2_SH", "[CH2]S", "Over CH2", false, verificarSmile));
        smilesHList.add(new Smiles("S_CH3", "C[S]", "Over Sulfur", false, verificarSmile));
        smilesHList.add(new Smiles("C2C", "[CH]=C", "Over CH", false, verificarSmile));
        smilesHList.add(new Smiles("C4ClH9", "CCCCCl", "   ASD", false, verificarSmile));
        return smilesHList;
    }
    @Override
    // return number of smiles added
    public int  addSmiles(String name, String smi, String message, boolean hydrogen) {
        smilesHList.add(new Smiles(name, smi, message, hydrogen, verificarSmile));
        return smilesHList.size()-1;
    }

    @Override
    public int addSmiles(SmilesHInterface smile) {
        smilesHList.add(new Smiles(smile, verificarSmile));
        return smilesHList.size()-1;
    }

}
