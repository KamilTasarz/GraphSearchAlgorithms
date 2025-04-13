package ai.code;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static int maxRecursionLevel = 0;
    public static int maxRecursionAllowed = 25;
    public static int visitedStatesNumber = 0;
    public static int processedStatesNumber = 0;
    public static int columns = 0;
    public static int rows = 0;


    public static void main(String[] args) {

        if (args.length == 5) {

            String algorithmChoice = args[0];
            String optionChoice = args[1];
            String originalBoardFileName = args[2];
            String solutionFileName = args[3];
            String statisticsFileName = args[4];

            byte[] temp;
            String resultString = new String();
            int code = -1;
            Node resultNode;
            long executionTime;

            try (Scanner br = new Scanner(new FileReader(originalBoardFileName))) {
                int w = br.nextInt();
                int k = br.nextInt();
                Main.columns = k;
                Main.rows = w;
                temp = new byte[w * k];
                int val;
                int iter = 0;
                while(br.hasNext()) {
                    val = br.nextByte();
                    temp[iter] = (byte) val;
                    iter++;
                }

                Graph graph = new Graph(optionChoice, false);
                State state = new State(temp);
                Node node = new Node(state, "", null, null); //heurystyka wezla poczatkowego
                // moze byc nieustalona, poniewaz jest sciagany z kolejki priorytetowej jako pierwszy i nie bedzie porownywany

                Algorithm algorithm = new Algorithm();
                long startTime = System.currentTimeMillis();
                if (algorithmChoice.equals("bfs")) {
                    resultNode = algorithm.bfs(graph, node);
                } else if (algorithmChoice.equals("dfs")) {
                    graph = new Graph(optionChoice, true);
                    resultNode = algorithm.dfs(graph, node);
                } else {
                    resultNode = algorithm.astar(graph, node);
                }
                long endTime = System.currentTimeMillis();
                executionTime = endTime - startTime;
                String result = new String("");
                if (resultNode != null) {
                    while (resultNode.getParent() != null) {
                        result += resultNode.getOperator();
                        resultNode = resultNode.getParent();
                    }
                }
                if (resultNode != null) {
                    resultString = new StringBuilder(result).reverse().toString();
                    code = resultString.length();
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            //zapis rozwiazania
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(solutionFileName))) {
                bw.write(String.valueOf(code));
                if (resultNode != null) {
                    bw.write('\n');
                    bw.write(resultString);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            //zapis statystyk

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(statisticsFileName))) {
                bw.write(String.valueOf(code));
                bw.write('\n');
                bw.write(String.valueOf(Main.visitedStatesNumber));
                bw.write('\n');
                bw.write(String.valueOf(Main.processedStatesNumber));
                bw.write('\n');
                bw.write(String.valueOf(Main.maxRecursionLevel));
                bw.write('\n');
                bw.write(String.valueOf(executionTime/((double) 1000)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } else {
            System.out.println("Niepoprawna ilość argumentów!");
        }
    }


}