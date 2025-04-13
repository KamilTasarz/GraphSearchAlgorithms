package ai.code;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Graph {

    private boolean reverse;

    private String paremeter;


    Graph(String parameter, boolean reverseNeighbours) {
        this.paremeter = parameter;
        reverse = reverseNeighbours;
    }

    public boolean test(Node testNode) {
        State testState = testNode.getState();
        for (byte i = 0; i < Main.columns * Main.rows - 1; i++) {
                if (testState.getNumber(i) != i + 1) {
                    return false;
                }
        }
        return true;
    }

    public Node[] getNeighbours(Node parent) {


        boolean left = true;
        boolean right = true;
        boolean up = true;
        boolean down = true;
        byte counter = 4;
        State actualState = parent.getState();

        if (actualState.getZeroPosition() % Main.columns == 0) {
            left = false;
            counter--;
        }
        if (actualState.getZeroPosition() <= Main.columns - 1) {
            up = false;
            counter--;
        }
        if (actualState.getZeroPosition() >= (Main.rows - 1) * Main.columns) {
            down = false;
            counter--;
        }
        if (actualState.getZeroPosition() % Main.columns == Main.columns - 1) {
            right = false;
            counter--;
        }

        if (parent.getRecursion() >= Main.maxRecursionAllowed) { //stałą zamiast 25
            counter = 0;
        }

        Node[] nodes = new Node[counter];

        byte childNodesIndex = 0;


        for (int i = 0; i < 4 && childNodesIndex < counter; i++) {

            byte[] tabCopy = new byte[Main.columns * Main.rows];
            System.arraycopy(actualState.getBoard(), 0, tabCopy, 0, Main.rows * Main.columns);
            byte zero = actualState.getZeroPosition();

            if (paremeter.charAt(i) == 'L' && left) {
                tabCopy[zero] = tabCopy[zero - 1];
                tabCopy[zero - 1] = 0;
                State newState = new State(tabCopy);
                Node newNode = new Node(newState, "L", parent, null);
                nodes[childNodesIndex] = newNode;
            } else if (paremeter.charAt(i) == 'R' && right) {
                tabCopy[zero] = tabCopy[zero + 1];
                tabCopy[zero + 1] = 0;
                State newState = new State(tabCopy);
                Node newNode = new Node(newState, "R", parent, null);
                nodes[childNodesIndex] = newNode;
            } else if (paremeter.charAt(i) == 'D' && down) {
                tabCopy[zero] = tabCopy[zero + 4];
                tabCopy[zero + 4] = 0;
                State newState = new State(tabCopy);
                Node newNode = new Node(newState, "D", parent, null);
                nodes[childNodesIndex] = newNode;
            } else if (paremeter.charAt(i) == 'U' && up) {
                tabCopy[zero] = tabCopy[zero - 4];
                tabCopy[zero - 4] = 0;
                State newState = new State(tabCopy);
                Node newNode = new Node(newState, "U", parent, null);
                nodes[childNodesIndex] = newNode;
            } else {
                childNodesIndex--;
            }
            childNodesIndex++;
        }
        if (reverse) {
            List<Node> reversedNeighbours = Arrays.asList(nodes);
            Collections.reverse(reversedNeighbours);
            return reversedNeighbours.toArray(new Node[0]);
        }
        return nodes;
    }

    public Node[] getNeighboursHeuristic(Node parent) {

        boolean left = true;
        boolean right = true;
        boolean up = true;
        boolean down = true;
        byte counter = 4;
        State actualState = parent.getState();

        if (actualState.getZeroPosition() % Main.columns == 0) {
            left = false;
            counter--;
        }
        if (actualState.getZeroPosition() <= Main.columns - 1) {
            up = false;
            counter--;
        }
        if (actualState.getZeroPosition() >= (Main.rows - 1) * Main.columns) {
            down = false;
            counter--;
        }
        if (actualState.getZeroPosition() % Main.columns == Main.columns - 1) {
            right = false;
            counter--;
        }
        Node[] nodes = new Node[counter];


        for (int i = 0; i < counter; i++) {

            byte[] tablica = new byte[Main.columns * Main.rows];
            System.arraycopy(actualState.getBoard(), 0, tablica, 0, Main.columns * Main.rows);
            byte zero = actualState.getZeroPosition();

            if (left) {
                tablica[zero] = tablica[zero - 1];
                tablica[zero - 1] = 0;
                State newState = new State(tablica);
                Node newNode = new Node(newState, "L", parent, paremeter);
                nodes[i] = newNode;
                left = false;
            } else if (right) {
                tablica[zero] = tablica[zero + 1];
                tablica[zero + 1] = 0;
                State newState = new State(tablica);
                Node newNode = new Node(newState, "R", parent, paremeter);
                nodes[i] = newNode;
                right = false;
            } else if (up) {
                tablica[zero] = tablica[zero - 4];
                tablica[zero - 4] = 0;
                State newState = new State(tablica);
                Node newNode = new Node(newState, "U", parent, paremeter);
                nodes[i] = newNode;
                up = false;
            } else if (down) {
                tablica[zero] = tablica[zero + 4];
                tablica[zero + 4] = 0;
                State newState = new State(tablica);
                Node newNode = new Node(newState, "D", parent, paremeter);
                nodes[i] = newNode;
            }
        }

        return nodes;
    }

}