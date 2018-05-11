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
public class Triangle extends ConcreteShape implements Shape{

     public Triangle() {
        super();
        this.properties.put("x2", 0.0);
        this.properties.put("y2", 0.0);
        this.properties.put("x3", 0.0);
        this.properties.put("y3", 0.0);
        
    }

    @Override
    public void draw(Graphics canvas) {
        int[] xPoints = {(int)position.getX(),properties.get("x2").intValue(),properties.get("x3").intValue()};
        int[] yPoints = {(int)position.getY(),properties.get("y2").intValue(),properties.get("y3").intValue()};
        canvas.setColor(this.color);
        canvas.drawPolygon(xPoints, yPoints, 3);
        canvas.setColor(this.fillColor);
        canvas.fillPolygon(xPoints, yPoints, 3);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        ConcreteShape Triangle = new Triangle();
        Iterator<Map.Entry<String, Double>> Iterator = properties.entrySet().iterator();
        while(Iterator.hasNext()) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            Triangle.properties.put(Entry.getKey(),Entry.getValue()); 
        }
        Triangle.setPosition(this.getPosition());
        Triangle.setColor(this.getColor());
        Triangle.setFillColor(this.getFillColor());
        return Triangle;
        
    }
    
}
