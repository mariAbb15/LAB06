package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import java.awt.Color;
import domain.*;

/**
 * Unit tests for the Fox class.
 * These tests verify correct initialization, color,
 * movement, hunting behavior, and inheritance.
 *
 * Each test starts with a new valley and a fox instance
 * to ensure isolation and consistency across runs.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class FoxTest {

    // Instancia del valle utilizada para las pruebas
    private Valley valley;

    // Instancia del zorro utilizada en cada prueba
    private Fox fox;

    /**
     * Sets up a new valley and fox before each test.
     */
    @BeforeEach
    public void setUp() {
        valley = new Valley();
        fox = new Fox(valley, 10, 10);
    }

    /**
     * Test 1: Verifies that the fox starts with 60 energy points.
     */
    @Test
    public void testInitialEnergy() {
        assertEquals(60, fox.getEnergy(), 
            "Fox should start with 60 energy points.");
    }

    /**
     * Test 2: Verifies that the fox is colored ORANGE.
     */
    @Test
    public void testColorIsOrange() {
        assertEquals(Color.ORANGE, fox.getColor(), 
            "Fox should be ORANGE.");
    }

    /**
     * Test 3: Verifies horizontal movement (east direction).
     * The fox should move to an adjacent column
     * while staying in the same row.
     */
    @Test
    public void testHorizontalMovement() {
        int initialCol = fox.getColumn();
        
        // Ejecuta una acción del zorro (debería moverse horizontalmente)
        fox.act();
        
        // Verifica que la columna haya cambiado
        assertTrue(fox.getColumn() == initialCol + 1 || 
                   fox.getColumn() == initialCol - 1,
            "Fox should move horizontally.");
        
        // La fila debe permanecer igual
        assertEquals(10, fox.getRow(), 
            "Fox row should remain the same.");
    }

    /**
     * Test 4: Verifies that the fox hunts nearby sheep.
     * The sheep should die and the fox should gain energy.
     */
    @Test
    public void testFoxHuntsSheep() {
        // Crea una oveja vecina para que el zorro pueda cazarla
        Sheep sheep = new Sheep(valley, 9, 10);
        
        int foxInitialEnergy = fox.getEnergy();
        
        // Ejecuta la acción de caza
        fox.act();
        
        // La oveja debe haber sido eliminada
        assertNull(valley.getUnit(9, 10), 
            "Sheep should be dead after being hunted.");
        
        // El zorro debe haber incrementado su energía
        assertTrue(fox.getEnergy() > foxInitialEnergy, 
            "Fox should gain energy after hunting.");
    }

    /**
     * Test 5: Verifies that the fox is recognized as an Animal.
     */
    @Test
    public void testFoxIsAnimal() {
        assertTrue(fox.isAnimal(), 
            "Fox should be recognized as an Animal.");
    }

    /**
     * Cleans up test references after each test.
     */
    @AfterEach
    public void tearDown() {
        // Limpia las referencias para evitar interferencias entre pruebas
        valley = null;
        fox = null;
    }
}
