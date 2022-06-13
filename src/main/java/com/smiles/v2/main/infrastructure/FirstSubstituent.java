package com.smiles.v2.main.infrastructure;

import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;

public final class FirstSubstituent {
    private FirstSubstituent() {
    }
    public static MoleculesList getMoleculeListInitializer(SmileVerificationInterface verificationSmile, MoleculeDataFactoryInterface factory) {
        MoleculesList  list= new MoleculesList(verificationSmile,factory);
        list.addSmiles("Alcohol", "[OH]", "Over Oxygen", false);
        list.addSmiles("Thiol", "[SH]", "Over Sulfur", false );
        list.addSmiles("Amine", "[NH2]", "Over Nitrogen", false );
        list.addSmiles("Chlorine", "[Cl]", "Over Chloride", false );
        list.addSmiles("CarboxylicAcid", "[C](=O)O", "Over Carbon", false );
        list.addSmiles("Chloromethane", "[CH2]Cl", "Over CH2", false );
        list.addSmiles("Aldehyde", "[CH]=O", "Over Carbon", false );
        list.addSmiles("Benzene", "c1ccc[c]c1", "Over C", false );
        list.addSmiles("Diichloromethane", "[CH](Cl)Cl", "Over Carbon", false);
        list.addSmiles("Fluoromethane", "[CH2]F", "Over Carbon", false );
        list.addSmiles("Difluoromethane", "[CH](F)F", "Over Carbon", false );
        list.addSmiles("Nitro", "[N](=O)([O])", "Over Nitrogen", false );
        list.addSmiles("CH2_SH", "[CH2]S", "Over CH2", false );
        list.addSmiles("S_CH3", "C[S]", "Over Sulfur", false );
        list.addSmiles("C2C", "[CH]=C", "Over CH", false );
        list.addSmiles("C4ClH9", "CCCCCl", "   ASD", false );
        return list;
    }
}
