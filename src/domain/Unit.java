package domain;

import java.awt.Color;

/**
 * The Unit interface defines the basic behavior and properties
 * of all elements that can exist inside the valley, such as animals or resources.
 * Each unit knows how to act during a simulation step and how it should be drawn
 * within the graphical representation of the valley.
 *
 * This interface provides default behaviors for most methods so that
 * subclasses can override only what they need.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public interface Unit {

    // Constante que representa una unidad con forma cuadrada.
    public static final int SQUARE = 2;

    // Constante que representa una unidad con forma redonda.
    public static final int ROUND = 1;

    /**
     * Defines the specific behavior of the unit during one simulation step.
     * Each concrete class (like Wolf, Sheep, or Grass) must implement
     * this method to describe its own actions in the valley.
     */
    public void act();

    /**
     * Returns the shape type of the unit (square or round).
     * By default, all units are square unless overridden.
     *
     * @return an integer constant representing the shape type.
     */
    public default int shape() {
        return SQUARE;
    }

    /**
     * Returns the color used to represent this unit on the grid.
     * By default, all units are black.
     *
     * @return a Color object representing the unit's color.
     */
    public default Color getColor() {
        return Color.black;
    }

    /**
     * Indicates whether this unit is a resource (for example, grass or hay).
     * By default, all units are considered resources.
     *
     * @return true if the unit is a resource; false otherwise.
     */
    public default boolean isResource() {
        return true;
    }

    /**
     * Indicates whether this unit is an animal (for example, wolf or sheep).
     * By default, units are not animals unless overridden.
     *
     * @return true if the unit is an animal; false otherwise.
     */
    public default boolean isAnimal() {
        return false;
    }
}
