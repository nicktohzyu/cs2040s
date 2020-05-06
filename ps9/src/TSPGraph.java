import java.util.ArrayList;
import java.util.Stack;

public class TSPGraph implements IApproximateTSP {

    TSPMap map;
    int numPoints;
    ArrayList<ArrayList<Integer>> adjList;

    // Empty constructor
    TSPGraph() {
    }

    int minKey(double key[], boolean mstSet[]) {
        // Initialize min value
        double min = Double.MAX_VALUE;
        int min_index = -1;

        for (int v = 0; v < numPoints; v++)
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }

        return min_index;
    }

    @Override
    public void MST() {
        adjList = new ArrayList<>(numPoints);
        for (int i = 0; i < numPoints; i++) {
            adjList.add(new ArrayList<Integer>());
        }
        double key[] = new double[numPoints];
        boolean mstSet[] = new boolean[numPoints];
        for (int i = 1; i < numPoints; i++) { //first point has key 0
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        for (int count = 0; count < numPoints - 1; count++) {
            int u = minKey(key, mstSet);
            mstSet[u] = true;
            if (map.getLink(u) >= 0) adjList.get(map.getLink(u)).add(u);
            for (int v = 0; v < numPoints; v++) {
                if (!mstSet[v] && map.pointDistance(u, v) < key[v]) {
                    map.setLink(v, u, false);
                    key[v] = map.pointDistance(u, v);
                }
            }
        }
        int u = minKey(key, mstSet);
        if (map.getLink(u) >= 0) adjList.get(map.getLink(u)).add(u);
    }

    @Override
    public void TSP() {
        MST();
//        boolean visited[] = new boolean[numPoints];
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        int current, previous = 0;
        while (!stack.empty()) {
            current = stack.pop();
            map.setLink(current, previous, false);
            previous = current;

            stack.addAll(adjList.get(current));
        }
        map.setLink(0, previous, false);
    }

    @Override
    public void initialize(TSPMap map) {
        this.map = map;
        numPoints = map.getCount();
    }

    @Override
    public boolean isValidTour() {
        int pointsVisited = 0;
        boolean[] visited = new boolean[numPoints];
        int currentPoint = 0;
        while (pointsVisited++ < numPoints) {
            if (currentPoint < 0 || visited[currentPoint]) {
                return false;
            }
            visited[currentPoint] = true;
            currentPoint = map.getLink(currentPoint);
        }
        return currentPoint == 0;
    }

    @Override
    public double tourDistance() {
        if (!isValidTour()) {
            return -1;
        }
        int distance = 0, currentPoint = 0, nextPoint;

        do {
            nextPoint = map.getLink(currentPoint);
            distance += map.pointDistance(currentPoint, nextPoint);
            currentPoint = nextPoint;
        } while (currentPoint != 0);

        return distance;
    }

    public static void main(String[] args) {
        TSPMap map = new TSPMap(args.length > 0 ? args[0] : "hundredpoints.txt");
        TSPGraph graph = new TSPGraph();
        graph.initialize(map);

//        graph.MST();
//        map.redraw();
//        System.out.println(graph.adjList);
        graph.TSP();
        map.redraw();
        System.out.println(graph.isValidTour());
        System.out.println(graph.tourDistance());
    }
}
