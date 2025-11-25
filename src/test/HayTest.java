package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;
import java.awt.Color;

/**
 * Unit tests for the Hay class.
 * Verifies correct color alternation and basic behavior.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class HayTest {

    // Referencia al valle donde se colocará el heno
    private Valley valley;
    // Instancia de heno usada en cada prueba
    private Hay hay;

    /**
     * Sets up a fresh test environment before each test.
     * Creates a new valley and a new hay unit at position (5,5).
     */
    @BeforeEach
    public void setUp() {
        valley = new Valley();
        hay = new Hay(5, 5, valley);
    }

    /**
     * Test 1: Verifies that the hay starts with YELLOW color.
     */
    @Test
    public void testInitialColorIsYellow() {
        // El color inicial debe ser amarillo
        assertEquals(Color.YELLOW, hay.getColor(),
            "Hay should start with YELLOW color.");
    }

    /**
     * Test 2: Verifies that the hay alternates its color after one act() call.
     */
    @Test
    public void testColorAlternatesAfterAct() {
        // Guarda el color inicial
        Color initialColor = hay.getColor();
        
        // Ejecuta un tic-tac (debe alternar el color)
        hay.act();

        // Obtiene el nuevo color
        Color newColor = hay.getColor();

        // Verifica que el color cambió
        assertNotEquals(initialColor, newColor, 
            "Color should change after calling act().");
    }

    /**
     * Test 3: Verifies the exact color alternation sequence:
     * YELLOW → RED → YELLOW → RED → YELLOW
     */
    @Test
    public void testColorSequence() {
        // Estado inicial: YELLOW
        assertEquals(Color.YELLOW, hay.getColor());

        hay.act(); // step 1 → RED
        assertEquals(Color.RED, hay.getColor());

        hay.act(); // step 2 → YELLOW
        assertEquals(Color.YELLOW, hay.getColor());

        hay.act(); // step 3 → RED
        assertEquals(Color.RED, hay.getColor());

        hay.act(); // step 4 → YELLOW
        assertEquals(Color.YELLOW, hay.getColor());
    }

    /**
     * Test 4: Verifies that hay shape is SQUARE.
     */
    @Test
    public void testShapeIsSquare() {
        // Verifica que el heno sea cuadrado (SQUARE = 2)
        assertEquals(Unit.SQUARE, hay.shape(), 
            "Hay should be represented as a SQUARE.");
    }

    /**
     * Cleans up test environment after each test.
     * Sets valley and hay references to null.
     */
    @AfterEach
    public void tearDown() {
        valley = null;
        hay = null;
    }
}
