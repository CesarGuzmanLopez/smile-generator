package com.smiles.v1.Generadores;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JCheckBox;
import org.openscience.cdk.interfaces.IAtomContainer;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.smiles.SmiFlavor;
import org.openscience.cdk.smiles.SmilesGenerator;
public class Derivados {
    public  IAtomContainer MolPrin;
    public  List<Integer> Selec;
    public  List<IAtomContainer> groupSust;
    public  List<ArrayList<Integer>> groupSelecSust;
    public  List<Boolean>HydroImpli;
    public List <JCheckBox> GruposSustitutosSeleccionados;
    int numRSubst;
    private List<IAtomContainer> result;
    public List<String> smiles;

    public Derivados(int nRSubstituent,IAtomContainer moleculePrincipal,ArrayList<Integer> selections,List<IAtomContainer> groupSubstitutes,
                     List<ArrayList<Integer>> groupSelecSust,List<Boolean>HydroImpli,
                     List <JCheckBox> GruposSustitutosSeleccionados){


        this.numRSubst=nRSubstituent;
        this.Selec=selections;
        this.MolPrin=moleculePrincipal;
        this.groupSust=groupSubstitutes;
        this.groupSelecSust=groupSelecSust;
        this.HydroImpli=HydroImpli;
        this.GruposSustitutosSeleccionados=GruposSustitutosSeleccionados;
        smiles=new ArrayList<String>();
        result=new ArrayList<IAtomContainer>();
        error=0;

        Todos(nRSubstituent,moleculePrincipal,selections);
    }


    private int error=0;
    @SuppressWarnings("unchecked")
    private void Todos(int NumRSubst,IAtomContainer MolPrin,ArrayList<Integer> Selec) {
        if(NumRSubst<1)
            return ;
        IAtomContainer copiaTemp;
        IAtomContainer h_clone;
        ArrayList<Integer> select_Copy;
        String smile_temp;
        for(int i: Selec) {
            int p=0;
            for(IAtomContainer H:groupSust ) {
                for(int j: groupSelecSust.get(p)) {
                    if(GruposSustitutosSeleccionados.get(p).isSelected())
                        try {
                            copiaTemp=MolPrin.clone();

                            h_clone =H.clone();
                            if(HydroImpli.get(p)) {
                                h_clone.getAtom(j).setImplicitHydrogenCount(copiaTemp.getAtom(j).getImplicitHydrogenCount()-1);
                            }
                            copiaTemp.add(h_clone);
                            copiaTemp.getAtom(i).setImplicitHydrogenCount(copiaTemp.getAtom(i).getImplicitHydrogenCount()-1);
                            copiaTemp.addBond(i,MolPrin.getAtomCount()+j,IBond.Order.SINGLE);
                            result.add(copiaTemp);
                            select_Copy = (ArrayList<Integer>) Selec.clone();
                            select_Copy.remove((Object) i);
                            Todos(NumRSubst-1,copiaTemp,select_Copy);

                            SmilesGenerator generator = new SmilesGenerator(SmiFlavor.Isomeric);
                            smile_temp=generator.create(copiaTemp);
                            if(!smiles.contains(smile_temp))
                                smiles.add(smile_temp);
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