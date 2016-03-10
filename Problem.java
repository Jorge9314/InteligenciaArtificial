package agenteIA;
import java.util.List;

/**
 * Definicion de un Problema
 */
public interface Problem<State, Action>
{
    /**
     * Devuelve el array de acciones aolicables a un estado dado
     */
    public List<Action> actionsFor(State state);
    
    /**
     * Devuelve el estado que se obtiene al aplicar la accion
     * al estado dado.
     */
    public State go(State state, Action action);
    
    /**
     * Devuelve si el estado es la meta o no.
     */
    public boolean isGoal(State state); 
   
}
