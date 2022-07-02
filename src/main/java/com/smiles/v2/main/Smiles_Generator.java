package com.smiles.v2.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.util.ArrayList;
import java.util.List;

import com.smiles.v2.main.command.Command;
import com.smiles.v2.main.command.EnumMolecule;
import com.smiles.v2.main.command.Img;
import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.framework.cdk.MoleculeDataFactory;
import com.smiles.v2.main.framework.cdk.MoleculeGraphPainter;
import com.smiles.v2.main.framework.cdk.VerifiedSmile;
import com.smiles.v2.main.infrastructure.FirstSubstituent;
import com.smiles.v2.main.views.PrincipalView;

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
        if (args.length == 0) {
            graphic();
        } else {
            commandLine(args);
        }
    }

    private static void commandLine(final String[] args) {
        final MoleculeDataFactory moleculeFactory = new MoleculeDataFactory();
        final VerifiedSmile verifierSmile = new VerifiedSmile();
        List<Command> commands = new ArrayList<>();
        for (String arg : args) {
            String argument = arg.toUpperCase();
            switch (argument) {
            case "--IMG":
                commands.add(new Img(verifierSmile, moleculeFactory));
                break;
            case "--ENUM":
                commands.add(new EnumMolecule(verifierSmile, moleculeFactory));
                break;
            default:
            }
            if (!commands.isEmpty()) {
                commands.get(commands.size() - 1).setCommands(arg);
            }
        }
        for (Command command : commands) {
            command.execute();
        }
    }

    /**
     * this method execute the graphic interface.
     */
    private static void graphic() {
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

        final String firsTheme = UIManager.getInstalledLookAndFeels()[0].getClassName();
        String themePoint = "";
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            String classname = info.getClassName();
            if ("GTKLookAndFeel".equals(info.getName())) {
                themePoint = classname;
                UIManager.setLookAndFeel(themePoint);
                break;
            }
            if ("Nimbus".equals(info.getName())) {
                themeDefault = classname;
            }
            if ("Windows".equals(info.getName())) {
                themePoint = classname;
            }
            if ("Metal".equals(info.getName())) {
                themePoint = classname;
            }

            UIManager.setLookAndFeel(themePoint);
        }
        if (!themeDefault.equals("") && themePoint.equals(firsTheme)) {
            UIManager.setLookAndFeel(themeDefault);
        }

    }
}
