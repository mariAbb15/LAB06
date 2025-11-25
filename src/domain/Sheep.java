package domain;

import java.awt.Color;

/**
 * The Sheep class represents a sheep that lives and moves inside the valley.
 * It extends the Mammal class and defines specific behavior for the sheep:
 * - Moves from north to south and back.
 * - Recovers energy when reaching the edge or when near another sheep.
 * - Dies when adjacent to a wolf.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class Sheep extends Mammal {

    // Referencia al valle al que pertenece la oveja.
    private Valley myValley;

    // Indica si la oveja se está moviendo hacia el norte (true) o hacia el sur (false).
    private boolean goingNorth;

    /**
     * Creates a new sheep inside the specified valley at the given position.
     * The sheep starts moving north with an initial energy value reduced
     * through several steps.
     *
     * @param valley The valley the sheep belongs to.
     * @param row Initial row position.
     * @param column Initial column position.
     */
    public Sheep(Valley valley, int row, int column) {
        super(valley, row, column);
        myValley = valley;
        color = Color.LIGHT_GRAY;
        setEnergy(5);
        goingNorth = true;
        
    }

    /**
     * Returns the shape type of this sheep.
     * Sheep are represented as squares.
     *
     * @return integer value representing a square shape.
     */
    public int getShape() {
        return Unit.SQUARE;
    }

    /**
     * Defines the behavior of the sheep for one simulation step.
     * The sheep moves north or south depending on its direction,
     * restores energy at the north edge, and dies if near a wolf.
     */
    public void act() {
    
        // Si no tiene energía, muere
        if (getEnergy() <= 0) {
            die();
            return;
        }
    
        // Si está en el borde norte: cambia dirección, recupera energía,
        // revisa vecinos y no se mueve este turno.
        if (row == 0 && goingNorth) {
            goingNorth = false;   // Cambia la dirección hacia el sur
            eat();                // Recupera energía a 100
            checkNeighbors();     // Revisa lobos u ovejas cercanas
            //return;
        }

        // Si está en el borde sur: cambia dirección hacia el norte
        if (row == myValley.getSize() - 1 && !goingNorth) {
            goingNorth = true;
            checkNeighbors();
            //return;
        }
    
        // Calcula la siguiente posición vertical según la dirección actual
        int newRow = goingNorth ? row - 1 : row + 1;
        int newCol = column;
        //System.out.println("Fila actual: " + row);
    
        // Verifica que la celda destino está dentro del valle
        if (newRow >= 0 && newRow < myValley.getSize()) {
            move(newRow, column); // Mueve un paso
            return;
        }
    
        // Revisa los vecinos después del movimiento
        checkNeighbors();
    }


    /**
     * Checks the neighboring cells around the sheep.
     * - If a wolf is found nearby, the sheep dies.
     * - If another sheep is nearby, it slightly restores energy.
     */
    private void checkNeighbors() {
        int size = myValley.getSize();
        
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                // Evita revisarse a sí misma
                if (!(dr == 0 && dc == 0)) {
                    int r = row + dr;
                    int c = column + dc;
                    
                    // Verifica límites del valle
                    if (r >= 0 && r < size && c >= 0 && c < size) {
                        Unit neighbor = myValley.getUnit(r, c);
                        
                        if (neighbor != null && neighbor.getClass() == Wolf.class) {
                            // Si hay un lobo cerca, muere
                            die();
                            return;
                        } else if (neighbor != null && neighbor.getClass() == Sheep.class) {
                            // Si hay otra oveja cerca, recupera 1 punto de energía
                            int newEnergy = getEnergy() + 1;
                            if (newEnergy > 100) newEnergy = 100;
                            
                            setEnergy(newEnergy);
                            return; // Solo gana energía de una oveja por turno
                        }
                    }
                }
            }
        }
    }
}
