package cards;

import fileio.CardInput;

public final class HeroBuilder {
    private HeroBuilder() { }

    /**
     * Builds a new Hero based on the given cardInput
     * @param cardInput Info needed for creating a new hero (containing attackDamage, mana, ...)
     * @return Created hero
     */
    public static Hero createHero(final CardInput cardInput) {

        return switch (cardInput.getName()) {
            case "General Kocioraw" -> new GeneralKocioraw(cardInput);
            case "Lord Royce" -> new LordRoyce(cardInput);
            case "Empress Thorina" -> new EmpressThorina(cardInput);
            case "King Mudface" -> new KingMudface(cardInput);
            default -> null;
        };

    }
}
