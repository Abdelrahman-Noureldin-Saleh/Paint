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
public class Ellipse extends ConcreteShape implements Shape{

    
    public Ellipse(){
        super();
        this.properties.put("Width", 0.0);
        this.properties.put("Height", 0.0);
    }   

    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(this.color);
        canvas.drawOval((int)position.getX(),(int) position.getY(),(properties.get("Width")).intValue(),(properties.get("Height")).intValue());
        canvas.setColor(this.fillColor);
        canvas.fillOval((int)position.getX(),(int) position.getY(),(properties.get("Width")).intValue(),(properties.get("Height")).intValue());
    }

    @Override
    public Object clone() throws CloneNotSupportedException{
        ConcreteShape Ellipse = new Ellipse();
        Iterator<Map.Entry<String, Double>> Iterator = properties.entrySet().iterator();
        while(Iterator.hasNext()) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            Ellipse.properties.put(Entry.getKey(),Entry.getValue()); 
        }
        Ellipse.setPosition(this.getPosition());
        Ellipse.setColor(this.getColor());
        Ellipse.setFillColor(this.getFillColor());
        return Ellipse;
        
    }
    
}
