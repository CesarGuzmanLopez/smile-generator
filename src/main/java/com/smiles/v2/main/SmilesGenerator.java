package com.smiles.v2.main;

import com.smiles.v2.main.framework.cdk.MoleculeDataFactory;
import com.smiles.v2.main.framework.cdk.MoleculeGraphPainter;
import com.smiles.v2.main.framework.cdk.VerifiedSmile;
import com.smiles.v2.main.infrastructure.GeneraSmilesInput;
import com.smiles.v2.main.views.PrincipalView;

public final class SmilesGenerator {
    
    public static void main(String[] args) {
        themeSelected();
        MoleculeGraphPainter moleculeGraphPainter = new MoleculeGraphPainter();
        MoleculeDataFactory moleculeDataOfSmile = new MoleculeDataFactory();
        VerifiedSmile verifierSmile = new VerifiedSmile();
        GeneraSmilesInput smiles = new GeneraSmilesInput(verifierSmile);
        PrincipalView principalView = new PrincipalView(smiles, verifierSmile, moleculeGraphPainter,
                moleculeDataOfSmile);
        principalView.initialize();
    }

    private static void themeSelected() {
        String themeDefault = "";
        try {
            String firsTheme = javax.swing.UIManager.getInstalledLookAndFeels()[0].getClassName();
            String themePoint = "";
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    themeDefault = info.getClassName();
                }
                themePoint = info.getClassName();
                javax.swing.UIManager.setLookAndFeel(themePoint);
            }
            if (!themeDefault.equals("") && themePoint.equals(firsTheme)) {
                javax.swing.UIManager.setLookAndFeel(themeDefault);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
