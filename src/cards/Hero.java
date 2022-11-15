package cards;

import fileio.CardInput;
import gameEnvironment.Row;

import java.util.ArrayList;

public abstract class Hero {
    private int health = 30;
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private boolean hasAttacked;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
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

    public boolean hasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public boolean isAttacked(int damage) {
        this.health -= damage;
        if (health <= 0) {
            return true;
        }
        return false;
    }

    public boolean isFriendly() {
        return this instanceof GeneralKocioraw || this instanceof KingMudface;
    }

    public abstract void ability(Row row);
}

class LordRoyce extends Hero {

    public LordRoyce(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void ability(Row row) {
        setHasAttacked(true);
        Minion theFrozenOne = row.getMinion(0);
        for(int i = 1; i < 5; i++)
            if(row.getMinions()[i] != null && row.getMinions()[i].getAttackDamage() > theFrozenOne.getAttackDamage())
                theFrozenOne = row.getMinions()[i];
        theFrozenOne.setIsFrozen(true);
    }
}

class EmpressThorina extends Hero {

    public EmpressThorina(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void ability(Row row) {
        setHasAttacked(true);
        Minion theDestroyedOne = row.getMinion(0);

        int index = 0;
        for(int i = 1; i < 5; i++)
            if(row.getMinion(i) != null && row.getMinion(i).getHealth() > theDestroyedOne.getHealth()) {
                theDestroyedOne = row.getMinion(i);
                index  = i;
            }

        if(theDestroyedOne != null)
            row.removeMinion(index);
    }
}

class KingMudface extends Hero {

    public KingMudface(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void ability(Row row) {
        setHasAttacked(true);
        for(Minion minion : row.getMinions())
            if(minion != null)
                minion.setHealth(minion.getHealth() + 1);
    }
}

class GeneralKocioraw extends Hero {

    public GeneralKocioraw(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void ability(Row row) {
        setHasAttacked(true);
        for(Minion minion : row.getMinions())
            if(minion != null)
                minion.setAttackDamage(minion.getAttackDamage() + 1);
    }
}
