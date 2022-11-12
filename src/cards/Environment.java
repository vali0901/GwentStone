package cards;


import fileio.CardInput;

import java.util.ArrayList;

public abstract class Environment implements Card {
    // DE SCHIMBAT ARGUMENTELE LA DOEFFECT
    private final int type = 0;

    @Override
    public int getType() {
        return type;
    }

    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;

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

    public abstract void doEffect(Minion[] enemyMinions, Minion[] friendlyMinions);
}

class Firestorm extends  Environment {

    public Firestorm(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void doEffect(Minion[] enemyMinions, Minion[] friendlyMinions) {
        for(Minion minion : enemyMinions)
            if(minion != null)
                minion.isAttacked(1);
    }
}

class Winterfell extends  Environment {

    public Winterfell(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void doEffect(Minion[] enemyMinions, Minion[] friendlyMinions) {
        for(Minion minion : enemyMinions)
            if(minion != null)
                minion.setIsFrozen(true);
    }
}

class HeartHound extends  Environment {

    public HeartHound(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void doEffect(Minion[] enemyMinions, Minion[] friendlyMinions) {
        Minion theStolenOne = enemyMinions[0];
        for(int i = 1; i < 5; i++)
            if(enemyMinions[i] != null && enemyMinions[i].getHealth() > theStolenOne.getHealth())
                theStolenOne = enemyMinions[i];
    }
}
