public class completeRoute {
    public int tWeight; //the total weight of all traversed spaces
    public char[][] path;

    public completeRoute(int totalWeight, char[][] solvedMaze){
        path = new char[solvedMaze.length][];
        for(int i = 0; i < solvedMaze.length; i++){
            path[i] = solvedMaze[i].clone();
        }
        tWeight = totalWeight;
    }

    public String toString(){
        String result = "";
        for(int i = 0; i < path.length; i++){
            for(int j = 0; j < path[i].length; j++){
                result += path[i][j];
            }
            result += "\n";
        }
        return result;
    }

    public static int get_tWeight(completeRoute a){
        return a.tWeight;
    }
    public static char[][] get_path(completeRoute a){
        return a.path;
    }
}
 