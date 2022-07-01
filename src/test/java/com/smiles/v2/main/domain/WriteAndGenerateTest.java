package com.smiles.v2.main.domain;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.smiles.v2.main.domain.generator.WriteAndGenerate;
import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.domain.models.MoleculesList;

import com.smiles.v2.main.framework.cdk.MoleculeDataFactory;
import com.smiles.v2.main.framework.cdk.VerifiedSmile;

public class WriteAndGenerateTest {

    private static final ByteArrayOutputStream OUT_CONTENT = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream ERR_CONTENT = new ByteArrayOutputStream();
    public static final PrintStream ORIGINAL_OUT = System.out;
    public static final PrintStream ORIGINAL_ERR = System.err;
    private static VerifiedSmile verifiedSmile;
    private static MoleculeDataFactory moleculeDataFactory;

    /**
     * Create the molecule. to Test.
     *
     */
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        System.setOut(new PrintStream(OUT_CONTENT));
        System.setErr(new PrintStream(ERR_CONTENT));
        verifiedSmile = new VerifiedSmile();
        moleculeDataFactory = new MoleculeDataFactory();
    }

    /**
     * Test of the molecule important methods.
     *
     */
    @Test
    void testGenerate() { // NOSONAR

        Molecule molecule1 = new Molecule("NCOC", "NCOC", "Is a test", true, verifiedSmile, moleculeDataFactory);
        molecule1.selectAtom(2);
        molecule1.selectAtom(0);
        molecule1.selectAtom(3);

        Molecule molecule2 = new Molecule("Ncccc", "Ncccc", "Is a test", true, verifiedSmile, moleculeDataFactory);
        molecule2.selectAtom(1);
        Molecule molecule3 = new Molecule("Oxygens", "O", "Is a test", true, verifiedSmile, moleculeDataFactory);

        Molecule molecule5 = new Molecule("ccOcc2", "ccOcc", "Is a test", true, verifiedSmile, moleculeDataFactory);
        molecule5.selectAtom(1);

        MoleculesList moleculesList = new MoleculesList(verifiedSmile, moleculeDataFactory);
        moleculesList.addMolecule(molecule5);

        moleculesList.addMolecule(molecule2);
        moleculesList.addMolecule(molecule3);

        WriteAndGenerate writeAndGenerate = new WriteAndGenerate(moleculesList, molecule1, 2, 2,
                new File("./fileDescription.log"), new File("./Output.txt"));

        writeAndGenerate.setSaveImages(new File("Imagenes/").getAbsolutePath());
        try {
            writeAndGenerate.generate();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }

    }
}
