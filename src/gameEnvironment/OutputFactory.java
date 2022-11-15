package gameEnvironment;

import cards.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.Coordinates;

import java.util.ArrayList;

public class OutputFactory {

    public static void outputBuilder(ArrayNode node, String command, Object arg1, Object arg2, Object arg3) {
        switch (command) {
            case "placeCard" -> {
                PlaceCardOutput output = new PlaceCardOutput(command, (int) arg1, (String) arg2);
                node.addPOJO(output);
            }
            case "cardUsesAttack" -> {
                CardUsesAttackOutput output = new CardUsesAttackOutput(command, (Coordinates) arg1, (Coordinates) arg2, (String) arg3);
                node.addPOJO(output);
            }
            case "cardUsesAbility" -> {
                CardUsesAbilityOutput output = new CardUsesAbilityOutput(command, (Coordinates) arg1, (Coordinates) arg2, (String) arg3);
                node.addPOJO(output);
            }
            case "useAttackHero" -> {
                if(arg2 == null) {
                    GameEndOutput output = new GameEndOutput((String) arg1);
                    node.addPOJO(output);
                } else {
                    UseAttackHeroOutput output = new UseAttackHeroOutput(command, (Coordinates) arg1, (String) arg2);
                    node.addPOJO(output);
                }
            }
            case "useHeroAbility" -> {
                UseHeroAbilityOutput output = new UseHeroAbilityOutput(command, (int) arg1, (String) arg2);
                node.addPOJO(output);
            }
            case "useEnvironmentCard" -> {
                UseEnvironmentCardOutput output = new UseEnvironmentCardOutput(command, (int) arg1, (int) arg2, (String) arg3);
                node.addPOJO(output);
            }
            case "getCardsInHand" -> {
                ArrayList<CardOutput> cardsOutput= new ArrayList<>();
                for(Card card : (ArrayList<Card>) arg2) {
                    CardOutput cardOutput;
                    if(card instanceof Minion)
                        cardOutput = new MinionOutput((Minion) card);
                    else
                        cardOutput = new EnvironmentOutput((Environment) card);
                    cardsOutput.add(cardOutput);
                }
                GetCardsInHandOutput output = new GetCardsInHandOutput(command, (int) arg1, cardsOutput);
                node.addPOJO(output);
            }
            case "getPlayerDeck" -> {
                ArrayList<CardOutput> cardsOutput= new ArrayList<>();
                for(Card card : (ArrayList<Card>) arg2) {
                    CardOutput cardOutput;
                    if(card instanceof Minion)
                        cardOutput = new MinionOutput((Minion) card);
                    else
                        cardOutput = new EnvironmentOutput((Environment) card);
                    cardsOutput.add(cardOutput);
                }
                GetPLayerDeckOutput output = new GetPLayerDeckOutput(command, (int) arg1, cardsOutput);
                node.addPOJO(output);
            }
            case "getCardsOnTable" -> {
                ArrayList<ArrayList<MinionOutput>> cardsOutput= new ArrayList<>();
                for(ArrayList<Minion> arr : (ArrayList<ArrayList<Minion>>) arg1) {
                    ArrayList<MinionOutput> aux = new ArrayList<>();
                    for(Minion card : arr) {
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
                GetPlayerHeroOutput output = new GetPlayerHeroOutput(command, (int) arg1, heroOutput);
                node.addPOJO(output);
            }
            case "getCardAtPosition" -> {
                Object output;
                if (arg3 instanceof String)
                    output = new GetCardAtPositionOutputError(command, (int) arg1, (int) arg2, (String) arg3);
                else {
                    MinionOutput minionOutput = new MinionOutput((Minion) arg3);
                    output = new GetCardAtPositionOutputSuccess(command, (int) arg1, (int) arg2, minionOutput);
                }
                node.addPOJO(output);
            }
            case "getPlayerMana" -> {
                GetPLayerManaOutput output = new GetPLayerManaOutput(command, (int) arg1, (int) arg2);
                node.addPOJO(output);
            }
            case "getEnvironmentCardsInHand" -> {
                ArrayList<EnvironmentOutput> cardsOutput= new ArrayList<>();
                for(Environment card : (ArrayList<Environment>) arg2) {
                    EnvironmentOutput cardOutput;
                    cardOutput = new EnvironmentOutput(card);
                    cardsOutput.add(cardOutput);
                }
                GetEnvironmentCardsInHandOutput output = new GetEnvironmentCardsInHandOutput(command, (int) arg1, cardsOutput);
                node.addPOJO(output);
            }
            case "getFrozenCardsOnTable" -> {
                ArrayList<MinionOutput> cardsOutput= new ArrayList<>();
                for(Minion card : (ArrayList<Minion>) arg1) {
                    MinionOutput cardOutput;
                    cardOutput = new MinionOutput(card);
                    cardsOutput.add(cardOutput);
                }
                GetFrozenCardsOnTableOutput output = new GetFrozenCardsOnTableOutput(command, cardsOutput);
                node.addPOJO(output);
            }
            case "getTotalGamesPlayed" -> {
                GetTotalGamesPlayedOutput output = new GetTotalGamesPlayedOutput(command, (int) arg1);
                node.addPOJO(output);
            }
            case "getPlayerOneWins", "getPlayerTwoWins" -> {
                GetPlayerTotalWinsOutput output = new GetPlayerTotalWinsOutput(command, (int) arg1);
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

    public PlaceCardOutput (String command, int handIdx, String error) {
        this.command = command;
        this.handIdx = handIdx;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getHandIdx() {
        return handIdx;
    }

    public void setHandIdx(int handIdx) {
        this.handIdx = handIdx;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

class CardUsesAttackOutput {
    private String command;
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;
    private String error;

    public CardUsesAttackOutput (String command, Coordinates cardAttacker, Coordinates cardAttacked, String error) {
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

    public void setCommand(String command) {
        this.command = command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public void setCardAttacker(Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    public void setCardAttacked(Coordinates cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

class CardUsesAbilityOutput {
    private String command;
    private Coordinates cardAttacker;
    private Coordinates cardAttacked;
    private String error;

    public CardUsesAbilityOutput (String command, Coordinates cardAttacker, Coordinates cardAttacked, String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.cardAttacked = cardAttacked;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public void setCardAttacker(Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public Coordinates getCardAttacked() {
        return cardAttacked;
    }

    public void setCardAttacked(Coordinates cardAttacked) {
        this.cardAttacked = cardAttacked;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

class UseAttackHeroOutput {
    private String command;
    private Coordinates cardAttacker;
    private String error;

    public UseAttackHeroOutput(String command, Coordinates cardAttacker, String error) {
        this.command = command;
        this.cardAttacker = cardAttacker;
        this.error = error;
    }


    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Coordinates getCardAttacker() {
        return cardAttacker;
    }

    public void setCardAttacker(Coordinates cardAttacker) {
        this.cardAttacker = cardAttacker;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

class GameEndOutput {
    private String gameEnded;

    public GameEndOutput (String gameEnded) {
        this.gameEnded = gameEnded;
    }

    public String getGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(String gameEnded) {
        this.gameEnded = gameEnded;
    }
}

class UseHeroAbilityOutput {
    private String command;
    private int affectedRow;
    private String error;

    public UseHeroAbilityOutput(String command, int affectedRow, String error) {
        this.command = command;
        this.affectedRow = affectedRow;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getAffectedRow() {
        return affectedRow;
    }

    public void setAffectedRow(int affectedRow) {
        this.affectedRow = affectedRow;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

class UseEnvironmentCardOutput {
    private String command;
    private int handIdx;
    private int affectedRow;
    private String error;

    public UseEnvironmentCardOutput(String command, int handIdx, int affectedRow, String error) {
        this.command = command;
        this.handIdx = handIdx;
        this.affectedRow = affectedRow;
        this.error = error;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getHandIdx() {
        return handIdx;
    }

    public void setHandIdx(int handIdx) {
        this.handIdx = handIdx;
    }

    public int getAffectedRow() {
        return affectedRow;
    }

    public void setAffectedRow(int affectedRow) {
        this.affectedRow = affectedRow;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

class GetCardsInHandOutput {
    private String command;
    private int playerIdx;
    private ArrayList<CardOutput> output;

    public GetCardsInHandOutput (String command, int playerIdx, ArrayList<CardOutput> output) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public ArrayList<CardOutput> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<CardOutput> output) {
        this.output = output;
    }
}

class GetPLayerDeckOutput {
    private String command;
    private int playerIdx;
    private ArrayList<CardOutput> output;

    public GetPLayerDeckOutput (String command, int playerIdx, ArrayList<CardOutput> output) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public ArrayList<CardOutput> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<CardOutput> output) {
        this.output = output;
    }
}

class GetCardOnTableOutput {
    private String command;
    private ArrayList<ArrayList<MinionOutput>> output;

    public GetCardOnTableOutput (String command, ArrayList<ArrayList<MinionOutput>> output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ArrayList<ArrayList<MinionOutput>> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<ArrayList<MinionOutput>> output) {
        this.output = output;
    }
}

class GetPlayerTurnOutput {
    private String command;
    private int output;

    public GetPlayerTurnOutput(String command, int output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}

class GetPlayerHeroOutput {
    private String command;
    private int playerIdx;
    private HeroOutput output;

    public GetPlayerHeroOutput(String command, int playerIdx, HeroOutput output) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public HeroOutput getOutput() {
        return output;
    }

    public void setOutput(HeroOutput output) {
        this.output = output;
    }
}


class GetCardAtPositionOutputSuccess {
    private String command;
    private MinionOutput output;
    private int x;
    private int y;

    public GetCardAtPositionOutputSuccess(String command, int x, int y, MinionOutput output) {
        this.command = command;
        this.output = output;
        this.x = x;
        this.y = y;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public MinionOutput getOutput() {
        return output;
    }

    public void setOutput(MinionOutput output) {
        this.output = output;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

class GetCardAtPositionOutputError {
    private String command;
    private int x;
    private int y;
    private String output;

    public GetCardAtPositionOutputError(String command, int x, int y, String output) {
        this.command = command;
        this.x = x;
        this.y = y;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String  getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}

class GetPLayerManaOutput {
    private String command;
    private int playerIdx;
    private int output;

    public GetPLayerManaOutput(String command, int playerIdx, int output) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}

class GetEnvironmentCardsInHandOutput {
    private String command;
    private int playerIdx;
    private ArrayList<EnvironmentOutput> output;

    public GetEnvironmentCardsInHandOutput (String command, int playerIdx, ArrayList<EnvironmentOutput> output) {
        this.command = command;
        this.playerIdx = playerIdx;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getPlayerIdx() {
        return playerIdx;
    }

    public void setPlayerIdx(int playerIdx) {
        this.playerIdx = playerIdx;
    }

    public ArrayList<EnvironmentOutput> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<EnvironmentOutput> output) {
        this.output = output;
    }
}

class GetFrozenCardsOnTableOutput {
    private String command;
    private ArrayList<MinionOutput> output;

    public GetFrozenCardsOnTableOutput(String command, ArrayList<MinionOutput> output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public ArrayList<MinionOutput> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<MinionOutput> output) {
        this.output = output;
    }
}

class GetTotalGamesPlayedOutput {
    private String command;
    private int output;

    public GetTotalGamesPlayedOutput(String command, int output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}

class GetPlayerTotalWinsOutput {
    private String command;
    private int output;

    public GetPlayerTotalWinsOutput(String command, int output) {
        this.command = command;
        this.output = output;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public int getOutput() {
        return output;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}

interface CardOutput{}

class MinionOutput implements CardOutput {
    private int mana;
    private int attackDamage;
    private int health;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public MinionOutput(Minion minion) {
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

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class EnvironmentOutput implements CardOutput {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;

    public EnvironmentOutput(Environment environment) {
        this.mana = environment.getMana();
        this.description = environment.getDescription();
        this.colors = environment.getColors();
        this.name = environment.getName();
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class HeroOutput {
    private int mana;
    private String description;
    private ArrayList<String> colors;
    private String name;
    private int health;

    public HeroOutput(Hero hero) {
        this.mana = hero.getMana();
        this.description = hero.getDescription();
        this.colors = hero.getColors();
        this.name = hero.getName();
        this.health = hero.getHealth();
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
