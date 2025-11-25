package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;

/**
 * Unit tests for the SheepLotVol class (Bonus Cycle 6).
 * UPDATED: SheepLotVol only eats HAY (not grass) and only dies near WolfLotVol (not Wolf).
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class SheepLotVolTest {

    private Valley valley;
    private SheepLotVol sheep;

    @BeforeEach
    public void setUp() {
        valley = new Valley();
        sheep = new SheepLotVol(valley, 10, 10);
    }

    /**
     * Test 1: Verifies sheep starts with 50 energy.
     */
    @Test
    public void testInitialEnergy() {
        assertEquals(50, sheep.getEnergy(), 
            "Sheep should start with 50 energy.");
    }

    /**
     * Test 2: Verifies sheep dies ONLY near WolfLotVol (not regular Wolf).
     */
    @Test
    public void testSheepDiesOnlyNearWolfLotVol() {
        // Test 1: Regular Wolf nearby - should NOT die
        Valley testValley1 = new Valley();
        SheepLotVol sheep1 = new SheepLotVol(testValley1, 10, 10);
        Wolf regularWolf = new Wolf(testValley1, 9, 10); // Regular wolf nearby
        
        sheep1.act();
        
        assertNotNull(testValley1.getUnit(sheep1.getRow(), sheep1.getColumn()), 
            "Sheep should NOT die when near a regular Wolf.");
        
        // Test 2: WolfLotVol nearby - SHOULD die
        Valley testValley2 = new Valley();
        SheepLotVol sheep2 = new SheepLotVol(testValley2, 10, 10);
        WolfLotVol wolfLotVol = new WolfLotVol(testValley2, 9, 10); // WolfLotVol nearby
        
        sheep2.act();
        
        assertNull(testValley2.getUnit(10, 10), 
            "Sheep SHOULD die when near a WolfLotVol.");
    }

    /**
     * Test 3: Verifies sheep eats HAY ONLY (not grass) and gains energy.
     */
    @Test
    public void testSheepEatsOnlyHay() {
        Valley testValley = new Valley();
        SheepLotVol testSheep = new SheepLotVol(testValley, 10, 10);
        
        // Test 1: Hay nearby - should eat it
        Hay hay = new Hay(9, 10, testValley);
        testValley.setUnit(9, 10, hay);
        
        int initialEnergy = testSheep.getEnergy();
        testSheep.act();
        
        assertTrue(testSheep.getEnergy() > initialEnergy, 
            "Sheep should gain energy after eating hay.");
        
        // Test 2: Grass nearby - should NOT eat it
        Valley testValley2 = new Valley();
        SheepLotVol testSheep2 = new SheepLotVol(testValley2, 10, 10);
        Grass grass = new Grass(9, 10, testValley2);
        
        int initialEnergy2 = testSheep2.getEnergy();
        testSheep2.act();
        
        // La energía puede disminuir por movimiento, pero NO debe aumentar por comer pasto
        assertTrue(grass.isMature(), 
            "Grass should remain mature (not eaten by SheepLotVol).");
    }

    /**
     * Test 4: Verifies sheep reproduction with nearby SheepLotVol.
     */
    @Test
    public void testSheepReproduction() {
        Valley testValley = new Valley();
        SheepLotVol sheep1 = new SheepLotVol(testValley, 10, 10);
        SheepLotVol sheep2 = new SheepLotVol(testValley, 9, 10);
        
        int initialSheep = countSheepLotVol(testValley);
        
        // Actuar varias veces para dar oportunidad de reproducción
        for (int i = 0; i < 5; i++) {
            sheep1.act();
            sheep2.act();
        }
        
        int finalSheep = countSheepLotVol(testValley);
        assertTrue(finalSheep > initialSheep, 
            "Sheep should reproduce when near each other.");
    }

    /**
     * Test 5: Verifies sheep has SQUARE shape.
     */
    @Test
    public void testShapeIsSquare() {
        assertEquals(Unit.SQUARE, sheep.getShape(), 
            "SheepLotVol should be SQUARE shaped.");
    }

    /**
     * Test 6: Verifies newborn sheep doesn't act on first turn.
     */
    @Test
    public void testNewbornDoesNotActImmediately() {
        SheepLotVol newborn = new SheepLotVol(valley, 5, 5, true);
        int initialEnergy = newborn.getEnergy();
        int initialRow = newborn.getRow();
        
        newborn.act();
        
        // No debería moverse ni perder energía en primer turno
        assertEquals(initialEnergy, newborn.getEnergy(), 
            "Newborn should not lose energy on first turn.");
        assertEquals(initialRow, newborn.getRow(), 
            "Newborn should not move on first turn.");
    }

    /**
     * Test 7: Verifies sheep is an Animal (inheritance check).
     */
    @Test
    public void testSheepIsAnimal() {
        assertTrue(sheep.isAnimal(), 
            "SheepLotVol should be recognized as an Animal.");
    }

    /**
     * Helper method to count SheepLotVol in the valley.
     */
    private int countSheepLotVol(Valley v) {
        int count = 0;
        for (int r = 0; r < v.getSize(); r++) {
            for (int c = 0; c < v.getSize(); c++) {
                Unit u = v.getUnit(r, c);
                if (u instanceof SheepLotVol) count++;
            }
        }
        return count;
    }

    @AfterEach
    public void tearDown() {
        valley = null;
        sheep = null;
    }
}
