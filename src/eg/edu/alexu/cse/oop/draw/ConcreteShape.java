/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.edu.alexu.cse.oop.draw;

import java.awt.Color;
import java.awt.Point;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Abd El Rahman
 */
public abstract class ConcreteShape implements Shape {

    protected Point position;
    protected Map<String, Double> properties;
    protected Color color;
    protected Color fillColor;

     public ConcreteShape() {  
        this.position = new Point(); 
        this.properties = new LinkedHashMap<>();
        this.color = new Color(Color.BLACK.getRGB()); // Default Color Is Black
        this.fillColor = new Color(Color.BLACK.getRGB()); // Defaul Fill Color Is Black
    }
    
    @Override
    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void setProperties(Map<String, Double> properties) {
        this.properties = properties;
    }

    @Override
    public Map<String, Double> getProperties() {
        return properties;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
                
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }
    
    // if I don't Override The Method here, It Gives Me an Error !!
    @Override
    public Object clone() throws CloneNotSupportedException{
        return null;
    }

}
