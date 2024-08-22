import java.io.BufferedWriter;
import java.io.FileWriter;

public class A6{
    
    public static void main(String[] args) {
        try{
        int height = 51;
        int width = 49;
        FileWriter fr = new FileWriter("successorMatrix.txt");
        BufferedWriter br = new BufferedWriter(fr);
        SuccessorMatrix successorMatrix = new SuccessorMatrix(height,width,"maze.txt");
        String[][] matrix = successorMatrix.getSuccessorMatrix();
        for(String[] row: matrix){
            for(String item: row){
                if(item != null){
                    br.write(item +",");
                }else{
                }
            }
            br.newLine();
        }
         br.close();
         fr = new FileWriter("actionSequence.txt");
         br = new BufferedWriter(fr);
         String sequence = successorMatrix.getActionSequence(matrix);
         br.write(sequence);
         br.close();
         successorMatrix.printSolution(sequence);

         fr = new FileWriter("moves.txt");
         br = new BufferedWriter(fr);
        int[][] moves = successorMatrix.getMoves();
        for(int[] array: moves){
            for(int move: array){
                br.write(""+move + ",");
            }
            br.newLine();
        }
        br.close();

        fr = new FileWriter("movesDFS.txt");
        br = new BufferedWriter(fr);
       int[][] movesDFS = successorMatrix.DFS(matrix);
       for(int[] array: movesDFS){
           for(int move: array){
               br.write(""+move + ",");
           }
           br.newLine();
       }
       br.close();
       fr = new FileWriter("manhattanDistances.txt");
       br = new BufferedWriter(fr);
       int[][] distances = successorMatrix.manhattanDistance();
       for(int[] array: distances){
            for(int distance: array){
                br.write(distance +",");
            }
            br.newLine();
       }
       br.close();
       fr = new FileWriter("heuristic.txt");
       br = new BufferedWriter(fr);
       int[][] heuristic = successorMatrix.AStar(matrix);
       for(int[] array: heuristic){
        for(int distance: array){
            br.write(distance +",");
        }
        br.newLine();
         }
        br.close();
    }catch(Exception e){
        e.printStackTrace();
    }
    }

}