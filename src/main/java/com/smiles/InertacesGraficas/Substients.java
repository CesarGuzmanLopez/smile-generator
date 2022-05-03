package com.smiles.InertacesGraficas;

import java.util.ArrayList;

import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.smiles.SmilesParser;

public class Substients {
    public ArrayList<String> SubsName;
    public ArrayList<String> MOLSmile;
    public ArrayList<IAtomContainer> Mol;
    public ArrayList<ArrayList<Integer>> SustitutosSelec;
    public ArrayList<Boolean> HydroImpli;

    public Substients(ArrayList<ArrayList<Integer>> SustitutosSelec) {
        Mol = new ArrayList<IAtomContainer>();
        SubsName = new ArrayList<String>();
        MOLSmile = new ArrayList<String>();
        this.SustitutosSelec =SustitutosSelec ;
        HydroImpli= new ArrayList<Boolean>();
        for (Sust valor : Sust.values()) {
            try {
                SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
                StructureDiagramGenerator sdg = new StructureDiagramGenerator();
                IAtomContainer mol = sp.parseSmiles(valor.getSmi());
                sdg.setMolecule(mol);
                sdg.generateCoordinates();
                mol = sdg.getMolecule();
//            System.out.println("LLEGUE: "+ mol.getAtomCount());
                SustitutosSelec.add(new ArrayList<Integer>());
                MOLSmile.add(valor.getSmi());
                SubsName.add(valor.name());
                Mol.add(mol);
                HydroImpli.add(Boolean.valueOf(valor.getHydrogen()));
            } catch (Exception e) {
                System.out.println("No se puede: " + e.getMessage());
            }
        }
    }

    public boolean AddSubs(String Smile,String name, boolean Impli) {
        try {
            SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
            StructureDiagramGenerator sdg;
            IAtomContainer mol = sp.parseSmiles(Smile);
            sdg = new StructureDiagramGenerator();
            sdg.setMolecule(mol);
            sdg.generateCoordinates();
            mol = sdg.getMolecule();
            Mol.add(mol);
            MOLSmile.add(Smile);
            SubsName.add(name);
            HydroImpli.add(Impli);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
