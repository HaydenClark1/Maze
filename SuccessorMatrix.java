
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class SuccessorMatrix {
    private String[] maze;
    private int height;
    private int width;
    private int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
    private char[] directionChars = {'U', 'D', 'L', 'R'};
    private int[][] move;
    private int[][] moves;
    private int[][] heuristic;

    public SuccessorMatrix(int height, int width,String fileName){
        this.height = height;
        this.width = width;
        generateMaze(fileName, height *2 +1);
    }

    public String[][] getSuccessorMatrix(){
        String[][] successorMatrix = new String[this.height][this.width];
            String[] maze = this.maze;
            for(int i = 0; i < this.height; i++){
                for(int j = 0; j < this.width; j++){
                    StringBuilder successors = new StringBuilder();
                        //Check Up
                        if (i > 0 && maze[(2*i)].charAt((3*j)+1) == ' '){
                            successors.append(("U"));
                        }
                        // Check Down
                        if (i < this.height && maze[(2*i)+2].charAt((3*j)+1) == ' ') {
                            successors.append("D");
                        }
                        
                        // Check Left
                        if (j > 0 && maze[(2*i)+1].charAt(3*j) == ' ') {
                            successors.append("L");
                        }
                        
                        // Check Right
                        if (j < this.width - 1 && maze[(2*i)+1].charAt((3*j)+3) == ' ') {
                            successors.append("R");
                        }
                        successorMatrix[i][j] = successors.toString();
                    
                }
            }
       

        return successorMatrix;
    }

    private void generateMaze(String fileName, int h){
        try {
            String[] maze = new String[h];
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            for(int i = 0; i < h; ++i){
                maze[i] = br.readLine();
            }
           this.maze = maze;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getActionSequence(String[][] successorMatrix){
        boolean[][] visited = new boolean[this.height][this.width];
        int startY = 0;
        int startX = width / 2;

        //Initialize BFS
        visited[startY][startX] = true;
        Node root = new Node(startX, startY);
        return traverseMaze(root,visited,successorMatrix);
    }
    private String traverseMaze(Node root,boolean[][] visited,String[][] successorMatrix){
        Queue<Node> queue = new LinkedList<>();
        this.move = new int[height][width];
        move[root.getY()][root.getX()] = 1;
        queue.add(root);
        // BFS loop
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int y = current.getY();
            int x = current.getX();
            // If we reach the bottom row, and we can move down
            if (y == height-1) {
                if(isValidMove(y, x, 'D', successorMatrix)){
                    Node endNode = new Node(x,y-1,current,'D');
                    return reconstructPath(endNode);
                }
            }

            // Explore neighbors
            for (int d = 0; d < 4; d++) {
                char direction =  directionChars[d];
                int newX = x + directions[d][0];
                int newY = y + directions[d][1];
                if(!(newY >=0) || !(newX >=0)){
                    continue;
                }
                if (isValidMove(y, x, direction,successorMatrix) && !visited[newY][newX]) { //ADD AND FOR IF VISITED
                    visited[newY][newX] = true;
                    Node newNode = new Node(newX, newY,current,direction);
                    queue.add(newNode);
                    move[newY][newX] = 1;
                }
            }
        }
        return "No path found";
    }

    private boolean isValidMove(int y, int x, char move, String[][] successorMatrix) {
        for(int i = 0; i < successorMatrix[y][x].length(); ++i){
            if(successorMatrix[y][x].charAt(i) == move){
                return true;
            }
        }
        return false;
    }

    private String reconstructPath(Node current) {
        StringBuilder path = new StringBuilder();
        while (current.getParent() != null) {
            if(current.getY() == 1 && current.getX() ==24){
                System.out.println();
            }
            path.append(current.getMove());
            current = current.getParent();

           
        }
        return path.reverse().toString();
    }

    public void printSolution(String actionSequence){
        try {
            String[] maze = getMaze();
            char[][] newMaze = new char[maze.length+1][this.width *3]; 
            for(int j = 0; j < maze.length; ++j){
                newMaze[j] = maze[j].toCharArray();
            }

            FileWriter fw = new FileWriter("solution.txt");
            BufferedWriter bw = new BufferedWriter(fw);

            // Start edditing newMaze
            int x = 24*3 +1;
            int y = 1;
            newMaze[y-1][x] = '@';
            newMaze[y-1][x+1] = '@';

            newMaze[y][x] = '@';
            newMaze[y][x+1] = '@';


            for(int i = 0; i < actionSequence.length(); ++i){
                    //Find new x and y
                    for(int d = 0; d < 4; ++d){
                        if(directionChars[d] == actionSequence.charAt(i)){
                            switch(d){
                                case(0): // UP
                                    newMaze[y-1][x] = '@';
                                    newMaze[y-1][x+1] = '@';
                                    y = y - 2;
                                    break;
                                case(1): // DOWN
                                    newMaze[y+1][x] = '@';
                                    newMaze[y+1][x+1] = '@';
                                    y = y + 2;
                                    break;
                                case(2): // LEFT
                                    x = x - 3;
                                    break;
                                case(3): // RIGHT
                                    x = x + 3;
                                    break;
                            }
                            break;
                        }
                    }
                  if(y == 103){
                    System.out.println();
                  }
                    newMaze[y][x] = '@';  
                    newMaze[y][x+1] = '@';         
            }
            StringBuilder builder;
            for(int i = 0; i < newMaze.length; ++i){
                builder = new StringBuilder();
                for(int j = 0; j < newMaze[i].length; ++j){
                    builder.append(newMaze[i][j]);
                }
               
                bw.write(builder.toString() +'\n');
            }            
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String[] getMaze(){
        return this.maze;
    }

    public int[][] getMoves(){
        return this.move;
    }

  
    public int[][] DFS(String[][] successorMatrix) {
        boolean[][] visited = new boolean[this.height][this.width];
        int startY = 0;
        int startX = width / 2;

        // Initialize DFS
        visited[startY][startX] = true;
        Node root = new Node(startX, startY);
        Stack<Node> stack = new Stack<>();
        stack.push(root);
        this.moves = new int[height][width];
        moves[startY][startX] = 1;
        privateDFS(stack, visited, successorMatrix);
        return moves;
    }

    private void privateDFS(Stack<Node> stack, boolean[][] visited, String[][] successorMatrix) {
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            int y = current.getY();
            int x = current.getX();

            // If we reach the bottom row
            if (y == height - 1) {
                continue; // Do nothing and keep searching
            }

            // Explore neighbors
            for (int d = 0; d < 4; d++) {
                char direction = directionChars[d];
                int newX = x + directions[d][1];
                int newY = y + directions[d][0];
                if (newY >= 0 && newY < height && newX >= 0 && newX < width && !visited[newY][newX]) {
                    if (isValidMove(y, x, direction, successorMatrix)) {
                        visited[newY][newX] = true;
                        moves[newY][newX] = 1; // Mark as visited
                        Node newNode = new Node(newX, newY, current, direction);
                        stack.push(newNode);
                    }
                }
            }
        }
    }

    public int[][] manhattanDistance(){
        int[][] distances = new int[height][width];
        int finalX = 24;
        int finalY = 50;
        for(int i = 0; i < height; ++i){
            for(int j = 0; j < width; ++j){
                distances[i][j] = Math.abs(i-finalY) + Math.abs(j - finalX);
            }
        }
        return distances;
    }
    
    public int[][] AStar(String[][] successorMatrix) {
        boolean[][] visited = new boolean[this.height][this.width];
        int startY = 0;
        int startX = width / 2;
        int goalY = 50;
        int goalX = 24;

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingDouble(Node::getPriority));
        Node root = new Node(startX, startY, 0, manhattanDistance(startX, startY, goalX, goalY));
        queue.add(root);
        this.heuristic = new int[height][width];
        heuristic[startY][startX] = 1;
        visited[startY][startX] = true;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            int y = current.getY();
            int x = current.getX();
            heuristic[y][x] = 1; // Update the heuristic array when enqueued


                if(y == goalY && x == goalX){
                    heuristic[y][x] = 1;
                    return heuristic;
                }
            

            for (int d = 0; d < 4; d++) {
                char direction = directionChars[d];
                int newX = x + directions[d][0];
                int newY = y + directions[d][1];
                if(!(newY >=0) || !(newX >=0)){
                    continue;
                }
                if (isValidMove(y, x, direction,successorMatrix) && !visited[newY][newX]) {
                        int newCost = current.getCost() + 1;
                        double newPriority = newCost + EDistance(newX, newY, goalX, goalY);
                        Node newNode = new Node(newX, newY, newCost, newPriority, current, direction);
                        queue.add(newNode);
                        visited[newY][newX] = true;
                    }
            }
        }

        return heuristic;
    }
  
    private int manhattanDistance(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }
    private double EDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1-x2,2)+ Math.pow(y1-y2, 2));
    }


}
