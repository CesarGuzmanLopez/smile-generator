package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class Smiles implements SmilesHInterface {

    private String  smiles="";
    private String  message="";
    private boolean hydrogenImplicit=false;
    private String name="";
    public Smiles(String name, String smiles, String message, boolean hydrogenImplicit,
                            SmileVerificationInterface smileVerification) {
        if(smiles == null || message == null || name == null) {
            throw new IllegalArgumentException("Smiles, message or name must be not null");
        }
        if(name .equals( "")) {
            throw new IllegalArgumentException("Name must be not empty");
        }
        if(!smileVerification.isValid(smiles)) {
            throw new IllegalArgumentException("Smiles: " + smiles+" is not valid");
        }
        this.name = name;
        this.smiles = smiles;
        this.message = message;
        this.hydrogenImplicit = hydrogenImplicit;
    }
    public Smiles(SmilesHInterface smile, SmileVerificationInterface smileVerification) {
        if(smile == null) {
            throw new IllegalArgumentException("Smiles must be not null");
        }
        if(smile.getName().equals( "")){
            throw new IllegalArgumentException("Name must be not empty");
        }
        if(!smileVerification.isValid(smile.getSmi())) {
            throw new IllegalArgumentException("Smiles is not valid");
        }
        this.name = smile.getName();
        this.smiles = smile.getSmi();
        this.message = smile.getMessage();
        this.hydrogenImplicit = smile.hasHydrogenImplicit();
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
    public boolean hasHydrogenImplicit() {
        return hydrogenImplicit;
    }

}
