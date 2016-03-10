package inteligenciaartificial;

import java.awt.Point;
import java.util.Collections;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.Vector;


/**
 *
 * @author utp
 */
public class InteligenciaArtificial {
       
    public static char laberinto[][];
    public static int pesos[][];
    
    
    //Busqueda en anchura
    public static boolean BFS(Point inicio, Point salida, 
        Hashtable<Point, Point> padres, int n,int m, char laberinto[][] ){
        
        Queue<Point> Q = new LinkedList<Point>();
        Q.add(inicio);
        boolean visitados[][] = new boolean[m][n];
        visitados[inicio.y][inicio.x] = true;
        
        int dx[] = {1,-1,0,0};       //X columnas
        int dy[] = {0,0,1,-1};       //Y filas
        
        while(!Q.isEmpty()){
            Point actual = Q.remove();
            if(actual.x == salida.x && actual.y == salida.y)
                return true;
            
            for(int i=0; i<4; i++){
                int x = actual.x + dx[i];
                int y = actual.y + dy[i];
                
                if(x>=0 && x < m && y>=0 && y < n){
                    if(!visitados[y][x]){
                        Point vecino = new Point(x,y);
                        if(laberinto[y][x] == '.' || laberinto[y][x] == 'S')
                            Q.add(vecino);
                        visitados[vecino.y][vecino.x] = true;
                        padres.put(vecino, actual);
                    }         
                }
            }
        }
          
        return false;
    }
    
    public static Hashtable<Point,Integer> initCostos(int N,int M){
        Hashtable<Point, Integer> costos = new Hashtable<Point, Integer>();
        for(int i=0;i<M;i++){ // Ys filas
            for(int j=0;j<N;j++){ //Xs columnas
                Point e = new Point(j,i);
                costos.put(e, Integer.MAX_VALUE);
            }
        }
        return costos;
    }
    
    public static boolean CostoUniforme(Point inicio, Point salida, int N, int M,
            Hashtable<Point, Point> padres, Hashtable<Point, Integer> costos,
            boolean matrizcostos){
        
        costos = initCostos(N,M);
        boolean visitados[][] = new boolean[M][N];
        PriorityQueue<ElementPriority> pq = new PriorityQueue<ElementPriority>();
        pq.add(new ElementPriority(inicio,0));
        costos.put(inicio,0);
        
        //moves
        int dx[] = {1,-1,0,0};       //X columnas
        int dy[] = {0,0,1,-1};       //Y filas
        
        while(!pq.isEmpty()){
            ElementPriority actual = pq.poll();
            if(actual.pos.equals(salida)){
                return true;
            }
            
            if(visitados[actual.pos.y][actual.pos.x])
                continue;
            visitados[actual.pos.y][actual.pos.x] = true;
            
            for(int i=0; i<4; i++){
                int nx = actual.pos.x + dx[i];
                int ny = actual.pos.y + dy[i];
                
                if(nx>=0 && nx < M && ny>=0 && ny < N){
                    if((laberinto[ny][nx] == '.' || laberinto[ny][nx] == 'S')
                            && !visitados[ny][nx] ) {
                         Point vecino = new Point(nx,ny);
                         // Se usa operador ternario, para tener en cuenta matriz de pesos
                         int nw = actual.weight + ((matrizcostos) ? pesos[ny][nx] : 1) ; 
                         if(costos.get(vecino)> nw ){
                             pq.add(new ElementPriority(vecino, nw));
                             costos.put(vecino, nw);
                             padres.put(vecino,actual.pos);
                         }
                    }         
                }
            }      
        }
        
        return false;
    }
    
    public static boolean DFS(Point inicio, Point salida, 
            Hashtable<Point, Point> padres, int n,int m, char laberinto[][] ){
        
        Stack<Point> ST = new Stack<>();
        ST.push(inicio);
        boolean visitados[][] = new boolean[m][n];
        visitados[inicio.y][inicio.x] = true;
        
        int dx[] = {1,-1,0,0};       //X columnas
        int dy[] = {0,0,1,-1};       //Y filas
        
        while(!ST.isEmpty()){
            Point actual = ST.pop();
            if(actual.x == salida.x && actual.y == salida.y)
                return true;
            
            for(int i=0; i<4; i++){
                int x = actual.x + dx[i];
                int y = actual.y + dy[i];
                
                if(x>=0 && x < m && y>=0 && y < n){
                    if(!visitados[y][x]){
                        Point vecino = new Point(x,y);
                        if(laberinto[y][x] == '.' || laberinto[y][x] == 'S')
                        ST.push(vecino);
                        visitados[vecino.y][vecino.x] = true;
                        padres.put(vecino, actual);
                    }         
                }
            }
        }
          
        return false;
    }
    
