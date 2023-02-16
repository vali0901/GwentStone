package cards;

import fileio.CardInput;

import java.util.ArrayList;

public abstract class Minion implements Card {

    /**
     *
     * @return Always 1 (Minion type)
     */
    @Override
    public int getType() {
        return 1;
    }

    private int mana;
    private int health;
    private int attackDamage;
    private String description;
    private ArrayList<String> colors;
    private String name;

    private boolean isFrozen = false;
    private boolean isTank = false;
    private boolean hasAttacked = false;
    private boolean firstRow = false;

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
     * @return AttackDamage
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     *
     * @param attackDamage AttackDamage
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
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
     * @return 'true' if card is frozen, 'false' otherwise
     */
    public boolean getIsFrozen() {
        return isFrozen;
    }

    /**
     *
     * @param frozen true / false
     */
    public void setIsFrozen(final boolean frozen) {
        isFrozen = frozen;
    }

    /**
     *
     * @return 'true' if card is a 'Tank', 'false' otherwise
     */
    public boolean getIsTank() {
        return isTank;
    }

    /**
     *
     * @param tank true / false
     */
    public void setIsTank(final boolean tank) {
        isTank = tank;
    }

    /**
     *
     * @return 'true' if card has attacked, 'false' otherwise
     */
    public boolean getHasAttacked() {
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
     * @return 'true' if this card can be placed only on the first row, 'false' otherwise
     */
    public boolean getFirstRow() {
        return firstRow;
    }

    /**
     *
     * @param firstRow true / false
     */
    public void setFirstRow(final boolean firstRow) {
        this.firstRow = firstRow;
    }

    /**
     * Using the minion's attack on other minion
     * @param minion Attacked minion
     * @return 'true' if the attacked minion is dead, 'false' otherwise
     */
    public boolean attack(final Minion minion) {
        this.hasAttacked = true;
        return minion.isAttacked(this.attackDamage);
    };

    /**
     * Using the minion's attack on the hero
     * @param hero Attacked hero
     * @return 'true' if the hero is dead, 'false' otherwise
     */
    public boolean attack(final Hero hero) {
        this.hasAttacked = true;
        return hero.isAttacked(this.attackDamage);
    };

    /**
     * Minion getting attacked, his health might go below 0
     * @param damage The damage that the minion gets
     * @return 'true' if the minion is dead, 'false' otherwise
     */
    public boolean isAttacked(final int damage) {
        this.health -= damage;
        return health <= 0;
    }
}

class Sentinel extends Minion {
    Sentinel(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());

        this.setHasAttacked(false);
        this.setIsFrozen(false);
    }
}

class Berserker extends Minion {
    Berserker(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());

        this.setHasAttacked(false);
        this.setIsFrozen(false);
    }
}

class Goliath extends Minion {

    Goliath(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());

        this.setHasAttacked(false);
        this.setIsFrozen(false);

        this.setFirstRow(true);
        this.setIsTank(true);
    }
}

class Warden extends Minion {

    Warden(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());

        this.setHasAttacked(false);
        this.setIsFrozen(false);

        this.setFirstRow(true);
        this.setIsTank(true);
    }
}







