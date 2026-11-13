public class completeRoute {
    public int tWeight; //the total weight of all traversed spaces
    public int[][] path;

    public completeRoute(int totalWeight, int[][] solvedMaze){
        path = new int[solvedMaze.length][];
        for(int i = 0; i < solvedMaze.length; i++){
            path[i] = solvedMaze[i].clone();
        }
        tWeight = totalWeight;
    }

    public static void toString(completeRoute a){

    }

    public static int get_tWeight(completeRoute a){
        return a.tWeight;
    }
    public static int[][] get_path(completeRoute a){
        return a.path;
    }
}
