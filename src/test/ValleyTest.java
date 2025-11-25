package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;

/**
 * Unit tests for the Valley class.
 * These tests verify correct initialization, placement,
 * and interaction of units within the valley grid.
 *
 * Each test starts with a new valley to ensure independence
 * and consistent behavior across runs.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class ValleyTest {

    // Instancia del valle usada para las pruebas
    private Valley valley;
    
    /**
     * Sets up a new valley before each test.
     */
    @BeforeEach
    public void setUp() {
        valley = new Valley();
    }

    /**
     * Test 1: Verifies that the valley initializes with the correct size.
     */
    @Test
    public void testSizeIsCorrect() {
        assertEquals(25, valley.getSize(), "The valley size must be 25.");
    }

    /**
     * Test 2: Checks that initial units (wolves and sheep) are placed correctly
     * and that empty positions remain empty.
     */
    @Test
    public void testInitialUnitsPlacement() {
        // Verifica que las posiciones iniciales tengan las unidades esperadas
        assertNotNull(valley.getUnit(10, 10), "A wolf should exist at (10,10).");
        assertNotNull(valley.getUnit(15, 15), "A wolf should exist at (15,15).");
        assertNotNull(valley.getUnit(3, 4), "A sheep should exist at (3,4).");
        assertNotNull(valley.getUnit(6, 8), "A sheep should exist at (6,8).");

        // Comprueba que otra celda aleatoria esté vacía
        assertTrue(valley.isEmpty(0, 1), "A random empty cell should remain empty.");
    }

    /**
     * Test 3: Ensures that setUnit() correctly places a unit
     * and getUnit() retrieves the same instance.
     */
    @Test
    public void testSetAndGetUnit() {
        // Crea un lobo y lo coloca en el valle
        Wolf testWolf = new Wolf(valley, 5, 5);
        valley.setUnit(5, 5, testWolf);

        // Recupera la misma posición para verificar que coincide
        Unit recovered = valley.getUnit(5, 5);
        assertSame(testWolf, recovered, "Should retrieve the same wolf instance.");
    }

    /**
     * Test 4: Verifies that isEmpty() correctly detects empty and occupied cells.
     */
    @Test
    public void testIsEmptyWorksCorrectly() {
        // Al inicio la celda debe estar vacía
        assertTrue(valley.isEmpty(0, 3), "The cell (0,3) should be empty initially.");

        // Coloca una oveja en la celda y verifica que ya no está vacía
        valley.setUnit(0, 3, new Sheep(valley, 0, 3));
        assertFalse(valley.isEmpty(0, 3), "The cell (0,3) should no longer be empty.");
    } 

    /**
     * Test 5: Verifies that neighborsEquals() correctly counts
     * the number of same-type neighbors.
     */
    @Test
    public void testNeighborsEquals() {
        // Coloca dos lobos vecinos para verificar el conteo
        Wolf w1 = new Wolf(valley, 5, 5);
        Wolf w2 = new Wolf(valley, 5, 6);
        valley.setUnit(5, 5, w1);
        valley.setUnit(5, 6, w2);

        int count = valley.neighborsEquals(5, 5);
        assertEquals(1, count, "There should be one same-type neighbor.");
    }

    /**
     * Test 6: Verifies that ticTac() calls act() on all non-null units.
     * Uses a dummy subclass to confirm act() execution.
     */
    @Test
    public void testTicTacCallsActOnUnits() {
        // Clase interna temporal que simula una unidad personalizada
        class DummyUnit implements Unit {
            boolean acted = false;
            public void act() { acted = true; }
        }

        // Inserta una unidad ficticia en el valle
        DummyUnit dummy = new DummyUnit();
        valley.setUnit(1, 1, dummy);

        // Ejecuta un ciclo del valle
        valley.ticTac();

        // Comprueba que el método act() haya sido ejecutado
        assertTrue(dummy.acted, "The act() method should have been executed on the unit.");
    }

    /**
     * Cleans up references after each test.
     */
    @AfterEach
    public void tearDown() {
        // Limpia las referencias para liberar memoria entre pruebas
        valley = null;
    }
}
