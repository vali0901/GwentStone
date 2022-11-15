package gameEnvironment;

import cards.*;
import fileio.CardInput;

import java.util.*;

public class Player {
    private int gamesWon;
    private int id;
    private int mana;
    private Row firstRow;
    private Row secondRow;
    private Hero hero;
    private ArrayList<Card> hand;
    private ArrayList<Card> deck;
    private int shuffleSeed;


    public Player(int id) {
        this.id = id;
        gamesWon = 0;
    }

    public int getGamesWon() {
        return gamesWon;
    }
    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMana() {
        return mana;
    }

    public Row getFirstRow() {
        return firstRow;
    }

    public Row getSecondRow() {
        return secondRow;
    }

    public Hero getHero() {
        return hero;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }
    public int getShuffleSeed() {
        return shuffleSeed;
    }
    public void wonGame() {
        gamesWon++;
    }
    public void addMana(int mana) {
        this.mana += mana;
    }
    public void initPlayer(ArrayList<CardInput> deck, int shuffleSeed, Hero hero) {
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();

        for(CardInput cardInput : deck) {
            Card aux = CardBuilder.createCard(cardInput);
            this.deck.add(aux);
        }
        this.shuffleSeed = shuffleSeed;
        this.hero = hero;

        this.firstRow = new Row();
        this.secondRow = new Row();

        this.mana = 0;

        Collections.shuffle(this.deck, new Random(shuffleSeed));
    }

    public void dealCard() {
        if(deck.size() == 0)
            return;

        Card card = deck.remove(0);
        hand.add(card);
    }

    public void placeCard(int index) {
        Card card = hand.remove(index);

        if(((Minion)card).getFirstRow()) {
            firstRow.addMinion((Minion)card);
        } else {
            secondRow.addMinion((Minion)card);
        }

        this.mana -= ((Minion) card).getMana();
    }

    public void useEnvironment(int index, Row attackedRow, Row friendlyRow) {
        Environment card = (Environment) hand.remove(index);

        mana -= card.getMana();
        card.doEffect(attackedRow, friendlyRow);
        attackedRow.removeDeadMinions();
    }

    public void useHeroAbility(Row row) {
        this.mana -= hero.getMana();
        hero.ability(row);
    }

    public void endTurn() {
        for(Minion minion : firstRow.getMinions()) {
            if(minion != null)
                minion.setHasAttacked(false);
            if(minion != null && minion.getIsFrozen())
                minion.setIsFrozen(false);
        }

        for(Minion minion : secondRow.getMinions()) {
            if(minion != null)
                minion.setHasAttacked(false);
            if(minion != null && minion.getIsFrozen())
                minion.setIsFrozen(false);
        }

        hero.setHasAttacked(false);
    }
}
