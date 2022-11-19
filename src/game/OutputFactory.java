package game;

import cards.Card;
import cards.Minion;
import cards.Environment;
import cards.Hero;

import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Coordinates;

import java.util.ArrayList;

public final class OutputFactory {

    private OutputFactory() { }

    /**
     * Builds a specific object for the given command that will be placed in the given ArrayNode;
     * the arguments must be given in an exact order as it is showed in the output examples
     * @param node The Array where the output will be placed
     * @param command The command that requires an output
     * @param arg1 Content of the output (might be null)
     * @param arg2 Content of the output (might be null)
     * @param arg3 Content of the output (might be null)
     */
    public static void outputBuilder(
            final ArrayNode node, final String command, final Object arg1, final Object arg2,
            final Object arg3) {
        switch (command) {
            case "placeCard" -> {
                PlaceCardOutput output = new PlaceCardOutput(command, (int) arg1, (String) arg2);
                node.addPOJO(output);
            }
            case "cardUsesAttack" -> {
                CardUsesAttackOutput output = new CardUsesAttackOutput(command,
                        (Coordinates) arg1, (Coordinates) arg2, (String) arg3);
                node.addPOJO(output);
            }
            case "cardUsesAbility" -> {
                CardUsesAbilityOutput output = new CardUsesAbilityOutput(command,
                        (Coordinates) arg1, (Coordinates) arg2, (String) arg3);
                node.addPOJO(output);
            }
            case "useAttackHero" -> {
                if (arg2 == null) {
                    GameEndOutput output = new GameEndOutput((String) arg1);
                    node.addPOJO(output);
                } else {
                    UseAttackHeroOutput output = new UseAttackHeroOutput(command,
                            (Coordinates) arg1, (String) arg2);
                    node.addPOJO(output);
                }
            }
            case "useHeroAbility" -> {
                UseHeroAbilityOutput output = new UseHeroAbilityOutput(command,
                        (int) arg1, (String) arg2);
                node.addPOJO(output);
            }
            case "useEnvironmentCard" -> {
                UseEnvironmentCardOutput output = new UseEnvironmentCardOutput(command,
                        (int) arg1, (int) arg2, (String) arg3);
                node.addPOJO(output);
            }
            case "getCardsInHand" -> {
                ArrayList<CardOutput> cardsOutput = new ArrayList<>();
                for (Card card : (ArrayList<Card>) arg2) {
                    CardOutput cardOutput;
                    if (card instanceof Minion) {
                        cardOutput = new MinionOutput((Minion) card);
                    } else {
                        cardOutput = new EnvironmentOutput((Environment) card);
                    }
                    cardsOutput.add(cardOutput);
                }
                GetCardsInHandOutput output = new GetCardsInHandOutput(command,
                        (int) arg1, cardsOutput);
                node.addPOJO(output);
            }
            case "getPlayerDeck" -> {
                ArrayList<CardOutput> cardsOutput = new ArrayList<>();
                for (Card card : (ArrayList<Card>) arg2) {
                    CardOutput cardOutput;
                    if (card instanceof Minion) {
                        cardOutput = new MinionOutput((Minion) card);
                    } else {
                        cardOutput = new EnvironmentOutput((Environment) card);
                    }
                    cardsOutput.add(cardOutput);
                }
                GetPLayerDeckOutput output = new GetPLayerDeckOutput(command,
                        (int) arg1, cardsOutput);
                node.addPOJO(output);
            }
            case "getCardsOnTable" -> {
                ArrayList<ArrayList<MinionOutput>> cardsOutput = new ArrayList<>();
                for (ArrayList<Minion> arr : (ArrayList<ArrayList<Minion>>) arg1) {
                    ArrayList<MinionOutput> aux = new ArrayList<>();
                    for (Minion card : arr) {
                        MinionOutput cardOutput;
                        cardOutput = new MinionOutput(card);
                        aux.add(cardOutput);
                    }
                    cardsOutput.add(aux);
                }

                GetCardOnTableOutput output = new GetCardOnTableOutput(command, cardsOutput);
                node.addPOJO(output);
            }
            case "getPlayerTurn" -> {
                GetPlayerTurnOutput output = new GetPlayerTurnOutput(command, (int) arg1);
                node.addPOJO(output);
            }
            case "getPlayerHero" -> {
                HeroOutput heroOutput = new HeroOutput((Hero) arg2);
                GetPlayerHeroOutput output = new GetPlayerHeroOutput(command,
                        (int) arg1, heroOutput);
                node.addPOJO(output);
            }
            case "getCardAtPosition" -> {
                Object output;
                if (arg3 instanceof String) {
                    output = new GetCardAtPositionOutputError(command,
                            (int) arg1, (int) arg2, (String) arg3);
                } else {
                    MinionOutput minionOutput = new MinionOutput((Minion) arg3);
                    output = new GetCardAtPositionOutputSuccess(command,
                            (int) arg1, (int) arg2, minionOutput);
                }
                node.addPOJO(output);
            }
            case "getPlayerMana" -> {
                GetPLayerManaOutput output = new GetPLayerManaOutput(command,
                        (int) arg1, (int) arg2);
                node.addPOJO(output);
            }
            case "getEnvironmentCardsInHand" -> {
                ArrayList<EnvironmentOutput> cardsOutput = new ArrayList<>();
                for (Environment card : (ArrayList<Environment>) arg2) {
                    EnvironmentOutput cardOutput;
                    cardOutput = new EnvironmentOutput(card);
                    cardsOutput.add(cardOutput);
                }
                GetEnvironmentCardsInHandOutput output = new GetEnvironmentCardsInHandOutput(
                        command, (int) arg1, cardsOutput);
                node.addPOJO(output);
            }
            case "getFrozenCardsOnTable" -> {
                ArrayList<MinionOutput> cardsOutput = new ArrayList<>();
                for (Minion card : (ArrayList<Minion>) arg1) {
                    MinionOutput cardOutput;
                    cardOutput = new MinionOutput(card);
                    cardsOutput.add(cardOutput);
                }
                GetFrozenCardsOnTableOutput output = new GetFrozenCardsOnTableOutput(command,
                        cardsOutput);
                node.addPOJO(output);
            }
            case "getTotalGamesPlayed" -> {
                GetTotalGamesPlayedOutput output = new GetTotalGamesPlayedOutput(command,
                        (int) arg1);
                node.addPOJO(output);
            }
            case "getPlayerOneWins", "getPlayerTwoWins" -> {
                GetPlayerTotalWinsOutput output = new GetPlayerTotalWinsOutput(command,
                        (int) arg1);
                node.addPOJO(output);
            }
            default -> {
            }
        }
    }
}

