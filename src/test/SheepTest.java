package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;

/**
 * The SheepTest class performs unit tests for the Sheep class.
 * It verifies correct behavior for initial energy, movement,
 * energy recovery, and death when near a wolf.
 *
 * Each test creates a new valley and sheep instance to ensure
 * isolated and repeatable test conditions.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class SheepTest {

    // Instancia del valle usada para las pruebas
    private Valley valley;

    // Instancia de oveja utilizada en cada prueba
    private Sheep sheep;

    /**
     * Sets up the test environment before each test.
     * Creates a new valley and a sheep at a specific position.
     */
    @BeforeEach
    public void setUp() {
        valley = new Valley();
        sheep = new Sheep(valley, 10, 10);
    }

    /**
     * Test 1: Verifies that the sheep starts with an energy value of 5.
     */
    @Test
    public void testInitialEnergy() {
        assertEquals(5, sheep.getEnergy(), "The sheep must start with 5 energy points.");
    }

    /**
     * Test 2: Checks that the sheep restores its energy when reaching
     * the north edge and changes its moving direction.
     */
    @Test
    public void testRecoverEnergyAtNorthEdge() {
        Sheep s = new Sheep(valley, 0, 5); // oveja colocada en el borde norte
        s.act(); // debe cambiar dirección y restaurar energía completa
        assertEquals(100, s.getEnergy(), "The sheep should recover full energy at the north edge.");
    }

    /**
     * Test 3: Verifies that the sheep dies if it is next to a wolf.
     */
    @Test
    public void testDiesNearWolf() {
        Wolf w = new Wolf(valley, 9, 10); // coloca un lobo justo arriba
        sheep.act(); // la oveja detecta el lobo y muere
        assertNull(valley.getUnit(10, 10), "The sheep must die if it is next to a wolf.");
    }

    /**
     * Test 4: Checks that the sheep gains energy when it is adjacent to another sheep.
     */
    @Test
    public void testGainEnergyNearSheep() {
        Sheep neighbor = new Sheep(valley, 9, 10); // crea una oveja vecina
        // reduce la energía para comprobar si se incrementa después
        while (sheep.getEnergy() > 3) {
            sheep.step();
        }

        int previousEnergy = sheep.getEnergy();
        sheep.act(); // debería ganar energía al estar junto a otra oveja

        assertTrue(sheep.getEnergy() > previousEnergy,
                "The sheep should gain energy when next to another sheep.");
    }

    /**
     * Cleans up the environment after each test to release resources.
     */
    @AfterEach
    public void tearDown() {
        // limpia las referencias para liberar memoria
        valley = null;
        sheep = null;
    }
}

