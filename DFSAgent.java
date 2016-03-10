package agenteIA;

import java.awt.Point;

public class DFSAgent implements Agent<Point, Action> {
    DFS mydfs;
    
    public DFSAgent(int N_,int M_,Point inicio_, Point salida_){
        mydfs = new DFS(N_,M_,inicio_, salida_);
    }

    @Override
    public void execute() {
        mydfs.imprimirLaberinto();
        if(mydfs.solve()){
            mydfs.construirCamino();
            System.out.println();
            mydfs.imprimirLaberinto();
        }
        else
            System.out.println("No existe una solción");
    }
    
    
    public static void main(String[] args){
        DFSAgent myagent = new DFSAgent(6,6, new Point(0,0), new Point(4,4));
        myagent.execute();
    }
    
}
