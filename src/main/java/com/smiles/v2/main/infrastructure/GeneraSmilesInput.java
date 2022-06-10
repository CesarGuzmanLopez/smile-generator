package com.smiles.v2.main.infrastructure;

import java.util.ArrayList;
import java.util.List;

import com.smiles.v2.main.domain.models.SmilesFactory;
import com.smiles.v2.main.framework.cdk.VerificarSmile;
import com.smiles.v2.main.interfaces.SmilesH;
import com.smiles.v2.main.interfaces.SmilesList;

public class GeneraSmilesInput implements SmilesList {
    public List<SmilesH> getSmilesHList() {
        List<SmilesH> smilesHList = new ArrayList<>();

        smilesHList.add(new SmilesFactory("Alcohol", "[OH]", "Over Oxygen", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("Thiol", "[SH]", "Over Sulfur", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("Amine", "[NH2]", "Over Nitrogen", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("Chlorine", "[Cl]", "Over Chloride", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("CarboxylicAcid", "[C](=O)O", "Over Carbon", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("Chloromethane", "[CH2]Cl", "Over CH2", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("Aldehyde", "[CH]=O", "Over Carbon", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("Benzene", "c1ccc[c]c1", "Over C", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("Diichloromethane", "[CH](Cl)Cl", "Over Carbon", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("FloroMethane", "[CH2]F", "Over Carbon", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("DiFloroMethane", "[CH](F)F", "Over Carbon", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("Nitro", "[N](=O)([O])", "Over Nitrogen", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("CH2_SH", "[CH2]S", "Over CH2", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("S_CH3", "C[S]", "Over Sulfur", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("C2C", "[CH]=C", "Over CH", false, new VerificarSmile()));
        smilesHList.add(new SmilesFactory("C4ClH9", "CCCCCl", "   ASD", false, new VerificarSmile()));
        return smilesHList;
    }

}
