# OOP Homework - GwentStone

## Class descrpition
* **Table** class -> designed as a singleton, it has two fields representing the two **players**,
  one that counts the number of games played and another one that indicates the current
  player's id; the most important methods here are 'startGame' (which calls a method
  that initialises the values of each player) and 'newRound' (which calls methods for 
  adding mana and dealing a new card to each player)
* **Player** class -> contains a deck (ArrayList of **Cards**), hero (of type **Hero**), hand
  (ArrayList of **Cards**), mana, two **rows** (one front row and one back row) which are initialised in 'initPLayer' method; another important method besides
  'initPlayer' is 'endPlayerTurn' which unfreezes all the frozen minions and unmark every
  card and hero that has used its attack / ability
* **Row** class -> contains an array of **Minions**, size and methods that manipulate the minions
  on this row (removing, adding, removing all dead minions)
* **Card** interface -> used for describing the cards in a deck, being implemented by **Minion**
  and **Environment** abstract classes
* **Minion** abstract class -> describes a card that can be placed on a **row** and has the
  following attributes: mana, health, attack damage, name, descriptions, color; it can
  attack ('attack' method, one for a minion and one for a hero), can be attacked
  ('isAttacked' method); also, depending on the type of the minion, it can be 'Tank' 
  ('isTank' field), and can be placed only on the first or the second row ('firstRow' field);
  this class is extended by another abstract class (**SpecialMinion**) and by the four types
  of minions (**Sentinel**, **Berserker**, **Goliath**, **Warden**)
* **SpecialMinion** abstract class -> extends **Minion** and describes a minion that has a special
  ability that affects other minion; it is extended by four classes representing the four
  minions with special abilities (**Disciple**, **TheCursedOne**, **TheRipper**, **Miraj**)
* **Environment** abstract class -> describes a card that cannot be placed on the table, who
  changes the 'environment' of the game; it contains fields like mana, name, description
  and colors, and a method that represent this card's ability which can apply on an enemy
  row or a friendly one; this class is extended by the three environment cards of this game
  (**Firestorm**, **Winterfell**, **HeartHound**)
* **Hero** abstract class -> describes a hero and contains the following fields: health, mana,
  description, name, colors; it can be attacked ('isAttacked' method) or it can use its
  ability which applies on a friendly or an enemy row; the four types of hero in this game
  are represented by the classes that extend this abstract class (**LordRoyce**, **EmpressThorina**,
  **KingMudface**, **GeneralKocioraw**)
* **CardBuilder** and **HeroBuilder** classes -> utility classes, used for creating a new object
  depending on the CardInput
* **ActionMaker** class -> the only method of this class takes the ActionInput and, depending on
  the command it either gives an output or calls the required methods, only after checking
  all the needed invalid cases; it uses **OutputFactory** class for creating an output
* **OutputFactory** class -> it has a method which adds an output in the given ArrayNode; it uses
  specific classes for every type of output
* **MagicNumbers** class -> used for keeping useful constants
  