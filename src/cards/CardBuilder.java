package cards;

import fileio.CardInput;

public final class CardBuilder {
    private CardBuilder() { }

    /**
     * Builds a new Card based on the given cardInput
     * @param cardInput Info needed for creating a new card (containing health, attackDamage, ...)
     * @return Created card
     */
    public static Card createCard(final CardInput cardInput) {

        return switch (cardInput.getName()) {
            case "Sentinel" -> new Sentinel(cardInput);
            case "Berserker" -> new Berserker(cardInput);
            case "Goliath" -> new Goliath(cardInput);
            case "Warden" -> new Warden(cardInput);
            case "The Ripper" -> new TheRipper(cardInput);
            case "Miraj" -> new Miraj(cardInput);
            case "The Cursed One" -> new TheCursedOne(cardInput);
            case "Disciple" -> new Disciple(cardInput);
            case "Firestorm" -> new Firestorm(cardInput);
            case "Winterfell" -> new Winterfell(cardInput);
            case "Heart Hound" -> new HeartHound(cardInput);
            default -> null;
        };
    }
}
