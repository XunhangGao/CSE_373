package graphs.minspantrees;

import disjointsets.DisjointSets;
import disjointsets.QuickFindDisjointSets;
import graphs.BaseEdge;
import graphs.KruskalGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Computes minimum spanning trees using Kruskal's algorithm.
 * @see MinimumSpanningTreeFinder for more documentation.
 */
public class KruskalMinimumSpanningTreeFinder<G extends KruskalGraph<V, E>, V, E extends BaseEdge<V, E>>
    implements MinimumSpanningTreeFinder<G, V, E> {

    protected DisjointSets<V> createDisjointSets() {
        return new QuickFindDisjointSets<>();
        /*
        Disable the line above and enable the one below after you've finished implementing
        your `UnionBySizeCompressingDisjointSets`.
         */
        //return new UnionBySizeCompressingDisjointSets<>();

        /*
        Otherwise, do not change this method.
        We override this during grading to test your code using our correct implementation so that
        you don't lose extra points if your implementation is buggy.
         */
    }

    @Override
    public MinimumSpanningTree<V, E> findMinimumSpanningTree(G graph) {
        // Here's some code to get you started; feel free to change or rearrange it if you'd like.

        // sort edges in the graph in ascending weight order
        List<E> edges = new ArrayList<>(graph.allEdges());
        edges.sort(Comparator.comparingDouble(E::weight));

        DisjointSets<V> disjointSets = createDisjointSets();
        Set<E> finalMST = new HashSet<>();

        int numVertex = 0;
        for (V vertex: graph.allVertices()) {
            numVertex++;
            disjointSets.makeSet(vertex);
        }

        for (E edge: edges) {
            V from = edge.from();
            V to = edge.to();

            if (!Objects.equals(disjointSets.findSet(from), disjointSets.findSet(to))) {
                finalMST.add(edge);
                disjointSets.union(from, to);
            }
        }

        if (finalMST.size() >= numVertex - 1 || (edges.size() == 0 && numVertex < 2)) {
            return new MinimumSpanningTree.Success<>(finalMST);
        }
        else {
            return new MinimumSpanningTree.Failure<>();
        }
    }
}
