import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Scanner fileReader = null;
    static LinkedList<Aresta>[] grafo;
    static LinkedList<ArrayList<Aresta>> caminhos;

    public static void addAresta(int origem, int destino, int peso){
        // System.out.println("origem: " + origem);
        // System.out.println("destino: " + destino);
        // System.out.println(origem + " " + destino + " " + peso + " ");
        grafo[origem].add(new Aresta(destino, peso));
    }

    public static int buscaProfundidade(ArrayList<Integer> centros){

        int peso = 0;

        // for(grafo){

        // }

        return peso;
    } 

    public static int getMaisDistante(ArrayList<Integer> centros){

        int menosDistante = 0;
        int[] peso = new int[grafo.length];
        for(int i=0; i<peso.length; i++){
            peso[i] = Integer.MAX_VALUE;
        }

        ArrayList<Aresta> grafoMenosDistante = new ArrayList<Aresta>();

        int j=0;
        System.out.println(grafo.length);
        for(LinkedList<Aresta> linkedA : grafo){
            System.out.println("grafo: " + j);
            if(j == grafo.length){
                break;
            }
            if(centros.contains(j)){

            }
            else{
                for(int i=0; i<linkedA.size(); i++){
                    System.out.println("   " + linkedA.get(i).destino + "   " + linkedA.get(i).peso);
                    if(centros.contains(linkedA.get(i).destino)){
                        // System.out.print(" em centros  ");
                        if(peso[j] >= linkedA.get(i).peso){
                            // System.out.println("  peso menor  ");
                            peso[j] = linkedA.get(i).peso;
                        }
                    }
                }
            }

            j++;
        }

        int maiorPeso = -1;

        int k=1;
        int a=0;

        for( ; k<peso.length; k++){
            // System.out.println(peso[k]);
            if(peso[k] > maiorPeso && peso[k] != Integer.MAX_VALUE){
                a = k;
                maiorPeso = peso[k];
            }
        }



        return a;
    }
    
    static void acharCentros(int n, int k)
    {
        int[] dist = new int[n+1];
        ArrayList<Integer> centers = new ArrayList<>();
        for(int i = 1; i < n; i++) 
        {
            dist[i] = Integer.MAX_VALUE;
        }
    
        // Index of city having the
        // maximum distance to it's
        // closest center
        int max = 1;
        for(int i = 0; i < k; i++)
        {
            // System.out.println("chegou aqui");
            centers.add(max);
            for(int j = 1; j < n && j < grafo[max].size() + 1 ; j++)
            {
                // Updating the distance
                // of the cities to their
                // closest centers
                dist[j] = Math.min(dist[j], grafo[max].get(j-1).peso);
                // System.out.println("chegou aqui 2" + dist[j]);
            }
    
            // Updating the index of the
            // city with the maximum
            // distance to it's closest center
            max = getMaisDistante(centers);
            // System.out.println("max: "+max);
        }
    
        // Printing the maximum distance
        // of a city to a center
        // that is our answer

        for(int i=0; i<dist.length; i++){
            // System.out.println(dist[i]);
        }

        // Printing the cities that
        // were chosen to be made
        // centers

        System.out.println("\n\nraio: " + getMaisDistante(centers));

        for(int i = 0; i < centers.size(); i++) 
        {
            System.out.print(centers.get(i) + " ");
        }
        System.out.print("\n");
    }

    // Driver Code
    public static void main(String[] args) {

        // System.out.println("Digite o arquivo que deseja ler para construir o grafo: ");
        // String fileString = sc.nextLine();

        // File file = new File(fileString);

        long tempoInicial = System.currentTimeMillis();

        File file = new File("pmed1.txt");
        

        try{

            fileReader = new Scanner(file);

            int n = fileReader.nextInt();
            int m = fileReader.nextInt();
            int k = fileReader.nextInt();

            
            grafo = new LinkedList[n+1];

            for(int i=0; i<=n; i++){
                grafo[i] = new LinkedList<Aresta>();
            }

            while(fileReader.hasNext()){
                addAresta(fileReader.nextInt(), fileReader.nextInt(), fileReader.nextInt());
            }

            // for(int i=1; i<n+1; i++){
            //     Iterator iter2 = grafo[i].iterator();
            //     while(iter2.hasNext()){
            //         System.out.println(iter2.next());
            //     }
            // }


            acharCentros(n, k);

            long tempoFinal = System.currentTimeMillis();

            System.out.println("tempo: " + (tempoFinal - tempoInicial));


            sc.close();
            fileReader.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }
}


class Aresta {
    public int peso;
    public int destino;

    Aresta(int destino, int peso){
        this.peso = peso;
        this.destino = destino;
    }

    public void turnToString(){
        System.out.print(" " + peso + " - " + destino + "|");
    }
}

class Vertice {
    public int vertice;
    public int peso;

    Vertice(int vertice, int peso){
        this.vertice = vertice;
        this.peso = peso;
    }
}
