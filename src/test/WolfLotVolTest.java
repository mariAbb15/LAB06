package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;
import java.awt.Color;

/**
 * Unit tests for the WolfLotVol class (Bonus Cycle 6).
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class WolfLotVolTest {

    private Valley valley;
    private WolfLotVol wolf;

    @BeforeEach
    public void setUp() {
        valley = new Valley();
        wolf = new WolfLotVol(valley, 10, 10);
    }

    /**
     * Test 1: Verifies wolf starts with 100 energy.
     */
    @Test
    public void testInitialEnergy() {
        assertEquals(100, wolf.getEnergy(), 
            "WolfLotVol should start with 100 energy.");
    }

    /**
     * Test 2: Verifies wolf has BLACK color.
     */
    @Test
    public void testColorIsBlack() {
        assertEquals(Color.BLACK, wolf.getColor(), 
            "WolfLotVol should be BLACK.");
    }

    /**
     * Test 3: Verifies wolf hunts SheepLotVol and gains energy.
     */
    @Test
    public void testWolfHuntsSheepLotVol() {
        Valley testValley = new Valley();
        WolfLotVol testWolf = new WolfLotVol(testValley, 10, 10);
        testWolf.setEnergy(50);
        
        // Crear oveja LotVol al lado
        SheepLotVol sheep = new SheepLotVol(testValley, 9, 10);
        
        testWolf.act();
        
        // La oveja debe haber muerto
        assertNull(testValley.getUnit(9, 10), 
            "Sheep should be dead after wolf hunts it.");
        
        // El lobo debe tener más energía
        assertTrue(testWolf.getEnergy() > 50, 
            "Wolf should gain energy after hunting.");
    }

    /**
     * Test 4: Verifies wolf reproduction creates new wolf.
     */
    @Test
    public void testWolfReproduction() {
        Valley testValley = new Valley();
        WolfLotVol wolf1 = new WolfLotVol(testValley, 10, 10);
        WolfLotVol wolf2 = new WolfLotVol(testValley, 9, 10);
        
        // Contar lobos iniciales
        int initialCount = 0;
        for (int r = 0; r < testValley.getSize(); r++) {
            for (int c = 0; c < testValley.getSize(); c++) {
                if (testValley.getUnit(r, c) instanceof WolfLotVol) {
                    initialCount++;
                }
            }
        }
        
        // Actuar varias veces
        for (int i = 0; i < 10; i++) {
            wolf1.act();
            wolf2.act();
        }
        
        // Contar lobos finales
        int finalCount = 0;
        for (int r = 0; r < testValley.getSize(); r++) {
            for (int c = 0; c < testValley.getSize(); c++) {
                if (testValley.getUnit(r, c) instanceof WolfLotVol) {
                    finalCount++;
                }
            }
        }
        
        assertTrue(finalCount > initialCount, 
            "Wolves should reproduce when near each other.");
    }

    /**
     * Test 5: Verifies wolf is an Animal.
     */
    @Test
    public void testWolfIsAnimal() {
        assertTrue(wolf.isAnimal(), 
            "WolfLotVol should be an Animal.");
    }

    @AfterEach
    public void tearDown() {
        valley = null;
        wolf = null;
    }
}