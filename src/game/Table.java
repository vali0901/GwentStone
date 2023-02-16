package game;

import cards.Minion;
import cards.HeroBuilder;

import fileio.Input;
import fileio.StartGameInput;
import fileio.Coordinates;

public final class Table {
    private static int nrGamesPlayed = 0;
    private static final Table TABLE = new Table();
    private final Player playerOne = new Player(1);
    private final Player playerTwo = new Player(2);
    private int currPlayer;

    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public static int getNrGamesPlayed() {
        return nrGamesPlayed;
    }

    public static void setNrGamesPlayed(final int nrGamesPlayed) {
        Table.nrGamesPlayed = nrGamesPlayed;
    }

    /**
     * Starting a new game and initializing each player with the new values for decks and heroes
     * @param input Info about the decks from which the deck of each player will be chosen
     * @param startGameInput Info needed for the start of each game(containing the hero, shuffle
     *                       seed and the chosen deck index)
     */
    public void startGame(final Input input, final StartGameInput startGameInput) {
        Table.nrGamesPlayed++;

        playerOne.initPlayer(
                input.getPlayerOneDecks().getDecks().get(startGameInput.getPlayerOneDeckIdx()),
                startGameInput.getShuffleSeed(),
                HeroBuilder.createHero(startGameInput.getPlayerOneHero()));
        playerTwo.initPlayer(
                input.getPlayerTwoDecks().getDecks().get(startGameInput.getPlayerTwoDeckIdx()),
                startGameInput.getShuffleSeed(),
                HeroBuilder.createHero(startGameInput.getPlayerTwoHero()));

        currPlayer = startGameInput.getStartingPlayer();
    }

    /**
     * Starting a new round; each player gets a card from the deck and mana
     * @param nrRounds The number of this current round (limited to 10)
     */
    public void newRound(final int nrRounds) {
        playerOne.addMana(nrRounds);
        playerTwo.addMana(nrRounds);

        playerOne.dealCard();
        playerTwo.dealCard();
    }

    /**
     *
     * @return The current player
     */
    public Player getCurrPlayer() {
        if (currPlayer == 1) {
            return playerOne;
        }

        return playerTwo;
    }

    /**
     *
     * @return The enemy player
     */
    public Player getEnemyPlayer() {
        if (currPlayer == 1) {
            return playerTwo;
        }

        return playerOne;
    }

    /**
     *
     * @param id The player id: '1' for playerOne, '2' for playerTwo
     * @return The player with the given id
     */
    public Player getPlayerById(final int id) {
        if (id == 1) {
            return playerOne;
        }

        return playerTwo;
    }

    /**
     * Changes the current player (used at the end of a turn)
     */
    public void changeCurrPlayer() {
        if (currPlayer == 1) {
            currPlayer = 2;
            return;
        }

        currPlayer = 1;
    }

    /**
     *
     * @param coords The position coordinates of the attacking card on the table
     * @return The attacking card of the current player
     */
    public Minion getCardAttacker(final Coordinates coords) {
        if (coords.getX() == MagicNumbers.MIN_ROW_INDEX
                || coords.getX() == MagicNumbers.MAX_ROW_INDEX) {
            return getCurrPlayer().getSecondRow().getMinion(coords.getY());
        }

        return getCurrPlayer().getFirstRow().getMinion(coords.getY());
    }

    /**
     *
     * @param coords The position coordinates of the attacked card on the table
     * @return The attacked card of the enemy player
     */
    public Minion getCardAttacked(final Coordinates coords) {
        if (coords.getX() == MagicNumbers.MIN_ROW_INDEX
                || coords.getX() == MagicNumbers.MAX_ROW_INDEX) {
            return getEnemyPlayer().getSecondRow().getMinion(coords.getY());
        }

        return getEnemyPlayer().getFirstRow().getMinion(coords.getY());
    }

    /**
     *
     * @param x Index of the row on table
     * @param y Index of the column on table
     * @return The minion at the given table coordinates
     */
    public Minion getCardAtPosition(final int x, final int y) {
        if (x == MagicNumbers.ROW_0_INDEX) {
            return playerTwo.getSecondRow().getMinion(y);
        }

        if (x == MagicNumbers.ROW_1_INDEX) {
            return playerTwo.getFirstRow().getMinion(y);
        }

        if (x == MagicNumbers.ROW_2_INDEX) {
            return playerOne.getFirstRow().getMinion(y);
        }

        if (x == MagicNumbers.ROW_3_INDEX) {
            return playerOne.getSecondRow().getMinion(y);
        }

        return null;
    }

    private Table() { }

    public static Table getTable() {
        return TABLE;
    }
}





