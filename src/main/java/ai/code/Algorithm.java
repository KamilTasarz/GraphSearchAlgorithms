package ai.code;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class Algorithm {

    public Node bfs(Graph graph, Node startNode) {
        if (graph.test(startNode)) {
            //czy jak od razu znajdzie stan, to to jest odwiedzony i przetworzony czy tylko odwiedzony
            Main.visitedStatesNumber = 1;
            return startNode;
        }
        ArrayDeque<Node> open_list = new ArrayDeque<>();
        HashSet<Node> sumed_list = new HashSet<>();
        open_list.add(startNode);
        sumed_list.add(startNode);
        while (!open_list.isEmpty()) {
            Node nextNode = open_list.pop();
            Node[] nodes = graph.getNeighbours(nextNode);
            for (Node node : nodes) {
                if (!sumed_list.contains(node)) {
                    if (graph.test(node)) {
                        int pom = sumed_list.size();
                        Main.processedStatesNumber = pom - open_list.size();
                        Main.visitedStatesNumber = pom + 1; //odwiedzony stan koncowy nie dodany jeszcze do listy
                        return node;
                    }
                    open_list.add(node);
                    sumed_list.add(node);

                }
            }
        }

        return null;
    }



    public Node dfs(Graph graph, Node startNode) {
        if (graph.test(startNode)) {
            Main.visitedStatesNumber = 1;
            return startNode;
        }
        ArrayDeque<Node> open_list = new ArrayDeque<>(200);
        HashSet<Node> closed_list = new HashSet<>();
        open_list.add(startNode);
        while (!open_list.isEmpty()) {
            Node nextNode = open_list.pollLast();
            if (!closed_list.contains(nextNode)) {
                closed_list.add(nextNode);

                Node[] nodes = graph.getNeighbours(nextNode);
                for (Node node : nodes) {
                    if (graph.test(node)) {
                        Main.processedStatesNumber = closed_list.size();
                        Main.visitedStatesNumber = closed_list.size() + open_list.size() + 1;
                        return node;
                    }
                    if (!closed_list.contains(node)) {
                        open_list.add(node);
                    }
                }
            }
        }
        Main.processedStatesNumber = closed_list.size();
        Main.visitedStatesNumber = closed_list.size();
        return null;
    }

    public Node astar(Graph graph, Node startNode) {
        PriorityQueue<Node> open_list;
        open_list = new PriorityQueue<>(ourComparator);
        HashSet<Node> closed_list = new HashSet<>();
        open_list.add(startNode);
        while (!open_list.isEmpty()) {
            Node nextNode = open_list.poll();
            if (!closed_list.contains(nextNode)) {
                if (graph.test(nextNode)) {
                    Main.processedStatesNumber = closed_list.size();
                    Main.visitedStatesNumber = closed_list.size() + open_list.size() + 1;
                    return nextNode;
                }
                closed_list.add(nextNode);
                Node[] nodes = graph.getNeighboursHeuristic(nextNode);
                for (Node node : nodes) {
                    if (!closed_list.contains(node)) {
                        open_list.add(node);
                    }
                }
            }
        }
        return null;
    }

    private static final Comparator<Node> ourComparator = Comparator.comparingInt(Node::getPriority);

}
