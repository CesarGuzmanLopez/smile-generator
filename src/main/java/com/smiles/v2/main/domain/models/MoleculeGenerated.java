package com.smiles.v2.main.domain.models;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("java:S2160")
public class MoleculeGenerated extends Molecule {
    private final MoleculeGenerated principal;
    private List<Substitutes> moleculeListSubstitutesAdd;
    private final Molecule substituentActual;

    public MoleculeGenerated(Molecule molecule) { // UNCHECK
        super(molecule);
        principal = this;
        substituentActual = null;
        moleculeListSubstitutesAdd = null;
    }

    public MoleculeGenerated(final Molecule molecule, MoleculeGenerated moleculeMain, // UNCHECK
            final Molecule substituent, final int position) { // UNCHECK
        super(molecule);
        this.principal = moleculeMain.principal;
        substituentActual = substituent;
        moleculeListSubstitutesAdd = new ArrayList<>();
        if (moleculeMain.substituentActual != null) {
            for (Substitutes substitute : moleculeMain.moleculeListSubstitutesAdd) {
                moleculeListSubstitutesAdd.add(substitute);
            }
        }
        moleculeListSubstitutesAdd.add(new Substitutes(position, substituent));
    }

    class Substitutes {
        private Integer position;
        private Molecule substituent;

        Substitutes(final Integer position, Molecule substituent) { // UNCHECK NOSONAR
            this.position = position;
            this.substituent = substituent;
        }

        Integer getPosition() {
            return position;
        }

        Molecule getSubstituent() {
            return substituent;
        }

    }

}
