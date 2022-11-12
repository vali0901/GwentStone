package cards;

import fileio.CardInput;

import java.util.ArrayList;

public abstract class Hero {
    private int health = 30;
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;


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

    public void isAttacked(int damage) {
        this.health -= damage;
        if (health <= 0) {
            //endgame
        }
    }

    public abstract void ability(Minion[] row);
}

class LordRoyce extends Hero {

    public LordRoyce(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void ability(Minion[] row) {
        Minion theFrozenOne = row[0];
        for(int i = 1; i < 5; i++)
            if(row[i].getAttackDamage() > theFrozenOne.getAttackDamage())
                theFrozenOne = row[i];
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
    public void ability(Minion[] row) {
        Minion theDestroyedOne = row[0];
        for(int i = 1; i < 5; i++)
            if(row[i].getHealth() > theDestroyedOne.getHealth())
                theDestroyedOne = row[i];
        // destroy theDDestryed one
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
    public void ability(Minion[] row) {
        for(Minion minion : row)
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
    public void ability(Minion[] row) {
        for(Minion minion : row)
            minion.setAttackDamage(minion.getAttackDamage() + 1);
    }
}
