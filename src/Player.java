import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/

enum Direction{
    RIGHT {
        @Override
        public String getDirection() {
            return "RIGHT";
        }
    },
    LEFT {
        @Override
        public String getDirection() {
            return "LEFT";
        }
    },
    UP {
        @Override
        public String getDirection() {
            return "UP";
        }
    },
    DOWN{
        @Override
        public String getDirection() {
            return "DOWN";
        }
    };
    public abstract String getDirection();
}
enum ORIENT{
    HOR,
    VERT

}
class Wall{
    int wallStartX;
    int wallStartY;

    int wallEndX;
    int wallEndY;

    ORIENT orient;

    public Wall(int x,int y,ORIENT orient){
        wallStartX = x;
        wallStartY = y;
        this.orient = orient;

        if(ORIENT.VERT ==orient){
            wallEndY = wallStartY+1;
            wallEndX = wallStartX;

        }else{
            wallEndY = wallStartY;
            wallEndX = wallStartX+1;

        }

    }

    public boolean expand(int x,int y,ORIENT orient ){
        if(this.orient==orient){

            if(ORIENT.VERT ==orient){
                if(wallEndX+1==x){
                    wallEndX=x+1;
                    return true;
                }
                if(wallStartX==x+2){
                    wallStartX=x;
                    return true;
                }
            }else{
                if(wallEndY+1==y){
                    wallEndY=y+1;
                    return true;
                }
                if(wallStartY==y+2){
                    wallStartY=y;
                    return true;
                }

            }
        }
        return false;

    }
    public boolean canWalk(int x,int y,Direction dir){
        switch(dir){
            case DOWN:
                if( this.orient == ORIENT.VERT){
                    return true;
                }else{

                    if(x>wallEndX||x<wallStartX){
                        return true;
                    }else{

                        if(y==wallStartY-1){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }

            case UP:
                if( this.orient== ORIENT.VERT){
                    return true;
                }else{
                    if(x>wallEndX||x<wallStartX){
                        return true;
                    }else{
                        if(y==wallStartY+1){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }

            case RIGHT:
                if( this.orient== ORIENT.HOR){
                    return true;
                }else{
                    if(y>wallEndY||y<wallStartY){
                        return true;
                    }else{
                        if(x==wallStartX-1){
                            return false;
                        }else{
                           return true;
                        }
                    }
                }

            case LEFT:
                if( this.orient== ORIENT.HOR){
                    return true;
                }else{
                    if(y>wallEndY||y<wallStartY){
                        return true;
                    }else{
                        if(x==wallStartX+1){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }

        }
        return true;

    }
}
class Mover{
    int id;

    Direction dir;

    int x=0;

    int y=0;

    public void setCoor(int x,int y){
        this.x= x;
        this.y= y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }


    public Mover(int id){
        this.id = id;
        resetDirection();
    }
    public void resetDirection(){
        switch(id){
            case 0:

                dir= Direction.RIGHT;
                break;
            case 1:
                dir= Direction.LEFT;
                break;
            case 2:
                dir= Direction.DOWN;
                break;
            default:
                dir= Direction.DOWN;
                break;
        }
    }

    public String getCommand(){
        return dir.getDirection();
    }
    public Direction getDir(){
        return dir;
    }
    public void setDir(Direction dir){
        this.dir= dir;
    }

    public int getFx() {
        switch(id){
            case 0:

                return Player.w-1;

            case 1:
                return 0;
            case 2:
                return x;

        }
        return 0;
    }
    public int getFy() {
        switch(id){
            case 0:

                return y;

            case 1:
                return y;
            case 2:
                return Player.h-1;

        }
        return 0;
    }
}
class SortedList {

    private ArrayList list = new ArrayList();

    public Object first() {
        return list.get(0);
    }


    public void clear() {
        list.clear();
    }


    public void add(Object o) {
        list.add(o);
        Collections.sort(list);
    }

    public void remove(Object o) {
        list.remove(o);
    }


    public int size() {
        return list.size();
    }


    public boolean contains(Object o) {
        return list.contains(o);
    }
}
class NodePath{
    public List<AStarResolver.Node> nodes= new ArrayList<AStarResolver.Node>();


    public NodePath(AStarResolver.Node finish){
        nodes.add(finish);
        AStarResolver.Node node =finish;
        while(node.parent!=null){
            System.err.println("path  " +node.x+" " +node.y);
            nodes.add(node.parent);

            node= node.parent;
        }
        System.err.println("path  " +node.x+" " +node.y);
    }
    public Direction getNextDir(){
        AStarResolver.Node node = nodes.get(nodes.size()-2);
        AStarResolver.Node start = nodes.get(nodes.size()-1);
        System.err.println("start " +start.x+" " +start.y);
        System.err.println("node " +node.x+" " +node.y);
        if(start.y!=node.y){
            if(start.y>node.y){
                return Direction.UP;
            }else{
                return Direction.DOWN;
            }
        }else{

            if(start.x>node.x){
                return Direction.LEFT;
            }else{
                return Direction.RIGHT;
            }
        }
    }

}

class AStarResolver{

    class Node  implements Comparable {
        int x;
        int y;
        private int cost;

        private int heuristic;

        private int depth;

        Node parent;
        List<Node> neibors = new ArrayList<Node>();

        Node(int x,int y){
            this.x= x;
            this.y= y;
        }
        void check(Node neib,Direction dir){
            for(Wall wall : walls){
                if(!wall.canWalk(x,y,dir)){
                    System.err.println("Wall block path form " +x+" " +y +" " +dir.getDirection()) ;
                    System.err.println("Wall " +wall.wallStartX+" " +wall.wallEndX) ;
                    System.err.println("Wall " +wall.wallStartY+" " +wall.wallEndY) ;
                    return;
                }
            }
            neibors.add(neib);
        }
        public void setParent(Node parent){
            this.parent = parent;
        }
        public int compareTo(Object other) {
            Node o = (Node) other;

            float f = heuristic + cost;
            float of = o.heuristic + o.cost;

            if (f < of) {
                return -1;
            } else if (f > of) {
                return 1;
            } else {
                return 0;
            }
        }

        public int distance(Node finish) {
            return Math.abs(finish.x -x)+ Math.abs(finish.y-y);
        }
    }

    public List<Node> allNode = new ArrayList<Node>();

    public List<List<Node>> map = new ArrayList< List<Node>>();

    public List<Wall> walls =  new ArrayList<Wall>();


    public void generate(){
        for(int x= 0;x<Player.w;x++){
            map.add(new ArrayList<Node>());
            for(int y = 0;y<Player.h;y++){
                Node node = new Node(x,y);
                map.get(x).add(node);
                allNode.add(node);

            }
        }



    }
    public void wallCheck(){
        for(Node node :allNode){
            node.neibors.clear();
            if(node.x>1){
                node.check(map.get(node.x-1).get(node.y),Direction.LEFT);
            }
            if(node.x<Player.w-1){
                node.check(map.get(node.x+1).get(node.y),Direction.RIGHT);
            }
            if(node.y>1){
                node.check(map.get(node.x).get(node.y-1),Direction.UP);
            }
            if(node.y<Player.h-1){
                node.check(map.get(node.x).get(node.y+1),Direction.DOWN);
            }
        }
    }


    public NodePath findPath(int x, int y, int fX,int fY){
        for(Node node : allNode){
            node.cost = 0;
            node.heuristic = 0;
            node.parent = null;
        }
        Node start =  map.get(x).get(y);
        Node finish =  map.get(fX).get(fY);
        System.err.println("finding path form " +start.x+" " +start.y);
        System.err.println("finding  path to" +finish.x+" " +finish.y);
        SortedList open  = new SortedList();
        List<Node> close= new ArrayList<Node>();
        start.cost = 0;
        start.heuristic = start.distance(finish);
        open.add(start);
        while( open.size()>0){
            Node current = (Node) open.first();
            if(current==finish){

                return new NodePath(finish);
            }
            System.err.println("path  " +current.x+" " +current.y);
            open.remove(current);
            close.add(current);

            for(Node neighbor :current.neibors){
                if(close.contains(neighbor)){
                    continue;
                }
                int tempg = start.cost + 1;
                if(!open.contains(neighbor)||tempg<neighbor.cost){
                    neighbor.setParent(current);
                    neighbor.cost = tempg;
                    neighbor.heuristic = neighbor.distance(finish);
                    if(!open.contains(neighbor)){
                        open.add(neighbor);
                    }
                }
            }
        }
        System.err.println("Path NUll" );
        return null;
    }

}
class Player {

    public static int w;

    public static int h;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        w = in.nextInt(); // width of the board
        h = in.nextInt(); // height of the board
        int playerCount = in.nextInt(); // number of players (2 or 3)
        int myId = in.nextInt(); // id of my player (0 = 1st player, 1 = 2nd player, ...)
        Mover mover = new Mover(myId);
        AStarResolver pathFinding= new AStarResolver();
        pathFinding.generate();
        // game loop
        while (true) {


            int myX =0;
            int myY =0;
            for (int i = 0; i < playerCount; i++) {

                int x = in.nextInt(); // x-coordinate of the player
                int y = in.nextInt(); // y-coordinate of the player
                if(myId==i){
                    mover.setCoor(x,y);
                }
                int wallsLeft = in.nextInt(); // number of walls available for the player
            }
            mover.resetDirection();
            pathFinding.walls.clear();
            int wallCount = in.nextInt(); // number of walls on the board
            for (int i = 0; i < wallCount; i++) {

                int wallX = in.nextInt(); // x-coordinate of the wall
                int wallY = in.nextInt(); // y-coordinate of the wall
                String wallOrientation = in.next(); // wall orientation ('H' or 'V')
                ORIENT orient;
                if(wallOrientation.equals("V")){
                    orient= ORIENT.VERT;
                }else{
                    orient= ORIENT.HOR;
                }
                for(Wall wall:pathFinding.walls){
                    if(wall.expand(wallX,wallY,orient)){
                        continue;
                    }
                }

                pathFinding.walls.add(new Wall (wallX,wallY,orient));
            }
            pathFinding.wallCheck();
            NodePath path= pathFinding.findPath(mover.x, mover.y, mover.getFx(), mover.getFy());
            if(path!=null){
                mover.setDir(path.getNextDir());
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(mover.getCommand()); // action: LEFT, RIGHT, UP, DOWN or "putX putY putOrientation" to place a wall
        }
    }
}