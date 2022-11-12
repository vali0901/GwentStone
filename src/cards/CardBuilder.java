package cards;

import fileio.CardInput;

public class CardBuilder {

    static public Card createCard(CardInput cardInput) {

        switch (cardInput.getName()) {
            case "Sentinel":
                return new Sentinel(cardInput);
            case "Berserker":
                return new Berserker(cardInput);
            case "Goliath":
                return new Goliath(cardInput);
            case "Warden":
                return new Warden(cardInput);
            case "The Ripper":
                return new TheRipper(cardInput);
            case "Miraj":
                return new Miraj(cardInput);
            case "The Cursed One":
                return new TheCursedOne(cardInput);
            case "Disciple":
                return new Disciple(cardInput);
            case "Firestorm":
                return new Firestorm(cardInput);
            case "Winterfell":
                return new Winterfell(cardInput);
            case "Heart Hound":
                return new HeartHound(cardInput);
            default:
                break;
        }
        return null;
    }
}
