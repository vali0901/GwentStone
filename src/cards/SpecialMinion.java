package cards;

import fileio.CardInput;

public abstract class SpecialMinion extends Minion {
    /**
     * Using ability of this special minion on other given minion
     * @param minion The given minion
     * @return 'true' if attacked minion's health goes below 1
     */
    public abstract boolean ability(Minion minion);

    /**
     *
     * @return 'true' if this Environment card is of type Disciple, 'false' otherwise
     */
    public boolean isDisciple() {
        return this.getName().equals("Disciple");
    }
}

class Miraj extends SpecialMinion {

    Miraj(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());

        this.setFirstRow(true);
    }

    @Override
    public boolean ability(final Minion minion) {
        this.setHasAttacked(true);

        int aux = this.getHealth();
        this.setHealth(minion.getHealth());
        minion.setHealth(aux);

        return false;
    }
}

class TheRipper extends SpecialMinion {

    TheRipper(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());

        this.setFirstRow(true);
    }

    @Override
    public boolean ability(final Minion minion) {
        this.setHasAttacked(true);

        minion.setAttackDamage(minion.getAttackDamage() - 2);
        if (minion.getAttackDamage() < 0) {
            minion.setAttackDamage(0);
        }

        return false;
    }
}

class Disciple extends SpecialMinion {
    Disciple(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public boolean ability(final Minion minion) {
        this.setHasAttacked(true);
        minion.setHealth(minion.getHealth() + 2);

        return false;
    }
}

class TheCursedOne extends SpecialMinion {

    TheCursedOne(final CardInput cardInput) {
        this.setMana(cardInput.getMana());
        this.setHealth(cardInput.getHealth());
        this.setAttackDamage(cardInput.getAttackDamage());
        this.setDescription(cardInput.getDescription());
        this.setColors(cardInput.getColors());
        this.setName(cardInput.getName());
    }

    @Override
    public boolean ability(final Minion minion) {
        this.setHasAttacked(true);

        int aux = minion.getHealth();
        minion.setHealth(minion.getAttackDamage());
        minion.setAttackDamage(aux);

        return minion.getHealth() <= 0;
    }
}
