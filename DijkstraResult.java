import java.util.*;


public class DijkstraResult {
    public Map<Vertex, Integer> distances;
    public Map<Vertex, List<Vertex>> shortestPaths;

    public DijkstraResult() {
        distances = new HashMap<>();
        shortestPaths = new HashMap<>();
    }
}
