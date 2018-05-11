/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.edu.alexu.cse.oop.draw;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Abd El Rahman
 */
public class Square extends ConcreteShape implements Shape{
    
    
    public Square(){
        super();
        this.properties.put("Side", 0.0);
    }
    
    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(this.color);
        canvas.drawRect((int)position.getX(), (int)position.getY(), (int)properties.get("Side").doubleValue(), (int)properties.get("Side").doubleValue()); 
        canvas.setColor(this.fillColor);
        canvas.fillRect((int)position.getX(), (int)position.getY(), (int)properties.get("Side").doubleValue(), (int)properties.get("Side").doubleValue()); 
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        ConcreteShape Square = new Square();
        Iterator<Map.Entry<String, Double>> Iterator = properties.entrySet().iterator();
        while(Iterator.hasNext()) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            Square.properties.put(Entry.getKey(),Entry.getValue()); 
        }
        Square.setPosition(this.getPosition());
        Square.setColor(this.getColor());
        Square.setFillColor(this.getFillColor());
        return Square;
        
    }
    
}
