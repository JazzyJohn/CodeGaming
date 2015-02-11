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

        callulateEnd();

    }

    public void callulateEnd() {
        if(ORIENT.VERT ==this.orient){
            wallEndY = wallStartY+1;
            wallEndX = wallStartX;

        }else{
            wallEndY = wallStartY;
            wallEndX = wallStartX+1;

        }
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

                        if(y+1==wallStartY){
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
                        if(y==wallStartY){

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
                        if(x==wallStartX){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }

        }
        return true;

    }

    public void validate() {
        if(orient==ORIENT.VERT) {
            if (wallStartX <= 0) {
                wallStartX = 1;
            }
            if (wallStartX >= Player.w ) {
                wallStartX = Player.w - 1;
            }
            if (wallStartY < 0) {
                wallStartY = 0;
            }
            if (wallStartY >= Player.h - 1) {
                wallStartY = Player.h - 2;
            }
        }else{
            if (wallStartX < 0) {
                wallStartX = 0;
            }
            if (wallStartX >= Player.w - 1) {
                wallStartX = Player.w - 2;
            }
            if (wallStartY <= 0) {
                wallStartY = 1;
            }
            if (wallStartY >= Player.h ) {
                wallStartY = Player.h - 1;
            }
        }
        callulateEnd();
    }

    public boolean botherYou(Wall wall) {
        if(orient==wall.orient) {
            if (orient == ORIENT.VERT) {
                if (wallStartX == wall.wallStartX&&(wallStartY == wall.wallStartY||wallStartY == wall.wallEndY||wallEndY==wall.wallStartY)){

                    return true;
                }
            }else{
                if (wallStartY == wall.wallStartY&&(wallStartX == wall.wallStartX||wallStartX == wall.wallEndX||wallEndX==wall.wallStartX)){
                    return true;
                }
            }
        }else{
            if(orient==ORIENT.VERT){
                if((wallStartX -1==wall.wallStartX)&&(wallStartY+1==wall.wallStartY)){
                    return true;
                }
            }else{
                if((wall.wallStartX -1==wallStartX)&&(wall.wallStartY+1==wallStartY)){
                    return true;
                }
            }

        }
        return false;
    }

    public boolean move(Wall wall, int x,int y, Direction dir) {
        if(orient==wall.orient){
            if (orient == ORIENT.VERT) {
                if(wallStartY == wall.wallStartY){
                    if(wallStartY-2>=0){
                        wallStartY-=2;
                        return true;
                    }
                    if(wallStartY+2<=Player.h-2){
                        wallStartY+=2;
                        return true;
                    }
                    return false;
                }
                if(wallStartY == wall.wallEndY){

                    if(wallStartY+1<=Player.h-2){
                        wallStartY+=1;
                        return true;
                    }
                    return false;
                }
                if(wallEndY == wall.wallStartY){

                    if(wallStartY-1>=0){
                        wallStartY-=1;
                        return true;
                    }
                    return false;
                }
            }else{
                if(wallStartX == wall.wallStartX){
                    if(wallStartX-2>=0){
                        wallStartX-=2;
                        return true;
                    }
                    if(wallStartX+2<Player.w-2){
                        wallStartX+=2;
                        return true;
                    }
                    return false;
                }
                if(wallStartX == wall.wallEndX){

                    if(wallStartX+1<Player.h-2){
                        wallStartX+=1;
                        return true;
                    }
                    return false;
                }
                if(wallEndX == wall.wallStartX){

                    if(wallStartX-1>=0){
                        wallStartX-=1;
                        return true;
                    }
                    return false;
                }
            }
        }else{
            //TODO FROM MOVE LOGIC
            if(orient==ORIENT.VERT){
                if(wallStartY==y){
                    if(wallStartY-1>=0){
                        wallStartY-=1;
                        return true;
                    }
                }
                if(wallEndY==y){
                    if(wallStartY+1<Player.h-1){
                        wallStartY+=1;
                        return true;
                    }
                }


                return false;
            }else{
                if(wallStartX ==x){
                    if(wallStartX-1>=0){
                        wallStartX-=1;
                        return true;
                    }
                }
                if(wallEndX ==x){
                    if(wallStartX+1<Player.h-1){
                        wallStartX+=1;
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
class Mover implements Comparable {


    enum ACTION {MOVE, WALL};

    ACTION action;

    int strikeCounter =0;

    NodePath lastPath;

    int id;

    Direction dir;

    int move =0;

    int x=0;

    int y=0;

    private boolean active;

    int wallCnt = 0;

    public void setWallCnt(int wallCnt) {
        if(this.wallCnt!=0&&this.wallCnt!=wallCnt){
            setAction(ACTION.WALL);
        }else{
            setAction(ACTION.MOVE);
        }
        this.wallCnt = wallCnt;
    }

    public void setAction(ACTION action) {
        if(action!=this.action){
            strikeCounter=0;
            this.action = action;
        }else{
            strikeCounter++;
        }


    }

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
    public boolean halfWay(){
        switch(id){
            case 0:
            case 1:
                return move>Player.w/2;



            case 2:
                return move>Player.h/2;
            default:
                return false;
        }
    }

    public String getCommand(){
        move++;
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

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    @Override
    public int compareTo(Object o) {
        Mover other = (Mover) o;

        float f =  lastPath.nodes.size();
        float of = other.lastPath.nodes.size();

        if (f < of) {
            return -1;
        } else if (f > of) {
            return 1;
        } else {
            return 0;
        }
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
    public ArrayList getList(){
        return list;
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
        // System.err.println("PAth!"+node.x+" "+node.y +" ");
        while(node.parent!=null){

            nodes.add(node.parent);
            //System.err.println("PAth!"+node.x+" "+node.y +" ");
            node= node.parent;
        }
        //System.err.println("PAth!"+node.x+" "+node.y +" ");
    }
    public Direction getNextDir(){
        AStarResolver.Node node = nodes.get(nodes.size()-2);
        AStarResolver.Node start = nodes.get(nodes.size()-1);
        //System.err.println("PAth!"+node.y+" "+start.y +" ");

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

    public Direction getAfterNext() {
        if(nodes.size()>2){
            AStarResolver.Node node = nodes.get(nodes.size()-3);
            AStarResolver.Node start = nodes.get(nodes.size()-2);
            //System.err.println("PAth!"+node.y+" "+start.y +" ");

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
        return null;
    }

    public AStarResolver.Node getNextNode() {
        return nodes.get(nodes.size()-2);
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

        public boolean isHasCost(){
            switch(mId){
                case 0:
                    if(x==Player.w-1){

                        return false;
                    }
                    break;
                case 1:
                    if(x==0){

                        return false;
                    }
                    break;
                case 2:
                    if(y==Player.h-1){

                        return false;
                    }
                    break;

            }
            return true;
        }

        public int distance(Node finish) {
            switch(mId){
                case 0:

                    return Math.abs(finish.x -x);


                case 1:

                    return Math.abs(finish.x -x);

                case 2:

                    return Math.abs(finish.y -y);



            }
            return Math.abs(finish.x -x)+ Math.abs(finish.y-y);
        }

    }

    public List<Node> allNode = new ArrayList<Node>();

    public List<List<Node>> map = new ArrayList< List<Node>>();

    public List<Wall> walls =  new ArrayList<Wall>();

    public boolean canMove(int x, int y, Direction dir){
        for(Wall wall : walls){
            if(!wall.canWalk(x,y,dir)){

                return false;
            }
        }
        return true;
    }

    private int mId =0;

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
            if(node.x>0){
                node.check(map.get(node.x-1).get(node.y),Direction.LEFT);
            }
            if(node.x<Player.w-1){
                node.check(map.get(node.x+1).get(node.y),Direction.RIGHT);
            }
            if(node.y>0){
                node.check(map.get(node.x).get(node.y-1),Direction.UP);
            }
            if(node.y<Player.h-1){
                node.check(map.get(node.x).get(node.y+1),Direction.DOWN);
            }


        }

    }


    public NodePath findPath(int x, int y, int fX,int fY,int mId){
        this.mId  = mId;
        for(Node node : allNode){
            node.cost = 0;
            node.heuristic = 0;
            node.parent = null;
        }
        Node start =  map.get(x).get(y);
        Node finish =  map.get(fX).get(fY);

        SortedList open  = new SortedList();
        List<Node> close= new ArrayList<Node>();
        start.cost = 0;
        start.heuristic = start.distance(finish);
        if(mId==2){
            //   System.err.println("Start?"+start.x+" "+start.y +" ");
            // System.err.println("Finish?"+finish.x+" "+finish.y +" ");
        }
      /* */
        open.add(start);
        while( open.size()>0){
            Node current = (Node) open.first();
            if(checkFinish(current,finish)){
                // System.err.println("NodePath?");
                return new NodePath(current);
            }

            open.remove(current);
            close.add(current);

            for(Node neighbor :current.neibors){
                if(close.contains(neighbor)){
                    continue;
                }

                int tempg = current.cost;
                if(neighbor.isHasCost()){
                    tempg    += 1;
                }

                if(!open.contains(neighbor)||tempg<neighbor.cost){
                    neighbor.setParent(current);
                    neighbor.cost = tempg;
                    neighbor.heuristic = neighbor.distance(finish);
                  /*  if(mId!=1){
                        System.err.println("PAth?"+neighbor.x+" "+neighbor.y +" "+neighbor.heuristic +" "+neighbor.cost);
                    }*/
                    if(!open.contains(neighbor)){
                        open.add(neighbor);
                    }
                }
            }
        }
      //  System.err.println("NULL PAth");
        return null;
    }
    public boolean checkFinish(Node current,Node finish){
        switch(mId){
            case 0:
            case 1:

                return current.x ==finish.x;




            case 2:

                return current.y ==finish.y;



        }
        return true;
    }

}
class WallPlacer{

    enum WALLRESULT{NONE,BAD, TOOEARLY,PLACED}

    private static final int GOOD_MOVE_DIFF=3;

    private static final boolean IS_TRY_NEXT = false;

    WALLRESULT   result =WALLRESULT.NONE;

    List<Mover>  enemys;

    Mover mover;

    Mover enemy;

    int x;

    int y;

    Direction dir;

    ORIENT orient;

    int beforeWallMy;

    int beforeWallEnemy;

    NodePath newMoverPath;

    List<NodePath> enemysPaths = new ArrayList<NodePath>();

    NodePath enemyPath;

    WallPlacer( List<Mover>  enemys,Mover enemy,Mover mover){
        this.enemys = enemys;
        this.mover = mover;
        this.dir = enemy.lastPath.getNextDir();
        this.enemy = enemy;

    }

    boolean placeWall(){
        beforeWallMy =mover.lastPath.nodes.size();
        beforeWallEnemy =enemy.lastPath.nodes.size();
        // System.err.println("War?"+enemy.id +" "+this.dir);

        constructWall(enemy.x,enemy.y);
        if(result==WALLRESULT.BAD&&IS_TRY_NEXT){
            this.dir =enemy.lastPath.getAfterNext();
            if(this.dir!=null){
                AStarResolver.Node node = enemy.lastPath.getNextNode();
                constructWall(node.x,node.y);
            }

        }
        if( result == WALLRESULT.PLACED){
            return true;
        }else{
            return false;
        }


    }
    private void constructWall(int startX,int startY){
        int wallX =startX;
        int wallY =startY;
        this.orient = ORIENT.HOR;
        switch(this.dir){
            case DOWN:
                wallY+=1;
                if(!Player.pathFinding.canMove(startX,startY,Direction.RIGHT)&&Player.pathFinding.canMove(startX,startY,Direction.LEFT)){
                    wallX-=1;
                }

                break;


            case UP:
                if(!Player.pathFinding.canMove(startX,startY,Direction.RIGHT)&&Player.pathFinding.canMove(startX,startY,Direction.LEFT)){
                    wallX-=1;
                }
                break;

            case RIGHT:
                wallX+=1;
                if(!Player.pathFinding.canMove(startX,startY,Direction.DOWN)&&Player.pathFinding.canMove(startX,startY,Direction.UP)){
                    wallY-=1;
                }
                this.orient= ORIENT.VERT;
                break;

            case LEFT:
                if(!Player.pathFinding.canMove(startX,startY,Direction.DOWN)&&Player.pathFinding.canMove(startX,startY,Direction.UP)){
                    wallY-=1;
                }
                this.orient= ORIENT.VERT;
                break;
        }

        _placeWall(wallX,wallY);
    }
    private  boolean checkAgainstWalls( Wall addon){
        return _checkAgainstWalls(addon,0);

    }
    private  boolean _checkAgainstWalls( Wall addon,int count){
        count++;
        if(count>2){
            // System.err.println("_checkAgainstWalls recursion");
            return false;
        }
        for(Wall wall :Player.pathFinding.walls){
            if(wall.botherYou(addon)){
               // System.err.println("PlaceWall botherYou "+addon.wallStartX+" "+addon.wallStartY+" "+addon.orient+ " "+wall.wallStartX+" "+wall.wallStartY +" "+wall.orient);
                if(!addon.move(wall,x,y,dir)){
                    //System.err.println("PlaceWall move "+addon.wallStartX+" "+addon.wallStartY);
                    return false;
                }
                addon.callulateEnd();
                return _checkAgainstWalls(addon,count);
            }
        }
        return true;
    }
    private void _placeWall(int x, int y) {
        this.x = x;
        this.y = y;

        Wall addon =new Wall (x,y,orient);
        addon.validate();
        // System.err.println("PlaceWall "+x+" "+y);
        //  System.err.println("PlaceWall valide "+addon.wallStartX+" "+addon.wallStartY);
        char commandOrient;
        if(ORIENT.HOR==orient){
            commandOrient='H';
        }else{
            commandOrient='V';
        }

        if(!checkAgainstWalls(addon)){
            //  System.err.println("wall false");
            result = WALLRESULT.BAD;
            return;
        }

        //System.err.println("PlaceWall valide "+addon.wallStartX+" "+addon.wallStartY);
        Player.pathFinding.walls.add(addon);
        Player.pathFinding.wallCheck();
        boolean canPlace =true;
        //System.err.println("AfterWall check");

        for(Mover enemyone : enemys){
            if(!enemyone.isActive()){
                continue;
            }
            // System.err.println("War?"+enemy.id);
            NodePath lastPath =Player.pathFinding.findPath(enemyone.x,enemyone.y,enemyone.getFx(),enemyone.getFy(),enemyone.id);
            if( lastPath ==null){
                canPlace=false;
                break;
            }
            enemysPaths.add(lastPath);
            if(enemy==enemyone){
                enemyPath = lastPath;
            }
        }

        newMoverPath =Player.pathFinding.findPath(mover.x,mover.y,mover.getFx(),mover.getFy(),mover.id);


        Player.pathFinding.walls.remove(addon);
        Player. pathFinding.wallCheck();
        if(!canPlace||newMoverPath==null){
            //   System.err.println("lastPath null");

            result = WALLRESULT.BAD;
            return;
        }


        if(isGoodMove()){
            mover.setAction( Mover.ACTION.WALL);
            System.out.println(addon.wallStartX +" "+addon.wallStartY +" "+commandOrient+" "+GetPhrase());
            result = WALLRESULT.PLACED;
        }
    }
    public boolean isGoodMove(){


        int diff =beforeWallEnemy-beforeWallMy;
        int diffNow = enemyPath.nodes.size()-newMoverPath.nodes.size();
        if(beforeWallEnemy==enemyPath.nodes.size()){
            //  System.err.println("useless");
            result = WALLRESULT.BAD;
            return false;
        }

        if(diff+GOOD_MOVE_DIFF<diffNow){
            result = WALLRESULT.PLACED;
            return true;
        }
        if(  newMoverPath.nodes.size()!=mover.lastPath.nodes.size()){
            //  System.err.println("bad way");
            result = WALLRESULT.BAD;
            return false;
        }
        if(mover.halfWay()){
            switch(mover.id){
                case 0:
                    if(beforeWallMy>beforeWallEnemy){
                        result = WALLRESULT.PLACED;
                        return true;
                    }
                    break;
                case 1:
                case 2:
                    if(beforeWallMy>=beforeWallEnemy){
                        result = WALLRESULT.PLACED;
                        return true;
                    }
                    break;
            }
        }
        //  System.err.println("no way");
        result = WALLRESULT.TOOEARLY;
        return false;
    }
    static String[] phrase = new String[]{"Stop Right There!!","LOL","LOOK OUT!","IT'S Not ME"};

    public static String GetPhrase(){
        return phrase[(int)(Math.random()*(float)phrase.length)];
    }


}
class Player {

    public static int w;

    public static int h;

    static AStarResolver pathFinding= new AStarResolver();

    static List<Mover>  enemys=  new ArrayList<Mover>();

    static Mover mover;


    public static boolean warAction(){

        SortedList enemysSort = new SortedList();
        for(Mover enemy : enemys){
            if(!enemy.isActive()){
                continue;
            }
            if(enemy.action== Mover.ACTION.WALL){
                continue;
            }
            // System.err.println("War?"+enemy.id);
            enemy.lastPath =pathFinding.findPath(enemy.x,enemy.y,enemy.getFx(),enemy.getFy(),enemy.id);
            enemysSort.add(enemy);
        }
        int tempCost =Integer.MIN_VALUE;



        for(Object obj : enemysSort.getList()){
            Mover enemy = (Mover)obj;
            if(!enemy.isActive()){
                continue;
            }
            WallPlacer placer = new WallPlacer(enemys,enemy,mover);
            if(placer.placeWall()){
                return true;
            }
            // System.err.println("War?"+enemy.id);
            //System.err.println("War?"+enemy.id+" "+enemy.lastPath.nodes.size()+" "+mover.lastPath.nodes.size());

        }
        return false;



    }



    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        w = in.nextInt(); // width of the board
        h = in.nextInt(); // height of the board
        int playerCount = in.nextInt(); // number of players (2 or 3)
        int myId = in.nextInt(); // id of my player (0 = 1st player, 1 = 2nd player, ...)
        mover = new Mover(myId);


        pathFinding.generate();
        for (int i = 0; i < playerCount; i++) {
            if (myId == i) {
                continue;
            }
            enemys.add(new Mover(i));


        }
        // game loop
        while (true) {

            for (Mover enemy : enemys) {
                enemy.setActive(false);
            }

            int myX = 0;
            int myY = 0;
            int enemyId = 0;
            int myWallCnt = 0;
            for (int i = 0; i < playerCount; i++) {

                int x = in.nextInt(); // x-coordinate of the player
                int y = in.nextInt(); // y-coordinate of the player
                int wallsLeft = in.nextInt(); // number of walls available for the player
                if (myId == i) {
                    mover.setCoor(x, y);
                    myWallCnt = wallsLeft;
                } else {
                    enemys.get(enemyId).setCoor(x, y);
                    enemys.get(enemyId).setWallCnt(wallsLeft);
                    if(x==-1&&y==-1&&wallsLeft==-1){
                        enemys.get(enemyId).setActive(false);
                    }else{
                        enemys.get(enemyId).setActive(true);
                    }
                    enemyId++;

                }

            }
            mover.resetDirection();
            pathFinding.walls.clear();
            int wallCount = in.nextInt(); // number of walls on the board
            for (int i = 0; i < wallCount; i++) {

                int wallX = in.nextInt(); // x-coordinate of the wall
                int wallY = in.nextInt(); // y-coordinate of the wall
                String wallOrientation = in.next(); // wall orientation ('H' or 'V')
                ORIENT orient;
                if (wallOrientation.equals("V")) {
                    orient = ORIENT.VERT;
                } else {
                    orient = ORIENT.HOR;
                }


                pathFinding.walls.add(new Wall(wallX, wallY, orient));
            }
            pathFinding.wallCheck();
            mover.lastPath = pathFinding.findPath(mover.x, mover.y, mover.getFx(), mover.getFy(), mover.id);
            if (mover.lastPath != null) {
                mover.setDir(mover.lastPath.getNextDir());
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            if (myWallCnt == 0 || !warAction()) {
                mover.setAction(Mover.ACTION.MOVE);
                System.out.println(mover.getCommand()); // action: LEFT, RIGHT, UP, DOWN or "putX putY putOrientation" to place a wall
            }
        }
    }
}