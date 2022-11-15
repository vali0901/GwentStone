package cards;

import fileio.CardInput;

public class CardBuilder {

    static public Card createCard(CardInput cardInput) {

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
