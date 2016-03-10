package agenteIA;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Random;

/**
 * El problema del 8 puzzle
 */
public class EightPuzzle implements Problem<EightPuzzle.Board, Action>
{
    /**
     * Tablero del 8 puzzle
     */
    public static class Board
    {
        /**
         * Contiene el valor de la baldosa en la posicion dada.
         * Las casillas del tablero se enumera de 0..8 como se muestra:
         * <pre>
         *     0  1  2
         *     3  4  5
         *     5  7  8
         *     </pre>
         */
         private int[] tiles = new int[9];
    
         /**
          * Constructor de un tablero aleatorio.
          */
         public Board()
         {
             tiles = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
             Random random = new Random();
             do {
                 for (int i = 0; i < tiles.length; i++) {
                     int j = random.nextInt(tiles.length);
                     int x = tiles[i];
                     tiles[i] = tiles[j];
                     tiles[j] = x;
                 }
             } while (!isSolvable());
         }
    
         /**
          * Crea un tablero con una configuracion dada
          */
         public Board(int[] tiles) 
         {
             System.arraycopy(tiles, 0, this.tiles, 0, tiles.length);
         }
    
         /**
          * Devuelve si la configuracion dada es solucionable o no.
          * Existe un conocido teorema que un 8-puzzle es solucionable 
          * si el numero de inversiones es par.
          */
         public boolean isSolvable()
         {
             boolean solvable = true;
             for (int i = 0; i < tiles.length; i++) {
                 if (tiles[i] == 0) continue;
                 for (int j = i + 1; j < tiles.length; j++) {
                     if (tiles[j] == 0) continue;
                     if (tiles[i] > tiles[j]) solvable = !solvable;
                 }
             }
             return solvable;
         }

         /**
          * Devuelve la suma de la distancia manhattan de cada baldosa
          */
         public int manhattan() 
         {
             int result = 0;
             for (int i = 0; i < 9; i++) {
                 result += Math.abs(tiles[i]/3-i/3)+Math.abs(tiles[i]%3-i%3);
             }
             return result;
         }
    
         /**
          * Devuelve una sencilla representacion textual del tablero.
          */
         @Override
         public String toString() 
         {
             return new Formatter().format("%d%d%d\n%d%d%d\n%d%d%d",
                tiles[0], tiles[1], tiles[2],
                tiles[3], tiles[4], tiles[5],
                tiles[6], tiles[7], tiles[8])
                .out().toString();
         }
    
         /**
          * Redefine el equals del objeto
          */
         @Override
         public boolean equals(Object o)
         {
             return o instanceof Board && Arrays.equals(tiles, ((Board)o).tiles);
         }
    
         /**
          * Redefine hashCode del objeto
          */
         @Override
         public int hashCode() 
         {
             return Arrays.hashCode(tiles);
         }
    }

    /**
     * Devuelve las acciones permitidas para un estado del tablero dado
     */
    @Override
    public List<Action> actionsFor(Board board) 
    {
        List<Action> legalActions = new ArrayList<Action>();
        int blankPosition = 0;
        while (board.tiles[blankPosition] != 0) blankPosition++;
        if (blankPosition > 2) legalActions.add(Action.UP);
        if (blankPosition % 3 != 0) legalActions.add(Action.LEFT);
        if (blankPosition % 3 != 2) legalActions.add(Action.RIGHT);
        if (blankPosition < 6) legalActions.add(Action.DOWN);
        return legalActions;
    }
    
    /**
     * Devuelve el tablero que se obtendría al aplicar la accion dada.
     */
    @Override
    public Board go(Board board, Action action) 
    {
        int offset = 0;
        switch (action) {
            case UP:    offset = -3; break;
            case DOWN:  offset = 3; break;
            case RIGHT: offset = 1; break;
            case LEFT:  offset = -1; break;
        }
        int blankPosition = 0;
        while (board.tiles[blankPosition] != 0) blankPosition++;
        int newBlankPosition = blankPosition + offset;
        Board result = new Board(board.tiles);
        result.tiles[blankPosition] = board.tiles[newBlankPosition];
        result.tiles[newBlankPosition] = 0;
        return result;
    }
    
    /**
     * Devuelve si la baldosa blanca esta el la parte superior izquierda
     * y las baldosas del 1 al 8 aparecen en orden de izquierda a derecha
     * y de arriba a abajo
     */
    @Override
    public boolean isGoal(Board board) 
    {
        return Arrays.equals(board.tiles, new int[]{0,1,2,3,4,5,6,7,8});
    }
    
     public static void main(String[] args) {
     
        EightPuzzle Ep = new EightPuzzle(); 
     }
    
}

 