package domain;

import java.awt.Color;

/**
 * The WolfLotVol class represents a wolf following the Lotka-Volterra model.
 * It moves randomly, hunts sheep, and can reproduce with nearby wolves.
 *
 * @author Alejandra Beltran - Adrian Ducuara
 * @version 2025-2
 */
public class WolfLotVol extends Mammal {
    
    private Valley myValley;
    private boolean hasActedThisTurn; // Para controlar que los recién nacidos no actúen

    /**
     * Creates a new WolfLotVol in the specified valley and position.
     *
     * @param valley The valley where the wolf will be placed.
     * @param row Initial row position.
     * @param column Initial column position.
     */
    public WolfLotVol(Valley valley, int row, int column) {
        super(valley, row, column);
        this.myValley = valley;
        this.color = Color.BLACK;
        setEnergy(100);
        this.hasActedThisTurn = false;
    }

    /**
     * Creates a new WolfLotVol that won't act this turn (newborn).
     */
    public WolfLotVol(Valley valley, int row, int column, boolean skipFirstTurn) {
        this(valley, row, column);
        this.hasActedThisTurn = skipFirstTurn;
    }

    public int getShape() {
        return Unit.ROUND;
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

        // 1. Intentar cazar ovejas cercanas
        if (huntSheep()) {
            return; // Si cazó, ya actuó este turno
        }

        // 2. Intentar reproducirse
        tryReproduce();

        // 3. Moverse aleatoriamente
        moveRandomly();
    }

    /**
     * Hunts a nearby sheep if available.
     * Gains 90% of the sheep's energy.
     *
     * @return true if hunted successfully, false otherwise.
     */
    private boolean huntSheep() {
        int size = myValley.getSize();

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;

                int r = row + dr;
                int c = column + dc;

                if (r >= 0 && r < size && c >= 0 && c < size) {
                    Unit neighbor = myValley.getUnit(r, c);

                    // Caza de oveja
                    if (neighbor instanceof SheepLotVol) {
                        Animal prey = (Animal) neighbor;
                        
                        // Gana 90% de la energía de la oveja
                        int energyGained = (prey.getEnergy() * 90) / 100;
                        int newEnergy = getEnergy() + energyGained;
                        if (newEnergy > 100) newEnergy = 100;
                        setEnergy(newEnergy);

                        // La oveja muere
                        if (neighbor instanceof SheepLotVol) {
                            ((SheepLotVol) neighbor).die();
                        } 
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Attempts to reproduce with a nearby wolf if there's an empty adjacent cell.
     */
    private void tryReproduce() {
        int size = myValley.getSize();
        WolfLotVol partner = null;
        int partnerRow = -1, partnerCol = -1;

        // Busca un lobo LotVol vecino
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;

                int r = row + dr;
                int c = column + dc;

                if (r >= 0 && r < size && c >= 0 && c < size) {
                    Unit neighbor = myValley.getUnit(r, c);

                    if (neighbor instanceof WolfLotVol && neighbor != this) {
                        partner = (WolfLotVol) neighbor;
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

                    // Verifica que esté vacío y sea adyacente a ambos lobos
                    if (myValley.isEmpty(r, c)) {
                        // Verifica que también esté cerca del partner
                        if (Math.abs(r - partnerRow) <= 1 && Math.abs(c - partnerCol) <= 1) {
                            // Crea nuevo lobo que no actuará este turno
                            new WolfLotVol(myValley, r, c, true);
                            return;
                        }
                    }
                }
            }
        }
    }

    /**
     * Moves the wolf randomly to an adjacent cell.
     */
    private void moveRandomly() {
        // Intenta moverse aleatoriamente
        int attempts = 0;
        while (attempts < 5) {
            int newRow = row + (int)(Math.random() * 3) - 1;
            int newCol = column + (int)(Math.random() * 3) - 1;

            if (move(newRow, newCol)) {
                return; // Movimiento exitoso
            }
            attempts++;
        }
    }
}