package com.smiles.v2.main.views.events;

import java.util.EventObject;

import com.smiles.v2.main.interfaces.SmilesHInterface;

@SuppressWarnings("java:S1948")
public class AddSmileHEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    private SmilesHInterface smileH;
    /**
     * Injects the smileH.
     * @param source the source of the event.
     * @param smileH the smileH.
     */
    public AddSmileHEvent(final Object source, final SmilesHInterface smileH) {
        super(source);
        if (smileH == null) {
            throw new NullPointerException("SmilesH is null");
        } else {
            this.smileH = smileH;
        }
    }
    /**
     * @return the smileH.
     */
    public SmilesHInterface getSmileH() {
        return smileH;
    }
}
