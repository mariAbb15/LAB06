package domain;

import java.awt.Color;

/**
 * The Hay class represents a hay package located inside the valley.
 * Its main behavior is to alternate its color between red and yellow
 * every simulation step (tic-tac).
 *
 * Hay is a static, non-animal unit that visually changes over time
 * to represent growth or decay.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class Hay implements Unit {

    // Posición del heno dentro de la cuadrícula del valle
    private int x, y;
    // Referencia al valle al que pertenece
    private Valley valley;
    // Color actual del heno (cambia entre amarillo y rojo)
    private Color color;
    // Contador interno que controla el cambio de color
    private int stepCounter;

    /**
     * Creates a new Hay unit in the given position inside the valley.
     * The hay starts in yellow color.
     *
     * @param x X coordinate of the hay position.
     * @param y Y coordinate of the hay position.
     * @param valley Reference to the valley where the hay is placed.
     */
    public Hay(int x, int y, Valley valley) {
        this.x = x;
        this.y = y;
        this.valley = valley;
        this.color = Color.YELLOW;  // Color inicial amarillo
        this.stepCounter = 0;
    }

    /**
     * Executes the hay behavior on each tic-tac (simulation step).
     * Alternates the color between red and yellow.
     */
    @Override
    public void act() {
        stepCounter++;
        // Cambia el color en cada paso
        color = (stepCounter % 2 == 0) ? Color.YELLOW : Color.RED;
    }

    /**
     * Returns the color currently used to represent the hay.
     *
     * @return The current Color object.
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Returns the shape type of this hay unit.
     * Hay is represented as a square.
     *
     * @return integer value 2 (square shape).
     */
    public int shape() {
        return Unit.SQUARE;
    }

    /**
     * Returns the X position (column) of the hay in the valley.
     *
     * @return integer value for X position.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y position (row) of the hay in the valley.
     *
     * @return integer value for Y position.
     */
    public int getY() {
        return y;
    }
}


