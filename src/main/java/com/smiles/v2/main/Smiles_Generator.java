package com.smiles.v2.main;

import javax.swing.UnsupportedLookAndFeelException;

import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.framework.cdk.MoleculeDataFactory;
import com.smiles.v2.main.framework.cdk.MoleculeGraphPainter;
import com.smiles.v2.main.framework.cdk.VerifiedSmile;
import com.smiles.v2.main.infrastructure.FirstSubstituent;
import com.smiles.v2.main.views.PrincipalView;

import ch.randelshofer.quaqua.QuaquaManager;

/**
 * This class is the main class of the application. It is the entry point of the
 * application.
 *
 * @author Cesar G. Guzman Lopez
 * @version 1.0
 * @since 1.0
 */
public final class Smiles_Generator { // NOSONAR

    /**
     * This method is the main method of the application. It is the entry point of
     * the application.
     *
     * @since 1.0
     */
    private Smiles_Generator() {

    }

    /**
     * This method is the main method of the application. It is the entry point of
     * the application.
     *
     * @since 1.0
     * @param args the arguments of the application.
     *
     */

    public static void main(final String[] args) {
        try {
            themeSelected();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        final MoleculeGraphPainter moleculeGraphPainter = new MoleculeGraphPainter();
        final MoleculeDataFactory moleculeFactory = new MoleculeDataFactory();
        final VerifiedSmile verifierSmile = new VerifiedSmile();
        final MoleculesList smiles = FirstSubstituent.getMoleculeListInitializer(verifierSmile, moleculeFactory);
        final PrincipalView principalView = new PrincipalView(smiles, verifierSmile, moleculeGraphPainter,
                moleculeFactory);
        principalView.initialize();
    }

    /**
     * This method is used to select the theme of the application.
     *
     * @since 1.0
     * @throws ClassNotFoundException          if the class is not found.
     * @throws InstantiationException          if the class is not instantiated.
     * @throws IllegalAccessException          if the class is not accessed.
     * @throws UnsupportedLookAndFeelException if the theme is not supported.
     */
    private static void themeSelected() throws ClassNotFoundException, InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {

        String themeDefault = "";

        final String firsTheme = javax.swing.UIManager.getInstalledLookAndFeels()[0].getClassName();
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
        /*
        javax.swing.UIManager.setLookAndFeel(QuaquaManager.getLookAndFeel());
        javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
*/
    }
}
