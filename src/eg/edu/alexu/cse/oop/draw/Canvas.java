/*
 * To change this license header, choose License Headers in Project Properties. 
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.edu.alexu.cse.oop.draw;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.swing.JPanel;

/**
 *
 * @author DELL
 */
public class Canvas extends JPanel implements DrawingEngine{
        
        private final Stack Undo;
        private final Stack Redo;
        private final Stack Temp;
        private int UndoSize;
        private int RedoSize;
        private ArrayList<Shape> shapes;
        private final ArrayList<Class<? extends Shape>> SupportedShapes;
        
    public Canvas (){
        shapes = new ArrayList<>();  
        SupportedShapes = new ArrayList<>();
        Undo = new Stack();
        Redo = new Stack();
        Temp = new Stack();
    }

    public void setShape(Shape Shape, int index) {
        this.shapes.set(index, Shape);
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        shapes.forEach((s) -> {
            s.draw(g);
        });
    }

    @Override
    public void addShape(Shape shape) {
       shapes.add(shape);
    }

    @Override
    public void removeShape(Shape shape) {
        shapes.remove(shape);
    }

    @Override
    public Shape[] getShapes() {
        return shapes.toArray(new Shape[shapes.size()]);
        //return shapes.toArray(shape[shapes.size()]);
    }

    @Override
    public void refresh(Graphics canvas) {
         this.repaint();
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {
            return SupportedShapes;
    }

    @Override
    public void installPluginShape(Class<? extends Shape> shapeClass) {
        SupportedShapes.add(shapeClass);
    }

     @Override
    public void undo() {
        if (!Undo.isEmpty()){
            if (RedoSize<20){
                Redo.push(shapes.clone());
                RedoSize++;
                UndoSize--;
                shapes = (ArrayList<Shape>) Undo.pop();
            }
            else{
                removeOldest(Redo);
                RedoSize--;
                undo(); 
            }
        }
        else
            System.out.println("Nothing to Undo");
        }
        
    @Override
    public void redo() {
        if (!Redo.isEmpty()){
            if (UndoSize<20){
            Undo.push(shapes.clone());
            UndoSize++;
            RedoSize--;
            shapes.clear();
            shapes = (ArrayList<Shape>) Redo.pop();
            }
            else{
                removeOldest(Undo);
                UndoSize--;
                redo();
            }
    }
        else
            System.out.println("Notingh to redo");
    }
    
    public void saveArray(){
        // empty The Redo Stack
        while (!Redo.empty()){
            Redo.pop();
            RedoSize=0;
        }
        
        if (UndoSize<20){
            Undo.push(shapes.clone());
            UndoSize++;
        }
        else{
            removeOldest(Undo);
            UndoSize--;
            saveArray();   
        }
    }
        
    private void removeOldest(Stack stack){
            while (!stack.empty())
                Temp.push(stack.pop());
            Temp.pop(); 
            while (!Temp.empty())
                stack.push(Temp.pop());
        }

    public int getUndoSize() {
        return UndoSize;
    }

    public int getRedoSize() {
        return RedoSize;
    }
}
