package agenteIA;

import inteligenciaartificial.ElementPriority;
import java.awt.Point;

public class CostoUniformeAgente implements Agent<ElementPriority, Action> {
    CostoUniforme mycostouniforme;
    
    public CostoUniformeAgente(int N_,int M_,Point inicio_, Point salida_){
        mycostouniforme = new CostoUniforme(N_,M_,inicio_, salida_);
    }

    @Override
    public void execute() {
        mycostouniforme.imprimirLaberinto();
        if(mycostouniforme.solve()){
            mycostouniforme.construirCamino();
            System.out.println();
            mycostouniforme.imprimirLaberinto();
        }
        else
            System.out.println("No existe una solción");
    }
    
    
    public static void main(String[] args){
        CostoUniformeAgente myagent = new CostoUniformeAgente(6,6, new Point(0,0), new Point(4,4));
        myagent.execute();
    }
    
}
