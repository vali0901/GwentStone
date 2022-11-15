package cards;

import fileio.CardInput;

public abstract class SpecialMinion extends Minion {
    public abstract boolean ability(Minion minion);

    public boolean isDisciple(){
        return this instanceof Disciple;
    }
}

class Miraj extends SpecialMinion {

    public Miraj(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());

        this.setFirstRow(true);
    }

    @Override
    public boolean ability(Minion minion) {
        this.setHasAttacked(true);

        int aux = this.getHealth();
        this.setHealth(minion.getHealth());
        minion.setHealth(aux);

        return false;
    }
}

class TheRipper extends SpecialMinion {

    public TheRipper(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());

        this.setFirstRow(true);
    }

    @Override
    public boolean ability(Minion minion) {
        this.setHasAttacked(true);

        minion.setAttackDamage(minion.getAttackDamage() - 2);
        if(minion.getAttackDamage() < 0)
            minion.setAttackDamage(0);
        return false;
        //return  minion.isAttacked(2);
    }
}

class Disciple extends SpecialMinion {
    public Disciple(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public boolean ability(Minion minion) {
        this.setHasAttacked(true);
        minion.setHealth(minion.getHealth() + 2);

        return false;
    }
}

class TheCursedOne extends SpecialMinion {

    public TheCursedOne(CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public boolean ability(Minion minion) {
        this.setHasAttacked(true);

        int aux = minion.getHealth();
        minion.setHealth(minion.getAttackDamage());
        minion.setAttackDamage(aux);
        if(minion.getHealth() <= 0)
            return true;
        return false;
    }
}
