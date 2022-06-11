package com.smiles.v2.main.framework.cdk;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class VerifiedSmile implements SmileVerificationInterface {

    @Override
    public boolean isValid(String smile) {
        SmilesParser smileParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        try {
            smileParser.parseSmiles(smile);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean isValid(SmilesHInterface smile) {
        SmilesParser smileParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        try {
            smileParser.parseSmiles(smile.getSmile());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
