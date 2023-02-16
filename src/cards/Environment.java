package cards;


import fileio.CardInput;
import game.MagicNumbers;
import game.Row;

import java.util.ArrayList;

public abstract class Environment implements Card {

    /**
     *
     * @return Always 2 (Environment type)
     */
    @Override
    public int getType() {
        return 2;
    }

    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;

    /**
     *
     * @return Mana
     */
    public int getMana() {
        return mana;
    }

    /**
     *
     * @param mana Mana
     */
    public void setMana(final int mana) {
        this.mana = mana;
    }

    /**
     *
     * @return Description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description Description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     *
     * @return Colors
     */
    public ArrayList<String> getColors() {
        return colors;
    }

    /**
     *
     * @param colors Colors
     */
    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    /**
     *
     * @return Name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name Name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     *
     * @return 'true' if the environment card is of type 'Heart Hound', 'false' otherwise
     */
    public boolean isHeartHound() {
        return this.name.equals("Heart Hound");
    }

    /**
     * Using environment card's ability on a given enemy / friendly row depending on its type
     * @param enemyMinions The enemy row
     * @param friendlyMinions The friendly row
     */
    public abstract void doEffect(Row enemyMinions, Row friendlyMinions);
}

class Firestorm extends  Environment {

    Firestorm(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void doEffect(final Row enemyMinions, final Row friendlyMinions) {
        for (Minion minion : enemyMinions.getMinions()) {
            if (minion != null) {
                minion.isAttacked(1);
            }
        }
    }
}

class Winterfell extends  Environment {

    Winterfell(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void doEffect(final Row enemyMinions, final Row friendlyMinions) {
        for (Minion minion : enemyMinions.getMinions()) {
            if (minion != null) {
                minion.setIsFrozen(true);
            }
        }
    }
}

class HeartHound extends  Environment {

    HeartHound(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void doEffect(final Row enemyMinions, final Row friendlyMinions) {
        Minion theStolenOne = enemyMinions.getMinion(0);
        int index = 0;
        for (int i = 1; i < MagicNumbers.MAX_ROW_LENGTH; i++) {
            if (enemyMinions.getMinion(i) != null
                    && enemyMinions.getMinion(i).getHealth() > theStolenOne.getHealth()) {
                theStolenOne = enemyMinions.getMinion(i);
                index = i;
            }
        }
        enemyMinions.removeMinion(index);
        friendlyMinions.addMinion(theStolenOne);
    }
}
