package domain;

import java.util.*;

/**
 * The Valley class represents the simulation environment where all units
 * (animals and resources) live and interact. The valley is structured as
 * a two-dimensional grid where each cell can hold a single {@link Unit}.
 *
 * This class manages the placement, movement, and behavior execution
 * of all units in the simulation.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class Valley {

    // Tamaño del valle (numero de filas y columnas de la cuadricula).
    static private int SIZE = 25;

    // Matriz bidimensional que almacena las unidades (animales y recursos).
    private Unit[][] places;

    /**
     * Constructs a new Valley with a fixed size and initializes
     * all positions as empty. Some default units (wolves and sheep)
     * are placed using the {@code someUnits()} method.
     */
    public Valley() {
        places = new Unit[SIZE][SIZE];

        // Inicializa todas las posiciones como vacias
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                places[r][c] = null;
            }
        }

        // Crea algunas unidades iniciales (lobos y ovejas)
        someUnits();
    }

    /**
     * Returns the total size of the valley.
     *
     * @return integer representing the number of rows and columns.
     */
    public int getSize() {
        return SIZE;
    }

    /**
     * Returns the unit located at the specified position in the grid.
     *
     * @param r Row index of the position.
     * @param c Column index of the position.
     * @return the Unit at that position, or {@code null} if empty.
     */
    public Unit getUnit(int r, int c) {
        return places[r][c];
    }

    /**
     * Sets a unit at the specified position in the valley.
     *
     * @param r Row index where the unit will be placed.
     * @param c Column index where the unit will be placed.
     * @param e The Unit object to insert.
     */
    public void setUnit(int r, int c, Unit e) {
        places[r][c] = e;
    }

    /**
     * Adds some initial units to the valley (wolves and sheep)
     * in predefined positions. This helps initialize the simulation
     * with some entities to observe behavior.
     */
    public void someUnits() {
        // Crea lobos
        Wolf akela = new Wolf(this, 10, 10);
        Wolf larka = new Wolf(this, 15, 15);
        setUnit(10, 10, akela);
        setUnit(15, 15, larka);

        // Crea ovejas
        Sheep shaun = new Sheep(this, 3, 4);
        Sheep wolly = new Sheep(this, 6, 8);
        setUnit(3, 4, shaun);
        setUnit(6, 8, wolly);
        
        // Crea henos
        Hay alarm = new Hay(0, 0, this);
        Hay alert = new Hay(0, SIZE-1, this);
        setUnit(0, 0, alarm);
        setUnit(0, SIZE-1, alert);
        
        // Crea zorros
        //Fox beltran = new Fox(this, 12, 5);
        //Fox ducuara = new Fox(this, 18, 20);
        
        // Crea pasto
        //Grass alejandra = new Grass(2, 10, this);
        //Grass adrian = new Grass(5, 15, this);
        //Grass extraGrass1 = new Grass(8, 8, this);
        //Grass extraGrass2 = new Grass(12, 18, this);
        
        // Bono
        // Lobos LotVol (cerca entre si para reproduccion)
        //WolfLotVol wolfLV1 = new WolfLotVol(this, 20, 5);
        //WolfLotVol wolfLV2 = new WolfLotVol(this, 21, 6);
        
        // Ovejas LotVol (cerca entre si para reproduccion)
        //SheepLotVol sheepLV1 = new SheepLotVol(this, 5, 20);
        //SheepLotVol sheepLV2 = new SheepLotVol(this, 6, 21);
        
        // HENO cerca de las ovejas LotVol (para que puedan comer)
        //Hay hayLV1 = new Hay(5, 22, this);
        //Hay hayLV2 = new Hay(7, 20, this);
        //Hay hayLV3 = new Hay(4, 19, this);
        //setUnit(5, 22, hayLV1);
        //setUnit(7, 20, hayLV2);
        //setUnit(4, 19, hayLV3);
    }

    /**
     * Counts how many neighboring units of the same class
     * are adjacent to the given position.
     *
     * @param r Row index of the reference unit.
     * @param c Column index of the reference unit.
     * @return number of neighboring units of the same type.
     */
    public int neighborsEquals(int r, int c) {
        int num = 0;
        if (inValley(r, c) && places[r][c] != null) {
            for (int dr = -1; dr < 2; dr++) {
                for (int dc = -1; dc < 2; dc++) {
                    // Verifica que no sea la misma posicion y que este dentro del valle
                    if ((dr != 0 || dc != 0) && inValley(r + dr, c + dc)
                            && (places[r + dr][c + dc] != null)
                            && (places[r][c].getClass() == places[r + dr][c + dc].getClass())) {
                        num++;
                    }
                }
            }
        }
        return num;
    }

    /**
     * Checks whether the specified position in the valley is empty.
     *
     * @param r Row index to check.
     * @param c Column index to check.
     * @return true if the position is inside the valley and has no unit.
     */
    public boolean isEmpty(int r, int c) {
        return (inValley(r, c) && places[r][c] == null);
    }

    /**
     * Checks if the given coordinates are within the valley's valid limits.
     *
     * @param r Row index to check.
     * @param c Column index to check.
     * @return true if the position is inside the valley boundaries.
     */
    private boolean inValley(int r, int c) {
        return ((0 <= r) && (r < SIZE) && (0 <= c) && (c < SIZE));
    }

    /**
     * Executes one simulation step ("Tic-tac") across the entire valley.
     * Each unit that exists in the valley performs its {@code act()} method.
     * This simulates one round of activity for all entities.
     */
    public void ticTac() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                Unit u = places[r][c];
                if (u != null) {
                    u.act(); // Cada unidad ejecuta su comportamiento
                }
            }
        }
    }
}


