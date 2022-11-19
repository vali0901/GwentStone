package game;

import cards.Minion;
import cards.Card;
import cards.Environment;
import cards.SpecialMinion;
import cards.Hero;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.ActionsInput;

import java.util.ArrayList;
import java.util.Objects;

public final class ActionMaker {
    private ActionMaker() { }

    /**
     * The method that takes the commands and does the actions needed, checking the required
     * invalid cases
     * @param actionsInput Info containing the command and some needed parameters
     * @param output ArrayNode where some output object will be placed
     * @return 'true' at the end of a turn, 'false' otherwise
     */
    public static boolean doAction(final ActionsInput actionsInput, final ArrayNode output) {
        Player currPlayer = Table.getTable().getCurrPlayer();
        switch (actionsInput.getCommand()) {
            case "placeCard" : {
                int index = actionsInput.getHandIdx();
                Card card = currPlayer.getHand().get(index);

                if (card.getType() == MagicNumbers.ENVIRONMENT_TYPE) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getHandIdx(), "Cannot place environment card on table.",
                            null);
                    return false;
                }

                if (((Minion) card).getMana() > currPlayer.getMana()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getHandIdx(), "Not enough mana to place card on table.",
                            null);
                    return false;
                }

                boolean placeOnFirstRow = ((Minion) card).getFirstRow();
                if (placeOnFirstRow
                        && currPlayer.getFirstRow().getSize() == MagicNumbers.MAX_ROW_LENGTH
                        || !placeOnFirstRow
                        && currPlayer.getSecondRow().getSize() == MagicNumbers.MAX_ROW_LENGTH) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getHandIdx(),
                            "Cannot place card on table since row is full.", null);
                    return false;
                }

                currPlayer.placeCard(index);
                return false;
            }
            case "cardUsesAttack": {
                Minion cardAttacker = Table.getTable().getCardAttacker(
                        actionsInput.getCardAttacker());
                Minion cardAttacked = Table.getTable().getCardAttacked(
                        actionsInput.getCardAttacked());

                if (cardAttacker.getIsFrozen()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getCardAttacker(), actionsInput.getCardAttacked(),
                            "Attacker card is frozen.");
                    return false;
                }

                if (cardAttacker.getHasAttacked()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getCardAttacker(), actionsInput.getCardAttacked(),
                            "Attacker card has already attacked this turn.");
                    return false;
                }

                if ((Table.getTable().getEnemyPlayer().getId() == MagicNumbers.PLAYER_TWO_ID
                        && (actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_2_INDEX
                        || actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_3_INDEX))
                        || (Table.getTable().getEnemyPlayer().getId() == MagicNumbers.PLAYER_ONE_ID
                        && (actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_0_INDEX
                        || actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_1_INDEX))) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getCardAttacker(), actionsInput.getCardAttacked(),
                            "Attacked card does not belong to the enemy.");
                    return false;
                }

                if (Table.getTable().getEnemyPlayer().getFirstRow().hasTank()
                        && !cardAttacked.getIsTank()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getCardAttacker(), actionsInput.getCardAttacked(),
                            "Attacked card is not of type 'Tank'.");
                    return false;
                }

                Row attackedRow;
                if (actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_3_INDEX
                        || actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_0_INDEX) {
                    attackedRow = Table.getTable().getEnemyPlayer().getSecondRow();
                } else {
                    attackedRow = Table.getTable().getEnemyPlayer().getFirstRow();
                }

                if (cardAttacker.attack(cardAttacked)) {
                    attackedRow.removeMinion(actionsInput.getCardAttacked().getY());
                }

                return false;
            }
            case "cardUsesAbility": {
                Minion cardAttacker = Table.getTable().getCardAttacker(
                        actionsInput.getCardAttacker());

                Row attackedRow;
                if (((SpecialMinion) cardAttacker).isDisciple()) {
                    if (actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_3_INDEX
                            || actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_0_INDEX) {
                        attackedRow = currPlayer.getSecondRow();
                    } else {
                        attackedRow = currPlayer.getFirstRow();
                    }
                } else {
                    if (actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_3_INDEX
                            || actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_0_INDEX) {
                        attackedRow = Table.getTable().getEnemyPlayer().getSecondRow();
                    } else {
                        attackedRow = Table.getTable().getEnemyPlayer().getFirstRow();
                    }
                }
                Minion cardAttacked = attackedRow.getMinion(actionsInput.getCardAttacked().getY());

                if (cardAttacker.getIsFrozen()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getCardAttacker(), actionsInput.getCardAttacked(),
                            "Attacker card is frozen.");
                    return false;
                }

                if (cardAttacker.getHasAttacked()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getCardAttacker(), actionsInput.getCardAttacked(),
                            "Attacker card has already attacked this turn.");
                    return false;
                }

                if (((SpecialMinion) cardAttacker).isDisciple()) {
                    if ((Table.getTable().getEnemyPlayer().getId() == MagicNumbers.PLAYER_TWO_ID
                            && (actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_0_INDEX
                            || actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_1_INDEX))
                            || (Table.getTable().getEnemyPlayer().getId()
                            == MagicNumbers.PLAYER_ONE_ID
                            && (actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_2_INDEX
                            || actionsInput.getCardAttacked().getX()
                            == MagicNumbers.ROW_3_INDEX))) {
                        OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                                actionsInput.getCardAttacker(), actionsInput.getCardAttacked(),
                                "Attacked card does not belong to the current player.");
                        return false;
                    }
                } else if (!((SpecialMinion) cardAttacker).isDisciple()) {
                    if ((Table.getTable().getEnemyPlayer().getId() == MagicNumbers.PLAYER_TWO_ID
                            && (actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_2_INDEX
                            || actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_3_INDEX)
                            || (Table.getTable().getEnemyPlayer().getId()
                            == MagicNumbers.PLAYER_ONE_ID
                            && (actionsInput.getCardAttacked().getX() == MagicNumbers.ROW_0_INDEX
                            || actionsInput.getCardAttacked().getX()
                            == MagicNumbers.ROW_1_INDEX)))) {
                        OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                                actionsInput.getCardAttacker(), actionsInput.getCardAttacked(),
                                "Attacked card does not belong to the enemy.");
                        return false;
                    }
                    if (Table.getTable().getEnemyPlayer().getFirstRow().hasTank()
                            && !cardAttacked.getIsTank()) {
                        OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                                actionsInput.getCardAttacker(), actionsInput.getCardAttacked(),
                                "Attacked card is not of type 'Tank'.");
                        return false;
                    }
                }

                if (((SpecialMinion) cardAttacker).ability(cardAttacked)) {
                    attackedRow.removeMinion(actionsInput.getCardAttacked().getY());
                }

                return false;
            }
            case "useAttackHero": {
                Minion cardAttacker = Table.getTable().getCardAttacker(
                        actionsInput.getCardAttacker());
                Hero enemyHero = Table.getTable().getEnemyPlayer().getHero();

                if (cardAttacker.getIsFrozen()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getCardAttacker(), "Attacker card is frozen.", null);
                    return false;
                }

                if (cardAttacker.getHasAttacked()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getCardAttacker(),
                            "Attacker card has already attacked this turn.", null);
                    return false;
                }

                if (Table.getTable().getEnemyPlayer().getFirstRow().hasTank()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getCardAttacker(),
                            "Attacked card is not of type 'Tank'.", null);
                    return false;
                }

                if (cardAttacker.attack(enemyHero)) {
                    if (currPlayer.getId() == 1) {
                        OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                                "Player one killed the enemy hero.", null, null);
                    } else {
                        OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                                "Player two killed the enemy hero.", null, null);
                    }
                    currPlayer.wonGame();
                    return false;
                }

                return false;
            }

            case "useHeroAbility": {
                Hero hero = currPlayer.getHero();

                if (currPlayer.getMana() < hero.getMana()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getAffectedRow(),
                            "Not enough mana to use hero's ability.", null);
                    return false;
                }

                if (hero.hasAttacked()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getAffectedRow(),
                            "Hero has already attacked this turn.", null);
                    return false;
                }

                Row selectedRow = null;
                if (!hero.isFriendly()
                        && ((currPlayer.getId() == MagicNumbers.PLAYER_ONE_ID
                        && (actionsInput.getAffectedRow() == MagicNumbers.ROW_2_INDEX
                        || actionsInput.getAffectedRow() == MagicNumbers.ROW_3_INDEX))
                        || (currPlayer.getId() == MagicNumbers.PLAYER_TWO_ID
                        && (actionsInput.getAffectedRow() == MagicNumbers.ROW_0_INDEX
                        || actionsInput.getAffectedRow() == MagicNumbers.ROW_1_INDEX)))) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getAffectedRow(),
                            "Selected row does not belong to the enemy.", null);
                    return false;
                } else if (hero.isFriendly()
                        && ((currPlayer.getId() == MagicNumbers.PLAYER_ONE_ID
                        && (actionsInput.getAffectedRow() == MagicNumbers.ROW_0_INDEX
                        || actionsInput.getAffectedRow() == MagicNumbers.ROW_1_INDEX))
                        || (currPlayer.getId() == MagicNumbers.PLAYER_TWO_ID
                        && (actionsInput.getAffectedRow() == MagicNumbers.ROW_2_INDEX
                        || actionsInput.getAffectedRow() == MagicNumbers.ROW_3_INDEX)))) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getAffectedRow(),
                            "Selected row does not belong to the current player.", null);
                    return false;
                }

                if (hero.isFriendly()) {
                    if (actionsInput.getAffectedRow() == MagicNumbers.ROW_0_INDEX
                            || actionsInput.getAffectedRow() == MagicNumbers.ROW_3_INDEX) {
                        selectedRow = currPlayer.getSecondRow();
                    } else {
                        selectedRow = currPlayer.getFirstRow();
                    }
                }

                if (!hero.isFriendly()) {
                    if (actionsInput.getAffectedRow() == MagicNumbers.ROW_0_INDEX
                            || actionsInput.getAffectedRow() == MagicNumbers.ROW_3_INDEX) {
                        selectedRow = Table.getTable().getEnemyPlayer().getSecondRow();
                    } else {
                        selectedRow = Table.getTable().getEnemyPlayer().getFirstRow();
                    }
                }

                currPlayer.useHeroAbility(selectedRow);
                return false;
            }
            case "useEnvironmentCard": {
                Card card = currPlayer.getHand().get(actionsInput.getHandIdx());
                if (card.getType() == MagicNumbers.MINION_TYPE) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getHandIdx(), actionsInput.getAffectedRow(),
                            "Chosen card is not of type environment.");
                    return false;
                }

                if (currPlayer.getMana() < ((Environment) card).getMana()) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getHandIdx(), actionsInput.getAffectedRow(),
                            "Not enough mana to use environment card.");
                    return false;
                }

                if ((currPlayer.getId() == MagicNumbers.PLAYER_ONE_ID
                        && (actionsInput.getAffectedRow() == MagicNumbers.ROW_2_INDEX
                        || actionsInput.getAffectedRow() == MagicNumbers.ROW_3_INDEX))
                        || (currPlayer.getId() == MagicNumbers.PLAYER_TWO_ID
                        && (actionsInput.getAffectedRow() == MagicNumbers.ROW_0_INDEX
                        || actionsInput.getAffectedRow() == MagicNumbers.ROW_1_INDEX))) {
                    OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                            actionsInput.getHandIdx(), actionsInput.getAffectedRow(),
                            "Chosen row does not belong to the enemy.");
                    return false;
                }

                if (((Environment) card).isHeartHound()) {
                    if ((actionsInput.getAffectedRow() == MagicNumbers.ROW_0_INDEX
                            || actionsInput.getAffectedRow() == MagicNumbers.ROW_3_INDEX
                            && currPlayer.getSecondRow().getSize() == MagicNumbers.MAX_ROW_LENGTH)
                            || (actionsInput.getAffectedRow() == MagicNumbers.ROW_1_INDEX
                            || actionsInput.getAffectedRow() == MagicNumbers.ROW_2_INDEX
                            && currPlayer.getFirstRow().getSize()
                            == MagicNumbers.MAX_ROW_LENGTH)) {
                        OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                                actionsInput.getHandIdx(), actionsInput.getAffectedRow(),
                                "Cannot steal enemy card since the player's row is full.");
                        return false;
                    }
                }

                Row attackedRow, friendlyRow;
                if (actionsInput.getAffectedRow() == MagicNumbers.ROW_0_INDEX
                        || actionsInput.getAffectedRow() == MagicNumbers.ROW_3_INDEX) {
                    attackedRow = Table.getTable().getEnemyPlayer().getSecondRow();
                    friendlyRow = currPlayer.getSecondRow();
                } else {
                    attackedRow = Table.getTable().getEnemyPlayer().getFirstRow();
                    friendlyRow = currPlayer.getFirstRow();
                }

                currPlayer.useEnvironment(actionsInput.getHandIdx(), attackedRow, friendlyRow);
                return false;
            }
            case "getCardsInHand": {
                Player player = Table.getTable().getPlayerById(actionsInput.getPlayerIdx());
                OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                        actionsInput.getPlayerIdx(), player.getHand(), null);
                return false;
            }
            case "getPlayerDeck": {
                Player player = Table.getTable().getPlayerById(actionsInput.getPlayerIdx());
                OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                        actionsInput.getPlayerIdx(), player.getDeck(), null);
                return false;
            }
            case "getCardsOnTable": {
                ArrayList<Minion> row0 = new ArrayList<>();
                ArrayList<Minion> row1 = new ArrayList<>();
                ArrayList<Minion> row2 = new ArrayList<>();
                ArrayList<Minion> row3 = new ArrayList<>();

                for (Minion minion : Table.getTable().getPlayerTwo().getSecondRow().getMinions()) {
                    if (minion != null) {
                        row0.add(minion);
                    }
                }

                for (Minion minion : Table.getTable().getPlayerTwo().getFirstRow().getMinions()) {
                    if (minion != null) {
                        row1.add(minion);
                    }
                }

                for (Minion minion : Table.getTable().getPlayerOne().getFirstRow().getMinions()) {
                    if (minion != null) {
                        row2.add(minion);
                    }
                }

                for (Minion minion : Table.getTable().getPlayerOne().getSecondRow().getMinions()) {
                    if (minion != null) {
                        row3.add(minion);
                    }
                }

                ArrayList<ArrayList<Minion>> cards = new ArrayList<>();
                cards.add(row0);
                cards.add(row1);
                cards.add(row2);
                cards.add(row3);

                OutputFactory.outputBuilder(output, actionsInput.getCommand(), cards,
                        null, null);
                return false;
            }
            case "getPlayerTurn": {
                OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                        Table.getTable().getCurrPlayer().getId(), null, null);
                return false;
            }
            case "getPlayerHero": {
                Player player = Table.getTable().getPlayerById(actionsInput.getPlayerIdx());
                OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                        actionsInput.getPlayerIdx(), player.getHero(), null);
                return false;
            }
            case "getCardAtPosition": {
                Minion card = Table.getTable().getCardAtPosition(actionsInput.getX(),
                        actionsInput.getY());

                OutputFactory.outputBuilder(output, actionsInput.getCommand(), actionsInput.getX(),
                        actionsInput.getY(), Objects.requireNonNullElse(card,
                                "No card available at that position."));

                return false;
            }
            case "getPlayerMana": {
                Player player = Table.getTable().getPlayerById(actionsInput.getPlayerIdx());
                OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                        actionsInput.getPlayerIdx(), player.getMana(), null);
                return false;
            }
            case "getEnvironmentCardsInHand": {
                Player player = Table.getTable().getPlayerById(actionsInput.getPlayerIdx());

                ArrayList<Environment> cards = new ArrayList<>();
                for (Card card : player.getHand()) {
                    if (card != null && card.getType() == MagicNumbers.ENVIRONMENT_TYPE) {
                        cards.add((Environment) card);
                    }
                }

                OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                        actionsInput.getPlayerIdx(), cards, null);
                return false;
            }
            case "getFrozenCardsOnTable": {
                ArrayList<Minion> cards = new ArrayList<>();
                for (Minion card : Table.getTable().getPlayerTwo().getSecondRow().getMinions()) {
                    if (card != null && card.getIsFrozen()) {
                        cards.add(card);
                    }
                }

                for (Minion card : Table.getTable().getPlayerTwo().getFirstRow().getMinions()) {
                    if (card != null && card.getIsFrozen()) {
                        cards.add(card);
                    }
                }

                for (Minion card : Table.getTable().getPlayerOne().getFirstRow().getMinions()) {
                    if (card != null && card.getIsFrozen()) {
                        cards.add(card);
                    }
                }

                for (Minion card : Table.getTable().getPlayerOne().getSecondRow().getMinions()) {
                    if (card != null && card.getIsFrozen()) {
                        cards.add(card);
                    }
                }

                OutputFactory.outputBuilder(output, actionsInput.getCommand(), cards, null, null);
                return false;
            }
            case "getTotalGamesPlayed": {
                OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                        Table.getNrGamesPlayed(), null, null);
                return false;
            }
            case "getPlayerOneWins": {
                OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                        Table.getTable().getPlayerOne().getGamesWon(), null, null);
                return false;
            }
            case "getPlayerTwoWins": {
                OutputFactory.outputBuilder(output, actionsInput.getCommand(),
                        Table.getTable().getPlayerTwo().getGamesWon(), null, null);
                return false;
            }
            case "endPlayerTurn": {
                currPlayer.endTurn();
                Table.getTable().changeCurrPlayer();
                return true;
            }
            default:
                return false;
        }
    }

}
