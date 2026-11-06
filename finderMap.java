public class finderMap{
    //public static String[][] maze == new String[][];

    //takes a String and stores the map into the
    //doubly-subscripted array that holds the map data. Remember that “\n” can be stored in
    //strings to designate the next line.

    public static int rows = 1; //fencepost problem, starts at 1 for counting
    public static int cols = 0;
    public static char[][] maze = new char[rows][cols];
    public static boolean[][] Visted = new boolean[rows][cols];

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
        input = input.replace("N", "");
        cols = input.length() / rows;
        //System.out.println(input);
        maze = new char[rows][cols];
        Visted = new boolean[rows][cols];

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                maze[i][j] = input.charAt(i*cols + j);
            }
        }
    }

    //creates a char 2d array displaying all walkable spaces in the maze
    public static char[][] convert(String input){
        rows = 0;
        cols = 0;



        char[][] paths = new char[rows][cols];
        return paths;
    }

    //returns whether a particular location is on a allowable pathway. 
    // That is you are eligible to “visit” or “walk on” this spot.
    public static boolean getPath(int X, int Y){
        return (maze[X][Y] != 'x'); //because any character other than x represents a walkable spot
    }
    //notes that a spot has been “visited” or
    //“walked on”. It should not allow you to visit a spot that is not on the pathway.
    public static boolean getVisited(int X, int Y){
        return Visted[X][Y];
    }

    public static boolean setVisited(int X, int Y){
        Visted[X][Y] = true;
        return true;
    }

    //represents the current state of the maze including where you have/have not visited.
    public static void getString(){
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                if(Visted[i][j]){
                    System.out.print("V");
                }
                else{
                    System.out.print(maze[i][j]);
                }
            }
            System.out.println();
        }
    }
    public static void main(String args[]){
        finderMap Dave= new finderMap("x!xxxxx\nxpxppp#\nxpppxxx\nxxxxxxx");
        System.out.println("rows: " + rows + ", cols: " + cols);
        System.out.println("maze: ");
        Dave.getString();
        System.out.println("solving maze");
        recursiveFloodSolve(1, 6);
}

    //solving the maze using flood fill, as specified in goal set 2
    //int x and y are the coordinates where each recursive call references
    public static void recursiveFloodSolve(int x, int y){
        //mark the current position with 'V' ✅
        //if a bordering index contains '!', then the maze is solved; print the maze and return ✅
        //else if there is a left index and it containts 'p':
        // then recursively call the function with the left index
        //else if there is an up index and it containts 'p':
        // then recursively call the function with the up index 
        //else if there is a right index and it containts 'p'"
        // then recursively call the function with the right index
        //else if there is a down index and it containts 'p':
        // then recursively call the function with the down index
        //else
        // return

        setVisited(x, y);
        //check for adjacent '!' spot
        if ((x > 0 && maze[x-1][y] == '!') || (x < rows-1 && maze[x+1][y] == '!') || (y > 0 && maze[x][y-1] == '!') || (y < cols-1 && maze[x][y+1] == '!')){
            getString();
            return;
        }
        //checking left
        if (x > 0 && maze[x-1][y] == 'p' && getVisited(x-1, y)) {
            recursiveFloodSolve(x-1, y);
        }
        //checking up
        if (y > 0 && maze[x][y-1] == 'p' && getVisited(x, y-1)) {
            recursiveFloodSolve(x, y-1);
        }
        //checking right
        if (x < rows-1 && maze[x+1][y] == 'p' && getVisited(x+1, y)) {
            recursiveFloodSolve(x+1, y);
        }
        //checking down
        if (y < cols-1 && maze[x][y+1] == 'p' && getVisited(x, y+1)) {
            recursiveFloodSolve(x, y+1);
        }
        System.out.println("debugging ,last return statement");
        return;
    }
}