    public static boolean DFSLimite(Point inicio, Point salida, 
            Hashtable<Point, Point> padres, int n,int m, char laberinto[][], int p ){
        Hashtable<Point, Integer> profundidad = new Hashtable<Point, Integer>();
        Stack<Point> ST = new Stack<>();
        ST.push(inicio);
        profundidad.put(inicio, 0);
        boolean visitados[][] = new boolean[m][n];
        visitados[inicio.y][inicio.x] = true;
        
        int dx[] = {1,-1,0,0};       //X columnas
        int dy[] = {0,0,1,-1};       //Y filas
        
        while(!ST.isEmpty()){
            Point actual = ST.pop();
            if(actual.x == salida.x && actual.y == salida.y)
                return true;
            if(profundidad.get(actual) >= p)
                continue;
            
            for(int i=0; i<4; i++){
                int x = actual.x + dx[i];
                int y = actual.y + dy[i];
                
                if(x>=0 && x < m && y>=0 && y < n){
                    if(!visitados[y][x]){
                        Point vecino = new Point(x,y);
                        if(laberinto[y][x] == '.' || laberinto[y][x] == 'S')
                        ST.push(vecino);
                        visitados[vecino.y][vecino.x] = true;
                        padres.put(vecino, actual);
                        profundidad.put(vecino, profundidad.get(actual)+1);
                    }         
                }
            }
        }
          
        return false;
    }
    

   public static boolean DFSIterative(Point inicio, Point salida, 
           int n, int m, Hashtable<Point, Point> padres){
        PriorityQueue<ElementPriority> PQ = new PriorityQueue<>();
        
        PQ.add(new ElementPriority(inicio,0));
        
        boolean visitados[][] = new boolean[m][n];
        visitados[inicio.y][inicio.x] = true;
        
        int dx[] = {1,-1,0,0};       //X columnas
        int dy[] = {0,0,1,-1};       //Y filas
        
        while(!PQ.isEmpty()){
            ElementPriority actual = PQ.poll();
            if(actual.pos.equals(salida))
                return true;
            
            
            for(int i=0; i<4; i++){
                int x = actual.pos.x + dx[i];
                int y = actual.pos.y + dy[i];
                    
                if(x>=0 && x < m && y>=0 && y < n){
                    if(!visitados[y][x]){
                        Point vecino = new Point(x,y);
                        if(laberinto[y][x] == '.' || laberinto[y][x] == 'S'){
                            PQ.add(new ElementPriority(vecino, actual.weight+1));
                            visitados[vecino.y][vecino.x] = true;
                            padres.put(vecino, actual.pos);
                        }
                    }         
                }
             
            }
        }  
        return false;
        
    }

    public static boolean BFSconstructor(int N, int M, Point inicio, Point salida){
        Queue<Point> Q = new LinkedList<>();
        Q.add(inicio);
        boolean visitados[][] = new boolean[M][N];
        visitados[inicio.y][inicio.x] = true;
        
        int dx[] = {1,-1,0,0};       //X columnas
        int dy[] = {0,0,1,-1};       //Y filas
        Random r = new Random();
        Vector<Integer> moves = new Vector<>();
        moves.add(0); moves.add(1); moves.add(2); moves.add(3); 

        
        while(!Q.isEmpty()){
            Point actual = Q.remove();
            Collections.shuffle(moves);
            
            // Vecinos del actual
            for(int i=0; i<4; i++){
                int x = actual.x + dx[moves.elementAt(i)];
                int y = actual.y + dy[moves.elementAt(i)];
                int cont = 0;
                if(x>=0 && x < M && y>=0 && y < N){
                    // Vecinos del vecino de actual
                    for(int j=0; j<4; j++){
                        int nx = x + dx[moves.elementAt(j)];
                        int ny = y + dy[moves.elementAt(j)];

                        if(nx>=0 && nx < M && ny>=0 && ny < N){
                            if(laberinto[ny][nx]=='.') cont+=1;
                            else if(laberinto[ny][nx] == 'S'){
                                laberinto[y][x] = '.';
                                return true;
                            }
                        }
                    }   
                    if(cont <= 1){
                        Point vecino = new Point(y,x);
                        if(laberinto[y][x] == '#')
                            laberinto[y][x] = '.';
                        Q.add(vecino);
                    }         
                 }
            }                 
        }
        
        return false;
    }
    
