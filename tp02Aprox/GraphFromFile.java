import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GraphFromFile {
    public static int k;
    public static int getK() {
        return k;
    }

    public static Graph read_graph_file(String fileName) {

        Graph g = new Graph();
        
        String linha;
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            linha = reader.readLine();
            int num[] = extractNumbers(linha);
            k = num[2];
            while ((linha = reader.readLine()) != null) {
                int dados[] = extractNumbers(linha);
                int v1 = dados[0];
                int v2 = dados[1];
                int p = dados[2];
                g.adicionarAresta(v1, v2, p);
                // System.out.println("Origem: " + v1 + ", Destino: " + v2 + ", Peso: " + p);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return g;
    }

    private static int[] extractNumbers(String input) {
        if(input.charAt(0) == ' '){
            input = input.substring(1);
        }
        
        String[] parts = input.split(" ");
        int[] numbers = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {

            numbers[i] = Integer.parseInt(parts[i]);

        }

        return numbers;
    }
}