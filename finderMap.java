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
    public char[][] convert(String input){
        rows = 0;
        cols = 0;



        char[][] paths = new char[rows][cols];
        return paths;
    }

    //returns whether a particular location is on a allowable pathway. 
    // That is you are eligible to “visit” or “walk on” this spot.
    public boolean getPath(int X, int Y){
        return (maze[X][Y] != 'x'); //because any character other than x represents a walkable spot
    }
    //notes that a spot has been “visited” or
    //“walked on”. It should not allow you to visit a spot that is not on the pathway.
    public boolean getVisited(int X, int Y){
        return Visted[X][Y];
    }

    public boolean setVisited(int X, int Y){
        Visted[X][Y] = true;
        return true;
    }

    //represents the current state of the maze including where you have/have not visited.
    public void getString(){
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
        finderMap Dave= new finderMap("xxp\nppx\nxpp");
        //System.out.println(rows + " " + cols);
        Dave.getString();
}
}

