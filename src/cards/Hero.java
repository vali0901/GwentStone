package cards;

import fileio.CardInput;
import game.MagicNumbers;
import game.Row;

import java.util.ArrayList;

public abstract class Hero {
    private int health = MagicNumbers.HERO_MAX_HEALTH;
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private boolean hasAttacked;

    /**
     *
     * @return Health
     */
    public int getHealth() {
        return health;
    }

    /**
     *
     * @param health Health
     */
    public void setHealth(final int health) {
        this.health = health;
    }

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
     * @return name
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
     * @return 'true' if hero has attacked, 'false' otherwise
     */
    public boolean hasAttacked() {
        return hasAttacked;
    }

    /**
     *
     * @param hasAttacked true / false
     */
    public void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     *
     * @param damage The damage given to the hero
     * @return 'true' if the hero's health goes below 1, 'false' otherwise
     */
    public boolean isAttacked(final int damage) {
        this.health -= damage;
        return health <= 0;
    }

    /**
     *
     * @return 'true' if this hero's ability affects friendly minions, 'false' otherwise
     */
    public boolean isFriendly() {
        return this.name.equals("General Kocioraw") || this.name.equals("King Mudface");
    }

    /**
     * Using this hero's ability on the given row
     * @param row The given row
     */
    public abstract void ability(Row row);
}

class LordRoyce extends Hero {

    LordRoyce(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void ability(final Row row) {
        setHasAttacked(true);
        Minion theFrozenOne = row.getMinion(0);
        for (int i = 1; i < MagicNumbers.MAX_ROW_LENGTH; i++) {
            if (row.getMinions()[i] != null
                    && row.getMinions()[i].getAttackDamage() > theFrozenOne.getAttackDamage()) {
                theFrozenOne = row.getMinions()[i];
            }
        }
        theFrozenOne.setIsFrozen(true);
    }
}

class EmpressThorina extends Hero {

    EmpressThorina(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void ability(final Row row) {
        setHasAttacked(true);
        Minion theDestroyedOne = row.getMinion(0);

        int index = 0;
        for (int i = 1; i < MagicNumbers.MAX_ROW_LENGTH; i++) {
            if (row.getMinion(i) != null
                    && row.getMinion(i).getHealth() > theDestroyedOne.getHealth()) {
                theDestroyedOne = row.getMinion(i);
                index = i;
            }
        }

        if (theDestroyedOne != null) {
            row.removeMinion(index);
        }
    }
}

class KingMudface extends Hero {

    KingMudface(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void ability(final Row row) {
        setHasAttacked(true);
        for (Minion minion : row.getMinions()) {
            if (minion != null) {
                minion.setHealth(minion.getHealth() + 1);
            }
        }
    }
}

class GeneralKocioraw extends Hero {

    GeneralKocioraw(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void ability(final Row row) {
        setHasAttacked(true);
        for (Minion minion : row.getMinions()) {
            if (minion != null) {
                minion.setAttackDamage(minion.getAttackDamage() + 1);
            }
        }
    }
}
