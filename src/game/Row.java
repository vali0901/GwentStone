package game;

import cards.Minion;

public final class Row {
    private Minion[] minions;
    private int size = 0;

    public Row() {
        minions = new Minion[MagicNumbers.MAX_ROW_LENGTH];
    }

    public Minion[] getMinions() {
        return minions;
    }

    /**
     * Adding a minion at the end of this row; the caller must be sure that the row isn't full
     * @param minion The minion that will be added at the end of this row
     */
    public void addMinion(final Minion minion) {
        int i;
        for (i = 0; i < MagicNumbers.MAX_ROW_LENGTH; i++) {
            if (minions[i] == null) {
                minions[i] = minion;
                size++;
                break;
            }
        }
    }

    /**
     * Removing the minion at a given index from the row, making sure all the remaining minions
     * are next to each other (no null values between them)
     * @param index The position of the card in the row
     */
    public void removeMinion(final int index) {
        minions[index] = null;
        size--;

        for (int i = index; i < MagicNumbers.MAX_ROW_LENGTH - 1; i++) {
            if (minions[i + 1] == null) {
                break;
            }

            Minion aux = minions[i];
            minions[i] = minions[i + 1];
            minions[i + 1] = aux;
        }
    }

    /**
     * Removes all minions with health <= 0
     */
    public void removeDeadMinions() {
        for (int i = 0; i < size; i++) {
            if (minions[i].getHealth() <= 0) {
                removeMinion(i);
                i--;
            }
        }
    }

    /**
     *
     * @param index The position of the minion in the row
     * @return The minion at the 'index' position
     */
    public Minion getMinion(final int index) {
        return minions[index];
    }

    /**
     *
     * @return 'true' if the row has a 'Tank' and 'false' otherwise
     */
    public boolean hasTank() {
        for (int i = 0; i < MagicNumbers.MAX_ROW_LENGTH; i++) {
            if (minions[i] != null && minions[i].getIsTank()) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @return The number of non-null minions in this row
     */
    public int getSize() {
        return size;
    }
}
