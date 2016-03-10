package agenteIA;

import static agenteIA.CostoUniforme.Laberinto.matrizcostos;
import inteligenciaartificial.ElementPriority;
import static inteligenciaartificial.InteligenciaArtificial.initCostos;
import static inteligenciaartificial.InteligenciaArtificial.pesos;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.PriorityQueue;


public class CostoUniforme implements Problem<Point, Action>{
    Laberinto maze;
    
    public CostoUniforme(int N_,int M_,Point inicio_, Point salida_){
        maze = new Laberinto(N_,M_,inicio_,salida_);
    }
  
    public static class Laberinto{
        static Point inicio;
        static Point salida;
        static char laberinto[][]; 
        static boolean visitados[][];
        static boolean matrizcostos[][];
        static int dx[] = {1,-1,0,0};       //X columnas
        static int dy[] = {0,0,1,-1};       //Y filas
        static int N;
        static int M;
        static Hashtable<Point, Point> padres;
        static Hashtable<Point, Integer> costos;
                
        public Laberinto(int N_,int M_,Point inicio_, Point salida_){
            N = N_;
            M = M_;
            inicio = inicio_;
            salida = salida_;
            laberinto = new char[M][N];
            visitados = new boolean[M][N];
            matrizcostos = new boolean[M][N];
            padres = new Hashtable<Point, Point>();
            costos = initCostos(N,M);
            
            char lab[][] = {    {'E', '.', '.', '.','.', '.'},
                                {'.', '#', '.', '#','.', '#'},
                                {'.', '.', '#', '#','.', '#'},
                                {'.', '#', '.', '#','.', '#'},
                                {'.', '.', '.', '#','S', '#'},
                                {'.', '#', '#', '#','#', '#'}
                            };
            laberinto = lab;
        }
        
    }
    
    
    
    @Override
    public List<Action> actionsFor(Point state) {
        List<Action> legalActions = new ArrayList<Action>();
        for(int i=0; i<4; i++){
            int x = state.x + Laberinto.dx[i];
            int y = state.y + Laberinto.dy[i];

            if(x>=0 && x < maze.M && y>=0 && y < maze.N){
                if(!maze.visitados[y][x]){
                    if(maze.laberinto[y][x] == '.' || maze.laberinto[y][x] == 'S'){
                        if (i == 3) legalActions.add(Action.UP);
                        if (i == 2) legalActions.add(Action.DOWN);
                        if (i == 1) legalActions.add(Action.LEFT);
                        if (i == 0) legalActions.add(Action.RIGHT);
                    }

                }         
            }
        }
        
        return legalActions;
    }

    @Override
    public Point go(Point state, Action action) {   
        int move = -1; 
        switch (action) {
            case UP:    move = 3; break;
            case DOWN:  move = 2; break;
            case LEFT:  move = 1; break;
            case RIGHT: move = 0; break;
        }
        Point result = new Point(state.x + maze.dx[move], state.y + maze.dy[move]);
   //     ElementPriority ans = new ElementPriority(result, state.weight);
        return result;
    }

    @Override
    public boolean isGoal(Point state) {
        if(state.equals(maze.salida))
                return true;
        
        /*Otra forma recibir el punto de salida como parametro en isGoal
            isGoal(Point state, Point salida)
                 if(state.equals(salida))
                return true;
        */
        return false;
    }
    
     /**
     * Soluciona el laberinto, returna si tiene o no solucion.
     * Toma los valores del Laberinto
     */
    public boolean solve() {    
        PriorityQueue<ElementPriority> pq = new PriorityQueue<ElementPriority>();
        pq.add(new ElementPriority(maze.inicio, 0));
        maze.visitados[maze.inicio.y][maze.inicio.x] = true;
        maze.costos.put(maze.inicio,0);
        maze.padres.put(maze.inicio, new Point(-1,-1));
        while(!pq.isEmpty()){
            ElementPriority actual = pq.poll();
            if(isGoal(actual.pos))
                return true;
            List<Action> acciones = actionsFor(actual.pos);
            for (Action a: acciones ) {
                Point vecino = go(actual.pos, a);
                int nw = actual.weight + 1;
                if(maze.costos.get(vecino)>nw){                   
                    pq.add(new ElementPriority(vecino,nw));
                    maze.visitados[vecino.y][vecino.x] = true;
                    maze.padres.put(vecino,actual.pos);
                    maze.costos.put(vecino, nw);
                }
            }
        }    
        return false;
    }
    
    public void construirCamino(){
        Point path = maze.salida;
        while(path.x!=-1 && path.y!=-1){
            maze.laberinto[path.y][path.x] = 'I';
            path = maze.padres.get(path);
        } 
    }
    
    public void imprimirLaberinto(){
        for(char i[]: maze.laberinto){
            for(char c: i){
                System.out.print(c);
            }
            System.out.println();
        }
        System.out.println();
    }
}
