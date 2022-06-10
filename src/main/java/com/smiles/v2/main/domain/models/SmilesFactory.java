package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.SmileVerification;
import com.smiles.v2.main.interfaces.SmilesH;

public class SmilesFactory implements SmilesH {

    private String  smiles="";
    private String  message="";
    private boolean hydrogenImplicit=false;
    private String name="";
    public SmilesFactory(String name, String smiles, String message, boolean hydrogenImplicit,
                            SmileVerification smileVerification) {
        if(smiles == null || message == null || name == null) {
            throw new IllegalArgumentException("Smiles, message or name must be not null");
        }
        if(!smileVerification.isValid(smiles)) {
            throw new IllegalArgumentException("Smiles is not valid");
        }
        this.smiles = smiles;
        this.message = message;
        this.hydrogenImplicit = hydrogenImplicit;
    }
    public SmilesFactory(SmilesH smile, SmileVerification smileVerification) {
        if(smile == null) {
            throw new IllegalArgumentException("Smiles must be not null");
        }
        if(!smileVerification.isValid(smile)) {
            throw new IllegalArgumentException("Smiles is not valid");
        }
        this.smiles = smile.getSmi();
        this.message = smile.getMessage();
        this.hydrogenImplicit = smile.getHydrogen();
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getSmi() {
        return smiles;
    }
    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public boolean getHydrogen() {
        return hydrogenImplicit;
    }

}
