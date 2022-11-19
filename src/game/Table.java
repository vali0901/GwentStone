package gameEnvironment;

import cards.*;
import fileio.*;

import java.util.ArrayList;

public class Table {
    public static int nrGamesPlayed = 0;
    private static final Table table = new Table();
    final Player playerOne = new Player(1);
    final Player playerTwo = new Player(2);
    int currPlayer;
    public void startGame(Input input, StartGameInput startGameInput) {
        Table.nrGamesPlayed++;

        playerOne.initPlayer(input.getPlayerOneDecks().getDecks().get(startGameInput.getPlayerOneDeckIdx()), startGameInput.getShuffleSeed(), HeroBuilder.createHero(startGameInput.getPlayerOneHero()));
        playerTwo.initPlayer(input.getPlayerTwoDecks().getDecks().get(startGameInput.getPlayerTwoDeckIdx()), startGameInput.getShuffleSeed(), HeroBuilder.createHero(startGameInput.getPlayerTwoHero()));

        currPlayer = startGameInput.getStartingPlayer();
    }

    public void newRound(int nrRounds) {
        playerOne.addMana(nrRounds);
        playerTwo.addMana(nrRounds);

        playerOne.dealCard();
        playerTwo.dealCard();
    }

    public Player getCurrPlayer() {
        if(currPlayer == 1)
            return playerOne;
        return playerTwo;
    }

    public Player getEnemyPlayer() {
        if(currPlayer == 1)
            return playerTwo;
        return playerOne;
    }

    public Player getPlayerById(int id) {
        if(id == 1)
            return playerOne;
        return playerTwo;
    }

    public void changeCurrPlayer() {
        if(currPlayer == 1) {
            currPlayer = 2;
            return;
        }
        currPlayer = 1;
    }

    public Minion getCardAttacker(Coordinates coords) {
        if(coords.getX() == 0 || coords.getX() == 3)
            return getCurrPlayer().getSecondRow().getMinion(coords.getY());
        return getCurrPlayer().getFirstRow().getMinion(coords.getY());
    }

    public Minion getCardAttacked(Coordinates coords) {
        if(coords.getX() == 0 || coords.getX() == 3)
            return getEnemyPlayer().getSecondRow().getMinion(coords.getY());
        return getEnemyPlayer().getFirstRow().getMinion(coords.getY());
    }

    public Minion getCardAtPosition(int x, int y) {
        if(x == 0)
            return playerTwo.getSecondRow().getMinion(y);
        if(x == 1)
            return playerTwo.getFirstRow().getMinion(y);
        if(x == 2)
            return playerOne.getFirstRow().getMinion(y);
        if(x == 3)
            return playerOne.getSecondRow().getMinion(y);
        return null;
    }

    private Table() {}

    static public Table getTable() {
        return table;
    }
}





