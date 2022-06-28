package com.smiles.v2.main.domain.generator;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.domain.models.MoleculesListAbstract;
import com.smiles.v2.main.domain.models.MoleculesListNotRepeat;

/**
 * generator of smiles.
 *
 * @author Cesar G G L
 * @version 1.01
 * @since 1.0
 */
public class Generator {
    private Molecule principal;
    private MoleculesList moleculeList;
    private int rSubstitutes;
    private int numBounds;
    private MoleculesListNotRepeat moleculeListNotRepeat;
    private MoleculesList listOfSubstitutes;
    /**
     * This Class is used to generate smiles permutes.
     *
     * @param principal The molecule Principal.
     * @param moleculeList The list of molecules.
     * @param rSubstitutes The number of substitutes.
     * @param numBounds The number of bounds.
     */
    public Generator(final Molecule principal, final MoleculesList moleculeList, final int rSubstitutes,
            final int numBounds) {
        this.principal = principal;
        this.moleculeList = moleculeList;
        this.rSubstitutes = rSubstitutes;
        this.numBounds = numBounds;
        moleculeListNotRepeat = new MoleculesListNotRepeat(moleculeList);
        generateSubstitutes();
    }
    /**
     * This method is used to generate the list of Substitutes.
     */
    private void generateSubstitutes() {
        listOfSubstitutes = new MoleculesList(moleculeList.getSmileVerifier(), moleculeList.getFactoryMol());

    }
    /**
     * This method is used to generate the smiles permutes.
     *
     * @return The list of smiles permutes.
     */
    public  MoleculesListAbstract getAllMolecules() {
        return moleculeListNotRepeat;
    }
    /**
     * @return the listOfSubstitutes
    */
    public final MoleculesList getListOfSubstitutes() {
        return listOfSubstitutes;
    }
}
