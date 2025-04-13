package ai.code;

import org.apache.commons.lang3.builder.*;

public class Node {

    private State state;
    private String operator;
    private Node parent;
    private int recursion;
    private int priority = 0;

    Node(State newState, String operator, Node prevNode, String heurisic) {
        state = newState;
        this.operator = operator;
        parent = prevNode;
        if (parent != null) {
            recursion = parent.getRecursion() + 1;
        } else {
            recursion = 0;
        }
        if (Main.maxRecursionLevel < recursion) { //określenie maksymalnej rekursji
            Main.maxRecursionLevel = recursion;
        }
        if (heurisic != null) {
            if (heurisic.equals("manh")) {
                priority = manhHeuristic();
            } else if (heurisic.equals("hamm")) {
                priority = hammHeuristic();
            }
        }
    }

    private int manhHeuristic() {
        int num = 0;
        int size = state.getBoard().length;
        for (byte i = 0; i < size; i++) {
            if (state.getNumber(i) != 0) { // nie liczymy bloczka 0
                if (state.getNumber(i) != i + 1) { // sprawdzamy czy na danej pozycji jest odpowiednia liczba, czyli np. na 2 pozycji 3
                    int pos = state.getNumber(i) - 1;
                    int div = Main.columns; //przypisanie liczby kolumn ze statycznej zmiennej w Main
                    int x = Math.abs((i % div) - (pos % div)); //sprawdzamy ile w kierunku x roznicy
                    int y = Math.abs((i / div) - (pos / div)); //sprawdzamy ile w kierunku y roznicy
                    num += x + y;
                }
            }
        }
        num += getRecursion();
        return num;
    }

    private int hammHeuristic() {
        int num = 0;
        for (byte i = 0; i < 16; i++) {
            if (state.getNumber(i) != 0) { // nie liczymy bloczka 0
                if (state.getNumber(i) != i + 1) { // sprawdzamy czy na danej pozycji jest odpowiednia liczba, czyli np. na 2 pozycji 3
                    num += 1; //jesli nie to dodajemy do licznika
                }
            }
        }
        num += getRecursion();
        return num;
    }

    public State getState() {
        return state;
    }

    public String getOperator() {
        return operator;
    }

    public Node getParent() {
        return parent;
    }

    public int getRecursion() {
        return recursion;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return new EqualsBuilder().append(state, node.state).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 13).append(state).toHashCode();
    }
}