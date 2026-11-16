import java.util.ArrayList;
import java.util.Scanner;

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
    public static ArrayList<completeRoute> routes = new ArrayList<>(); //we're gonna add on more routes each completion
    public static int totalWeight = 0;

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
    
    public static char[][] makeSolution(){
        char[][] solution = new char[rows][cols];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if(backupMaze[i][j] == '!'){
                    solution[i][j] = '!';
                }
                else if(Visted[i][j] >  0){
                    //System.out.print(getVisited(i,j));
                    solution[i][j] = toHexDigit(getVisited(i,j));
                }
                else if(backupMaze[i][j] == 'x'){
                    //System.out.print('x');
                    solution[i][j] = 'x';
                }
                else{
                    //System.out.print('p');
                    solution[i][j] = '0';
                }
            }
            //System.out.println();
        }   
        return solution;   
    }

    public static void printString(){
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
                    System.out.print(Integer.toHexString(weights[i][j]));
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
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a maze: ");
        String m1 = sc.next();
        //finderMap Dave= new finderMap("x!xxxxx\nx1x888#\nAabBxxx\nFfCcEeD");
        finderMap Dave= new finderMap(m1);
        System.out.println("rows: " + rows + ", cols: " + cols);
        System.out.println("maze: ");
        Dave.printString();
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
            (x < rows-1 && maze[x+1][y] == '!') || 
            (y > 0 && maze[x][y-1] == '!') || 
            (y < cols-1 && maze[x][y+1] == '!')){
                cleanMaze();
                routes.add(new completeRoute(sumTotalWeights() ,makeSolution())); //storing the next completed route
                //printString();
                //System.out.println(routes.get(0).tWeight + " total weight");
                totalWeight = 0; //resetting for the next path
    
                System.out.println("Maze solved " + routes.size() + " times");

                boolean allRequiredCellsCovered = true;
                for(int i = 0; i < rows && allRequiredCellsCovered; i++){
                    for(int j = 0; j < cols; j++){
                        if(backupMaze[i][j] != '!' && backupMaze[i][j] != '#'
                            && backupMaze[i][j] != 'x' && !DeadEnd[i][j]){
                            boolean coveredByRoute = false;
                            for(completeRoute route : routes){
                                if(Character.digit(route.path[i][j], 16) > 0){
                                    coveredByRoute = true;
                                    break;
                                }
                            }
                            if(!coveredByRoute){
                                allRequiredCellsCovered = false;
                                break;
                            }
                        }
                    }
                }
                if(allRequiredCellsCovered){
                    isSolved = true;
                    System.out.println("All eligible maze cells are covered by saved routes.");
                    for(completeRoute i : routes){
                        System.out.println(i.toString());
                    }
                    return;
                }
                
                //resetting visited
                for(int i = 0; i < Visted.length; i++){
                    for(int j = 0; j < Visted[i].length; j++){
                        Visted[i][j] = 0;
                    }
                }
                //resetting stepnum
                stepNum = 0;

                recursiveFloodSolve(findHashtag()[0],findHashtag()[1],1);
            }
            boolean canLeft = (x > 0 && maze[x-1][y] != 'x'
                && getVisited(x-1, y) == 0 && !DeadEnd[x-1][y]);
            boolean canUp = (y > 0 && maze[x][y-1] != 'x'
                && getVisited(x, y-1) == 0 && !DeadEnd[x][y-1]);
            boolean canRight = (x < rows-1 && maze[x+1][y] != 'x'
                && getVisited(x+1, y) == 0 && !DeadEnd[x+1][y]);
            boolean canDown = (y < cols-1 && maze[x][y+1] != 'x'
                && getVisited(x, y+1) == 0 && !DeadEnd[x][y+1]);

            boolean leftIsNew = (canLeft && !anyRouteHasValueAt(x-1, y, stepNum+1));
            boolean upIsNew = (canUp && !anyRouteHasValueAt(x, y-1, stepNum+1));
            boolean rightIsNew = (canRight && !anyRouteHasValueAt(x+1, y, stepNum+1));
            boolean downIsNew = (canDown && !anyRouteHasValueAt(x, y+1, stepNum+1));
            boolean hasNewSpot = (leftIsNew || upIsNew || rightIsNew || downIsNew);

            if (hasNewSpot) {
                if (leftIsNew) recursiveFloodSolve(x-1, y, stepNum+1);
                if (upIsNew) recursiveFloodSolve(x, y-1, stepNum+1);
                if (rightIsNew) recursiveFloodSolve(x+1, y, stepNum+1);
                if (downIsNew) recursiveFloodSolve(x, y+1, stepNum+1);
            }
            else if (canLeft || canUp || canRight || canDown) {
                if (canLeft) recursiveFloodSolve(x-1, y, stepNum+1);
                if (canUp) recursiveFloodSolve(x, y-1, stepNum+1);
                if (canRight) recursiveFloodSolve(x+1, y, stepNum+1);
                if (canDown) recursiveFloodSolve(x, y+1, stepNum+1);
            }
            else{
                maze[x][y] = backupMaze[x][y];
                DeadEnd[x][y] = true;
            }
            //System.out.println("debugging ,last return statement");
            return;
        }
    }

    public static int sumTotalWeights(){
        totalWeight = 0;
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                if(Visted[i][j] > 0){
                    totalWeight += weights[i][j];
                }
            }
        }
        return totalWeight;
    }

    public static int[] findHashtag(){
        for(int i = 0; i < backupMaze.length; i++){
            for(int j = 0; j < backupMaze[i].length; j++){
                if(backupMaze[i][j] == '#'){
                    return new int[]{i, j};
                }
            }
        }
        System.out.println("findHashtag() error");
        return new int[]{-1,-1};
    }

    public static boolean anyRouteHasValueAt(int row, int col, int value){
        if(row < 0 || row >= rows || col < 0 || col >= cols){
            throw new IndexOutOfBoundsException("Coordinates are outside the maze");
        }

        char hexValue = toHexDigit(value);
        for(completeRoute route : routes){
            if(route.path[row][col] == hexValue){
                return true;
            }
        }
        return false;
    }

    private static char toHexDigit(int value){
        if(value < 0 || value > 15){
            throw new IllegalArgumentException("Path values must be between 0 and 15, hexidecimal");
        }
        return Character.forDigit(value, 16);
    }
}