package com.smiles.v2.main.domain.generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.interfaces.AtomInterface;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.MoleculeDataInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;

public class WriteAndGenerate {
    static final Charset CHARSET = StandardCharsets.UTF_8;
    MoleculesList substitutes;
    Molecule principal;
    int rSubstitutes;
    File fileDescription;
    MoleculeDataFactoryInterface factory;
    SmileVerificationInterface smileVerifier;

    private BufferedWriter writeDescription;
    private BufferedWriter writeOutput;
    private File fileOutput;

    public WriteAndGenerate(MoleculesList substitutes, Molecule principal,
            int rSubstitutes,
            File fileDescription, File fileOutput,
            MoleculeDataFactoryInterface factory,
            SmileVerificationInterface smileVerifier) {
        verifyEntry(substitutes, principal, rSubstitutes, fileDescription,
                fileOutput);
        this.substitutes = substitutes;
        this.principal = principal;
        this.rSubstitutes = rSubstitutes;
        this.fileDescription = fileDescription;
        this.fileOutput = fileOutput;
        this.factory = factory;
        this.smileVerifier = smileVerifier;
        verificationAndCreateFiles();


    }
    public static final boolean verifyEntry(
            MoleculesList substitutes,
            Molecule principal,
            int rSubstitutes,
            File fileDescription,
            File fileOutput

    ) {
        if (substitutes == null || principal == null)
            throw new IllegalArgumentException("Null argument");
        if (rSubstitutes <= 0)
            throw new IllegalArgumentException("rSubstitutes <= 0");
        if (principal.getMoleculeData().getListAtomsSelected().size() > 1
                && rSubstitutes > principal.getMoleculeData().getListAtomsSelected().size())
            throw new IllegalArgumentException("rSubstitutes cannot be greater than the selected atoms");
        if (!aAtomOrSelected(principal))
            throw new IllegalArgumentException(principal.getName() + "Principal molecule has no atom or selected");
        List<Molecule> list = substitutes.getMoleculeListMolecule();
        if (list.isEmpty())
            throw new IllegalArgumentException("List of substitutes is empty");
        for (Molecule molecule : list) {
            if (!aAtomOrSelected(molecule))
                throw new IllegalArgumentException(
                        molecule.getName() + ": is notSubstitute molecule has no atom or selected");
        }
        if (fileOutput == null)
            throw new IllegalArgumentException("File output is null");
        if (fileDescription != null)
            fileDescription.getParentFile().mkdirs();
        return true;
    }

    static final boolean aAtomOrSelected(Molecule molecule) {
        MoleculeDataInterface moleculeData = molecule.getMoleculeData();
        if (moleculeData == null) {
            throw new IllegalArgumentException("moleculeData is null");
        }
        if (moleculeData.getNumberAtoms() == 1)
            return true;
        if (moleculeData.getListAtomsSelected().isEmpty())
            return false;

        return !moleculeData.getListAtoms().isEmpty();
    }
    public void verificationAndCreateFiles(){
        boolean createFile = true;
        try {
            if (this.fileDescription != null) {
                this.writeDescription = Files.newBufferedWriter(fileDescription.toPath(), CHARSET,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
                if (!fileDescription.exists())
                    createFile = fileDescription.createNewFile();
            }
            if (!fileOutput.exists())
                createFile = fileOutput.createNewFile();
            this.writeOutput = Files.newBufferedWriter(fileOutput.toPath(), CHARSET,
            StandardOpenOption.CREATE,
            StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new IllegalArgumentException("\nError opening file -  Description or output \n" + e.getMessage()
            + (!createFile?"\nFile not created":""));//NOSONAR

        }
    }
    public void generate() throws IOException {
        writeHeadDescription();


        closeFiles();
    }
    private void writeHeadDescription() throws IOException {
        if (writeDescription != null) {
            writeDescription.write(principal.getName() + "\n");
            writeDescription.write(substitutes.getMoleculeListMolecule().size() + "\n");
            writeDescription.write(rSubstitutes + "\n");
            writeDescription.write(substitutes.getMoleculeListMolecule().size() + "\n");
            for (Molecule molecule : substitutes.getMoleculeListMolecule()) {
                writeDescription.write(molecule.getName() + "\n");
                writeDescription.write(molecule.getMoleculeData().getListAtomsSelected().size() + "\n");
                for (AtomInterface atom : molecule.getMoleculeData().getListAtomsSelected()) {
                    writeDescription.write(atom + "\n");
               }
            }
        }
    }
    /**
     *
    */

    protected void closeFiles() throws IOException {
    if (writeDescription != null)
        writeDescription.close();
    if (writeOutput != null)
        writeOutput.close();
    }
}
