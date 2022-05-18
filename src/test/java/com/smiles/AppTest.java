package com.smiles;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.
     */
    /**
     * obtengo 2 numeros aleatorios
     */
    int a = (int) (Math.random() * 10);
    int b = (int) (Math.random() * 10);
    @Test
    void testApp() {
        assertEquals(a, b);
    }
}
