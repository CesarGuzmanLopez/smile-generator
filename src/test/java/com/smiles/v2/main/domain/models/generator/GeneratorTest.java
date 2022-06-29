package com.smiles.v2.main.domain.models.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.domain.models.MoleculesListAbstract;
import com.smiles.v2.main.framework.cdk.MoleculeDataFactory;
import com.smiles.v2.main.framework.cdk.VerifiedSmile;

/**
 * Class WriteAndGenerate.
 *
 * @author Cesar G G L
 * @version 1.0
 * @since 1.0
 * @Charset: UTF-8
 *
 */
public class GeneratorTest {
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
     * TestGenerateSubstitutes.
     *
     */
    @Test
    public void testGenerateSubstitutes() {
        Molecule molecule1 = new Molecule("ccOcc", "ccOcc", "Is a prove", true, verifiedSmile, moleculeDataFactory);
        molecule1.selectAtom(1);
        molecule1.selectAtom(3);

        Molecule molecule2 = new Molecule("Ncccc", "Ncccc", "Is a prove", true, verifiedSmile, moleculeDataFactory);
        molecule2.selectAtom(1);
        molecule2.selectAtom(0);
        Molecule molecule3 = new Molecule("Oxygens", "O", "Is a prove", true, verifiedSmile, moleculeDataFactory);
        Molecule molecule4 = new Molecule("Dimetoxianfetamine", "CC1=CC(=C(C=C1OC)CC(C)N)OC", "Is a prove", true,
                verifiedSmile, moleculeDataFactory);
        molecule4.selectAtom(2);
        molecule4.selectAtom(6);

        MoleculesList moleculesList = new MoleculesList(verifiedSmile, moleculeDataFactory);
        moleculesList.addMolecule(molecule1);
        moleculesList.addMolecule(molecule2);
        moleculesList.addMolecule(molecule3);
        moleculesList.addMolecule(molecule4);
        Generator generator = new Generator(molecule3, moleculesList, 2, 2);
        MoleculesList moleculesListSubstitutes = generator.getListOfSubstitutes();
        /*
         * for (Molecule molecule : moleculesListSubstitutes.getListMolecule()) {
         * ORIGINAL_OUT.println(molecule.getName() + " S:" + molecule.getSmile()); }
         */
        assertEquals(7, moleculesListSubstitutes.getListMolecule().size(), "The number of substitutes is not correct");
        for (Molecule molecule : moleculesListSubstitutes.getListMolecule()) {
            assertTrue(
                    1 == molecule.getMoleculeData().getListAtomsSelected().size()
                            || 0 == molecule.getMoleculeData().getListAtomsSelected().size(),
                    "The number of selected should be 1");
        }
    };

    /**
     * TestGenerateSubstitutes.
     *
     */
    @Test
    public void testGenerate() {
        Molecule molecule1 = new Molecule("ccOcc", "ccOcc", "Is a prove", true, verifiedSmile, moleculeDataFactory);
        molecule1.selectAtom(1);
        molecule1.selectAtom(3);

        Molecule molecule2 = new Molecule("Ncccc", "Ncccc", "Is a prove", true, verifiedSmile, moleculeDataFactory);
        molecule2.selectAtom(1);
        molecule2.selectAtom(0);
        Molecule molecule3 = new Molecule("Oxygens", "O", "Is a prove", true, verifiedSmile, moleculeDataFactory);
        Molecule molecule4 = new Molecule("Dimetoxianfetamine", "CC1=CC(=C(C=C1OC)CC(C)N)OC", "Is a prove", true,
                verifiedSmile, moleculeDataFactory);
        molecule4.selectAtom(2);
        molecule4.selectAtom(13);

        MoleculesList moleculesList = new MoleculesList(verifiedSmile, moleculeDataFactory);
        moleculesList.addMolecule(molecule1);
        moleculesList.addMolecule(molecule2);
        moleculesList.addMolecule(molecule3);
        moleculesList.addMolecule(molecule4);
        Generator generator = new Generator(molecule4, moleculesList, 1, 1);
        MoleculesListAbstract all = generator.getAllMolecules();
        for (Molecule molecule : all.getListMolecule()) {
            ORIGINAL_OUT.println(molecule.getName() + " S:" + molecule.getSmile());
        }

    }

};
