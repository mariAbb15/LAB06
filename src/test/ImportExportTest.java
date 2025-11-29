package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import domain.*;
import java.io.*;

/**
 * Unit tests for import and export text file operations.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class ImportExportTest {

    private Fachada fachada;
    private File testFile;

    @BeforeEach
    public void setUp() {
        fachada = new Fachada();
        testFile = new File("testExport.txt");
    }

    /**
     * Test 1: Verifies export creates a text file.
     */
    @Test
    public void testExportCreatesFile() {
        try {
            fachada.export(testFile);
            assertTrue(testFile.exists(), "Export should create a file");
            assertTrue(testFile.length() > 0, "File should not be empty");
        } catch (ValleyException e) {
            fail("Export should not throw exception: " + e.getMessage());
        }
    }

    /**
     * Test 2: Verifies export file has correct format.
     */
    @Test
    public void testExportFormat() {
        try {
            fachada.export(testFile);
            
            BufferedReader reader = new BufferedReader(new FileReader(testFile));
            String firstLine = reader.readLine();
            reader.close();
            
            assertNotNull(firstLine, "File should have content");
            
            String[] parts = firstLine.split("\\s+");
            assertEquals(3, parts.length, 
                "Each line should have 3 parts: Type Row Column");
            
            // Verify it's a number for row and column
            assertDoesNotThrow(() -> {
                Integer.parseInt(parts[1]);
                Integer.parseInt(parts[2]);
            }, "Row and column should be numbers");
            
        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }

    /**
     * Test 3: Verifies import reads a valid file correctly.
     */
    @Test
    public void testImportValidFile() {
        try {
            // Create a valid test file
            PrintWriter writer = new PrintWriter(testFile);
            writer.println("Wolf 5 5");
            writer.println("Sheep 10 10");
            writer.close();
            
            fachada.importFile(testFile);
            Valley valley = fachada.getValley();
            
            assertNotNull(valley.getUnit(5, 5), "Unit should exist at (5,5)");
            assertNotNull(valley.getUnit(10, 10), "Unit should exist at (10,10)");
            
            assertTrue(valley.getUnit(5, 5) instanceof Wolf, 
                "Unit at (5,5) should be a Wolf");
            assertTrue(valley.getUnit(10, 10) instanceof Sheep, 
                "Unit at (10,10) should be a Sheep");
            
        } catch (Exception e) {
            fail("Import should not throw exception: " + e.getMessage());
        }
    }

    /**
     * Test 4: Verifies import throws exception for invalid format.
     */
    @Test
    public void testImportInvalidFormat() {
        try {
            PrintWriter writer = new PrintWriter(testFile);
            writer.println("Wolf 5");
            writer.close();
            
            ValleyException exception = assertThrows(ValleyException.class, () -> {
                fachada.importFile(testFile);
            });
            
            assertTrue(exception.getMessage().contains("Formato invalido"), 
                "Exception should mention invalid format");
            
        } catch (IOException e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }

    /**
     * Test 5: Verifies import throws exception for unknown unit type.
     */
    @Test
    public void testImportUnknownType() {
        try {
            PrintWriter writer = new PrintWriter(testFile);
            writer.println("Dragon 5 5");
            writer.close();
            
            ValleyException exception = assertThrows(ValleyException.class, () -> {
                fachada.importFile(testFile);
            });
            
            assertTrue(exception.getMessage().contains("desconocido"), 
                "Exception should mention unknown type");
            
        } catch (IOException e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }

    /**
     * Test 6: Verifies import throws exception for out of bounds position.
     */
    @Test
    public void testImportOutOfBounds() {
        try {
            PrintWriter writer = new PrintWriter(testFile);
            writer.println("Wolf 50 50");
            writer.close();
            
            ValleyException exception = assertThrows(ValleyException.class, () -> {
                fachada.importFile(testFile);
            });
            
            assertTrue(exception.getMessage().contains("fuera de rango"), 
                "Exception should mention out of range");
            
        } catch (IOException e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }

    /**
     * Test 7: Verifies import throws exception for non-numeric coordinates.
     */
    @Test
    public void testImportNonNumericCoordinates() {
        try {
            PrintWriter writer = new PrintWriter(testFile);
            writer.println("Wolf abc def");
            writer.close();
            
            ValleyException exception = assertThrows(ValleyException.class, () -> {
                fachada.importFile(testFile);
            });
            
            assertTrue(exception.getMessage().contains("numeros"), 
                "Exception should mention numeric requirement");
            
        } catch (IOException e) {
            fail("Test setup failed: " + e.getMessage());
        }
    }

    /**
     * Test 8: Verifies export then import preserves valley state.
     */
    @Test
    public void testExportImportCycle() {
        try {
            Valley originalValley = fachada.getValley();
            Unit original10_10 = originalValley.getUnit(10, 10);
            Unit original15_15 = originalValley.getUnit(15, 15);
            
            fachada.export(testFile);
            fachada.newValley();
            fachada.importFile(testFile);
            
            Valley loadedValley = fachada.getValley();
            
            assertNotNull(loadedValley.getUnit(10, 10), 
                "Unit at (10,10) should exist after import");
            assertNotNull(loadedValley.getUnit(15, 15), 
                "Unit at (15,15) should exist after import");
            
            assertEquals(original10_10.getClass(), loadedValley.getUnit(10, 10).getClass(),
                "Unit type at (10,10) should be preserved");
            assertEquals(original15_15.getClass(), loadedValley.getUnit(15, 15).getClass(),
                "Unit type at (15,15) should be preserved");
            
        } catch (ValleyException e) {
            fail("Export/Import cycle should not throw exception: " + e.getMessage());
        }
    }

    @AfterEach
    public void tearDown() {
        if (testFile.exists()) {
            testFile.delete();
        }
    }
}