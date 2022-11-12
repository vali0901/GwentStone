package gameEnvironment;

import cards.*;
import fileio.CardInput;
import fileio.GameInput;
import fileio.Input;
import fileio.StartGameInput;

import java.util.ArrayList;

public class Table {

    final Player playerOne = new Player(1);
    final Player playerTwo = new Player(2);
    int startingPlayer;
    public void startGame(Input input, StartGameInput startGameInput) {
        playerOne.initPlayer(input.getPlayerOneDecks().getDecks().get(startGameInput.getPlayerOneDeckIdx()), startGameInput.getShuffleSeed(), HeroBuilder.createHero(startGameInput.getPlayerOneHero()));
        playerTwo.initPlayer(input.getPlayerTwoDecks().getDecks().get(startGameInput.getPlayerOneDeckIdx()), startGameInput.getShuffleSeed(), HeroBuilder.createHero(startGameInput.getPlayerOneHero()));
        startingPlayer = startGameInput.getStartingPlayer();
    }

    private static final Table table = new Table();

    private Table() {

    }

    static public Table getTable() {
        return table;
    }

}

class Player {
    private int gamesPlayed;
    private int gamesWon;

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    private int id;
    private int mana;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    private final Row firstRow = new Row(0);
    private final Row secondRow = new Row(1);

    public Row getFirstRow() {
        return firstRow;
    }

    public Row getSecondRow() {
        return secondRow;
    }

    private Hero hero;
    private ArrayList<Card> hand;
    private ArrayList<Card> deck;

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    private int shuffleSeed;
    private boolean finishedTurn = false;

    public int getShuffleSeed() {
        return shuffleSeed;
    }

    public void setFinishedTurn (boolean bool) {
        this.finishedTurn = bool;
    }

    public boolean getFinishedTurn() {
        return this.finishedTurn;
    }

    public Player(int id) {
        this.id = id;
    }

    public void initPlayer(ArrayList<CardInput> deck, int shuffleSeed, Hero hero) {
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();
        for(CardInput cardInput : deck) {
            Card aux = CardBuilder.createCard(cardInput);
            this.getDeck().add(aux);
        }
        this.shuffleSeed = shuffleSeed;
        this.hero = hero;
    }

    public void dealCard() {
        if(deck.size() == 0)
            return;

        Card card = deck.remove(0);
        hand.add(card);
    }

}



class Row {
    Minion[] minions;
    int type; //0 pt first row, 1 pt second row

    public Row(int type) {
        minions = new Minion[5];
        this.type = type;
    }

    public void addMinion(Minion minion) {
        if(minion.getFirstRow() && type != 0) {
            // eroare cv
        }
        int i;
        for(i = 0; i < 5; i++)
            if(minions[i] == null) {
                minions[i] = minion;
                break;
            }

        if(i >= 5) {
            //row is full
        }
    }

    public void removeMinion(int index) {
        minions[index] = null;
        for(int i = index; i < 4; i++) {
            if (minions[i + 1] == null) {
                break;
            }

            Minion aux = minions[i];
            minions[i] = minions[i + 1];
            minions[i + 1] = aux;
        }
    }

    // to do anything else

}