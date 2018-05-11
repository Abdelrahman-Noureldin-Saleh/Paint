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
public class LineSegment extends ConcreteShape implements Shape{
    
    
    public LineSegment(){
        super();
        this.properties.put("x2", 0.0);
        this.properties.put("y2", 0.0);
    }   

    @Override
    public void draw(Graphics canvas) {
        canvas.setColor(this.color);
        canvas.drawLine((int)position.getX(), (int)position.getY(), (int)(properties.get("x2").doubleValue()),(int)(properties.get("y2").doubleValue()));	
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        LineSegment LineSegment = new LineSegment();
        Iterator<Map.Entry<String, Double>> Iterator = properties.entrySet().iterator();
        while(Iterator.hasNext()) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            LineSegment.properties.put(Entry.getKey(),Entry.getValue()); 
        }
        LineSegment.setPosition(this.getPosition());
        LineSegment.setColor(this.getColor());
        LineSegment.setFillColor(this.getFillColor());
        return LineSegment;
        
    }
    
}
