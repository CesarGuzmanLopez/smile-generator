package com.smiles.v2.main.domain.models;

public class MoleculesAdded {
    private Integer locate;
    private Molecule molecule;
    /**
     * @param locate index atom aggregate
     * @param molecule molecule added
    */
    public MoleculesAdded(final Integer locate, final Molecule molecule) {
        if (locate == null) {
            this.locate = 0;
        } else {
            this.locate = locate;
        }
        this.molecule = molecule;
    }
    /**
     * @return index atom aggregate
    */
    public Integer getLocate() {
        return locate;
    }
    /**
     * @return molecule added
    */
    public Molecule getMolecule() {
        return molecule;
    }
}
