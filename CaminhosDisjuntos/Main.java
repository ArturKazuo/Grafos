import java.io.File;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.*;
 
@SuppressWarnings({"unchecked"})

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Scanner fileReader = null;
    static LinkedList<Integer>[] grafo;
    static LinkedList<ArrayList<Aresta>> caminhos;

    public static void addAresta(int origem, int destino){
        // System.out.println("origem: " + origem);
        // System.out.println("destino: " + destino);
        grafo[origem].add(destino);
    }

    public static boolean aindaSaida(ArrayList<Aresta> descobertos, int origem){

        boolean bool = true;
        int count = 0;

        LinkedList<Integer> arestasSaida = grafo[origem];


        for(int i=0; i<grafo[origem].size(); i++){
            int destino = arestasSaida.peek();
            if(descobertos.contains(new Aresta(origem, destino))){
                count++;
            }
            arestasSaida.remove();
        }

        if(count == arestasSaida.size()){
            bool = false;
        }

        return bool;
    }

    // public static boolean jaExisteASerexplorado(int vertice, Queue<Vertice> aSerExplorado){

    //     boolean bool = false;

    //     for(Vertice v : aSerExplorado){
    //         if(v.vertice == vertice){
    //             bool = true;
    //         }
    //     }

    //     return bool;

    // }

    public static void acharCaminhos(int origem, int destino, int m){

        ArrayList<Aresta> descobertos = new ArrayList<>();  
        ArrayList<Aresta> descobertosMasNaoExplorados = new ArrayList<>();  
        Queue<Vertice> aSerExplorados = new LinkedList<>();
        // ArrayList<Aresta> caminhoAtual = new ArrayList<>(); 
        caminhos = new LinkedList<ArrayList<Aresta>>();

        // System.out.println("foi1");

        int atual = origem;

        aSerExplorados.add(new Vertice(atual, 1));

        System.out.println(atual);

        // for(int i : grafo[atual]){
        //     System.out.println("aqui: "+i);
        // }

        // System.out.println("foi2");
 
        while(aindaSaida(descobertos, origem) && aSerExplorados.size() > 0){

            // System.out.println("foi");
            
            Vertice verticeAtual = aSerExplorados.poll();
            atual = verticeAtual.vertice;
            int pesoAtual = verticeAtual.peso; 
            // System.out.println(atual);

            // for(int i : grafo[atual]){
            //     System.out.println("aqui: "+i);
            // }

            grafo[atual].forEach((element) -> System.out.print(element + " "));

            // System.out.println("foi4");
            
            for(int i : grafo[atual]){

                // System.out.println(i);

                // System.out.println("foi5");

                // System.out.println("wdadwna: "+grafo[atual].get(i));

                if(descobertos.contains(new Aresta(atual, i))){ //se já foi descoberto
                    // System.out.println("descoberto: "+ atual);
                }
                else{ //se ainda não foi
                    if(i == destino){
                        // System.out.println("chegou");
                        // for(int j=0; j<descobertosMasNaoExplorados.size(); j++){
                        //     if(!descobertos.contains(descobertosMasNaoExplorados.get(j))){
                        //         descobertos.add(descobertosMasNaoExplorados.get(j));
                        //     }
                        // }
                        descobertos.add(new Aresta(atual, i));
                        caminhos.add(descobertos);
                    }
                    else{
                        // System.out.println("wdadwna");
                        // if(aSerExplorados.contains(new Vertice(i, -1))){
                        // if(jaExisteASerexplorado(i, aSerExplorados)){

                        // }
                        // else{
                            aSerExplorados.add(new Vertice(i, pesoAtual+1));
                            descobertos.add(new Aresta(atual, i));
                        // }
                    }
                }
            }
            
        }

        // System.out.println("foi3");

        System.out.println("O número máximo de caminhos é: " + caminhos.size());

        System.out.println("O(s) caminho(s) é(são): ");

        for(int i=0; i<caminhos.size(); i++){
            ArrayList<Aresta> aresta = caminhos.poll();
            for(int j=0; j<aresta.size(); j++){
                aresta.get(j).turnToString();
            }
            System.out.println();
        }

    }
    
    // Driver Code
    public static void main(String[] args) {

        // System.out.println("Digite o arquivo que deseja ler para construir o grafo: ");
        // String fileString = sc.nextLine();

        // File file = new File(fileString);

        long tempoInicial = System.currentTimeMillis();

        File file = new File("graph-test-100.txt");
        

        try{

            fileReader = new Scanner(file);

            int n = fileReader.nextInt();
            int m = fileReader.nextInt();

            
            grafo = new LinkedList[n+1];

            for(int i=0; i<=n; i++){
                grafo[i] = new LinkedList<Integer>();
            }

            while(fileReader.hasNext()){
                addAresta(fileReader.nextInt(), fileReader.nextInt());
            }

            // for(int i=1; i<n+1; i++){
            //     Iterator iter2 = grafo[i].iterator();
            //     while(iter2.hasNext()){
            //         System.out.println(iter2.next());
            //     }
            // }



            System.out.println("Digite o número do vertice de origem: ");
            int origem = sc.nextInt();

            System.out.println("Digite o número vertice de destino: ");
            int destino = sc.nextInt();


            acharCaminhos(origem, destino, m);

            long tempoFinal = System.currentTimeMillis();

            System.out.println("tempo: " + (tempoFinal - tempoInicial));


            sc.close();
            fileReader.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }


        // Let us create a graph shown in the above example
        // int graph[][] = {{0, 1, 1, 1, 0, 0, 0, 0},
        //                 {0, 0, 1, 0, 0, 0, 0, 0},
        //                 {0, 0, 0, 1, 0, 0, 1, 0},
        //                 {0, 0, 0, 0, 0, 0, 1, 0},
        //                 {0, 0, 1, 0, 0, 0, 0, 1},
        //                 {0, 1, 0, 0, 0, 0, 0, 1},
        //                 {0, 0, 0, 0, 0, 1, 0, 1},
        //                 {0, 0, 0, 0, 0, 0, 0, 0}};
    
        // int s = 0;
        // int t = 7;
        // System.out.println("There can be maximum " +
        //             findDisjointPaths(graph, s, t) +
        //             " edge-disjoint paths from " +
        //                             s + " to "+ t);
    }
}


class Aresta {
    public int origem;
    public int destino;

    Aresta(int origem, int destino){
        this.origem = origem;
        this.destino = destino;
    }

    public void turnToString(){
        System.out.print(" " + origem + " - " + destino + "|");
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




class GFG {

    static Scanner sc = new Scanner(System.in);
    static Scanner fileReader = null;
    public static void main(String[] args){

        LinkedList<Integer>[] listaAdj = null;

        System.out.println("Digite o arquivo que deseja ler para construir o grafo: ");
        String fileString = sc.nextLine();

        File file = new File(fileString);

        try{

            fileReader = new Scanner(file);

            int n = fileReader.nextInt();
            int m = fileReader.nextInt();

            for(int i=0; i<=n; i++){
                listaAdj[i] = new LinkedList<>();
            }

            System.out.println("Digite o número do vertice de origem: ");
            int origem = sc.nextInt();

            System.out.println("Digite o número vertice de destino: ");
            int destino = sc.nextInt();




            sc.close();
            fileReader.close();

        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }
}