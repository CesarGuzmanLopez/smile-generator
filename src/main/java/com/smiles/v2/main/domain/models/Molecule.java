package com.smiles.v2.main.domain.models;

import com.smiles.v2.main.interfaces.MoleculeComparableInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;
import com.smiles.v2.main.interfaces.SmilesHInterface;

public class Molecule extends Smiles implements MoleculeComparableInterface {
    private MoleculeDataFactoryInterface moleculeDataFactory;
    private MoleculeDataInterface moleculeDataOfSmile;

    public Molecule(SmilesHInterface smile, SmileVerificationInterface smileVerification,
            MoleculeDataFactoryInterface moleculeFactory) {
        super(smile, smileVerification);
        this.moleculeDataFactory = moleculeFactory;
        initialize();
    }

    public Molecule(String name, String smiles, String message, boolean hydrogenImplicit,
            SmileVerificationInterface smileVerification,
            MoleculeDataFactoryInterface moleculeDataOfSmile) {
        super(name, smiles, message, hydrogenImplicit, smileVerification);
        this.moleculeDataFactory = moleculeDataOfSmile;
        initialize();
    }

    public Molecule(Molecule molecule) {
        super(molecule, molecule.smileVerification);
        this.moleculeDataFactory = molecule.moleculeDataFactory;
        initialize();
    }

    void initialize() {
        moleculeDataOfSmile = moleculeDataFactory.getMoleculeDataOfSmile(this);
    }

    public int getNumberAtoms() {
        return moleculeDataOfSmile.getNumberAtoms();
    }

    public MoleculeDataInterface getMoleculeData() {
        if (moleculeDataOfSmile == null)
            throw new NullPointerException("MoleculeDataOfSmile is null");
        return moleculeDataOfSmile;
    }

    @Override
    public int compareTo(Molecule molecule) {
        return getMoleculeData().compareTo(molecule);
    }

    @Override
    public String toString() {
        return getMoleculeData().isomericSmile();
    }

    public static Molecule fusionMolecule(Molecule principal, Molecule Substituent,
            Integer numAtomPrincipal, Integer numAtomSubstitute) {

        verifyToSubstitute(principal, Substituent, numAtomPrincipal, numAtomSubstitute);


        return null;
    }
    public void resetSmile(){
        setSmiles(getMoleculeData().isomericSmile());
    }
    private static void verifyToSubstitute(Molecule principal, Molecule Substituent, Integer numAtomPrincipal,
            Integer numAtomSubstitute) {
        if (principal == null || Substituent == null)
            throw new NullPointerException("Molecule is null");
        if ((numAtomPrincipal < 0 || numAtomPrincipal == null) && principal.getNumberAtoms() > 1)
            throw new NullPointerException("toSubstitute is null or 0 ");
        if (numAtomPrincipal >= principal.getMoleculeData().getListAtomsSelected().size() &&
                principal.getMoleculeData().getListAtomsSelected().size() > 1)
            throw new NullPointerException("toSubstitute is greater than number of atoms selected");
        if ((numAtomSubstitute < 0 || numAtomSubstitute == null) && Substituent.getNumberAtoms() > 1)
            throw new NullPointerException("numAtomSubstitute is null or 0 ");
        if (numAtomSubstitute >= Substituent.getMoleculeData().getListAtomsSelected().size() &&
                Substituent.getMoleculeData().getListAtomsSelected().size() > 1)
            throw new NullPointerException("toSubstitute is greater than number of atoms selected");
    }

}
