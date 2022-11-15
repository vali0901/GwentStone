package gameEnvironment;

import cards.Card;
import cards.Minion;

public class Row {
    private Minion[] minions;
    private int size = 0;

    public Row() {
        minions = new Minion[5];
    }

    public Minion[] getMinions() {
        return minions;
    }

    public void addMinion(Minion minion) {
        int i;
        for(i = 0; i < 5; i++)
            if(minions[i] == null) {
                minions[i] = minion;
                size++;
                break;
            }
    }

    public void removeMinion(int index) {
        minions[index] = null;
        size--;

        for(int i = index; i < 4; i++) {
            if (minions[i + 1] == null) {
                break;
            }

            Minion aux = minions[i];
            minions[i] = minions[i + 1];
            minions[i + 1] = aux;
        }
    }

    public void removeDeadMinions() {
        for(int i = 0; i < size; i++) {
            if(minions[i].getHealth() <= 0) {
                removeMinion(i);
                i--;
            }
        }
    }

    public Minion getMinion(int index) {
        return minions[index];
    }

    public boolean hasTank() {
        for(int i = 0; i < 5; i++)
            if(minions[i] != null && minions[i].getIsTank())
                return true;
        return false;
    }

    public int getSize() {
        return size;
    }
}
