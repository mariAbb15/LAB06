package domain;

import java.awt.Color;

/**
 * The SheepLotVol class represents a sheep following the Lotka-Volterra model.
 * It moves randomly, eats HAY ONLY (not grass), and can reproduce with nearby sheep.
 * Dies ONLY when near a WolfLotVol (not regular Wolf).
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class SheepLotVol extends Mammal {
    
    private Valley myValley;
    private boolean hasActedThisTurn;

    /**
     * Creates a new SheepLotVol in the specified valley and position.
     *
     * @param valley The valley where the sheep will be placed.
     * @param row Initial row position.
     * @param column Initial column position.
     */
    public SheepLotVol(Valley valley, int row, int column) {
        super(valley, row, column);
        this.myValley = valley;
        this.color = Color.LIGHT_GRAY;
        setEnergy(50);
        this.hasActedThisTurn = false;
    }

    /**
     * Creates a new SheepLotVol that won't act this turn (newborn).
     */
    public SheepLotVol(Valley valley, int row, int column, boolean skipFirstTurn) {
        this(valley, row, column);
        this.hasActedThisTurn = skipFirstTurn;
    }

    public int getShape() {
        return Unit.SQUARE;
    }

    @Override
    public void act() {
        // Los recién nacidos no actúan en su primer turno
        if (hasActedThisTurn) {
            hasActedThisTurn = false;
            return;
        }

        // Si no tiene energía, muere
        if (getEnergy() <= 0) {
            die();
            return;
        }

        // 1. Verificar si hay WolfLotVol cerca (peligro) - SOLO WolfLotVol
        if (checkForWolvesLotVol()) {
            die(); // Muere si hay un WolfLotVol cerca
            return;
        }

        // 2. Intentar comer HENO únicamente (NO pasto)
        eatNearbyHay();

        // 3. Intentar reproducirse
        tryReproduce();

        // 4. Moverse aleatoriamente
        moveRandomly();
    }

    /**
     * Checks if there are WolfLotVol nearby (ONLY WolfLotVol, not regular Wolf).
     *
     * @return true if a WolfLotVol is detected, false otherwise.
     */
    private boolean checkForWolvesLotVol() {
        int size = myValley.getSize();

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;

                int r = row + dr;
                int c = column + dc;

                if (r >= 0 && r < size && c >= 0 && c < size) {
                    Unit neighbor = myValley.getUnit(r, c);

                    // 🔥 SOLO muere si encuentra WolfLotVol, NO Wolf normal
                    if (neighbor instanceof WolfLotVol) {
                        return true; // WolfLotVol detectado
                    }
                }
            }
        }
        return false;
    }

    /**
     * Eats nearby HAY ONLY (not grass) to gain energy.
     * Gains 10% of current energy when eating hay.
     */
    private void eatNearbyHay() {
        int size = myValley.getSize();

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;

                int r = row + dr;
                int c = column + dc;

                if (r >= 0 && r < size && c >= 0 && c < size) {
                    Unit neighbor = myValley.getUnit(r, c);

                    // 🔥 SOLO come HENO, NO pasto
                    if (neighbor instanceof Hay) {
                        // Aumenta 10% de su energía actual
                        int energyGain = getEnergy() / 10;
                        if (energyGain < 1) energyGain = 1; // Mínimo 1 punto
                        
                        int newEnergy = getEnergy() + energyGain;
                        if (newEnergy > 100) newEnergy = 100;
                        setEnergy(newEnergy);
                        
                        return; // Solo come un heno por turno
                    }
                }
            }
        }
    }

    /**
     * Attempts to reproduce with a nearby SheepLotVol if there's an empty adjacent cell.
     */
    private void tryReproduce() {
        int size = myValley.getSize();
        SheepLotVol partner = null;
        int partnerRow = -1, partnerCol = -1;

        // Busca una oveja LotVol vecina
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;

                int r = row + dr;
                int c = column + dc;

                if (r >= 0 && r < size && c >= 0 && c < size) {
                    Unit neighbor = myValley.getUnit(r, c);

                    if (neighbor instanceof SheepLotVol && neighbor != this) {
                        partner = (SheepLotVol) neighbor;
                        partnerRow = r;
                        partnerCol = c;
                        break;
                    }
                }
            }
            if (partner != null) break;
        }

        // Si encontró pareja, busca espacio vacío entre ellos
        if (partner != null) {
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    if (dr == 0 && dc == 0) continue;

                    int r = row + dr;
                    int c = column + dc;

                    if (myValley.isEmpty(r, c)) {
                        // Verifica que también esté cerca del partner
                        if (Math.abs(r - partnerRow) <= 1 && Math.abs(c - partnerCol) <= 1) {
                            // Crea nueva oveja que no actuará este turno
                            new SheepLotVol(myValley, r, c, true);
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Moves the sheep randomly to an adjacent cell.
     */
    private void moveRandomly() {
        int attempts = 0;
        while (attempts < 5) {
            int newRow = row + (int)(Math.random() * 3) - 1;
            int newCol = column + (int)(Math.random() * 3) - 1;

            if (move(newRow, newCol)) {
                return;
            }
            attempts++;
        }
    }
}