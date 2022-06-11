package com.smiles.v2.main.framework.cdk;

import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class VerificarSmile implements SmileVerificationInterface {

    @Override
    public boolean isValid(String smile) {
        return !smile.equals("manzana");
    }

    @Override
    public boolean isValid(SmilesHInterface smile) {
        // TODO Auto-generated method stub
        return false;
    }

}
