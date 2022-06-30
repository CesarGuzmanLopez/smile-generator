package com.smiles.v2.main.domain.models.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.smiles.v2.main.domain.generator.Generator;
import com.smiles.v2.main.domain.models.Molecule;
import com.smiles.v2.main.domain.models.MoleculesList;
import com.smiles.v2.main.domain.models.MoleculesListAbstract;
import com.smiles.v2.main.framework.cdk.MoleculeDataFactory;
import com.smiles.v2.main.framework.cdk.VerifiedSmile;
import com.smiles.v2.main.interfaces.AtomInterface;

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
        Molecule molecule1 = new Molecule("ccOcc", "ccOcc", "Is a test", true, verifiedSmile, moleculeDataFactory);
        molecule1.selectAtom(1);
        molecule1.selectAtom(3);

        Molecule molecule2 = new Molecule("Ncccc", "Ncccc", "Is a test", true, verifiedSmile, moleculeDataFactory);
        molecule2.selectAtom(1);
        molecule2.selectAtom(0);
        Molecule molecule3 = new Molecule("Oxygens", "O", "Is a test", true, verifiedSmile, moleculeDataFactory);
        Molecule molecule4 = new Molecule("Dimetoxianfetamine", "CC1=CC(=C(C=C1OC)CC(C)N)OC", "Is a test", true,
                verifiedSmile, moleculeDataFactory);
        molecule4.selectAtom(2);
        molecule4.selectAtom(6);

        MoleculesList moleculesList = new MoleculesList(verifiedSmile, moleculeDataFactory);
        moleculesList.addMolecule(molecule1);
        moleculesList.addMolecule(molecule2);
        moleculesList.addMolecule(molecule3);
        moleculesList.addMolecule(molecule4);
        Generator generator = new Generator(molecule3, moleculesList, 1, 2);
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
     */
    @Test
    public void testGenerate() {
        Molecule molecule1 = new Molecule("ccOcc", "ccOcc", "Is a test", true, verifiedSmile, moleculeDataFactory);
        molecule1.selectAtom(1);
        molecule1.selectAtom(3);

        Molecule molecule2 = new Molecule("Ncccc", "Ncccc", "Is a test", true, verifiedSmile, moleculeDataFactory);
        molecule2.selectAtom(1);
        molecule2.selectAtom(0);
        Molecule molecule3 = new Molecule("Oxygens", "O", "Is a test", true, verifiedSmile, moleculeDataFactory);
        Molecule molecule4 = new Molecule("Dimetoxianfetamine", "CC1=CC(=C(C=C1OC)CC(C)N)OC", "Is a test", true,
                verifiedSmile, moleculeDataFactory);
        molecule4.selectAtom(13);
        molecule4.selectAtom(2);

        Molecule molecule5 = new Molecule("ccOcc2", "ccOcc", "Is a test", true, verifiedSmile, moleculeDataFactory);
        molecule5.selectAtom(1);
        molecule5.selectAtom(3);

        MoleculesList moleculesList = new MoleculesList(verifiedSmile, moleculeDataFactory);
        moleculesList.addMolecule(molecule5);
        moleculesList.addMolecule(molecule1);
        moleculesList.addMolecule(molecule2);
        moleculesList.addMolecule(molecule3);
        moleculesList.addMolecule(molecule4);

        Generator generator = new Generator(molecule4, moleculesList, 1, 1);

        MoleculesListAbstract all = generator.getAllMolecules();

        for (Molecule molecule : all.getListMolecule()) {
            ORIGINAL_OUT.println(molecule);
        }

        assertEquals(12, all.getListMolecule().size(), "The number of permutes not correct");

        molecule4.selectAtom(4);
        molecule4.selectAtom(0);
        Generator generator2 = new Generator(molecule4, moleculesList, 3, 1);
        all = generator2.getAllMolecules();
        assertEquals(1104, all.getListMolecule().size(), "The number of permutes not correct");
    }

    /**
     * TestBenzene.
     */
    @Test
    public void testBenzene() {
        Molecule benzene = new Molecule("Benzene", "C1=CC=CC=C1", "Is a test", true, verifiedSmile,
                moleculeDataFactory);
        benzene.selectAtom(0);
        benzene.selectAtom(1);
        benzene.selectAtom(2);
        benzene.selectAtom(3);
        benzene.selectAtom(4);
        benzene.selectAtom(5);
        Molecule oxygen = new Molecule("Oxigeno", "O", "Is a test", true, verifiedSmile, moleculeDataFactory);
        Molecule nitrogen = new Molecule("Nitrogen", "N", "Is a test", true, verifiedSmile, moleculeDataFactory);
        Molecule fluor = new Molecule("Fluor", "F", "Is a test", true, verifiedSmile, moleculeDataFactory);
        Molecule bromo = new Molecule("Bromo", "Br", "Is a test", true, verifiedSmile, moleculeDataFactory);
        Molecule iodine = new Molecule("iodine", "I", "Is a test", true, verifiedSmile, moleculeDataFactory);
        Molecule boron = new Molecule("boron", "B", "Is a test", true, verifiedSmile, moleculeDataFactory);
        MoleculesList listMolecules = new MoleculesList(verifiedSmile, moleculeDataFactory);
        listMolecules.addMolecule(oxygen);

        Generator generator = new Generator(benzene, listMolecules, 1, 1);
        MoleculesListAbstract all = generator.getAllMolecules();
        assertEquals(2, all.getListMolecule().size(), "The number of permutes not correct");

        // for (Molecule molecule : all.getListMolecule()) {
        // ORIGINAL_OUT.println(molecule);
        // }
        listMolecules.addMolecule(nitrogen);
        listMolecules.addMolecule(fluor);
        listMolecules.addMolecule(bromo);
        listMolecules.addMolecule(iodine);
        listMolecules.addMolecule(boron);

        Generator generator2 = new Generator(benzene, listMolecules, 1, 1);
        all = generator2.getAllMolecules();
        for (Molecule molecule : all.getListMolecule()) {
            ORIGINAL_OUT.println(molecule);
        }
        assertEquals(7, all.getListMolecule().size(), "The number of permutes not correct");
        // Generator generator3 = new Generator(benzene, listMolecules, 6, 1);
        // all = generator2.getAllMolecules();
        // for (Molecule molecule : all.getListMolecule()) {
        // ORIGINAL_OUT.println(molecule);
        // }
        /*
         * Very big number of permutes.
         */
    }

    /**
     * test Asterisk.
     *
     */
    @Test
    public void testAsterisk() {
        Molecule molecule1 = new Molecule("ccOcc", "ccOcc", "Is a test", true, verifiedSmile, moleculeDataFactory);
        molecule1.selectAtom(3);
        molecule1.selectAtom(0);
        Molecule molecule2 = new Molecule("*=N", "*=N", "Is a test", true, verifiedSmile, moleculeDataFactory);
        molecule2.selectAtom(0);
        MoleculesList listMolecules = new MoleculesList(verifiedSmile, moleculeDataFactory);
        listMolecules.addMolecule(molecule2);
        assertEquals("R", molecule2.getAtom(0).getSymbol(), "The symbol of the atom * should be R");
        Generator generator1 = new Generator(molecule1, listMolecules, 1, 1);
        MoleculesListAbstract all = generator1.getAllMolecules();
        assertEquals(3, all.getListMolecule().size(), "The number of permutes not correct");

        for (Molecule molecule : all.getListMolecule()) {
            ORIGINAL_OUT.println(molecule);
        }
        Molecule oxygen = new Molecule("Oxygen", "O", "Is a test", true, verifiedSmile, moleculeDataFactory);
        generator1 = new Generator(oxygen, listMolecules, 1, 1);
        all = generator1.getAllMolecules();
        // ORIGINAL_OUT.println("===============================");
        // for (Molecule molecule : all.getListMolecule()) {
        // ORIGINAL_OUT.println(molecule);
        // }
        assertEquals(2, all.getListMolecule().size(), "The number of permutes not correct");
        assertEquals("N=O", all.getListMolecule().get(1).getSmile(), "The smile of the molecule should be N=O");
        Molecule testNames = new Molecule("Botha", "C1=(Ccc(F)NO(cc1))=BrNO", "Is a test", true, verifiedSmile,
                moleculeDataFactory);
        ORIGINAL_OUT.println(testNames);
        AtomInterface atom = testNames.getAtom(5);
        assertEquals("N", atom.getSymbol(), "The symbol not correct");
        // ORIGINAL_OUT.println(atom.getSymbol());
        atom = testNames.getAtom(11);
        assertEquals("O", atom.getSymbol(), "The symbol not correct");
        // ORIGINAL_OUT.println(atom.getSymbol());
        Molecule piretrina = new Molecule("Piretrina",
                "COC(=O)C(\\C)=C\\C1C(C)(C*)[C@H]1C(=O)O[C@@H]2C(C)=C(C(=O)C2)CC=CC=C", "Is a test", true,
                verifiedSmile, moleculeDataFactory);
        // for (int i = 0; i < piretrina.atomCount(); i++) {
        //     ORIGINAL_OUT.println(" " + i + " " + piretrina.getAtom(i).getSymbol());
        // }
        atom = piretrina.getAtom(11);
        assertEquals("R", atom.getSymbol(), "The symbol not correct");
        atom = piretrina.getAtom(21);
        assertEquals("O", atom.getSymbol(), "The symbol not correct");
    }
};
