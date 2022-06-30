package com.smiles.v2.main.domain.models;

public class MoleculesAdded {
    private Integer locate;
    private Molecule molecule;
    private Integer hydrogensDeleted;

    /**
     * @param locate   index atom aggregate
     * @param molecule molecule added
     */
    public MoleculesAdded(final Integer locate, final Molecule molecule) {
        if (locate == null) {
            this.locate = 0;
        } else {
            this.locate = locate;
        }
        this.molecule = molecule;
        hydrogensDeleted = 0;
    }

    /**
     * @return number of hydrogens deleted
     */
    public Integer getHydrogensDeleted() {
        return hydrogensDeleted;
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

    /**
     * Num of hydrogen deleted
     */
    public void addHydrogensDelete() {
        hydrogensDeleted++;
    }
}