class PlaceCardOutput {
    private String command;
    private int handIdx;
    private String error;

    PlaceCardOutput(final String command, final int handIdx, final String error) {
        this.command = command;
        this.handIdx = handIdx;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getHandIdx() {
        return handIdx;
    }

    public void setHandIdx(final int handIdx) {
        this.handIdx = handIdx;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }
}

class CardUsesAttackOutput {
    private String command;
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;
    private String error;

    CardUsesAttackOutput(final String command, final Coordinates cardAttacker,
                         final Coordinates cardAttacked, final String error) {
        this.command = command;

        this.cardAttacker = new Coordinates();
        this.cardAttacker.setX(cardAttacker.getX());
        this.cardAttacker.setY(cardAttacker.getY());

        this.cardAttacked = new Coordinates();
        this.cardAttacked.setX(cardAttacked.getX());
        this.cardAttacked.setY(cardAttacked.getY());

        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public void setCardAttacker(final Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    public void setCardAttacked(final Coordinates cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }
}

class CardUsesAbilityOutput {
    private String command;
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;
    private String error;

    CardUsesAbilityOutput(final String command, final Coordinates cardAttacker,
                          final Coordinates cardAttacked, final String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.cardAttacked = cardAttacked;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public void setCardAttacker(final Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    public void setCardAttacked(final Coordinates cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }
}

class UseAttackHeroOutput {
    private String command;
    private Coordinates cardAttacker;
    private String error;

    UseAttackHeroOutput(final String command, final Coordinates cardAttacker, final String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.error = error;
    }


    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public void setCardAttacker(final Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }
}

class GameEndOutput {
    private String gameEnded;

    GameEndOutput(final String gameEnded) {
        this.gameEnded = gameEnded;
    }

    public String getGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(final String gameEnded) {
        this.gameEnded = gameEnded;
    }
}

class UseHeroAbilityOutput {
    private String command;
    private int affectedRow;
    private String error;

    UseHeroAbilityOutput(final String command, final int affectedRow, final String error) {
        this.command = command;
        this.affectedRow = affectedRow;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getAffectedRow() {
        return affectedRow;
    }

    public void setAffectedRow(final int affectedRow) {
        this.affectedRow = affectedRow;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }
}

class UseEnvironmentCardOutput {
    private String command;
    private int handIdx;
    private int affectedRow;
    private String error;

    UseEnvironmentCardOutput(final String command, final int handIdx, final int affectedRow,
                             final String error) {
        this.command = command;
        this.handIdx = handIdx;
        this.affectedRow = affectedRow;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getHandIdx() {
        return handIdx;
    }

    public void setHandIdx(final int handIdx) {
        this.handIdx = handIdx;
    }

    public int getAffectedRow() {
        return affectedRow;
    }

    public void setAffectedRow(final int affectedRow) {
        this.affectedRow = affectedRow;
    }

    public String getError() {
        return error;
    }

    public void setError(final String error) {
        this.error = error;
    }
}

class GetCardsInHandOutput {
    private String command;
    private int playerIdx;
    private ArrayList<CardOutput> output;

    GetCardsInHandOutput(final String command, final int playerIdx,
                         final ArrayList<CardOutput> output) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public ArrayList<CardOutput> getOutput() {
        return output;
    }

    public void setOutput(final ArrayList<CardOutput> output) {
        this.output = output;
    }
}

class GetPLayerDeckOutput {
    private String command;
    private int playerIdx;
    private ArrayList<CardOutput> output;

    GetPLayerDeckOutput(final String command, final int playerIdx,
                        final ArrayList<CardOutput> output) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public ArrayList<CardOutput> getOutput() {
        return output;
    }

    public void setOutput(final ArrayList<CardOutput> output) {
        this.output = output;
    }
}

class GetCardOnTableOutput {
    private String command;
    private ArrayList<ArrayList<MinionOutput>> output;

    GetCardOnTableOutput(final String command, final ArrayList<ArrayList<MinionOutput>> output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public ArrayList<ArrayList<MinionOutput>> getOutput() {
        return output;
    }

    public void setOutput(final ArrayList<ArrayList<MinionOutput>> output) {
        this.output = output;
    }
}

class GetPlayerTurnOutput {
    private String command;
    private int output;

    GetPlayerTurnOutput(final String command, final int output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(final int output) {
        this.output = output;
    }
}

class GetPlayerHeroOutput {
    private String command;
    private int playerIdx;
    private HeroOutput output;

    GetPlayerHeroOutput(final String command, final int playerIdx, final HeroOutput output) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public HeroOutput getOutput() {
        return output;
    }

    public void setOutput(final HeroOutput output) {
        this.output = output;
    }
}


class GetCardAtPositionOutputSuccess {
    private String command;
    private MinionOutput output;
    private int x;
    private int y;

    GetCardAtPositionOutputSuccess(final String command, final int x, final int y,
                                   final MinionOutput output) {
        this.command = command;
        this.output = output;
        this.x = x;
        this.y = y;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public MinionOutput getOutput() {
        return output;
    }

    public void setOutput(final MinionOutput output) {
        this.output = output;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }
}

class GetCardAtPositionOutputError {
    private String command;
    private int x;
    private int y;
    private String output;

    GetCardAtPositionOutputError(final String command, final int x, final int y,
                                 final String output) {
        this.command = command;
        this.x = x;
        this.y = y;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getX() {
        return x;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public String  getOutput() {
        return output;
    }

    public void setOutput(final String output) {
        this.output = output;
    }
}

class GetPLayerManaOutput {
    private String command;
    private int playerIdx;
    private int output;

    GetPLayerManaOutput(final String command, final int playerIdx, final int output) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(final int output) {
        this.output = output;
    }
}

class GetEnvironmentCardsInHandOutput {
    private String command;
    private int playerIdx;
    private ArrayList<EnvironmentOutput> output;

    GetEnvironmentCardsInHandOutput(final String command, final int playerIdx,
                                    final ArrayList<EnvironmentOutput> output) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(final int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public ArrayList<EnvironmentOutput> getOutput() {
        return output;
    }

    public void setOutput(final ArrayList<EnvironmentOutput> output) {
        this.output = output;
    }
}

class GetFrozenCardsOnTableOutput {
    private String command;
    private ArrayList<MinionOutput> output;

    GetFrozenCardsOnTableOutput(final String command, final ArrayList<MinionOutput> output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public ArrayList<MinionOutput> getOutput() {
        return output;
    }

    public void setOutput(final ArrayList<MinionOutput> output) {
        this.output = output;
    }
}

class GetTotalGamesPlayedOutput {
    private String command;
    private int output;

    GetTotalGamesPlayedOutput(final String command, final int output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(final int output) {
        this.output = output;
    }
}

class GetPlayerTotalWinsOutput {
    private String command;
    private int output;

    GetPlayerTotalWinsOutput(final String command, final int output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(final String command) {
        this.command = command;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(final int output) {
        this.output = output;
    }
}

interface CardOutput { }

class MinionOutput implements CardOutput {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;

    MinionOutput(final Minion minion) {
        this.mana = minion.getMana();
        this.attackDamage = minion.getAttackDamage();
        this.health = minion.getHealth();
        this.description = (minion.getDescription());
        this.colors = minion.getColors();
        this.name = minion.getName();
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}

class EnvironmentOutput implements CardOutput {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;

    EnvironmentOutput(final Environment environment) {
        this.mana = environment.getMana();
        this.description = environment.getDescription();
        this.colors = environment.getColors();
        this.name = environment.getName();
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}

class HeroOutput {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int health;

    HeroOutput(final Hero hero) {
        this.mana = hero.getMana();
        this.description = hero.getDescription();
        this.colors = hero.getColors();
        this.name = hero.getName();
        this.health = hero.getHealth();
    }

    public int getMana() {
        return mana;
    }

    public void setMana(final int mana) {
        this.mana = mana;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(final ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(final int health) {
        this.health = health;
    }
}
