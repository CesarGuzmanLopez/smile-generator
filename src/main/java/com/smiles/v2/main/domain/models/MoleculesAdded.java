package com.smiles.v2.main.domain.models;

public class MoleculesAdded {
    private Integer locate;
    private Molecule molecule;
    private Integer hydrogensDeleted;
    private Integer selectedPrincipal;



    /**
     * @param locate   index atom aggregate
     * @param molecule molecule added
     * @param selectedPrincipal index atom principal
     */
    public MoleculesAdded(final Integer locate, final Molecule molecule, final Integer selectedPrincipal) {
        if (locate == null) {
            this.locate = 0;
        } else {
            this.locate = locate;
        }
        this.molecule = molecule;
        hydrogensDeleted = 0;
        this.selectedPrincipal = selectedPrincipal;
    }

    /**
     * @return number of hydrogens deleted
     */
    public Integer getHydrogensDeleted() {
        return hydrogensDeleted;
    }

    /**
     * @return index The first atom of molecule.
     */
    public Integer getLocate() {
        return locate;
    }
    /**
     *
     * @return index atom aggregate.
     */

    public Integer getSelectedPrincipal() {
        return selectedPrincipal;
    }
    /**
     * @return molecule added.
     */
    public Molecule getMolecule() {
        return molecule;
    }

    /**
     * Num of hydrogen deleted.
     */
    public void addHydrogensDelete() {
        hydrogensDeleted++;
    }
}
