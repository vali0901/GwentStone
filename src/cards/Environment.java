package cards;


import fileio.CardInput;
import gameEnvironment.Row;

import java.util.ArrayList;

public abstract class Environment implements Card {

    @Override
    public int getType() {
        return 2;
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

    public boolean isHeartHound() {
        return this instanceof HeartHound;
    }
    public abstract void doEffect(Row enemyMinions, Row friendlyMinions);
}

class Firestorm extends  Environment {

    public Firestorm(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public void doEffect(Row enemyMinions, Row friendlyMinions) {
        for(Minion minion : enemyMinions.getMinions())
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
    public void doEffect(Row enemyMinions, Row friendlyMinions) {
        for(Minion minion : enemyMinions.getMinions())
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
    public void doEffect(Row enemyMinions, Row friendlyMinions) {
        Minion theStolenOne = enemyMinions.getMinion(0);
        int index = 0;
        for(int i = 1; i < 5; i++)
            if(enemyMinions.getMinion(i) != null && enemyMinions.getMinion(i).getHealth() > theStolenOne.getHealth()) {
                theStolenOne = enemyMinions.getMinion(i);
                index = i;
            }
        enemyMinions.removeMinion(index);
        friendlyMinions.addMinion(theStolenOne);
    }
}
