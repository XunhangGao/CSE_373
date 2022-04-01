package graphs.shortestpaths;

import graphs.BaseEdge;
import graphs.Graph;
import priorityqueues.DoubleMapMinPQ;
import priorityqueues.ExtrinsicMinPQ;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Computes shortest paths using Dijkstra's algorithm.
 * @see SPTShortestPathFinder for more documentation.
 */
public class DijkstraShortestPathFinder<G extends Graph<V, E>, V, E extends BaseEdge<V, E>>
    extends SPTShortestPathFinder<G, V, E> {

    protected <T> ExtrinsicMinPQ<T> createMinPQ() {
        return new DoubleMapMinPQ<>();
        /*
        If you have confidence in your heap implementation, you can disable the line above
        and enable the one below.
         */
        //return new ArrayHeapMinPQ<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    protected Map<V, E> constructShortestPathsTree(G graph, V start, V end) {
        ExtrinsicMinPQ<V> nextV = createMinPQ();
        Map<V, E> edgeTo = new HashMap<>();
        Map<V, Double> distTo = new HashMap<>();

        if (Objects.equals(start, end)) {
            return edgeTo;
        }

        for (E edge: graph.outgoingEdgesFrom(start)) {
            distTo.put(edge.to(), Double.POSITIVE_INFINITY);
        }
        nextV.add(start, 0.0);
        distTo.put(start, 0.0);

        while (!nextV.isEmpty()) {
            V from = nextV.removeMin();

            if (Objects.equals(from, end)) {
                return edgeTo;
            }

            for (E edge: graph.outgoingEdgesFrom(from)) {
                V to = edge.to();

                if (!distTo.containsKey(to)) {
                    distTo.put(to, Double.POSITIVE_INFINITY);
                }

                double oldDist = distTo.get(to);
                double newDist = distTo.get(from) + edge.weight();
                if (newDist < oldDist) {
                    edgeTo.put(to, edge);
                    distTo.put(to, newDist);

                    if (nextV.contains(to)) {
                        nextV.changePriority(to, newDist);
                    }
                    else {
                        nextV.add(to, newDist);
                    }
                }
            }
        }
        return edgeTo;
    }

    @Override
    protected ShortestPath<V, E> extractShortestPath(Map<V, E> spt, V start, V end) {
        if (Objects.equals(start, end)) {
            return new ShortestPath.SingleVertex<>(start);
        }

        if (!spt.containsKey(end)) {
            return new ShortestPath.Failure<>();
        }

        E edge = spt.get(end);
        List<E> temp = new ArrayList<>();
        while (edge != null) {
            temp.add(0, edge);
            edge = spt.get(edge.from());
        }
        return new ShortestPath.Success<>(temp);
    }
}
