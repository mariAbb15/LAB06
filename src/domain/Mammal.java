package domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * The Mammal class defines the common structure and behavior
 * for all mammals that exist in the valley, such as wolves or sheep.
 * It inherits basic attributes from Animal and implements Unit,
 * providing shared functionality like movement, position, and color.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public abstract class Mammal extends Animal implements Unit, Serializable {

    private static final long serialVersionUID = 1L;

    // Referencia al valle donde el mamífero existe y se mueve.
    private Valley valley;

    // Fila actual donde se encuentra el mamífero.
    protected int row;

    // Columna actual donde se encuentra el mamífero.
    protected int column;

    // Color con el que se representará el mamífero en la interfaz gráfica.
    protected Color color;

    /**
     * Creates a new mammal in the specified valley and position.
     * Automatically assigns it to the corresponding location in the grid.
     *
     * @param valley The valley where the mammal will be placed.
     * @param row Row position where the mammal is located.
     * @param column Column position where the mammal is located.
     */
    public Mammal(Valley valley, int row, int column) {
        this.valley = valley;
        this.row = row;
        this.column = column;

        // Coloca el mamífero en la posición indicada dentro del valle
        this.valley.setUnit(row, column, this);
    }

    /**
     * Returns the row index of the mammal's current position.
     *
     * @return integer representing the row position.
     */
    public final int getRow() {
        return row;
    }

    /**
     * Returns the column index of the mammal's current position.
     *
     * @return integer representing the column position.
     */
    public final int getColumn() {
        return column;
    }

    /**
     * Returns the color used to represent the mammal on the screen.
     *
     * @return a Color object representing the mammal's color.
     */
    public final Color getColor() {
        return color;
    }

    /**
     * Moves the mammal to a new position inside the valley if the destination
     * is within bounds and empty. Each movement consumes one energy point.
     * If the mammal has no energy left, it dies.
     *
     * @param r Target row position.
     * @param c Target column position.
     * @return true if the movement was successful; false otherwise.
     */
    public boolean move(int r, int c) {
        // Verificar que el destino esté dentro del valle
        if (r < 0 || r >= valley.getSize() || c < 0 || c >= valley.getSize()) {
            return false; // fuera de los límites
        }
    
        // Verificar que el destino esté vacío
        if (!valley.isEmpty(r, c)) {
            return false; // celda ocupada
        }
    
        // Ejecutar un paso: resta energía. Si no tiene energía, muere.
        if (!step()) {
            die();
            return false;
        }
    
        // Liberar la posición actual
        valley.setUnit(row, column, null);
    
        // Actualizar coordenadas
        row = r;
        column = c;
    
        // Colocar el mamífero en la nueva posición
        valley.setUnit(row, column, this);
    
        return true;
    }

    /**
     * Removes the mammal from the valley, representing its death.
     * The cell it occupied becomes empty.
     */
    public void die() {
        valley.setUnit(row, column, null);
    }
}
