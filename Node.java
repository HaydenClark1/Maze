public class Node {
    private int x;
    private int y;
    private Node parent;
    private char move;
    private int cost;
    private double priority;

    public Node(int x, int y, Node parent,char move){
        this.x = x;
        this.y= y;
        this.parent = parent;
        this.move = move;
    }
    public Node(int x, int y, Node parent,char move,int cost, int priority){
        this.x = x;
        this.y= y;
        this.parent = parent;
        this.move = move;
        this.cost = cost;
    }
    public Node(int x, int y, int cost, int priority) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.priority = priority;
        this.parent = null;
    }
    public Node(int x, int y, int cost, double priority, Node parent, char move) {
        this.x = x;
        this.y = y;
        this.cost = cost;
        this.priority = priority;
        this.parent = parent;
        this.move = move;
    }

    public Node(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
    public Node getParent(){
        return this.parent;
    }
    public char getMove(){
        return this.move;
    }
    public int getCost(){
        return this.cost;
    }

    public double getPriority(){
        return this.priority;
    }
}
