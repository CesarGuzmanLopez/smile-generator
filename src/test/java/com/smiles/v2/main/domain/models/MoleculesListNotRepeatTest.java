package com.smiles.v2.main.domain.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.smiles.v2.main.framework.cdk.MoleculeDataFactory;
import com.smiles.v2.main.framework.cdk.VerifiedSmile;

public class MoleculesListNotRepeatTest {
    private static final ByteArrayOutputStream OUT_CONTENT = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream ERR_CONTENT = new ByteArrayOutputStream();
    public static final PrintStream ORIGINAL_OUT = System.out;
    public static final PrintStream ORIGINAL_ERR = System.err;

    /**
     * Create the molecule. to Test.
     *
     */
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        System.setOut(new PrintStream(OUT_CONTENT));
        System.setErr(new PrintStream(ERR_CONTENT));
    }

    @Test
    void testAddMolecule() {
        MoleculesListNotRepeat moleculesListNotRepeat = new MoleculesListNotRepeat(new VerifiedSmile(),
                new MoleculeDataFactory());
        moleculesListNotRepeat.addMolecule(
                new Molecule("name", "cccc", "Is a prove", true, new VerifiedSmile(), new MoleculeDataFactory()));
        moleculesListNotRepeat.addMolecule(
                new Molecule("name", "cccc", "Is a prove", true, new VerifiedSmile(), new MoleculeDataFactory()));
        assertEquals(1, moleculesListNotRepeat.getListMolecule().size(), "the size is 1");

    }

    @Test
    void testAddMoleculeRepeat() {
        MoleculesListNotRepeat moleculesListNotRepeat = new MoleculesListNotRepeat(new VerifiedSmile(),
                new MoleculeDataFactory());
        Molecule mol1 = new Molecule("name", "cccc", "Is asdf a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());
        Molecule mol2 = new Molecule("name 2", "C=CC=C", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());
        ORIGINAL_OUT.println("The molecule 1" + mol1.smile());
        ORIGINAL_OUT.println("The molecule 2" + mol2.smile());
        moleculesListNotRepeat.addMolecule(mol1);
        assertEquals(-1, moleculesListNotRepeat.addMolecule(mol2), "add element repeat");
        assertEquals(1, moleculesListNotRepeat.getListMolecule().size(), "the size not is 1");

    }
}
