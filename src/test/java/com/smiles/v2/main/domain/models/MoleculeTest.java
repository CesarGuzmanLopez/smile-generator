package com.smiles.v2.main.domain.models;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.smiles.v2.main.framework.cdk.MoleculeDataFactory;
import com.smiles.v2.main.framework.cdk.VerifiedSmile;


/** Test of Molecule.
 * @author Cesar Guzman Lopez
*/
public class MoleculeTest {
    private static Molecule molecule;
    /**Create the molecule. to Test.
     *
    */
    @BeforeAll
    public static void setUpBeforeClass() throws Exception {
        molecule = new Molecule("name", "cc", "Is a prove",
            true, new VerifiedSmile(), new MoleculeDataFactory());
    }
    /**Test of the molecule important methods.
     *
    */
    @Test
    public  void molecule() {
        assertEquals("name", molecule.getName());
        assertEquals("C=C", molecule.getSmile());
        assertEquals("Is a prove", molecule.getMessage());
        assertEquals(true, molecule.hasHydrogenImplicit());
    }

}