    public static void imprimirLaberinto(){
        for(char i[]: laberinto){
            for(char c: i){
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
        //IMPRIME LA MATRIX COSTO
        /*for(int i[]: pesos){
            for(int c: i){
                System.out.print(c);
            }
            System.out.println();
        }*/
    }
    
    public static void crearLaberinto(int N, int M, Point inicio, Point salida){
        laberinto = new char[M][N];
        char lab[][] = { {'E', '.', '.', '.','.', '.'},
                      {'.', '#', '.', '#','.', '#'},
                      {'.', '.', '#', '#','.', '#'},
                      {'.', '#', '.', '#','.', '#'},
                      {'.', '.', '.', '#','S', '#'},
                      {'.', '#', '#', '#','#', '#'}
                    };
        pesos = new int[M][N];
        laberinto[inicio.y][inicio.x] = 'E';
        laberinto[salida.y][salida.x] = 'S';
        Random r = new Random();
        for(int i=0; i<M; i++){
            for(int j=0; j<N; j++){
                if(laberinto[i][j]!= 'E' && laberinto[i][j] != 'S'){
                    laberinto[i][j] = '#';
                }
                pesos[i][j] = r.nextInt(9) + 1;
            }
        }
        laberinto = lab;
        BFSconstructor(N, M, inicio, salida);
    }
    
  
    public static void main(String[] args) {
              
        
        // PARA EL CREADOR DE LABERINTOS POR TECLADO: (N, M, POS INICIAL, POS FINAL)
        /*
        Scanner in = new Scanner(System.in);
        System.out.println("Ingrese el Ancho: ");
        int N = in.nextInt();
        System.out.println("Ingrese el Alto: ");
        int M = in.nextInt();
        System.out.println("Ingrese la posicion del Inicio: ");
        int e_x = in.nextInt();
        int e_y = in.nextInt();
        System.out.println("Ingrese la posicion del Final: ");
        int s_x = in.nextInt();
        int s_y = in.nextInt();
        Point nn = new Point(-1,-1);
        Point inicial = new Point(e_x, e_y);
        Point salida = new Point(s_x, s_y);
        padres.put(inicial, nn);
        crearLaberinto(N,M,inicial,salida);
        imprimirLaberinto();
        */
        
        int N = 6; // Ancho Por Defecto
        int M = 6; // Alto Por Defecto
        Hashtable<Point, Point> padres = new Hashtable<Point,Point>();
        Hashtable<Point, Integer> costos = new  Hashtable<Point, Integer>();
        Point nn = new Point(-1,-1);
        Point inicial = new Point(0,0);
        Point salida = new Point(4,4);
        padres.put(inicial, nn);
  
        
        crearLaberinto(N,M,inicial,salida);
        System.out.println("LABERINTO: \n");
        imprimirLaberinto();
                
        System.out.println("\nSolucion BFS: ");
        if(InteligenciaArtificial.BFS(inicial, salida, padres, N, M, laberinto)){
            // Construir camino
            Point path = salida;
            while(path.x!=-1 && path.y!=-1){
                laberinto[path.y][path.x] = 'I';
                path = padres.get(path);
            }
            for(int i=0; i<N; i++){
                for(int j=0; j<M; j++){
                    System.out.print(laberinto[i][j] + " ");
                }
            System.out.println();
            }
            
        }else System.out.println("NO existe un camino");
       
        //System.out.println("\nDFS Limitado: ");
        //System.out.println("Ingrese la profundidad limite del DFS: ");
        //Scanner depth = new Scanner(System.in);
        //int profundidad = depth.nextInt();
        //System.out.println("Solución:");
        //if(InteligenciaArtificial.DFSLimite(inicial, salida, padres, N, M, laberinto, profundidad)){
       
        crearLaberinto(N,M,inicial,salida);
        
        System.out.println("\nSolucion CostoUniforme: ");
        if(InteligenciaArtificial.CostoUniforme(inicial, salida, N, M, padres,costos,true)){
            Point path = salida;
            while(path.x!=-1 && path.y!=-1){
                laberinto[path.y][path.x] = 'I';
                path = padres.get(path);
            }
            for(int i=0; i<N; i++){
                for(int j=0; j<M; j++){
                    System.out.print(laberinto[i][j] + " ");
                }
            System.out.println();
            }
            
        }
        else System.out.println("NO existe un camino");
 
        
        crearLaberinto(N,M,inicial,salida);      
        
        System.out.println("\nSolucion DFS: ");
        if(InteligenciaArtificial.DFS(inicial, salida, padres, N, M, laberinto)){
            // Construir camino
            Point path = salida;
            while(path.x!=-1 && path.y!=-1){
                laberinto[path.y][path.x] = 'I';
                path = padres.get(path);
            }
            for(int i=0; i<N; i++){
                for(int j=0; j<M; j++){
                    System.out.print(laberinto[i][j] + " ");
                }
            System.out.println();
            }
            
        }
        else System.out.println("NO existe un camino");
        
        
        crearLaberinto(N,M,inicial,salida); 
               
        System.out.println("\nSolucion DFS Limitado: ");
        // EL ULTIMO ARGUMENTO ES LA PROFUNDIDAD 
        if(InteligenciaArtificial.DFSLimite(inicial, salida, padres, N, M, laberinto, 10)){
            // Construir camino
            Point path = salida;
            while(path.x!=-1 && path.y!=-1){
                laberinto[path.y][path.x] = 'I';
                path = padres.get(path);
            }
            for(int i=0; i<N; i++){
                for(int j=0; j<M; j++){
                    System.out.print(laberinto[i][j] + " ");
                }
            System.out.println();
            }   
        }
        else System.out.println("NO existe un camino");
        
        
        
        crearLaberinto(N,M,inicial,salida);  
        
        System.out.println("\nSolucion DFS con profundidad Iterativa: ");
        if(InteligenciaArtificial.DFSIterative(inicial, salida, N, M, padres)){
            // Construir camino
            Point path = salida;
            while(path.x!=-1 && path.y!=-1){
                laberinto[path.y][path.x] = 'I';
                path = padres.get(path);
            }
            for(int i=0; i<N; i++){
                for(int j=0; j<M; j++){
                    System.out.print(laberinto[i][j] + " ");
                }
            System.out.println();
            }
        }
        else System.out.println("NO existe un camino");      
    }
}
