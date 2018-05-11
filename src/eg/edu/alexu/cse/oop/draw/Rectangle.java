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
public class Rectangle extends ConcreteShape implements Shape{

    
    public Rectangle(){
        super();
        this.properties.put("Width", 0.0);
        this.properties.put("Height", 0.0);
    }   

    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(this.color);
        canvas.drawRect((int)position.getX(), (int)position.getY(), (int)properties.get("Width").doubleValue(), (int)properties.get("Height").doubleValue());
        canvas.setColor(this.fillColor);
        canvas.fillRect((int)position.getX(), (int)position.getY(), (int)properties.get("Width").doubleValue(), (int)properties.get("Height").doubleValue());
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        ConcreteShape Rectangle = new Rectangle();
        Iterator<Map.Entry<String, Double>> Iterator = properties.entrySet().iterator();
        while(Iterator.hasNext()) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            Rectangle.properties.put(Entry.getKey(),Entry.getValue()); 
        }
        Rectangle.setPosition(this.getPosition());
        Rectangle.setColor(this.getColor());
        Rectangle.setFillColor(this.getFillColor());
        return Rectangle;
        
    }
    
}
