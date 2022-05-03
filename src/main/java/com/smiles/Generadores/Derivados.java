package com.smiles.Generadores;

import java.util.ArrayList;
import java.util.Comparator;

import java.util.Vector;

import javax.swing.JCheckBox;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;

public class Derivados {
    public  IAtomContainer MolPrin;
    public  ArrayList<Integer> Selec;
    public  ArrayList<IAtomContainer> groupSust;
    public  ArrayList<ArrayList<Integer>> groupSelecSust;
    public  ArrayList<Boolean>HydroImpli;
    public Vector <JCheckBox> GruposSustitutosSeleccionados;
    int numRSubst;
    private ArrayList<IAtomContainer> result;
    public ArrayList<String> smiles; 
    
    public Derivados(int NumRSubst,IAtomContainer MolPrin,ArrayList<Integer> Selec,ArrayList<IAtomContainer> groupSust,
                     ArrayList<ArrayList<Integer>> groupSelecSust,ArrayList<Boolean>HydroImpli,
                     Vector <JCheckBox> GruposSustitutosSeleccionados){

        this.numRSubst=NumRSubst;
        this.Selec=Selec;
        this.MolPrin=MolPrin;
        this.groupSust=groupSust;
        this.groupSelecSust=groupSelecSust;
        this.HydroImpli=HydroImpli;
        this.GruposSustitutosSeleccionados=GruposSustitutosSeleccionados;
        smiles=new ArrayList<String>();
        result=new ArrayList<IAtomContainer>();
        error=0;
        
        Todos(NumRSubst,MolPrin,Selec);
    }
   

    private int error=0;
    @SuppressWarnings("unchecked")
    private void Todos(int NumRSubst,IAtomContainer MolPrin,ArrayList<Integer> Selec) {
        if(NumRSubst<1)
            return ;
        IAtomContainer copiaTemp,hclone;
        ArrayList<Integer> selecCopy;    
        String smiletemp;
        for(int i: Selec) {
            int p=0;
            for(IAtomContainer H:groupSust ) {
                for(int j: groupSelecSust.get(p)) {
                    if(GruposSustitutosSeleccionados.get(p).isSelected())
                        try {
                            copiaTemp=MolPrin.clone();
                            
                            hclone =H.clone();
                            if(HydroImpli.get(p)) {
                                hclone.getAtom(j).setImplicitHydrogenCount(copiaTemp.getAtom(j).getImplicitHydrogenCount()-1);
                            }        
                            copiaTemp.add(hclone);
                            copiaTemp.getAtom(i).setImplicitHydrogenCount(copiaTemp.getAtom(i).getImplicitHydrogenCount()-1);
                            copiaTemp.addBond(i,MolPrin.getAtomCount()+j,IBond.Order.SINGLE);
                            result.add(copiaTemp);
                            selecCopy = (ArrayList<Integer>) Selec.clone();
                            selecCopy.remove((Object) i);
                            Todos(NumRSubst-1,copiaTemp,selecCopy);
                            
                            SmilesGenerator generator = new SmilesGenerator(SmiFlavor.Isomeric);
                            smiletemp=generator.create(copiaTemp);
                            if(!smiles.contains(smiletemp))
                                smiles.add(smiletemp);
                        } catch (Exception e) {
                            error++;
                        }                    
                }
                p++;
            }
        }
    }
    
    public String TodosSmilesJuntos() {
        String y="";
        smiles.sort(new Comparator<String>() {
            @Override
            public int compare(String arg0, String arg1) {
                return arg0.compareTo(arg1);
            }
        });
        for(String x :smiles) {
            y+=x;
            y+="\n";
        }
        return y;
    }
}
