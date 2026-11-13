public class completeRoute {
    public int tWeight;
    public int[][] path;

    public completeRoute(int[][] solvedMaze){
        path = new int[solvedMaze.length][];
        for(int i = 0; i < solvedMaze.length; i++){
            path[i] = new int[solvedMaze[i].length];
            for(int j = 0; j < solvedMaze[i].length; j++){
                path[i][j] = solvedMaze[i][j];
                if(solvedMaze[i][j]>=0){
                    tWeight += solvedMaze[i][j];
                }
            }
        }
    }
}
