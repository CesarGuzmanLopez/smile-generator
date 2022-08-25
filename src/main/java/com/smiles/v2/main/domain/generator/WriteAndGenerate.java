package com.smiles.v2.main.domain.generator;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.domain.models.MoleculesListAbstract;
import com.smiles.v2.main.interfaces.AtomInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Class WriteAndGenerate.
 *
 * @author Cesar G G L
 * @version 1.0
 * @since 1.0
 * @Charset: UTF-8
 * @Date: 01/05/20201 CHARSET
 */
public class WriteAndGenerate {
    static final int WIDTH = 700;
    static final int HEIGHT = 700;
    static final Charset CHARSET = StandardCharsets.UTF_8;
    private MoleculesList substitutes;
    private Molecule principal;
    private int rSubstitutes;
    private File fileDescription;
    private BufferedWriter writeDescription;
    private BufferedWriter writeOutput;
    private File fileOutput;
    private int numBounds;
    private boolean repeated;

    private String saveImages;

    public WriteAndGenerate(final MoleculesList substitutes, final Molecule principal, final int rSubstitutes,
            final int numBounds, final File fileDescription, final File fileOutput) {
        verifyEntry(principal, substitutes, rSubstitutes, fileDescription, fileOutput);
        this.substitutes = substitutes;
        this.principal = principal;
        this.rSubstitutes = rSubstitutes;
        this.fileDescription = fileDescription;
        this.fileOutput = fileOutput;
        this.numBounds = numBounds;
        this.repeated = false;
        this.saveImages = null;
        verificationAndCreateFiles();

    }

    /**
     * Verify a entry for generator.
     *
     * @param substitutes     To Substituent Principal
     * @param principal       The molecule Principal
     * @param rSubstitutes    Number profundity Substituent
     * @param fileDescription File save Description
     * @param fileOutput      File save Output
     * @return true if the entry is correct
     */
    public static final boolean verifyEntry(final Molecule principal, final MoleculesList substitutes,
            final int rSubstitutes, final File fileDescription, final File fileOutput) {
        if (substitutes == null || principal == null) {
            throw new IllegalArgumentException("Null argument");
        }
        if (rSubstitutes <= 0) {
            throw new IllegalArgumentException("rSubstitutes <= 0");
        }
        if (principal.getMoleculeData().getListAtomsSelected().size() > 1
                && rSubstitutes > principal.getMoleculeData().getListAtomsSelected().size()) {
            throw new IllegalArgumentException("rSubstitutes cannot be greater than the selected atoms");
        }
        if (!aAtomOrSelected(principal)) {
            throw new IllegalArgumentException(principal.getName() + "Principal molecule has no atom or selected");
        }
        final List<Molecule> list = substitutes.getListMolecule();
        if (list.isEmpty()) {
            throw new IllegalArgumentException("List of substitutes is empty");
        }
        for (Molecule molecule : list) {
            if (!aAtomOrSelected(molecule)) {
                throw new IllegalArgumentException(
                        molecule.getName() + ": is notSubstitute molecule has no atom or selected");
            }
        }
        if (fileOutput == null) {
            throw new IllegalArgumentException("File output is null");
        }
        if (fileDescription != null) {
            fileDescription.getParentFile().mkdirs();
        }
        return true;
    }

    /**
     * setRepeated.
     *
     * @param repeated
     */
    public void setRepeated(final boolean repeated) {
        this.repeated = repeated;
    }

    /**
     * @param directory to save images.
     */

    public void setSaveImages(final String directory) {
        this.saveImages = directory;
    }

    /**
     * Verify if Atom is selected o only is a Atom.
     *
     * @param molecule Molecule to verify
     * @return true if is a Atom or selected
     */
    static final boolean aAtomOrSelected(final Molecule molecule) {
        final MoleculeDataInterface moleculeData = molecule.getMoleculeData();
        if (moleculeData == null) {
            throw new IllegalArgumentException("moleculeData is null");
        }
        if (moleculeData.getNumberAtoms() == 1) {
            return true;
        }
        if (moleculeData.getListAtomsSelected().isEmpty()) {
            return false;
        }

        return !moleculeData.getListAtoms().isEmpty();
    }

