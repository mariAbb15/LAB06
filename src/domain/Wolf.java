
package domain;

import java.awt.Color;

/**
 * The Wolf class represents a wolf that lives in the valley.
 * It extends the Mammal class and defines specific behavior for wolves:
 * - Moves randomly around the valley.
 * - Dies when its energy reaches zero.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class Wolf extends Mammal {
    /**
     * Creates a new wolf in the specified valley and position.
     * The wolf is represented in black color.
     *
     * @param valley The valley where the wolf will be placed.
     * @param row Initial row position.
     * @param column Initial column position.
     */
    public Wolf(Valley valley, int row, int column) {
        super(valley, row, column);
        color = Color.black;
    }

    /**
     * Returns the shape type of this wolf.
     * Wolves are represented as circles.
     *
     * @return integer value representing a round shape.
     */
    public int getShape() {
        return 1;
    }

    /**
     * Defines the behavior of the wolf for one simulation step.
     * The wolf moves randomly around its current position.
     * If its energy reaches zero, it dies and disappears from the valley.
     */
    public void act() {
        // Si no tiene energía, muere
        if (getEnergy() == 0) {
            die();
        } else {
            // Intenta moverse a una posición aleatoria cercana
            if (!move(row + (int)(Math.random() * 3) - 1,
                      column + (int)(Math.random() * 3) - 1)) {

                // Si no se pudo mover, intenta nuevamente otra posición aleatoria
                move(row + (int)(Math.random() * 3) - 1,
                     column + (int)(Math.random() * 3) - 1);
            }
        }
    }
}

