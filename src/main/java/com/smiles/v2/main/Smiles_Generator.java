package com.smiles.v2.main;

import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.framework.cdk.MoleculeDataFactory;
import com.smiles.v2.main.framework.cdk.MoleculeGraphPainter;
import com.smiles.v2.main.framework.cdk.VerifiedSmile;
import com.smiles.v2.main.infrastructure.FirstSubstituent;
import com.smiles.v2.main.views.PrincipalView;

public final class Smiles_Generator {//NOSONAR

    public static void main(String[] args) {
        themeSelected();
        MoleculeGraphPainter moleculeGraphPainter = new MoleculeGraphPainter();
        MoleculeDataFactory moleculeFactory = new MoleculeDataFactory();
        VerifiedSmile verifierSmile = new VerifiedSmile();
        MoleculesList smiles = FirstSubstituent.getMoleculeListInitializer (verifierSmile,moleculeFactory);
        PrincipalView principalView = new PrincipalView(smiles, verifierSmile, moleculeGraphPainter,
                moleculeFactory);
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
