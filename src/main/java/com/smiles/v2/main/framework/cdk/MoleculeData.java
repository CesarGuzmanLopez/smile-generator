package com.smiles.v2.main.framework.cdk;

import java.util.ArrayList;
import java.util.List;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.smiles.SmilesParser;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.interfaces.AtomInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;

public class MoleculeData implements MoleculeDataInterface {
    Molecule molecule;
    IAtomContainer moleculeContainer;
    List<AtomInterface> listAtoms;
    List<AtomInterface> selectedList;

    MoleculeData(Molecule molecule) {
        this.molecule = molecule;
        SmilesParser smileParser = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        try {
            moleculeContainer = smileParser.parseSmiles(molecule.getSmile());
        } catch (Exception e) {
            throw new IllegalArgumentException("Molecule error");
        }
        listAtoms = new ArrayList<>();
        for (int i = 0; i < moleculeContainer.getAtomCount(); i++) {
            listAtoms.add(new AtomSelectable(moleculeContainer.getAtom(i), i));
        }

        selectedList = new ArrayList<>();
    }

    class AtomSelectable extends AtomCDK {
        public AtomSelectable(IAtom atom, int id) {
            super(atom, id);
        }

        @Override
        public void selected() {
            super.selected();
        }
    }

    IAtomContainer getMoleculeContainer() {
        return moleculeContainer;
    }

    @Override
    public int getNumberAtoms() {
        return moleculeContainer.getAtomCount();
    }

    @Override
    public List<AtomInterface> getListAtoms() {
        return listAtoms;
    }

    @Override
    public void selectOrderAtom(AtomInterface atom) {

        if (!selectedList.contains(atom))
            selectedList.add(atom);
        else
            selectedList.remove(atom);
        ((AtomSelectable) atom).selected();

    }

    @Override
    public List<AtomInterface> getListAtomsSelected() {
        return selectedList;

    }

}
