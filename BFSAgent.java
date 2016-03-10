package agenteIA;

import java.awt.Point;

public class BFSAgent implements Agent<Point, Action> {
    BFS mybfs;
    
    public BFSAgent(int N_,int M_,Point inicio_, Point salida_){
        mybfs = new BFS(N_,M_,inicio_, salida_);
    }

    @Override
    public void execute() {
        mybfs.imprimirLaberinto();
        if(mybfs.solve()){
            mybfs.construirCamino();
            System.out.println();
            mybfs.imprimirLaberinto();
        }
        else
            System.out.println("No existe una solción");
    }
    
    
    public static void main(String[] args){
        BFSAgent myagent = new BFSAgent(6,6, new Point(0,0), new Point(4,4));
        myagent.execute();
    }
    
}
