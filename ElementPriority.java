/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inteligenciaartificial;

import java.awt.Point;


public class ElementPriority implements Comparable<ElementPriority> {
    public Point pos;
    public int weight;
    
    public ElementPriority(Point _pos, int _w){
        pos = _pos;
        weight = _w;
    }

    @Override public int compareTo(ElementPriority other) {
        return Integer.compare(this.weight, other.weight);
    }
}
