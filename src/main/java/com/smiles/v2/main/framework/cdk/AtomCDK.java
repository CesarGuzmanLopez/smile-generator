package com.smiles.v2.main.framework.cdk;

import org.openscience.cdk.interfaces.IAtom;

import com.smiles.v2.main.interfaces.AtomInterface;

public class AtomCDK implements AtomInterface {
    private IAtom iAtom;
    private boolean selected;
    private final int id;
    public AtomCDK(IAtom atom, int id){
        this.iAtom = atom;
        this.id = id;
    }
    public IAtom getIAtom() {
        return iAtom;
    }
    @Override
    public boolean isSelected() {
        return selected;
    }



    protected void selected() {
        this.selected = !selected;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getSymbol() {
        return iAtom.getSymbol();
    }


}
