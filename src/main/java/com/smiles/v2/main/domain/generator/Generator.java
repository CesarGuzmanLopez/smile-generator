package com.smiles.v2.main.domain.generator;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;

public class Generator {
    private MoleculesList substitutes;
    private Molecule principal;
    private int rSubstitutes;
    private MoleculeDataFactoryInterface factory;
    private SmileVerificationInterface smileVerifier;

    public  Generator(
            MoleculesList substitutes,
            Molecule principal,
            int rSubstitutes,
            MoleculeDataFactoryInterface factory,
            SmileVerificationInterface smileVerifier ){
        this.substitutes = substitutes;
        this.principal = principal;
        this.rSubstitutes = rSubstitutes;
        this.factory = factory;
        this.smileVerifier = smileVerifier;
    }


}
