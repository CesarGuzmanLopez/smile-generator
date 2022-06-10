package com.smiles.v2.main;

import com.smiles.v2.main.infrastructure.GeneraSmilesInput;
import com.smiles.v2.main.views.PrincipalView;


public final class SmilesGenerator {
    public static void main(String[] args) {
        seleccionarTema();
        GeneraSmilesInput smiles = new GeneraSmilesInput();
        PrincipalView principalView = new PrincipalView( smiles );

        principalView.initialize();

    }
    private static void seleccionarTema() {
        String temaDefault = "";
        try{
            String primerTema= javax.swing.UIManager.getInstalledLookAndFeels()[0].getClassName();
            String temaActual ="";
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    temaDefault = info.getClassName();
                }
                temaActual = info.getClassName();
                javax.swing.UIManager.setLookAndFeel(temaActual);
            }
            if(!temaDefault.equals("") && temaActual.equals(primerTema)){
                javax.swing.UIManager.setLookAndFeel(temaDefault);
            }
        }catch(Exception e){
            e.getMessage();
        }
    }
}
