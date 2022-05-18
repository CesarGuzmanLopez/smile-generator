package com.smiles.InertacesGraficas;

import java.util.ArrayList;
import java.util.List;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.layout.StructureDiagramGenerator;
import org.openscience.cdk.smiles.SmilesParser;

public class Substituent {
    private List<String> SubsName;
    private List<String> MOLSmile;
    private List<IAtomContainer> Mol;
    private List<Boolean> HydroImpli;
    
    public Substituent(List<ArrayList<Integer>> SustitutosSelec) {
        Mol = new ArrayList<>();
        setSubsName(new ArrayList<>());
        setMOLSmile(new ArrayList<>());
        setHydroImpli(new ArrayList<>());
        for (Substitutes valor : Substitutes.values()) {
           try{
                SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
                StructureDiagramGenerator sdg = new StructureDiagramGenerator();
                IAtomContainer mol = sp.parseSmiles(valor.getSmi());
                sdg.setMolecule(mol);
                sdg.generateCoordinates();
                mol = sdg.getMolecule();
                SustitutosSelec.add(new ArrayList<>());
                getMOLSmile().add(valor.getSmi());
                getSubsName().add(valor.name());
                Mol.add(mol);
                getHydroImpli().add(Boolean.valueOf(valor.getHydrogen()));
            }catch(Exception e){
                
            }
        }
    }

    public List<String> getMOLSmile() {
        return MOLSmile;
    }

    public void setMOLSmile(List<String> mOLSmile) {
        this.MOLSmile = mOLSmile;
    }

    public List<Boolean> getHydroImpli() {
        return HydroImpli;
    }

    public void setHydroImpli(List<Boolean> hydroImpli) {
        this.HydroImpli = hydroImpli;
    }

    public List<String> getSubsName() {
        return SubsName;
    }

    public void setSubsName(List<String> subsName) {
        this.SubsName = subsName;
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
            getMOLSmile().add(Smile);
            getSubsName().add(name);
            getHydroImpli().add(Impli);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
