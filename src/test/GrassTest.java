package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;
import java.awt.Color;

/**
 * Unit tests for the Grass class (Cycle 5).
 * Verifies growth cycle, regeneration, and interaction with sheep.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class GrassTest {

    private Valley valley;
    private Grass grass;

    /**
     * Sets up the test environment before each test.
     */
    @BeforeEach
    public void setUp() {
        valley = new Valley();
        grass = new Grass(5, 5, valley);
    }

    /**
     * Test 1: Verifies grass starts in mature state.
     */
    @Test
    public void testInitiallyMature() {
        assertTrue(grass.isMature(), 
            "Grass should start mature (growth level 2).");
        assertEquals(2, grass.getGrowthLevel(), 
            "Growth level should be 2 initially.");
    }

    /**
     * Test 2: Verifies grass is NOT an animal.
     */
    @Test
    public void testGrassIsNotAnimal() {
        assertFalse(grass.isAnimal(), 
            "Grass should NOT be classified as an animal.");
    }

    /**
     * Test 3: Verifies grass IS a resource.
     */
    @Test
    public void testGrassIsResource() {
        assertTrue(grass.isResource(), 
            "Grass should be classified as a resource.");
    }

    /**
     * Test 4: Verifies grass has SQUARE shape.
     */
    @Test
    public void testShapeIsSquare() {
        assertEquals(Unit.SQUARE, grass.shape(), 
            "Grass should be represented as a SQUARE.");
    }

    /**
     * Test 5: Verifies grass can be eaten and changes state.
     */
    @Test
    public void testGrassCanBeEaten() {
        grass.beEaten();
        
        assertFalse(grass.isMature(), 
            "Grass should not be mature after being eaten.");
        assertEquals(0, grass.getGrowthLevel(), 
            "Growth level should be 0 after being eaten.");
    }

    /**
     * Test 6: Verifies grass regenerates over time.
     * Regeneration cycle: 10 steps to level 1, then 3 more to level 2.
     */
    @Test
    public void testGrassRegenerates() {
        grass.beEaten();
        assertEquals(0, grass.getGrowthLevel(), 
            "Should start at level 0 after being eaten.");
        
        // Phase 1: Regenerate to growing stage (10 tic-tacs)
        for (int i = 0; i < 10; i++) {
            grass.act();
        }
        assertEquals(1, grass.getGrowthLevel(), 
            "Should be at growth level 1 after 10 steps.");
        assertFalse(grass.isMature(), 
            "Should not be mature yet at level 1.");
        
        // Phase 2: Mature completely (3 more tic-tacs)
        for (int i = 0; i < 3; i++) {
            grass.act();
        }
        assertEquals(2, grass.getGrowthLevel(), 
            "Should be at growth level 2 after 13 total steps.");
        assertTrue(grass.isMature(), 
            "Should be fully mature at level 2.");
    }

    /**
     * Test 7: Verifies grass color changes with growth level.
     */
    @Test
    public void testColorChangesWithGrowth() {
        Color matureColor = grass.getColor();
        
        grass.beEaten();
        Color eatenColor = grass.getColor();
        assertNotEquals(matureColor, eatenColor, 
            "Color should change when eaten.");
        
        // Regenerate to level 1
        for (int i = 0; i < 10; i++) grass.act();
        Color growingColor = grass.getColor();
        assertNotEquals(eatenColor, growingColor, 
            "Color should change when growing.");
        
        // Regenerate to level 2
        for (int i = 0; i < 3; i++) grass.act();
        Color finalColor = grass.getColor();
        assertNotEquals(growingColor, finalColor, 
            "Color should change when mature.");
    }

    /**
     * Test 8: Verifies grass stays mature if not eaten.
     */
    @Test
    public void testMatureGrassStaysMature() {
        assertTrue(grass.isMature());
        
        // Act multiple times
        for (int i = 0; i < 20; i++) {
            grass.act();
        }
        
        assertTrue(grass.isMature(), 
            "Mature grass should remain mature if not eaten.");
        assertEquals(2, grass.getGrowthLevel());
    }

    /**
     * Cleans up references after each test.
     */
    @AfterEach
    public void tearDown() {
        valley = null;
        grass = null;
    }
}