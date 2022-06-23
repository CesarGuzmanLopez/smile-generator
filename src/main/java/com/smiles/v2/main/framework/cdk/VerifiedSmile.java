package com.smiles.v2.main.framework.cdk;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.smiles.SmilesParser;

import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class VerifiedSmile implements SmileVerificationInterface {
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final String smile) {
        final SmilesParser smileParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        try {
            smileParser.parseSmiles(smile);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isValid(final SmilesHInterface smile) {
        final SmilesParser smileParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        try {
            smileParser.parseSmiles(smile.getSmile());
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
