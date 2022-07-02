package com.smiles.v2.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.smiles.v2.main.command.Command;
import com.smiles.v2.main.command.EnumMolecule;
import com.smiles.v2.main.command.GenerateMain;
import com.smiles.v2.main.command.GenerateSubstitute;
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
    public static final MoleculeDataFactory moleculeFactory = new MoleculeDataFactory();
    public static final VerifiedSmile verifierSmile = new VerifiedSmile();

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
        List<Command> commands = new ArrayList<>();
        List<GenerateSubstitute> substitutes = new ArrayList<>();
        GenerateMain mainGenerate = null;
        for (String arg : args) {
            String argument = arg.toUpperCase();
            switch (argument) {
            case "--IMG":
                commands.add(new Img(verifierSmile, moleculeFactory));
                break;
            case "--ENUM":
                commands.add(new EnumMolecule(verifierSmile, moleculeFactory));
                break;
            case "--SWAPS":
                mainGenerate = new GenerateMain(verifierSmile, moleculeFactory);
                commands.add(mainGenerate);
                break;
            case "--S":
                GenerateSubstitute substitute = new GenerateSubstitute(verifierSmile, moleculeFactory);
                substitutes.add(substitute);
                commands.add(substitute);
                break;
            default:
                if (argument.startsWith("--H") || argument.startsWith("-H") || argument.startsWith("-A")
                        || argument.startsWith("/H")) {
                    help();
                    return;
                }
            }
            if (!commands.isEmpty()) {
                commands.get(commands.size() - 1).setCommands(arg);
            }
        }
        for (Command command : commands) {
            command.execute();
        }
        if (mainGenerate != null) {
            for (GenerateSubstitute substitute : substitutes) {
                mainGenerate.addSubstitute(substitute);
            }
            mainGenerate.generate();
        }
    }

    /**
     * This method print the help of the application.
     *
     * @since 1.0
     */
    private static void help() {
        StringBuilder output = new StringBuilder();
        output.append("Usage: java -jar smiles-generator.jar  [options]\n");
        output.append("Options:\n");
        output.append(" --IMG [smile]: Create a image of the molecule.\n");
        output.append("\t\t-name Name \n");
        output.append("\t\t-smile [smile] \n");
        output.append("\t\t-width width \n");
        output.append("\t\t-height height \n");
        output.append("\t\t-path \n");
        output.append("\t\t-explicit  (implicit Hydrogens) \n");
        output.append("\n --ENUM smile : Enumerate the atoms of the molecule.\n");
        output.append(
                "\n--swaps smile -p [select1,select2,select3,...]  -r r-substitutes -Output [FileOutput] -Log [FileLog] -path[Directory Images] [...]"
                        + " --S SmileSubstitute -p [select1,select2,select3,...]  ... \n");
        output.append("\t\t-name Name \n");
        output.append("\t\t-p [{1,2,3,...}] selects Atoms \n");
        output.append("\t\t-smile [smile] \n");
        output.append("\t\t-width width of the images \n");
        output.append("\t\t-height width of the images \n");
        output.append("\t\t-Output [FileOutput] \n");
        output.append("\t\t-Log [Filelog] \n");
        output.append("\t\t-path \n");
        output.append("\t\t-explicit  (No implicit Hydrogens) \n");
        output.append("\t\t-Bounds [bounds] \n");
        output.append("\t\t--S SmileSubstitute [select1,select2,select3,...] \n");
        String outputString = output.toString();
        Command.LOGGER.log(Level.INFO, outputString);

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
