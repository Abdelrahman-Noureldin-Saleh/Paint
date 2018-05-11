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
public class Diamond  extends ConcreteShape implements Shape{

    
    public Diamond (){
        super();
        this.properties.put("h", 0.0);
        this.properties.put("w", 0.0);
    }   

    @Override
    public void draw(Graphics canvas) {
        int x=(int)position.getX();
        int y=(int)position.getY();
        int w=properties.get("w").intValue();
        int h=properties.get("h").intValue();
        int [] xPoints={x+(w/2),x+w,x+(w/2),x};
        int [] yPoints={y,y+(h/2),y+h,y+(h/2)};
        canvas.setColor(this.color);
        canvas.drawPolygon(xPoints, yPoints, 4);
        canvas.setColor(this.fillColor);
        canvas.fillPolygon(xPoints, yPoints, 4);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        ConcreteShape Diamond = new Diamond ();
        Iterator<Map.Entry<String, Double>> Iterator = properties.entrySet().iterator();
        while(Iterator.hasNext()) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            Diamond .properties.put(Entry.getKey(),Entry.getValue()); 
        }
        Diamond .setPosition(this.getPosition());
        Diamond .setColor(this.getColor());
        Diamond .setFillColor(this.getFillColor());
        return Diamond ;
        
    }
    
}
