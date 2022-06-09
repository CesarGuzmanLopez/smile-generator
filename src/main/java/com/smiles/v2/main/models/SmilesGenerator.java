package com.smiles.v2.main.models;

import com.smiles.v2.main.interfaces.SmilesH;

public class SmilesGenerator implements SmilesH {

    private String  smiles;
    private String  message;
    private boolean hydrogenImplicit;

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
