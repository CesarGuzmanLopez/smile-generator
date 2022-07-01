package com.smiles.v2.main.domain.models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.smiles.v2.main.framework.cdk.MoleculeDataFactory;
import com.smiles.v2.main.framework.cdk.VerifiedSmile;
import com.smiles.v2.main.interfaces.AtomInterface;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.imageio.ImageIO;

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
                assertEquals("C=C", molecule.smile(), "Possible error obtain isomeric SMILES");
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
                assertEquals("C=CC=C", moleculeClone.smile(), "Possible error obtain isomeric SMILES");
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
                assertEquals("Primero<2> |1| Segundo<1>", fusionA2To1.getName(), "the name no generated correctly");
                assertEquals("C=CC(=CN)CO", fusionA2To1.smile(), "Error generate smile fusion");
                assertEquals(7, fusionA2To1.atomCount(), "Error generate number of atoms");
        }

        /**
         * Test for simple Atoms fusion.
         *
         */
        @Test
        public void testSimpleFusionAtom() {
                Molecule molecule1 = new Molecule("Primero", "C", "Is a prove", true, new VerifiedSmile(),
                                new MoleculeDataFactory());
                Molecule molecule2 = new Molecule("Segundo", "C", "Is a prove", true, new VerifiedSmile(),
                                new MoleculeDataFactory());

                Molecule fusionA2To1 = Molecule.fusionMolecule(molecule1, molecule2, null, null);
                assertEquals("Primero<null> |1| Segundo<null>", fusionA2To1.getName(),
                                "the name no generated correctly");
                // ORIGINAL_OUT.println(fusionA2To1.getSmile());
                assertEquals("CC", fusionA2To1.smile(), "Error generate smile fusion");
                assertEquals(2, fusionA2To1.atomCount(), "Error generate number of atoms");
                fusionA2To1.selectAtom(0);
                Molecule fusion2A2to1 = Molecule.fusionMolecule(fusionA2To1, molecule1, 0, null);
                assertEquals("CCC", fusion2A2to1.smile(), "Error generate smile fusion");
                assertEquals(3, fusion2A2to1.atomCount(), "Error generate number of atoms");
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
                assertEquals("Primero<4> |1| Segundo<0>", fusionA2To1.getName(), "the name no generated correctly");
                Molecule fusion2A2to1 = Molecule.fusionMolecule(fusionA2To1, molecule2, 0, 0);
                assertEquals("C1=CC=C(C=C1)C2=CC=CC=C2", fusionA2To1.smile(), "Error generate smile fusion");
                assertEquals("C1=CC=C(C=C1)C2=CC(=CC=C2)C3=CC=CC=C3", fusion2A2to1.smile(),
                                "Error generate smile fusion");
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
                assertEquals("Primero<null> |2| Segundo<null>", fusionA2To1.getName(),
                                "the name no generated correctly");
                // ORIGINAL_OUT.println(fusionA2To1.getSmile());
                assertEquals("C=C", fusionA2To1.smile(), "Error generate smile fusion");
                assertEquals(2, fusionA2To1.atomCount(), "Error generate number of atoms");
                fusionA2To1.selectAtom(0);
                Molecule fusion2A2to1 = Molecule.fusionMolecule(fusionA2To1, molecule1, 0, null, 2);
                // ORIGINAL_OUT.println(fusion2A2to1.getSmile());
                assertEquals("C=C=C", fusion2A2to1.smile(), "Error generate smile fusion");
                assertEquals(3, fusion2A2to1.atomCount(), "Error generate number of atoms");
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

        /**
         * Test Select.
         */
        @Test
        public void testSelect() {
                Molecule molecule1 = new Molecule("Primero", "C=CN", "Is a prove", true, new VerifiedSmile(),
                                new MoleculeDataFactory());
                molecule1.selectAtom(1);
                molecule1.selectAtom(2);
                Molecule mol2 = new Molecule("Segundo", "NO=C", "Is a prove", true, new VerifiedSmile(),
                                new MoleculeDataFactory());
                mol2.selectAtom(1);
                Molecule fusion = Molecule.fusionMolecule(molecule1, mol2, 1, 1);

                // ORIGINAL_OUT.println(fusion2A2to1.getSmile());
                for (AtomInterface a : fusion.getMoleculeData().getListAtomsSelected()) {
                        ORIGINAL_OUT.println(a.getSymbol() + " " + a.isSelected());
                }
                ORIGINAL_OUT.println(fusion.isSelected(1));
                ORIGINAL_OUT.println(fusion.isSelected(2));
                Molecule substitute = fusion.getSubstitute(1);
                ORIGINAL_OUT.println(substitute.smile());
                fusion = Molecule.fusionMolecule(fusion, mol2, 2, 1);
                ORIGINAL_OUT.println(fusion.isSelected(1));
                ORIGINAL_OUT.println(fusion.isSelected(2));
                substitute = fusion.getSubstitute(1);
                ORIGINAL_OUT.println(substitute.smile());
                assertEquals("C=ON", substitute.smile(), "Error generate smile fusion");
        }
        /**
         * Test of the molecule important methods.
         * @throws IOException
        */
        @Test
        public void testSaveImage()  {
                Molecule molecule1 = new Molecule("Primero", "C=CN", "Is a prove", true, new VerifiedSmile(),
                                new MoleculeDataFactory());
                molecule1.selectAtom(1);
                molecule1.selectAtom(2);
                Molecule mol2 = new Molecule("Segundo", "NO=C", "Is a prove", true, new VerifiedSmile(),
                                new MoleculeDataFactory());
                mol2.selectAtom(1);
                Molecule fusion = Molecule.fusionMolecule(molecule1, mol2, 1, 1);

                // ORIGINAL_OUT.println(fusion2A2to1.getSmile());
                for (AtomInterface a : fusion.getMoleculeData().getListAtomsSelected()) {
                        ORIGINAL_OUT.println(a.getSymbol() + " " + a.isSelected());
                }
                ORIGINAL_OUT.println(fusion.isSelected(1));
                ORIGINAL_OUT.println(fusion.isSelected(2));
                Molecule sustituto = fusion.getSubstitute(1);
                ORIGINAL_OUT.println(sustituto.smile());
                fusion = Molecule.fusionMolecule(fusion, mol2, 2, 1);
                BufferedImage bi = fusion.getImage(700, 700);
                try {
                        ImageIO.write(bi, "png", new File("test.png"));
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }

        }

}
