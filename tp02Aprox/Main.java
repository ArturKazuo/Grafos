import java.io.File;
import java.io.IOException;
import java.util.List;

//A solução aproximada é baseada em um algoritmo guloso que seleciona os centros de forma iterativa. Ele começa selecionando um vértice aleatório como o primeiro centro e, em seguida, encontra os k-1 centros restantes de forma a maximizar a distância mínima entre os centros.
public class Main {
    static int k;

    public static void main(String[] args) {
        String filename = "input.txt";
        Graph graph = teste(filename);
        graph.imprimirGrafo();
        Kcenters kcenter = new Kcenters();

        List<Integer> centers = kcenter.solve(graph, k);
        int a = kcenter.getMaxDistance();
        System.out.println("Raio aproximado: " + a);

        System.out.println("Centros encontrados:");
        for (int center : centers) {
            System.out.println(center);
        }

    }

    static Graph teste(String filename) {
        GraphFromFile g = new GraphFromFile();
        Graph gg = GraphFromFile.read_graph_file(filename);
        k = g.getK();

        return gg;

    }
}
