package domain;

import java.awt.Color;
import java.io.Serializable;

/**
 * The Animal class represents the general characteristics and behavior
 * shared by all animals that exist in the valley.
 * It defines basic attributes such as energy and days, and provides
 * common methods like moving one step, eating, and checking if the
 * object is an animal.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public abstract class Animal implements Serializable{

    private static final long serialVersionUID = 1L;

    // Días que han pasado desde que el animal fue creado o comenzó la simulación.
    private int days;

    // Nivel actual de energía del animal (de 0 a 100).
    private int energy;

    /**
     * Creates a new Animal with full energy (100) and zero days.
     */
    public Animal() {
        energy = 100;
        days = 0;
    }

    /**
     * Simulates one step of the animal's life cycle.
     * Each step consumes one energy point if the animal has energy left.
     *
     * @return true if the animal could take the step; false if it had no energy.
     */
    public final boolean step() {
        boolean ok = false;

        // Si el animal tiene energía suficiente, avanza un paso
        if (energy >= 1) {
            energy -= 1; // Pierde energía al moverse
            ok = true;
        }

        return ok;
    }

    /**
     * Restores the animal's energy to the maximum level (100),
     * representing the act of eating.
     */
    protected void eat() {
        energy = 100;
    }

    /**
     * Returns the current energy level of the animal.
     *
     * @return integer representing the energy value (0 to 100).
     */
    public final int getEnergy() {
        return energy;
    }
    
    public int setEnergy(int energy){
        this.energy = energy;
        return energy;
    }

    /**
     * Confirms that this object is an animal.
     *
     * @return always true, since all instances of this class are animals.
     */
    public final boolean isAnimal() {
        return true;
    }
}
