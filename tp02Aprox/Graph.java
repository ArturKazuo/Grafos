import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Graph {

    private Map<Integer, List<Aresta>> grafo;

    public Graph() {
        this.grafo = new HashMap<>();
    }

    public void adicionarAresta(int origem, int destino, int peso) {
        Aresta aresta = new Aresta(destino, peso);

        if (grafo.containsKey(origem)) {
            grafo.get(origem).add(aresta);
        } else {
            List<Aresta> arestas = new ArrayList<>();
            arestas.add(aresta);
            grafo.put(origem, arestas);
        }
    }

    public List<Aresta> obterAdjacentes(int vertice) {
        return grafo.get(vertice);
    }

    public int obterPesoAresta(int origem, int destino) {
        List<Aresta> arestas = grafo.get(origem);
        for (Aresta aresta : arestas) {
            if (aresta.getDestino() == destino) {
                return aresta.getPeso();
            }
        }
        return -1; // Aresta não encontrada
    }

    public List<Integer> getVertices() {
        List<Integer> vertices = new ArrayList<>();
        for (int vertice : grafo.keySet()) {
            vertices.add(vertice);
        }
        return vertices;
    }

    public int getShortestDistance(int origem, int destino) {
        List<Aresta> arestas = grafo.get(origem);
        if (arestas != null) {
            for (Aresta aresta : arestas) {
                if (aresta.getDestino() == destino) {
                    return aresta.getPeso();
                }
            }
        }
        return Integer.MAX_VALUE; // Aresta não encontrada ou distância desconhecida
    }

    public static class Aresta {
        private int destino;
        private int peso;

        public Aresta(int destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }

        public int getDestino() {
            return destino;
        }

        public int getPeso() {
            return peso;
        }
    }

    public static class VertexDistancePair implements Comparable<VertexDistancePair> {
        private int vertex;
        private int distance;

        public VertexDistancePair(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }

        public int getVertex() {
            return vertex;
        }

        public int getDistance() {
            return distance;
        }

        @Override
        public int compareTo(VertexDistancePair other) {
            return Integer.compare(distance, other.distance);
        }

    }

    public void imprimirGrafo() {
        for (int vertice : grafo.keySet()) {
            System.out.print("Vértice " + vertice + ": ");
            List<Aresta> arestas = grafo.get(vertice);
            for (Aresta aresta : arestas) {
                System.out.print(aresta.getDestino() + "(" + aresta.getPeso() + ") ");
            }
            System.out.println();
        }
    }
}