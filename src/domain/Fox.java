package domain;

import java.awt.Color;

/**
 * The Fox class represents a fox that lives in the valley.
 * It extends the Mammal class and defines specific behavior for foxes:
 * - Moves horizontally (east to west).
 * - Hunts nearby sheep to gain energy.
 * - Dies when its energy reaches zero.
 *
 * This class introduces a new species to the simulation,
 * following the same behavioral structure as wolves and sheep.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class Fox extends Mammal {
    
    // Referencia al valle donde habita el zorro
    private Valley myValley;
    
    // Dirección del movimiento: true = este, false = oeste
    private boolean goingEast;

    /**
     * Creates a new fox in the specified valley and position.
     * The fox is represented in orange color with 60 initial energy.
     *
     * @param valley The valley where the fox will be placed.
     * @param row Initial row position.
     * @param column Initial column position.
     */
    public Fox(Valley valley, int row, int column) {
        super(valley, row, column);
        this.myValley = valley;
        this.color = Color.ORANGE;
        setEnergy(60);  // Establece energía inicial en 60
        this.goingEast = true;  // Comienza moviéndose hacia el este
    }

    /**
     * Returns the shape type of this fox.
     * Foxes are represented as circles.
     *
     * @return integer value representing a round shape.
     */
    public int getShape() {
        return Unit.ROUND;
    }

    /**
     * Defines the behavior of the fox for one simulation step.
     * The fox moves horizontally (east-west) and hunts nearby sheep.
     */
    @Override
    public void act() {
        // Si no tiene energía, muere
        if (getEnergy() <= 0) {
            die();
            return;
        }

        // Busca y caza ovejas cercanas para ganar energía
        huntNearby();

        // Se mueve horizontalmente según su dirección actual
        moveHorizontally();
    }

    /**
     * Moves the fox horizontally across the valley.
     * Changes direction when reaching the edges.
     */
    private void moveHorizontally() {
        // Si llega al borde izquierdo y va al oeste, cambia de dirección
        if (column == 0 && !goingEast) {
            goingEast = true;  // Cambia hacia el este
            return;
        }
        
        // Si llega al borde derecho y va al este, cambia de dirección
        if (column == myValley.getSize() - 1 && goingEast) {
            goingEast = false;  // Cambia hacia el oeste
            return;
        }

        // Calcula nueva columna según la dirección actual
        int newCol = goingEast ? column + 1 : column - 1;

        // Intenta moverse si la posición está dentro del valle
        if (newCol >= 0 && newCol < myValley.getSize()) {
            move(row, newCol);
        }
    }

    /**
     * Checks neighboring cells for sheep to hunt.
     * If a sheep is found, the fox eats it and gains 50% of the sheep's energy.
     */
    private void huntNearby() {
        int size = myValley.getSize();

        // Recorre todas las celdas vecinas al zorro
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue; // Ignora su propia celda

                int r = row + dr;
                int c = column + dc;

                // Verifica que la posición esté dentro de los límites del valle
                if (r >= 0 && r < size && c >= 0 && c < size) {
                    Unit neighbor = myValley.getUnit(r, c);

                    // Si encuentra una oveja, la caza
                    if (neighbor instanceof Sheep) {
                        Sheep prey = (Sheep) neighbor;

                        // Gana la mitad de la energía de la oveja
                        int energyGained = prey.getEnergy() / 2;
                        int newEnergy = getEnergy() + energyGained;
                        if (newEnergy > 100) newEnergy = 100; // No supera 100

                        setEnergy(newEnergy);  // Actualiza la energía del zorro
                        prey.die();  // Elimina a la oveja cazada
                        return;  // Termina tras cazar una oveja
                    }
                }
            }
        }
    }
}
