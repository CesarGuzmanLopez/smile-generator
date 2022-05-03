package com.smiles.Generadores;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
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
    int NumRSubst;
    private ArrayList<IAtomContainer> Result;
    public Stack<String> Smiles; 
    public Derivados(int NumRSubst,IAtomContainer MolPrin,ArrayList<Integer> Selec,ArrayList<IAtomContainer> groupSust,
                     ArrayList<ArrayList<Integer>> groupSelecSust,ArrayList<Boolean>HydroImpli,
                     Vector <JCheckBox> GruposSustitutosSeleccionados){
        this.NumRSubst=NumRSubst;
        this.Selec=Selec;
        this.MolPrin=MolPrin;
        this.groupSust=groupSust;
        this.groupSelecSust=groupSelecSust;
        this.HydroImpli=HydroImpli;
        this.GruposSustitutosSeleccionados=GruposSustitutosSeleccionados;
        Smiles=new Stack<String>();
        Result=new ArrayList<IAtomContainer>();
    //    SmilesGenerator generator = new SmilesGenerator(SmiFlavor.Absolute);

    //    System.out.println("--------------------\n");
        //try {
    //        System.out.println("---"+generator.create(MolPrin)+"-----");
    //    } catch (CDKException e) {
        //    e.printStackTrace();
    //    }
        error=0;
        try {
            Todos(NumRSubst,MolPrin,Selec);
            Smiles.sort(new Comparator<String>() {
                @Override
                public int compare(String arg0, String arg1) {
                    return arg0.compareTo(arg1);
                }
            });
            
        }catch(Exception e){
            JOptionPane.showMessageDialog( null,"Expedited time limit, try with less r-substitutes");
        }
        if(error>0) {
            JOptionPane.showMessageDialog( null,"Ended with "+error+" errors");    
        }
    }

    private int error=0;
    @SuppressWarnings("unchecked")
    private void Todos(int NumRSubst,IAtomContainer MolPrin,ArrayList<Integer> Selec) {
        if(NumRSubst<1)
            return ;
        IAtomContainer CopiaTemp,Hclone;
        ArrayList<Integer> SelecCopy;    
        String Smiletemp;
        for(int i: Selec) {
            int p=0;
            for(IAtomContainer H:groupSust ) {
                for(int j: groupSelecSust.get(p)) {
                    if(GruposSustitutosSeleccionados.get(p).isSelected())
                    try {
                        CopiaTemp=MolPrin.clone();
                        
                        Hclone =H.clone();
                        if(HydroImpli.get(p)) {
                            Hclone.getAtom(j).setImplicitHydrogenCount(CopiaTemp.getAtom(j).getImplicitHydrogenCount()-1);
                        }        
                        CopiaTemp.add(Hclone);
                        CopiaTemp.getAtom(i).setImplicitHydrogenCount(CopiaTemp.getAtom(i).getImplicitHydrogenCount()-1);
                        CopiaTemp.addBond(i,MolPrin.getAtomCount()+j,IBond.Order.SINGLE);
                        Result.add(CopiaTemp);
                        SelecCopy = (ArrayList<Integer>) Selec.clone();
                        SelecCopy.remove((Object) i);
                        Todos(NumRSubst-1,CopiaTemp,SelecCopy);
                        
                        SmilesGenerator generator = new SmilesGenerator(SmiFlavor.Isomeric);
                        Smiletemp=generator.create(CopiaTemp);
                        if(!Smiles.contains(Smiletemp))
                        Smiles.add(Smiletemp);
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
        
        Smiles.sort(new Comparator<String>() {
            @Override
            public int compare(String arg0, String arg1) {
                return arg0.compareTo(arg1);
            }
        });
        for(String x :Smiles) {
            y+=x;
            y+="\n";
        }
        return y;
    }
}
