package cards;

import fileio.CardInput;

import java.util.ArrayList;

public abstract class Minion implements Card {

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

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsFrozen() {
        return isFrozen;
    }

    public void setIsFrozen(boolean frozen) {
        isFrozen = frozen;
    }

    public boolean getIsTank() {
        return isTank;
    }

    public void setIsTank(boolean tank) {
        isTank = tank;
    }

    public boolean getHasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public boolean getFirstRow() {
        return firstRow;
    }

    public void setFirstRow(boolean firstRow) {
        this.firstRow = firstRow;
    }

    public boolean attack(Minion minion) {
        this.hasAttacked = true;
        return minion.isAttacked(this.attackDamage);
    };

    public boolean attack(Hero hero) {
        this.hasAttacked = true;
        return hero.isAttacked(this.attackDamage);
    };

    public boolean isAttacked(int damage) {
        this.health -= damage;
        return health <= 0;
    }

    @Override
    public String toString() {
        return "{"
                +  "mana="
                + mana
                +  ", attackDamage="
                + attackDamage
                + ", health="
                + health
                +  ", description='"
                + description
                + '\''
                + ", colors="
                + colors
                + ", name='"
                +  ""
                + name
                + '\''
                + '}';
    }

}

class Sentinel extends Minion {
    public Sentinel(CardInput cardInput) {
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
    public Berserker(CardInput cardInput) {
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

    public Goliath(CardInput cardInput) {
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

    public Warden(CardInput cardInput) {
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







