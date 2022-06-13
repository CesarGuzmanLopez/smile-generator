package com.smiles.v2.main.views.events;

import java.util.EventObject;

import com.smiles.v2.main.interfaces.SmilesHInterface;

@SuppressWarnings("java:S1948")
public class AddSmileHEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    private SmilesHInterface smileH;

    public AddSmileHEvent(Object source, SmilesHInterface smileH) {
        super(source);
        if (smileH == null) {
            throw new NullPointerException("SmilesH is null");
        } else {
            this.smileH = smileH;
        }
    }

    public SmilesHInterface getSmileH() {
        return smileH;
    }
}
