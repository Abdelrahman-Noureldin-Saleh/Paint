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
public class Circle extends ConcreteShape implements Shape{
    
    
    public Circle() {
        super();
        this.properties.put("Radius", 0.0);
    }

    @Override
    public void draw(Graphics canvas){
        canvas.setColor(this.color);
        canvas.drawOval((int)position.getX(),(int) position.getY(),(properties.get("Radius")).intValue()*2,(properties.get("Radius")).intValue()*2);
        canvas.setColor(this.fillColor);
        canvas.fillOval((int)position.getX(),(int) position.getY(),(properties.get("Radius")).intValue()*2 ,(properties.get("Radius")).intValue()*2);
    }
    
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        ConcreteShape Circle = new Circle();
        Iterator<Map.Entry<String, Double>> Iterator = properties.entrySet().iterator();
        while(Iterator.hasNext()) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            Circle.properties.put(Entry.getKey(),Entry.getValue()); 
        }
        Circle.setPosition(this.position);
        Circle.setColor(this.color);
        Circle.setFillColor(this.fillColor);
        return Circle;
        
    }
}
