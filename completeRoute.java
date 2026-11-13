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
}
 