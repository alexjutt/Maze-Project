public class finderMap{
    //public static String[][] maze == new String[][];

    //takes a String and stores the map into the
    //doubly-subscripted array that holds the map data. Remember that “\n” can be stored in
    //strings to designate the next line.

    public static int rows = 1; //fencepost problem, starts at 1 for counting
    public static int cols = 0;
    public static char[][] maze = new char[rows][cols]; //the maze
    public static char[][] backupMaze = new char[rows][cols]; //copy of the original maze that can get referenced
    public static int[][] weights = new int[rows][cols]; //only the weights of each spot
    public static int[][] Visted = new int[rows][cols]; //whether each spot was visited
    public static boolean[][] DeadEnd = new boolean[rows][cols]; //whether each path spot leads to a dead end
    public static int weightSum = 0; //sum total of all steps taken so far
    public static boolean isSolved = false;

    public finderMap(String input){
        //for each instance of "\n" in the input string, increment rows by 1
        input = input.replace("\n", "N");
        //System.out.println(input);
        for(int i = 0; i < input.length(); i++){
            //System.out.println("for");
            if(input.charAt(i) == 'N'){
                rows++;
                //System.out.println(rows);
            }
        }
        input = input.replace("N", ""); //cleaning up
        cols = input.length() / rows;
        
        maze = new char[rows][cols];
        backupMaze = new char[rows][cols];
        Visted = new int[rows][cols];
        DeadEnd = new boolean[rows][cols];
        weights = new int[rows][cols];

        //instatiating the maze
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                maze[i][j] = input.charAt(i*cols + j);
                backupMaze[i][j] = input.charAt(i*cols + j);
            }
        }

        //initializing the weights
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(maze[i][j] >= '0' && maze[i][j] <= '9'){
                    weights[i][j] = maze[i][j] - 48;
                    //System.out.println(weights[i][j]);
                }
                else if(maze[i][j] >= 'a' && maze[i][j] <='f'){
                    weights[i][j] = maze[i][j] - 87;
                    //System.out.println(weights[i][j]);
                }
                else if(maze[i][j] >= 'A' && maze[i][j] <= 'F'){
                    weights[i][j] = maze[i][j] - 55;
                    //System.out.println(weights[i][j]);
                }
                //  fill weights[i][j] with the int version of that number
            }
        }
    }

    //returns whether a particular location is on a allowable pathway. 
    // That is you are eligible to “visit” or “walk on” this spot.
    public static boolean getPath(int X, int Y){
        return (maze[X][Y] != 'x'); //because any character other than x represents a walkable spot
    }

    //return the step # of a spot
    public static int getVisited(int X, int Y){
        return Visted[X][Y];
    }

    public static int getCost(int x, int y){
        return weights[x][y]; 
    }
    //sets the step #
    public static int setVisited(int X, int Y, int visitNum){
        Visted[X][Y] = visitNum;
        return visitNum;
    }

    //represents the current state of the maze including where you have/have not visited.
    public static void getString(){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if(Visted[i][j] >  0){
                    System.out.print(getVisited(i,j));
                }
                else if(backupMaze[i][j] == '!'){
                    System.out.print('!');
                }
                else if(backupMaze[i][j] == 'x'){
                    System.out.print('x');
                }
                else{
                    System.out.print('p');
                }
            }
            System.out.println();
        }      
    }

    public static void cleanMaze(){ //tidies up the maze for printing
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(backupMaze[i][j]=='x'){
                    maze[i][j] = 'x';
                }
            }
        }

    }

    public static void main(String args[]){
        finderMap Dave= new finderMap("x!xxxxx\nx1x888#\nAabBxxx\nFfCcEeD");
        System.out.println("rows: " + rows + ", cols: " + cols);
        System.out.println("maze: ");
        Dave.getString();
        System.out.println("solving maze");
        recursiveFloodSolve(1, 6, 1);
    }

    //solving the maze using flood fill, as specified in goal set 2
    //int x and y are the coordinates where each recursive call references
    public static void recursiveFloodSolve(int x, int y, int stepNum){
        if (!isSolved){
            //System.out.println("visiting: " + x + ", " + y);
            setVisited(x, y, stepNum);
            //check for adjacent '!' spot
            if ((x > 0 && maze[x-1][y] == '!') || 
            (x < cols-1 && maze[x+1][y] == '!') || 
            (y > 0 && maze[x][y-1] == '!') || 
            (y < rows-1 && maze[x][y+1] == '!')){
                cleanMaze();
                getString();

                System.out.println("Maze solved!");
                isSolved = true;
                return;
            }
            //checking left
            if (x > 0 && maze[x-1][y] != 'x' && (getVisited(x-1, y) == 0) && !(DeadEnd[x-1][y])) {
                recursiveFloodSolve(x-1, y, stepNum +1);
            }
            //checking up
            if (y > 0 && maze[x][y-1] != 'x' && (getVisited(x, y-1) == 0) && !(DeadEnd[x][y-1])) {
                recursiveFloodSolve(x, y-1, stepNum +1);
            }
            //checking right
            if (x < cols-1 && maze[x+1][y] != 'x' && (getVisited(x+1, y) == 0) && !(DeadEnd[x+1][y])) {
                recursiveFloodSolve(x+1, y, stepNum +1);
            }
            //checking down
            if (y < rows-1 && maze[x][y+1] != 'x' && (getVisited(x, y+1) == 0) && !(DeadEnd[x][y+1])) {
                recursiveFloodSolve(x, y+1, stepNum +1);
            }
            else{
                maze[x][y] = backupMaze[x][y];
                DeadEnd[x][y] = true;
            }
            //System.out.println("debugging ,last return statement");
            return;
        }
    }
}
