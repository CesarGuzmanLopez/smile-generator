package com.smiles.v2.main.framework.cdk;

import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;

import com.smiles.v2.main.interfaces.AtomInterface;

public class AtomCDK implements AtomInterface {
    private IAtom iAtom;
    private boolean selected;
    private final int id;
    public AtomCDK(final IAtom atom, final int id) {
        this.iAtom = atom;
        this.id = id;
    }
    /**
     * @return the bonds connected to this atom. cdk
     *
    */
    Iterable<IBond> bonds() {
        return iAtom.bonds();
    }
    /** return IAtom.
     * @return IAtom
    */
    public IAtom getIAtom() {
        return iAtom;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSelected() {
        return selected;
    }
    /**select the atom.
     * change the state of the atom.
     */
    protected void selected() {
        this.selected = !selected;
    }
    /**
     * {@inheritDoc}
     */
    @Override
     public final int getId() {
        return id;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public  final  String getSymbol() {
        return iAtom.getSymbol();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public final  String toString() {
        return iAtom.toString();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public int getImplicitHydrogens() {
        return iAtom.getImplicitHydrogenCount();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void setImplicitHydrogens(final int implicitHydrogens) {
        iAtom.setImplicitHydrogenCount(implicitHydrogens);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void decreaseImplicitHydrogens() {
        iAtom.setImplicitHydrogenCount(iAtom.getImplicitHydrogenCount() - 1);
    }


}
