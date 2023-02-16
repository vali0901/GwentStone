package game;

import cards.Card;
import cards.Hero;
import cards.Minion;
import cards.Environment;
import cards.CardBuilder;
import fileio.CardInput;

import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
public final class Player {
    private int gamesWon;
    private final int id;
    private int mana;
    private Row firstRow;
    private Row secondRow;
    private Hero hero;
    private ArrayList<Card> hand;
    private ArrayList<Card> deck;

    public Player(final int id) {
        this.id = id;
        gamesWon = 0;
    }

    public int getGamesWon() {
        return gamesWon;
    }
    public void setGamesWon(final int gamesWon) {
        this.gamesWon = gamesWon;
    }
    public int getId() {
        return id;
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

    /**
     * Increments the number of games won by the current player
     */
    public void wonGame() {
        this.gamesWon++;
    }

    /**
     *
     * @param addedMana Added mana to the player's total mana
     */
    public void addMana(final int addedMana) {
        this.mana += addedMana;
    }

    /**
     * Initializes the rows of the table for each player, the deck of cards which is also shuffled
     * and the hero for the current game
     * @param chosenDeck Deck of cords from which the player will get his cards in hand
     * @param seed The seed for the random number generator used by shuffle
     * @param givenHero The hero that will be assigned to the player for this game
     */
    public void initPlayer(final ArrayList<CardInput> chosenDeck, final int seed,
                           final Hero givenHero) {
        this.deck = new ArrayList<>();
        this.hand = new ArrayList<>();

        for (CardInput cardInput : chosenDeck) {
            Card aux = CardBuilder.createCard(cardInput);
            this.deck.add(aux);
        }
        this.hero = givenHero;

        this.firstRow = new Row();
        this.secondRow = new Row();

        this.mana = 0;

        Collections.shuffle(this.deck, new Random(seed));
    }

    /**
     * Takes the first card of the deck which will pe added to player's hand
     */
    public void dealCard() {
        if (deck.size() == 0) {
            return;
        }

        Card card = deck.remove(0);
        hand.add(card);
    }

    /**
     * Places a minion card on the table; the caller must be sure that the card is of type 'Minion'
     * @param index The position of the card in player's deck
     */
    public void placeCard(final int index) {
        Card card = hand.remove(index);

        if (((Minion) card).getFirstRow()) {
            firstRow.addMinion((Minion) card);
        } else {
            secondRow.addMinion((Minion) card);
        }

        this.mana -= ((Minion) card).getMana();
    }

    /**
     * Uses an environment card from the player's hand; the caller must be sure that the card at
     * the given index is of type 'Environment'
     * @param index The position of the card in player's hand
     * @param attackedRow Enemy's row that will be affected by the environment card's ability
     * @param friendlyRow Current player's row that will be affected by the environment card's
     *                    ability
     */
    public void useEnvironment(final int index, final Row attackedRow, final Row friendlyRow) {
        Environment card = (Environment) hand.remove(index);

        mana -= card.getMana();
        card.doEffect(attackedRow, friendlyRow);
        attackedRow.removeDeadMinions();
    }

    /**
     * Uses player's hero ability on the given row
     * @param row The row that will be affected by the hero's ability
     */
    public void useHeroAbility(final Row row) {
        this.mana -= hero.getMana();
        hero.ability(row);
    }

    /**
     * Unfreezing all the frozen minions and unmark every card that has attacked including the hero
     */
    public void endTurn() {
        for (Minion minion : firstRow.getMinions()) {
            if (minion != null) {
                minion.setHasAttacked(false);
            }
            if (minion != null && minion.getIsFrozen()) {
                minion.setIsFrozen(false);
            }
        }

        for (Minion minion : secondRow.getMinions()) {
            if (minion != null) {
                minion.setHasAttacked(false);
            }
            if (minion != null && minion.getIsFrozen()) {
                minion.setIsFrozen(false);
            }
        }

        hero.setHasAttacked(false);
    }
}
