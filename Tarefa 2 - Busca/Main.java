import java.io.File;
import java.io.RandomAccessFile;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static Vertice[] arestas = null;
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

            int[] vertices = new int[n+1]; //array de vertices e arestas 
            arestas = new Vertice[m+1];

            for(int i=0; i<arestas.length; i++){
                arestas[i] = new Vertice();
            }

            int numArestas = 1, verticeAtual = 1, vertice = 1;
            boolean first = false;

            while(scFile.hasNext()){                    //le o arquivo todo e controi a representação do grafo em forward star
                vertices[verticeAtual] = numArestas;
                vertice = verticeAtual;
                while(scFile.hasNext() && verticeAtual == vertice){
                    if(first == false){
                        verticeAtual = scFile.nextInt();
                    }
                    if(verticeAtual != vertice){
                        break;
                    }
                    arestas[numArestas].verticeNum = scFile.nextInt();
                    numArestas++;
                    first = false;
                }
                first = true;
            }

            scFile.close();

            for(int i=1; i<vertices.length - 1; i++){ //ordena em ordem lexicográfica todos os vertices sucessores de um determinado vertice
                ArrayList<Integer> sucessores = new ArrayList<Integer>();
                int j = vertices[i];
                for( ; j<vertices[i+1]; j++){  
                    sucessores.add(arestas[j].verticeNum);
                }
                sucessores.add(arestas[j].verticeNum);
                j = vertices[i];
                Collections.sort(sucessores);
                int k=0;
                for(; j<vertices[i+1]; j++){  
                    arestas[j].verticeNum = sucessores.get(k);
                    k++;
                }
                arestas[j].verticeNum = sucessores.get(k);
            }

            Status[] status = new Status[vertices.length]; //diz se um vertice (indicado pelo indice do array) é marcado, explorado e quais são seus antecesores

            for(int i = 0; i<status.length; i++){ //chama um construtor para cada elemento do array de status
                status[i] = new Status(n);
            }

            for(int i=1; i<100; i++){
                status = buscaProfundidade(vertices, i, status, 0);
            }

            System.out.println("Digite o número do vertice que deseja pesquisar: ");
            int verticeParaPesquisa = sc.nextInt();

            System.out.println("Aresta | Classificação");
            for(int i=vertices[verticeParaPesquisa]; i<vertices[verticeParaPesquisa + 1]; i++){
                System.out.println("  " + arestas[i].verticeNum + "    |  " + arestas[i].type);
            }

            for(int i=0; i<n*2;i++){
                //System.out.println(status[verticeParaPesquisa].antecessores[i]);
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    static Status[] buscaProfundidade(int[] vertices, int verticesPointer, Status[] status, int verticeAnterior){

        if(status[verticesPointer].explorado){
            return status;
        }

        //System.out.println(verticesPointer + " " + verticeAnterior);

        //System.out.println(arestas[vertices[verticesPointer]].verticeNum);

        status[verticesPointer].marcado = true;

        if(verticesPointer == 100 || verticesPointer == 50000){
            for(int i=vertices[verticesPointer]; i<arestas.length; i++){

                //System.out.println(arestas[i].verticeNum + "" + status[verticesPointer].marcado);
    
                if(!status[arestas[i].verticeNum].marcado && !status[arestas[i].verticeNum].explorado){
                    arestas[i].type += "arvore";
                    Status tmp = new Status(vertices.length);
                    for(int atual=0; atual<status[verticesPointer].antecessores.length; atual++){
                        tmp.antecessores[atual] = status[arestas[i].verticeNum].antecessores[atual];
                    }
                    for(int atual=0; atual<status[verticesPointer].antecessores.length; atual++){
                        status[arestas[i].verticeNum].antecessores[atual] = status[verticesPointer].antecessores[atual];
                    }
                    for(int atual = 0; atual<status[verticesPointer].antecessores.length; atual++){
                        if(status[arestas[i].verticeNum].antecessores[atual] == verticesPointer){
                            for(int atualD=0; atualD<status[verticesPointer].antecessores.length; atualD++){
                                status[arestas[i].verticeNum].antecessores[atualD] = tmp.antecessores[atualD];
                            }
                            break;
                        }
                        else if(status[arestas[i].verticeNum].antecessores[atual] == arestas[i].verticeNum){
                            status[arestas[i].verticeNum].antecessores = tmp.antecessores;
                            break;
                        }
                        if(status[arestas[i].verticeNum].antecessores[atual] == -1){
                            status[arestas[i].verticeNum].antecessores[atual] = verticesPointer;
                            break;
                        }
                    }
                    status = buscaProfundidade(vertices, arestas[i].verticeNum, status, verticesPointer);
                    //System.out.println("voltou para" + arestas[vertices[verticesPointer]].verticeNum);
                }
                else if(status[arestas[i].verticeNum].marcado){
                    //System.out.println(vertices[verticesPointer]);
                    if(ifRetorno(status, verticesPointer, arestas[i].verticeNum)){
                        arestas[i].type += "retorno";
                    }
                    else if(ifAvanco(status, verticesPointer, arestas[i].verticeNum)){
                        arestas[i].type += "avanco";
                    }
                    else if(ifCruzamento(status, verticesPointer, arestas[i].verticeNum)){
                        arestas[i].type += "cruzamento";
                    }
    
                    //System.out.println("here: " + arestas[i].type);
    
                    // if(status[arestas[i].verticeNum].explorado){
                    //     //System.out.println("dosss");
                    //     break;
                    // }
                }
                else{
                    //System.out.println("uno");
                    // break;
                }
            }
        }
        else{
            for(int i=vertices[verticesPointer]; i<vertices[verticesPointer + 1]; i++){

                //System.out.println(arestas[i].verticeNum + "" + status[verticesPointer].marcado);
    
                if(!status[arestas[i].verticeNum].marcado && !status[arestas[i].verticeNum].explorado){
                    arestas[i].type += "arvore";
                    Status tmp = new Status(vertices.length);
                    for(int atual=0; atual<status[verticesPointer].antecessores.length; atual++){
                        tmp.antecessores[atual] = status[arestas[i].verticeNum].antecessores[atual];
                    }
                    for(int atual=0; atual<status[verticesPointer].antecessores.length; atual++){
                        status[arestas[i].verticeNum].antecessores[atual] = status[verticesPointer].antecessores[atual];
                    }
                    for(int atual = 0; atual<status[verticesPointer].antecessores.length; atual++){
                        if(status[arestas[i].verticeNum].antecessores[atual] == verticesPointer){
                            for(int atualD=0; atualD<status[verticesPointer].antecessores.length; atualD++){
                                status[arestas[i].verticeNum].antecessores[atualD] = tmp.antecessores[atualD];
                            }
                            break;
                        }
                        else if(status[arestas[i].verticeNum].antecessores[atual] == arestas[i].verticeNum){
                            status[arestas[i].verticeNum].antecessores = tmp.antecessores;
                            break;
                        }
                        if(status[arestas[i].verticeNum].antecessores[atual] == -1){
                            status[arestas[i].verticeNum].antecessores[atual] = verticesPointer;
                            break;
                        }
                    }
                    status = buscaProfundidade(vertices, arestas[i].verticeNum, status, verticesPointer);
                    //System.out.println("voltou para" + arestas[vertices[verticesPointer]].verticeNum);
                }
                else if(status[arestas[i].verticeNum].marcado){
                    //System.out.println(vertices[verticesPointer]);
                    if(ifRetorno(status, verticesPointer, arestas[i].verticeNum)){
                        arestas[i].type += "retorno";
                    }
                    else if(ifAvanco(status, verticesPointer, arestas[i].verticeNum)){
                        arestas[i].type += "avanco";
                    }
                    else if(ifCruzamento(status, verticesPointer, arestas[i].verticeNum)){
                        arestas[i].type += "cruzamento";
                    }
    
                    //System.out.println("here: " + arestas[i].type);
    
                    // if(status[arestas[i].verticeNum].explorado){
                    //     //System.out.println("dosss");
                    //     break;
                    // }
                }
                else{
                    //System.out.println("uno");
                    // break;
                }
            }
        }


        status[verticesPointer].explorado = true;

        return status;
    }
    

    static boolean ifRetorno(Status[] status, int verticeAtual, int verticePesquisa){ //checa se o vertice 
        boolean isRetorno = false;


        for(int i=0; i<status[verticeAtual].antecessores.length; i++){
            if(status[verticeAtual].antecessores[i] == verticePesquisa){
                isRetorno = true;
                break;
            }
        }

        return isRetorno;
        
    }

    static boolean ifAvanco(Status[] status, int verticePesquisa, int verticeAtual){
        boolean isAvanco = false;

        for(int i=0; i<status[verticeAtual].antecessores.length; i++){
            if(status[verticeAtual].antecessores[i] == verticePesquisa){
                isAvanco = true;
                break;
            }
        }

        return isAvanco;
        
    }

    static boolean ifCruzamento(Status[] status, int verticePesquisa, int verticeAtual){
        boolean isCruzamento = false;

        isCruzamento = ifAvanco(status, verticePesquisa, verticeAtual);
        if(!isCruzamento){
            isCruzamento = ifRetorno(status, verticePesquisa, verticeAtual);
        }

        return !isCruzamento;
        
    }

    
}

class Vertice {
    public String type = "";
    public int verticeNum = 0;

    Vertice(){
        type = "";
        verticeNum = 0;
    }
}

class Status {
    public String tipoAresta = "";
    boolean marcado = false;
    boolean explorado = false;
    int[] antecessores;

    Status(int n){
        marcado = false;
        explorado = false;
        antecessores = new int[n*2];
        for(int i=0; i<n*2; i++){
            antecessores[i] = -1;
        } 
    }  
}