package cards;

import fileio.CardInput;

public class HeroBuilder {

    static public Hero createHero(CardInput cardInput) {

        switch (cardInput.getName()) {
            case "General Kocioraw":
                return new GeneralKocioraw(cardInput);
            case "Lord Royce":
                return new LordRoyce(cardInput);
            case "Empress Thorina":
                return new EmpressThorina(cardInput);
            case "King Mudface":
                return new KingMudface(cardInput);
            default:
                break;
        }
        return null;
    }
}
