package domain;

import java.awt.Color;

/**
 * The Grass class represents grass in the valley, serving as a food source
 * for herbivores like sheep. Grass grows over time and regenerates after
 * being eaten.
 * 
 * This class does NOT inherit from Animal or Mammal - itac directly implements Unit,
 * representing a non-animal resource in the ecosystem.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class Grass implements Unit {

    private int x, y;
    private Valley valley;
    private Color color;
    private int growthLevel;      // 0 = eaten (brown), 1 = growing (light green), 2 = mature (dark green)
    private int timeSinceEaten;   // Counter for regeneration

    /**
     * Creates new grass at the specified position.
     * Grass starts in a mature state (fully grown).
     *
     * @param x X coordinate in the valley.
     * @param y Y coordinate in the valley.
     * @param valley Reference to the valley.
     */
    public Grass(int x, int y, Valley valley) {
        this.x = x;
        this.y = y;
        this.valley = valley;
        this.growthLevel = 2; // Starts mature
        this.timeSinceEaten = 0;
        this.color = new Color(34, 139, 34); // Dark green (mature)
        valley.setUnit(x, y, this);
    }

    /**
     * Simulates grass growth over time.
     * Grass regenerates through different stages after being eaten.
     */
    @Override
    public void act() {
        // If eaten (level 0), count time to regenerate to growing stage
        if (growthLevel == 0) {
            timeSinceEaten++;
            if (timeSinceEaten >= 10) {
                growthLevel = 1;
                timeSinceEaten = 0;
                updateColor();
            }
        }
        // If growing (level 1), mature gradually
        else if (growthLevel == 1) {
            timeSinceEaten++;
            if (timeSinceEaten >= 3) {
                growthLevel = 2;
                timeSinceEaten = 0;
                updateColor();
            }
        }
        // If mature (level 2), stays mature
    }

    /**
     * Called when a sheep eats this grass.
     * The grass is consumed and begins regeneration cycle.
     */
    public void beEaten() {
        if (growthLevel > 0) {
            growthLevel = 0;
            timeSinceEaten = 0;
            updateColor();
        }
    }

    /**
     * Updates grass color based on its growth level.
     * - Level 0 (eaten): Brown
     * - Level 1 (growing): Light green
     * - Level 2 (mature): Dark green
     */
    private void updateColor() {
        switch (growthLevel) {
            case 0:
                color = new Color(160, 82, 45); // Saddle brown (eaten)
                break;
            case 1:
                color = new Color(144, 238, 144); // Light green (growing)
                break;
            case 2:
                color = new Color(34, 139, 34); // Forest green (mature)
                break;
        }
    }

    /**
     * Returns true if grass is mature and can be eaten by sheep.
     *
     * @return true if growth level is 2, false otherwise.
     */
    public boolean isMature() {
        return growthLevel == 2;
    }

    /**
     * Returns the current color of the grass.
     *
     * @return Color object representing the grass shade.
     */
    @Override
    public Color getColor() {
        return color;
    }

    /**
     * Returns the shape used to draw this grass.
     *
     * @return SQUARE constant from Unit interface.
     */
    @Override
    public int shape() {
        return Unit.SQUARE;
    }

    /**
     * Indicates that grass is a resource in the ecosystem.
     *
     * @return true, since grass is a resource.
     */
    @Override
    public boolean isResource() {
        return true;
    }

    /**
     * Indicates that grass is NOT an animal.
     *
     * @return false, since grass is not an animal.
     */
    @Override
    public boolean isAnimal() {
        return false;
    }

    /**
     * Returns the X coordinate of this grass.
     *
     * @return X position in the valley.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate of this grass.
     *
     * @return Y position in the valley.
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the current growth level of the grass.
     *
     * @return 0 (eaten), 1 (growing), or 2 (mature).
     */
    public int getGrowthLevel() {
        return growthLevel;
    }
}