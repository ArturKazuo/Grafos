import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class Kcenters {
    static Graph graph;

    static List<Integer> centers;

    public static List<Integer> solve(Graph g, int k) {
        graph = g;
        // Verificar se o número de centros é válido
        if (k <= 0 || k > graph.getVertices().size()) {
            throw new IllegalArgumentException("Número inválido de centros");
        }

        // Selecionar um vértice aleatório como o primeiro centro
        List<Integer> vertices = graph.getVertices();
        int randomIndex = (int) (Math.random() * vertices.size());
        int firstCenter = vertices.get(randomIndex);

        // Inicializar a lista de centros
        centers = new ArrayList<>();
        centers.add(firstCenter);

        // Encontrar os k-1 centros restantes
        for (int i = 1; i < k; i++) {
            // Encontrar o vértice mais distante dos centros atuais
            int nextCenter = findFarthestVertex(centers);
            centers.add(nextCenter);
        }

        return centers;
    }

    private static int findFarthestVertex(List<Integer> centers) {
        int farthestVertex = -1;
        int maxDistance = Integer.MIN_VALUE;

        // Utilizar uma fila de prioridade para encontrar o vértice mais distante dos
        // centros
        PriorityQueue<Graph.VertexDistancePair> queue = new PriorityQueue<>();
        Set<Integer> visited = new HashSet<>();

        // Inserir todos os centros na fila de prioridade
        for (int center : centers) {
            queue.offer(new Graph.VertexDistancePair(center, 0));
        }

        while (!queue.isEmpty()) {
            Graph.VertexDistancePair pair = queue.poll();
            int vertex = pair.getVertex();
            int distance = pair.getDistance();

            if (visited.contains(vertex)) {
                continue;
            }

            visited.add(vertex);

            if (distance > maxDistance) {
                farthestVertex = vertex;
                maxDistance = distance;
            }

            // Inserir os vértices adjacentes na fila de prioridade
            List<Graph.Aresta> adjacentes = graph.obterAdjacentes(vertex);
            for (Graph.Aresta aresta : adjacentes) {
                int neighbor = aresta.getDestino();
                if (!visited.contains(neighbor)) {
                    int newDistance = distance + aresta.getPeso();
                    queue.offer(new Graph.VertexDistancePair(neighbor, newDistance));
                }
            }
        }

        return farthestVertex;
    }

    public static int getMaxDistance() {
        int maxDistance = -1;

        // Verificar a distância máxima entre um ponto e seu centro mais próximo
        for (int vertex : graph.getVertices()) {
            int minDistance = Integer.MAX_VALUE;
            for (int center : centers) {
                int distance = graph.getShortestDistance(vertex, center);
                
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
            if (minDistance > maxDistance && minDistance != Integer.MAX_VALUE) {
                maxDistance = minDistance;
            }
        }

        return maxDistance;
    }
}