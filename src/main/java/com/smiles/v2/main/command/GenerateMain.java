package com.smiles.v2.main.command;

import java.io.File;
import java.io.IOException;

import com.smiles.v2.main.domain.generator.WriteAndGenerate;
import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.interfaces.MoleculeDataFactoryInterface;
import com.smiles.v2.main.interfaces.SmileVerificationInterface;

public class GenerateMain extends GenerateSubstitute {
    private MoleculesList listMolecules;
    private String outputFileName;
    private String logFileName;
    private String pathImages;
    private int nunBounds;
    private int rSubstitutes;

    public GenerateMain(final SmileVerificationInterface verification, final MoleculeDataFactoryInterface factory) {
        super(verification, factory);
        listMolecules = new MoleculesList(verification, factory);
        outputFileName = null;
        logFileName = null;
        pathImages = null;
        nunBounds = 1;
        rSubstitutes = 1;
    }

    /**
     * @param substitute
     */
    public void addSubstitute(final GenerateSubstitute substitute) { //NOSONAR
        listMolecules.addMolecule(substitute.molecule());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute() {
        super.execute();
        defineVar();
    }

    /**
     * @return rSubstitutes to permute.
     */
    public int getRSubstitutes() {
        return rSubstitutes;
    }

    private void defineVar() {
        outputFileName = getArg("-Output");
        if (outputFileName == null) {
            throw new IllegalArgumentException("Output file name is required");
        }
        logFileName = getArg("-Log");
        pathImages = getArg("-Path");
        try {
            nunBounds = Integer.parseInt(getArg("-Bounds"));
        } catch (java.lang.NumberFormatException e) {
            nunBounds = 1;
        }

    }

    /**
     * generate permutations of the molecule.
     */
    public void generate() {

        File log = new File(logFileName);
        File output = new File(outputFileName);
        pathImages = new File(pathImages).getAbsolutePath();
        WriteAndGenerate generator = new WriteAndGenerate(listMolecules, molecule(), rSubstitutes, nunBounds, log,
                output);
        generator.setSaveImages(pathImages);
        try {
            generator.generate();
        } catch (IOException e) {
            throw new RuntimeException("Error save data." + e.getMessage());
        }
    }
}
