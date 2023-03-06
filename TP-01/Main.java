import java.io.File;
import java.io.RandomAccessFile;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args){

        int n, m; // n = numero de vertices, m = numero de arestas
        String file;

        System.out.println("Digite o nome do arquivo a ser lido: ");
        file = sc.nextLine();

        File fileF = new File(file);

        try{

            Scanner scFile = new Scanner(fileF);

            n = scFile.nextInt();
            m = scFile.nextInt();
            

            int[] vertices = new int[n+1]; //array de vertices e arestas para fazer forward start
            int[] arestas = new int[m+1];

            int numArestas = 1, verticeAtual = 1, vertice = 1;
            boolean first = false;

            while(scFile.hasNext()){                    //le o arquivo todo e controi a representação do grafo em forward star
                vertices[verticeAtual] = numArestas;
                vertice = verticeAtual;
                //System.out.println("v: " + verticeAtual);
                while(scFile.hasNext() && verticeAtual == vertice){
                    if(first == false){
                        verticeAtual = scFile.nextInt();
                    }
                    if(verticeAtual != vertice){
                        break;
                    }
                    arestas[numArestas] = scFile.nextInt();
                    //System.out.println("a: " + arestas[numArestas]);
                    numArestas++;
                    first = false;
                }
                first = true;
            }

            scFile.close();


            System.out.println("Digite o número do vértice: ");
            int verticePesquisa = sc.nextInt();

            //grau de saida
            int grauSaida = 0;
            for(int i=vertices[verticePesquisa]; i<vertices[verticePesquisa + 1]; i++){ //percorre todos os vertices para qual o vertice desejado aponta
                grauSaida++;
            }

            //sucessores
            int[] sucessores = new int[(vertices[verticePesquisa + 1] - vertices[verticePesquisa])];
            int j=0;
            for(int i=vertices[verticePesquisa]; i<vertices[verticePesquisa + 1]; i++){ //percorre todos os vertices para qual o vertice desejado aponta
                sucessores[j] = arestas[i];
                j++;
            }

            //grau de entrada
            int grauEntrada = 0;
            //predecessores
            ArrayList<Integer> predecessores = new ArrayList<Integer>();
            int numArestasAtualP;
            for(int i=1; i<vertices.length - 1; i++){ //percorre todas as arestas do array para descobrir quais vertices apontam para o vertice desejado
                //numArestasAtualP = vertices[i + 1] - vertices[i];
                //System.out.println(numArestasAtualP);
                for(j=vertices[i]; j<vertices[i + 1]; j++){

                    if(arestas[j] == verticePesquisa){
                        // System.out.println(arestas[j]);
                        // System.out.println("v: "+ vertices[i]);
                        predecessores.add(i);
                        grauEntrada++;
                        break;
                    }
                }
            }

            System.out.println("Grau de saida: " + grauSaida);
            System.out.println("Grau de entrada: " + grauEntrada);
            System.out.println("Sucessores: ");
            for(int i=0; i<sucessores.length; i++){
                System.out.println(sucessores[i]);
            }
            System.out.println("Predecessores:");
            for(int i=0; i<predecessores.size(); i++){
                System.out.println(predecessores.get(i));
            }


        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
}

class Vertice {
    public Vertice[] destinos;
    public int num;
}