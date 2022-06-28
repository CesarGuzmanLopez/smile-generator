package com.smiles.v2.main.domain.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.smiles.v2.main.framework.cdk.MoleculeDataFactory;
import com.smiles.v2.main.framework.cdk.VerifiedSmile;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Test of Molecule.
 *
 * @author Cesar Guzman Lopez
 */
public class MoleculeTest {
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

    /**
     * Test of the molecule important methods.
     *
     */
    @Test
    public void testMolecule() {
        Molecule molecule = new Molecule("name", "cc", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());
        assertEquals("name", molecule.getName(), "the name is change in the molecule object");
        assertEquals("C=C", molecule.getSmile(), "Possible error obtain isomeric SMILES");
        assertEquals("Is a prove", molecule.getMessage(), "Problem in the clone object");
        assertEquals(true, molecule.hasHydrogenImplicit()); // NOSONAR
    }

    /**
     * Test of clone data.
     *
     */
    @Test
    public void testMoleculeCloneData() {
        Molecule molecule = new Molecule("name", "cccc", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());
        molecule.selectAtom(3);
        molecule.selectAtom(2);
        Molecule moleculeClone = new Molecule(molecule, true);
        assertEquals("name", moleculeClone.getName(), "the name is change in the molecule object");
        assertEquals("C=CC=C", moleculeClone.getSmile(), "Possible error obtain isomeric SMILES");
        assertEquals("Is a prove", moleculeClone.getMessage(), "Problem in the clone object");
        assertEquals(true, moleculeClone.hasHydrogenImplicit()); // NOSONAR
        assertEquals(2, moleculeClone.getMoleculeData().getListAtomsSelected().size(),
                "The number of atoms selected is not correct in the clone object");

        assertEquals(3, moleculeClone.getMoleculeData().getListAtomsSelected().get(0).getId(),
                "The number of atoms selected is not correct in the clone object");
        assertEquals(2, moleculeClone.getMoleculeData().getListAtomsSelected().get(1).getId(),
                "The number of atoms selected is not correct in the clone object");

    }

    /**
     * Test fusion Molecule.
     */
    @Test
    public void testFusionMolecule() {
        Molecule molecule1 = new Molecule("Primero", "NccCO", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());
        Molecule molecule2 = new Molecule("Segundo", "cc", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());
        molecule1.selectAtom(2);
        molecule2.selectAtom(1);
        Molecule fusionA2To1 = Molecule.fusionMolecule(molecule1, molecule2, 2, 1);
        assertEquals("Primero - Segundo", fusionA2To1.getName(), "the name no generated correctly");
        assertEquals("C=CC(=CN)CO", fusionA2To1.getSmile(), "Error generate smile fusion");
        assertEquals(7, fusionA2To1.getNumberAtoms(), "Error generate number of atoms");
    }

    /** Test for simple Atoms fusion.
     *
    */
    @Test
    public void testSimpleFusionAtom() {
        Molecule molecule1 = new Molecule("Primero", "C", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());
        Molecule molecule2 = new Molecule("Segundo", "C", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());

        Molecule fusionA2To1 = Molecule.fusionMolecule(molecule1, molecule2, null, null);
        assertEquals("Primero - Segundo", fusionA2To1.getName(), "the name no generated correctly");
        //ORIGINAL_OUT.println(fusionA2To1.getSmile());
        assertEquals("CC", fusionA2To1.getSmile(), "Error generate smile fusion");
        assertEquals(2, fusionA2To1.getNumberAtoms(), "Error generate number of atoms");
        fusionA2To1.selectAtom(0);
        Molecule fusion2A2to1 = Molecule.fusionMolecule(fusionA2To1, molecule1, 0, null);
        assertEquals("CCC", fusion2A2to1.getSmile(), "Error generate smile fusion");
        assertEquals(3, fusion2A2to1.getNumberAtoms(), "Error generate number of atoms");
    }
    /**
     * Test of the molecule important methods.
    */
    @Test
    public void testFusionBenzene() {
        Molecule molecule1 = new Molecule("Primero", "c1ccccc1", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());
        Molecule molecule2 = new Molecule("Segundo", "c1ccccc1", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());

        molecule1.selectAtom(0);
        molecule2.selectAtom(0);
        molecule1.selectAtom(4);

        Molecule fusionA2To1 = Molecule.fusionMolecule(molecule1, molecule2, 4, 0);
        assertEquals("Primero - Segundo", fusionA2To1.getName(), "the name no generated correctly");
        Molecule fusion2A2to1 = Molecule.fusionMolecule(fusionA2To1, molecule2, 0, 0);
        assertEquals("C1=CC=C(C=C1)C2=CC=CC=C2", fusionA2To1.getSmile(), "Error generate smile fusion");
        assertEquals("C1=CC=C(C=C1)C2=CC(=CC=C2)C3=CC=CC=C3", fusion2A2to1.getSmile(), "Error generate smile fusion");
    }
    /**
     * Test of the molecule important methods.
     */
    @Test
    public void testFusionBounds() {
        Molecule molecule1 = new Molecule("Primero", "C", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());
        Molecule molecule2 = new Molecule("Segundo", "C", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());

        Molecule fusionA2To1 = Molecule.fusionMolecule(molecule1, molecule2, null, null, 2);
        assertEquals("Primero - Segundo", fusionA2To1.getName(), "the name no generated correctly");
        //ORIGINAL_OUT.println(fusionA2To1.getSmile());
        assertEquals("C=C", fusionA2To1.getSmile(), "Error generate smile fusion");
        assertEquals(2, fusionA2To1.getNumberAtoms(), "Error generate number of atoms");
        fusionA2To1.selectAtom(0);
        Molecule fusion2A2to1 = Molecule.fusionMolecule(fusionA2To1, molecule1, 0, null, 2);
        //ORIGINAL_OUT.println(fusion2A2to1.getSmile());
        assertEquals("C=C=C", fusion2A2to1.getSmile(), "Error generate smile fusion");
        assertEquals(3, fusion2A2to1.getNumberAtoms(), "Error generate number of atoms");
    }
    /**
     * Test compareTo.
     */
    @Test
    public void testCompareTo() {
        Molecule molecule1 = new Molecule("Primero", "C=C", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());
        Molecule molecule2 = new Molecule("Segundo", "cc", "Is a prove", true, new VerifiedSmile(),
                new MoleculeDataFactory());
        molecule2.selectAtom(0);
        molecule1.selectAtom(1);
        assertEquals(0, molecule1.compareTo(molecule2), "Error toCompare");
    }


}
