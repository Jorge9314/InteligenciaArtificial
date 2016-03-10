package agenteIA;

import java.awt.Point;

public class DFSLimiteAgent implements Agent<Point, Action> {
    DFSLimite mydfslimite;
    
    public DFSLimiteAgent(int N_,int M_,int P_,Point inicio_, Point salida_){
        mydfslimite = new DFSLimite(N_,M_,P_,inicio_, salida_);
    }

    @Override
    public void execute() {
        mydfslimite.imprimirLaberinto();
        if(mydfslimite.solve()){
            mydfslimite.construirCamino();
            System.out.println();
            mydfslimite.imprimirLaberinto();
        }
        else
            System.out.println("No existe una solción");
    }
    
    
    public static void main(String[] args){
        DFSLimiteAgent myagent = new DFSLimiteAgent(6,6,10, new Point(0,0), new Point(4,4));
        myagent.execute();
    }
    
}