    /**
     * Verify and create files.
     */
    public final void verificationAndCreateFiles() {
        boolean createFile = true;
        try {
            if (this.fileDescription != null) {
                this.writeDescription = Files.newBufferedWriter(fileDescription.toPath(), CHARSET,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                if (!fileDescription.exists()) {
                    createFile = fileDescription.createNewFile();
                }
            }
            if (!fileOutput.exists()) {
                createFile = fileOutput.createNewFile();
            }
            this.writeOutput = Files.newBufferedWriter(fileOutput.toPath(), CHARSET, StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IllegalArgumentException("\nError opening file -  Description or output \n" + e.getMessage()
                    + (!createFile ? "\nFile not created" : "")); // NOSONAR

        }
    }

    /**
     * Write and generate.
     */
    public final void generate() throws IOException {
        writeHeadDescription();
        GeneratorPermutesSmile generator = new GeneratorPermutesSmile(principal, substitutes, rSubstitutes, numBounds,
                repeated);
        MoleculesListAbstract generateList = generator.getAllMolecules();
        if (writeDescription != null) {
            writeDescription.write("\n\n\t=== ===\t\tTotal: " + generateList.getListMolecule().size() + "\t=== ===\n");
        }
        for (Molecule molecule : generateList.getListMolecule()) {
            writeOutput.write(molecule.smile() + "\n");
        }
        printStructureSubstitute(generateList);
        if (saveImages != null) {
            saveImages(generateList);
        }
        closeFiles();

    }

    /**
     *
     * @param generateList List of molecules permutes.
     */
    private void saveImages(final MoleculesListAbstract generateList) { // UNCHECK
        final List<Molecule> list = generateList.getListMolecule();
        int i = 0;
        for (Molecule molecule : list) {
            String name = principal.getName() + "_" + i++ + ".png";

            BufferedImage bi = molecule.getImage(WIDTH, HEIGHT, "SMILE: " + molecule.smile());
            try {
                ImageIO.write(bi, "png", new File(saveImages + System.getProperty("file.separator") + name));
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
    }

    /**
     * Write head description.
     */
    private void printStructureSubstitute(MoleculesListAbstract generateList) throws IOException { // UNCHECK
        if (writeDescription != null) {
            writeDescription.write("==========================================================\n");
            for (Molecule molecule : generateList.getListMolecule()) {
                for (AtomInterface toSubstitute : principal.getMoleculeData().getListAtomsSelected()) {
                    String symbol = molecule.isOnlySubstitutedHydrogens() ? "H" : "*"; // NOSONAR
                    if (!molecule.isSelected(toSubstitute.getId()) || molecule.atomCount() == 1) {
                        Molecule a = molecule.getSubstitute(toSubstitute.getId());
                        symbol = a.smile();
                    }
                    writeDescription.write("\t\t" + symbol);
                }
                writeDescription.write("\t\t"+molecule.smile()+"\n");
            }
        }
    }

    /**
     * Write Head Description.
     */
    private void writeHeadDescription() throws IOException {
        if (writeDescription != null) {
            writeDescription.write("Main molecule: " + principal.getName() + " Smile " + principal.smile() + "\n");
            writeDescription.write("No Substitutes " + substitutes.getListMolecule().size() + "\n");
            writeDescription.write("R Substitutes" + rSubstitutes + "\n");
            writeDescription.write("substitutes: " + "\n");
            for (Molecule molecule : substitutes.getListMolecule()) {
                writeDescription.write("\t " + molecule.getName());
                writeDescription.write("\n");
            }
        }
    }
    /**
     * Close Files.
     */
    protected void closeFiles() throws IOException {
        if (writeDescription != null) {
            writeDescription.close();
        }
        if (writeOutput != null) {
            writeOutput.close();
        }
    }
}
