/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eg.edu.alexu.cse.oop.draw;

import java.awt.Point;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author DELL
 */
public class GUI extends javax.swing.JFrame {

    private static int Counter;
    private static int CurrentShapeCounter;
    private static Shape CurrentShape;
    private static Map<String, Double> CurrentShapeProperties;
    private static Canvas Canvas ;
    private static final JTextField[] TextFields = new JTextField[6];
    private static final JLabel[] Labels = new JLabel[6];
    private static char Choice;

    /**
     * Creates new form Draw
     */
    public GUI() {
        initComponents(); 
        Canvas = (Canvas) PaintingCanvas;   
        
        
        // For Easier Looping Later
        TextFields[0] = XTextField;
        TextFields[1] = YTextField;     
        TextFields[2] = PropertiesTextField1;
        TextFields[3] = PropertiesTextField2;
        TextFields[4] = PropertiesTextField3;
        TextFields[5] = PropertiesTextField4;
        
        Labels[0] = xLabel; 
        Labels[1] = yLabel;
        Labels[2] = PropertiesLabel1;
        Labels[3] = PropertiesLabel2;
        Labels[4] = PropertiesLabel3;
        Labels[5] = PropertiesLabel4;
        
    }
    
    private void CleanDialogBox(){
        Labels[0].setText("x:");
        Labels[1].setText("y:");
        
        for (int i = 0; i<2; i++)
            TextFields[i].setText("");
        
        for (int i = 2; i<6; i++) {
            TextFields[i].setText(""); 
            TextFields[i].setEnabled(false); 
            Labels[i].setEnabled(false); 
            Labels[i].setText("N/A"); 
        }
    }

    private void prepareAddDialogBox(){
        Choice = 'A';
        CurrentShapeProperties = CurrentShape.getProperties();
        CleanDialogBox();
        for (int i = 0; i<2; i++) {
            TextFields[i].setEnabled(true); 
            Labels[i].setEnabled(true); 
        }
        setPropertiesFields();
        AddDialog.setVisible(true);
    }
    
    private void prepareEditDialogBox(){
        CurrentShapeProperties = CurrentShape.getProperties();
        CleanDialogBox();
        for (int i = 0; i<2; i++) {
            TextFields[i].setEnabled(false); 
            Labels[i].setEnabled(false); 
        }
        setPropertiesFields();
        AddDialog.setVisible(true);
    }
    
    private void setPropertiesFields(){
        // Iterate Over The Properties Map To Get Each Key And Put it In The Label
        Iterator<Map.Entry<String, Double>> Iterator = CurrentShapeProperties.entrySet().iterator();
        for (int i = 2; Iterator.hasNext();i++) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            // In case that the Shape uses points to be Drawn, set The x,y Labels to x1,y1 (Line or Triangle e.g) 
            if (Entry.getKey().equals("x2")){
                 for (int j = 0; j<2; j++){
                     TextFields[j].setEnabled(true); 
                     Labels[j].setEnabled(true);
                 }
                 Labels[0].setText("x1:");
                 Labels[1].setText("y1:");
            }
            Labels[i].setText(Entry.getKey() + ": "); 
            Labels[i].setEnabled(true); TextFields[i].setEnabled(true);
            AddDialog.setSize(410, (160+35*(i-2)));
        }
    }
    
    private void readProperties(){
        // Iterate Over The Map, Get The Key From The Label, Use it to OverWrite The Value With The Same Key In The Shape
        Iterator<Map.Entry<String, Double>> Iterator = CurrentShapeProperties.entrySet().iterator();
        for (int i = 2; Iterator.hasNext();i++) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            if (TextFields[i].getText().isEmpty() && Choice == 'A')
                JOptionPane.showConfirmDialog(null," Please Fill All Fields "  , "No Input !!", 2, 2);
            else if(TextFields[i].getText().isEmpty())
                continue;
            else if (!TextFields[i].getText().matches("[0-9]*"))
                JOptionPane.showConfirmDialog(null,"\"" + TextFields[i].getText() + "\" Is Not A Number "  , "Invalid Input !!", 2, 3);
            else
                CurrentShapeProperties.put(Entry.getKey(),Double.parseDouble(TextFields[i].getText()));
        }
    }
    
    private void MoveUpBy(int x){
        // Change The Position by The Desired Value.
        CurrentShapeProperties = CurrentShape.getProperties();
        CurrentShape.setPosition(new Point( (int) CurrentShape.getPosition().getX(), (int) CurrentShape.getPosition().getY() - x )); 
            // Check for x2,x3 in the properties (Line or Triangle e.g) 
        Iterator<Map.Entry<String, Double>> Iterator = CurrentShapeProperties.entrySet().iterator();
        while (Iterator.hasNext()) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            if (Entry.getKey().equals("y2"))
                Entry.setValue(Entry.getValue() - x );
            if (Entry.getKey().equals("y3"))
                Entry.setValue(Entry.getValue() - x );
        }
        Canvas.refresh(PaintingCanvas.getGraphics());
    }
    
     private void MoveDownBy(int x){
         // Change The Position by The Desired Value.
        CurrentShapeProperties = CurrentShape.getProperties();
        CurrentShape.setPosition(new Point ((int) CurrentShape.getPosition().getX(), (int) CurrentShape.getPosition().getY() + x )); 
            // Check for x2,x3,... in the properties
        Iterator<Map.Entry<String, Double>> Iterator = CurrentShapeProperties.entrySet().iterator();
        while (Iterator.hasNext()) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            if (Entry.getKey().equals("y2"))
                Entry.setValue(Entry.getValue() + x );
            if (Entry.getKey().equals("y3"))
                Entry.setValue(Entry.getValue() + x );
        }
        Canvas.refresh(PaintingCanvas.getGraphics());
     }
    
    private void MoveRightBy(int x){
        // Change The Position by The Desired Value.
        CurrentShapeProperties = CurrentShape.getProperties();
        CurrentShape.setPosition(new Point( (int) CurrentShape.getPosition().getX() + x, (int) CurrentShape.getPosition().getY())); 
            // Check for x2,x3,... in the properties
        Iterator<Map.Entry<String, Double>> Iterator = CurrentShapeProperties.entrySet().iterator();
        while (Iterator.hasNext()) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            if (Entry.getKey().equals("x2"))
                Entry.setValue(Entry.getValue() + x );
            if (Entry.getKey().equals("x3"))
                Entry.setValue(Entry.getValue() + x );
        }
        Canvas.refresh(PaintingCanvas.getGraphics());
    }
    
    private void MoveLeftBy(int x){
        // Change The Position by The Desired Value.
        CurrentShapeProperties = CurrentShape.getProperties();
        CurrentShape.setPosition(new Point( (int) CurrentShape.getPosition().getX() - x, (int) CurrentShape.getPosition().getY())); 
            // Check for x2,x3,... in the properties
        Iterator<Map.Entry<String, Double>> Iterator = CurrentShapeProperties.entrySet().iterator();
        while (Iterator.hasNext()) { 
            Map.Entry<String, Double> Entry = Iterator.next();
            if (Entry.getKey().equals("x2"))
                Entry.setValue(Entry.getValue() - x );
            if (Entry.getKey().equals("x3"))
                Entry.setValue(Entry.getValue() - x );
        }
        Canvas.refresh(PaintingCanvas.getGraphics());
    }
    
    private Point getNewPosition(){
        AddDialog.setVisible(false);
        int x = (int) CurrentShape.getPosition().getX();
        int y = (int) CurrentShape.getPosition().getY();
        if (!XTextField.getText().equals(""))
             x = Integer.parseInt(XTextField.getText());
        if (!YTextField.getText().equals(""))
            y = Integer.parseInt(YTextField.getText());
        return new Point(x,y);
    }
    
    private void addNewShape(){
        CurrentShape.setPosition(getNewPosition());
        readProperties();
        CurrentShape.setProperties(CurrentShapeProperties);
        Canvas.addShape(CurrentShape);
        UndoButton.setEnabled(true);
        RedoButton.setEnabled(false);
        AddDialog.setVisible(false);
    }
    
    private Map<String, Double> getNewProperties(){
        readProperties();
        AddDialog.setVisible(false);
        return CurrentShapeProperties;
    }
    
    private void UpdateList(){
        // Adding The Shapes to The list in A Bit Fancy Format ^_^
        ShapesList.removeAllItems();
        Shape[] Shapes = Canvas.getShapes();
        Counter = 0;
        for (int i = 0; i<Shapes.length; i++) {
            CurrentShapeCounter = 1; 
            for (int j = 0; j<i; j++){
                if (Shapes[i].getClass().getSimpleName().equals(Shapes[j].getClass().getSimpleName())) // count the number of shapes of the same type as the current shape
                CurrentShapeCounter++;
            }
            ShapesList.addItem(++Counter + ". " + Shapes[i].getClass().getSimpleName() + " #" + CurrentShapeCounter);
        }
        ShapesList.setSelectedIndex(ShapesList.getItemCount()-1);
        if (ShapesList.getItemCount() == 0)
            ShapesList.addItem("Choose Shape"); 
    }
    
    private Boolean changeColor(){
        if (FillCheckBox.isSelected())
            CurrentShape.setFillColor(ColorChooser.getColor());
        if (OutlineCheckBox.isSelected())
            CurrentShape.setColor(ColorChooser.getColor());
        Canvas.refresh(PaintingCanvas.getGraphics());
        if (!FillCheckBox.isSelected() && !OutlineCheckBox.isSelected()){
             JOptionPane.showConfirmDialog(null," Please Select A Color to Change "  , "No Selection !!", 2, 2);  
             return false;
        }
        return true; 
}
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        AddDialog = new javax.swing.JDialog();
        PropertiesPanel = new javax.swing.JPanel();
        xLabel = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
        PropertiesLabel1 = new javax.swing.JLabel();
        PropertiesLabel2 = new javax.swing.JLabel();
        PropertiesLabel3 = new javax.swing.JLabel();
        PropertiesLabel4 = new javax.swing.JLabel();
        XTextField = new javax.swing.JTextField();
        YTextField = new javax.swing.JTextField();
        PropertiesTextField1 = new javax.swing.JTextField();
        PropertiesTextField2 = new javax.swing.JTextField();
        PropertiesTextField3 = new javax.swing.JTextField();
        PropertiesTextField4 = new javax.swing.JTextField();
        EnterButton = new javax.swing.JButton();
        ColorsFrame = new javax.swing.JFrame();
        ColorChooser = new javax.swing.JColorChooser();
        OKButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        OutlineCheckBox = new javax.swing.JCheckBox();
        FillCheckBox = new javax.swing.JCheckBox();
        ApplyButton = new javax.swing.JButton();
        MoveDialog = new javax.swing.JDialog();
        MoveRightButton = new javax.swing.JButton();
        MoveByTextField = new javax.swing.JTextField();
        MoveUpButton = new javax.swing.JButton();
        MoveLeftButton = new javax.swing.JButton();
        MoveDownButton = new javax.swing.JButton();
        MoveByLabel = new javax.swing.JLabel();
        DoneButton = new javax.swing.JButton();
        jFileChooser1 = new javax.swing.JFileChooser();
        PaintingCanvas = new eg.edu.alexu.cse.oop.draw.Canvas();
        CircleButton = new javax.swing.JButton();
        ShapesList = new javax.swing.JComboBox<>();
        EditButton = new javax.swing.JButton();
        MoveButton = new javax.swing.JButton();
        UndoButton = new javax.swing.JButton();
        RedoButton = new javax.swing.JButton();
        CopyButton = new javax.swing.JButton();
        ColorizeButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        LineSegmentButton = new javax.swing.JButton();
        SquareButton = new javax.swing.JButton();
        EllipseButton = new javax.swing.JButton();
        TriangleButton = new javax.swing.JButton();
        RectangleButton = new javax.swing.JButton();
        SelectShapeLabel = new javax.swing.JLabel();
        PluginsList = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        DrawButton = new javax.swing.JButton();
        LoadButton = new javax.swing.JButton();
        ClearAllButton = new javax.swing.JButton();

        AddDialog.setTitle("Please Enter Your Values:");

        PropertiesPanel.setPreferredSize(new java.awt.Dimension(400, 150));

        xLabel.setText("x: ");

        yLabel.setText("y: ");

        PropertiesLabel1.setText("jLabel4");

        PropertiesLabel2.setText("jLabel4");

        PropertiesLabel3.setText("jLabel4");

        PropertiesLabel4.setText("jLabel4");

        XTextField.setNextFocusableComponent(YTextField);
        XTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                XTextFieldActionPerformed(evt);
            }
        });

        YTextField.setNextFocusableComponent(PropertiesTextField1);
        YTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                YTextFieldActionPerformed(evt);
            }
        });

        PropertiesTextField1.setToolTipText("");
        PropertiesTextField1.setNextFocusableComponent(PropertiesTextField2);
        PropertiesTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PropertiesTextField1ActionPerformed(evt);
            }
        });

        PropertiesTextField2.setToolTipText("");
        PropertiesTextField2.setNextFocusableComponent(PropertiesTextField3);
        PropertiesTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PropertiesTextField2ActionPerformed(evt);
            }
        });

        PropertiesTextField3.setToolTipText("");
        PropertiesTextField3.setNextFocusableComponent(PropertiesTextField4);
        PropertiesTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PropertiesTextField3ActionPerformed(evt);
            }
        });

        PropertiesTextField4.setToolTipText("");
        PropertiesTextField4.setNextFocusableComponent(EnterButton);
        PropertiesTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PropertiesTextField4ActionPerformed(evt);
            }
        });

        EnterButton.setText("Enter");
        EnterButton.setToolTipText("Confirm Your Values");
        EnterButton.setNextFocusableComponent(XTextField);
        EnterButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EnterButtonActionPerformed(evt);
            }
        });
        EnterButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                EnterButtonKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout PropertiesPanelLayout = new javax.swing.GroupLayout(PropertiesPanel);
        PropertiesPanel.setLayout(PropertiesPanelLayout);
        PropertiesPanelLayout.setHorizontalGroup(
            PropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PropertiesPanelLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(PropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PropertiesLabel1)
                    .addComponent(PropertiesLabel2)
                    .addComponent(PropertiesLabel3)
                    .addComponent(PropertiesLabel4)
                    .addComponent(yLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(PropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(XTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
                    .addComponent(YTextField, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PropertiesTextField1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PropertiesTextField2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PropertiesTextField3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PropertiesTextField4, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(28, 28, 28)
                .addComponent(EnterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
        );
        PropertiesPanelLayout.setVerticalGroup(
            PropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PropertiesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(XTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(xLabel))
                .addGroup(PropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PropertiesPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(YTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(yLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(PropertiesTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PropertiesLabel1)))
                    .addGroup(PropertiesPanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(EnterButton, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PropertiesTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PropertiesLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PropertiesTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PropertiesLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PropertiesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PropertiesTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PropertiesLabel4))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout AddDialogLayout = new javax.swing.GroupLayout(AddDialog.getContentPane());
        AddDialog.getContentPane().setLayout(AddDialogLayout);
        AddDialogLayout.setHorizontalGroup(
            AddDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PropertiesPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AddDialogLayout.setVerticalGroup(
            AddDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AddDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PropertiesPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                .addContainerGap())
        );

        ColorsFrame.setTitle("Coloring");
        ColorsFrame.setResizable(false);

        OKButton.setText("Ok");
        OKButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OKButtonActionPerformed(evt);
            }
        });

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        OutlineCheckBox.setText("Outline Color");

        FillCheckBox.setText("Fill Color");

        ApplyButton.setText("Apply");
        ApplyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ApplyButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ColorsFrameLayout = new javax.swing.GroupLayout(ColorsFrame.getContentPane());
        ColorsFrame.getContentPane().setLayout(ColorsFrameLayout);
        ColorsFrameLayout.setHorizontalGroup(
            ColorsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ColorsFrameLayout.createSequentialGroup()
                .addComponent(ColorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(ColorsFrameLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(OKButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ApplyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(OutlineCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FillCheckBox)
                .addGap(73, 73, 73))
        );
        ColorsFrameLayout.setVerticalGroup(
            ColorsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ColorsFrameLayout.createSequentialGroup()
                .addComponent(ColorChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ColorsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(OKButton)
                    .addGroup(ColorsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(CancelButton)
                        .addComponent(ApplyButton))
                    .addGroup(ColorsFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(OutlineCheckBox)
                        .addComponent(FillCheckBox)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MoveRightButton.setText("►");
        MoveRightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoveRightButtonActionPerformed(evt);
            }
        });

        MoveByTextField.setFocusTraversalPolicyProvider(true);
        MoveByTextField.setNextFocusableComponent(DoneButton);

        MoveUpButton.setText("▲");
        MoveUpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoveUpButtonActionPerformed(evt);
            }
        });

        MoveLeftButton.setText("◄");
        MoveLeftButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoveLeftButtonActionPerformed(evt);
            }
        });

        MoveDownButton.setText("▼");
        MoveDownButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoveDownButtonActionPerformed(evt);
            }
        });

        MoveByLabel.setText("Move By:");

        DoneButton.setText("Done");
        DoneButton.setFocusTraversalPolicyProvider(true);
        DoneButton.setNextFocusableComponent(MoveByTextField);
        DoneButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DoneButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout MoveDialogLayout = new javax.swing.GroupLayout(MoveDialog.getContentPane());
        MoveDialog.getContentPane().setLayout(MoveDialogLayout);
        MoveDialogLayout.setHorizontalGroup(
            MoveDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MoveDialogLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(MoveLeftButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(MoveDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(MoveUpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(MoveDownButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(MoveDialogLayout.createSequentialGroup()
                        .addGroup(MoveDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MoveByTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MoveByLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MoveRightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(DoneButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        MoveDialogLayout.setVerticalGroup(
            MoveDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MoveDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(MoveDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(DoneButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(MoveDialogLayout.createSequentialGroup()
                        .addComponent(MoveUpButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MoveDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(MoveDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(MoveRightButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(MoveLeftButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MoveDialogLayout.createSequentialGroup()
                                .addComponent(MoveByLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MoveByTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MoveDownButton, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Vector Based Drawing Application");
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(760, 380));
        setSize(new java.awt.Dimension(760, 400));

        PaintingCanvas.setBackground(new java.awt.Color(255, 255, 255));
        PaintingCanvas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 153, 255)));
        PaintingCanvas.setForeground(new java.awt.Color(255, 153, 204));

        javax.swing.GroupLayout PaintingCanvasLayout = new javax.swing.GroupLayout(PaintingCanvas);
        PaintingCanvas.setLayout(PaintingCanvasLayout);
        PaintingCanvasLayout.setHorizontalGroup(
            PaintingCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PaintingCanvasLayout.setVerticalGroup(
            PaintingCanvasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 258, Short.MAX_VALUE)
        );

        CircleButton.setText("Circle");
        CircleButton.setToolTipText("Create A New Circle");
        CircleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CircleButtonActionPerformed(evt);
            }
        });

        ShapesList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Choose Shape" }));
        ShapesList.setToolTipText("Choose A Drawn shape");

        EditButton.setText("Edit");
        EditButton.setToolTipText("Adjust Selected Shape's Properties");
        EditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButtonActionPerformed(evt);
            }
        });

        MoveButton.setText("Move");
        MoveButton.setToolTipText("Adjust Selected Shape's Position");
        MoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MoveButtonActionPerformed(evt);
            }
        });

        UndoButton.setText("Undo");
        UndoButton.setToolTipText("Undo Last Action");
        UndoButton.setEnabled(false);
        UndoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UndoButtonActionPerformed(evt);
            }
        });

        RedoButton.setText("Redo");
        RedoButton.setToolTipText("Redo Last Undo");
        RedoButton.setEnabled(false);
        RedoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RedoButtonActionPerformed(evt);
            }
        });

        CopyButton.setText("Copy");
        CopyButton.setToolTipText("Clone Selected Shape");
        CopyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CopyButtonActionPerformed(evt);
            }
        });

        ColorizeButton.setText("Colorize");
        ColorizeButton.setToolTipText("Colorize selected Shape");
        ColorizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ColorizeButtonActionPerformed(evt);
            }
        });

        DeleteButton.setText("Delete");
        DeleteButton.setToolTipText("Remove Selected Shape");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        LineSegmentButton.setText("Line Segment");
        LineSegmentButton.setToolTipText("Create A New Line Segment");
        LineSegmentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LineSegmentButtonActionPerformed(evt);
            }
        });

        SquareButton.setText("Square");
        SquareButton.setToolTipText("Create A New Square");
        SquareButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SquareButtonActionPerformed(evt);
            }
        });

        EllipseButton.setText("Ellipse");
        EllipseButton.setToolTipText("Create A New Ellipse");
        EllipseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EllipseButtonActionPerformed(evt);
            }
        });

        TriangleButton.setText("Triangle");
        TriangleButton.setToolTipText("Create A New Triangle");
        TriangleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TriangleButtonActionPerformed(evt);
            }
        });

        RectangleButton.setText("Rectangle");
        RectangleButton.setToolTipText("Create A New Rectangle");
        RectangleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RectangleButtonActionPerformed(evt);
            }
        });

        SelectShapeLabel.setText("Select Shape");
        SelectShapeLabel.setToolTipText("Why Are You Pointing Here :)");

        PluginsList.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Plugin" }));
        PluginsList.setToolTipText("Choose A Loaded Plugin");

        DrawButton.setText("Draw");
        DrawButton.setToolTipText("Draw Selected Shape");
        DrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DrawButtonActionPerformed(evt);
            }
        });

        LoadButton.setText("Load");
        LoadButton.setToolTipText("Load A New Shape");
        LoadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoadButtonActionPerformed(evt);
            }
        });

        ClearAllButton.setText("Clear All");
        ClearAllButton.setToolTipText("Remove All Shapes");
        ClearAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ClearAllButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ShapesList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(EditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(MoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(CopyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ClearAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(DeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(ColorizeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(SelectShapeLabel)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(UndoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(RedoButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(PluginsList, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator1))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(LoadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CircleButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LineSegmentButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SquareButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(EllipseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TriangleButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(RectangleButton))
                    .addComponent(PaintingCanvas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CircleButton)
                            .addComponent(LineSegmentButton)
                            .addComponent(SquareButton)
                            .addComponent(EllipseButton)
                            .addComponent(TriangleButton)
                            .addComponent(RectangleButton))
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(LoadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(DrawButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(PluginsList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UndoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(RedoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(SelectShapeLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ShapesList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MoveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CopyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ColorizeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ClearAllButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(PaintingCanvas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CircleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CircleButtonActionPerformed
        ConcreteShape Circle = new Circle();
        CurrentShape = Circle;
        prepareAddDialogBox(); 
    }//GEN-LAST:event_CircleButtonActionPerformed

    private void LineSegmentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LineSegmentButtonActionPerformed
        ConcreteShape LineSegment = new LineSegment();
        CurrentShape = LineSegment;
        prepareAddDialogBox(); 
    }//GEN-LAST:event_LineSegmentButtonActionPerformed

    private void SquareButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SquareButtonActionPerformed
        ConcreteShape Square = new Square();
        CurrentShape = Square;
        prepareAddDialogBox(); 
    }//GEN-LAST:event_SquareButtonActionPerformed

    private void EllipseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EllipseButtonActionPerformed
        ConcreteShape Ellipse = new Ellipse();
        CurrentShape = Ellipse;
        prepareAddDialogBox(); 
    }//GEN-LAST:event_EllipseButtonActionPerformed

    private void TriangleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TriangleButtonActionPerformed
        ConcreteShape Triangle = new Triangle();
        CurrentShape = Triangle;
        prepareAddDialogBox(); 
    }//GEN-LAST:event_TriangleButtonActionPerformed

    private void RectangleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RectangleButtonActionPerformed
        ConcreteShape Rectangle = new Rectangle();
        CurrentShape = Rectangle;
        prepareAddDialogBox(); 
    }//GEN-LAST:event_RectangleButtonActionPerformed

    private void XTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_XTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_XTextFieldActionPerformed

    private void PropertiesTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PropertiesTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PropertiesTextField1ActionPerformed

    private void EnterButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EnterButtonActionPerformed
        // Input Validation
        boolean flag = false;
        for (JTextField TextField : TextFields) 
           if (!TextField.getText().matches("[0-9]*")){
               flag = true;  
               JOptionPane.showConfirmDialog(null,"\"" + TextField.getText() + "\" Is Not A Number "  , "Invalid Input !!", 2, 3);
               break;
           }
        // Action
        if (!flag){
        switch (Choice) {
            case 'A' :
                Canvas.saveArray();
                addNewShape();
                UpdateList();
                break;
            case 'E' :
                CurrentShape.setPosition(getNewPosition());
                CurrentShape.setProperties(getNewProperties());
                break;
        }
        Canvas.refresh(PaintingCanvas.getGraphics());
        }
    }//GEN-LAST:event_EnterButtonActionPerformed

    private void EnterButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EnterButtonKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_EnterButtonKeyPressed

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButtonActionPerformed
       if (Canvas.getShapes().length != 0){
            Choice = 'E';
            CurrentShape = Canvas.getShapes()[ShapesList.getSelectedIndex()];
            prepareEditDialogBox();  
       }
       else
           JOptionPane.showConfirmDialog(null, "Please Create A New Shape First !!  ", "No Shapes Found!", 2, 0);
    }//GEN-LAST:event_EditButtonActionPerformed

    private void PropertiesTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PropertiesTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PropertiesTextField2ActionPerformed

    private void PropertiesTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PropertiesTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PropertiesTextField3ActionPerformed

    private void PropertiesTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PropertiesTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PropertiesTextField4ActionPerformed

    private void MoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoveButtonActionPerformed
        if (Canvas.getShapes().length != 0){
            CurrentShape = Canvas.getShapes()[ShapesList.getSelectedIndex()];
            MoveDialog.setVisible(true);
            MoveDialog.setSize(290, 260); 
       }
       else
           JOptionPane.showConfirmDialog(null, "Please Create A New Shape First !!  ", "No Shapes Found!", 2, 0);
    }//GEN-LAST:event_MoveButtonActionPerformed

    private void CopyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CopyButtonActionPerformed
         if (Canvas.getShapes().length != 0){
            CurrentShape = Canvas.getShapes()[ShapesList.getSelectedIndex()];
            CurrentShapeProperties = CurrentShape.getProperties();
        try {
            CurrentShape = (Shape) CurrentShape.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        Canvas.saveArray();
        Canvas.addShape(CurrentShape);
        CurrentShape = Canvas.getShapes()[Canvas.getShapes().length-1];
        MoveDownBy(20);
        MoveRightBy(20);
        UpdateList();
        Canvas.refresh(PaintingCanvas.getGraphics());
        
        }
        else
           JOptionPane.showConfirmDialog(null, "Please Create A New Shape First !!  ", "No Shapes Found!", 2,0);
    }//GEN-LAST:event_CopyButtonActionPerformed

    private void ColorizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ColorizeButtonActionPerformed
        if (Canvas.getShapes().length != 0){
            CurrentShape = Canvas.getShapes()[ShapesList.getSelectedIndex()];
            ColorsFrame.setVisible(true);
            ColorsFrame.setSize(663, 460);
        }
        else
           JOptionPane.showConfirmDialog(null, "Please Create A New Shape First !!  ", "No Shapes Found!", 2,0);
    }//GEN-LAST:event_ColorizeButtonActionPerformed

    private void OKButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OKButtonActionPerformed
        if (changeColor())
            ColorsFrame.setVisible(false);
    }//GEN-LAST:event_OKButtonActionPerformed

    private void ApplyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ApplyButtonActionPerformed
       changeColor();
    }//GEN-LAST:event_ApplyButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        ColorsFrame.setVisible(false);
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void MoveRightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoveRightButtonActionPerformed
        if (MoveByTextField.getText().matches("[0-9]+"))
            MoveRightBy(Integer.parseInt(MoveByTextField.getText()));
        else if (MoveByTextField.getText().equals(""))
            JOptionPane.showConfirmDialog(null,"Please Enter Any Value"  , "No Input !!", 2, 2);
        else
           JOptionPane.showConfirmDialog(null,"\"" + MoveByTextField.getText() + "\" Is Not A Number "  , "Invalid Input !!", 2, 3);
    }//GEN-LAST:event_MoveRightButtonActionPerformed

    private void MoveUpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoveUpButtonActionPerformed
       if (MoveByTextField.getText().matches("[0-9]+"))
            MoveUpBy(Integer.parseInt(MoveByTextField.getText()));
        else if (MoveByTextField.getText().equals(""))
            JOptionPane.showConfirmDialog(null,"Please Enter Any Value"  , "No Input !!", 2, 2);
        else
           JOptionPane.showConfirmDialog(null,"\"" + MoveByTextField.getText() + "\" Is Not A Number "  , "Invalid Input !!", 2, 3);
    }//GEN-LAST:event_MoveUpButtonActionPerformed

    private void MoveLeftButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoveLeftButtonActionPerformed
        if (MoveByTextField.getText().matches("[0-9]+"))
            MoveLeftBy(Integer.parseInt(MoveByTextField.getText()));
        else if (MoveByTextField.getText().equals(""))
            JOptionPane.showConfirmDialog(null,"Please Enter Any Value"  , "No Input !!", 2, 2);
        else
           JOptionPane.showConfirmDialog(null,"\"" + MoveByTextField.getText() + "\" Is Not A Number "  , "Invalid Input !!", 2, 3);
    }//GEN-LAST:event_MoveLeftButtonActionPerformed

    private void MoveDownButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MoveDownButtonActionPerformed
        if (MoveByTextField.getText().matches("[0-9]+"))
            MoveDownBy(Integer.parseInt(MoveByTextField.getText()));
        else if (MoveByTextField.getText().equals(""))
            JOptionPane.showConfirmDialog(null,"Please Enter Any Value"  , "No Input !!", 2, 2);
        else
           JOptionPane.showConfirmDialog(null,"\"" + MoveByTextField.getText() + "\" Is Not A Number "  , "Invalid Input !!", 2, 3);
    }//GEN-LAST:event_MoveDownButtonActionPerformed

    private void YTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_YTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_YTextFieldActionPerformed

    private void DoneButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DoneButtonActionPerformed
        MoveDialog.setVisible(false);
    }//GEN-LAST:event_DoneButtonActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        if (Canvas.getShapes().length != 0){
            Canvas.saveArray();
            Canvas.removeShape(Canvas.getShapes()[ShapesList.getSelectedIndex()]);
            Canvas.refresh(PaintingCanvas.getGraphics());
            UpdateList();
            RedoButton.setEnabled(false);
            UndoButton.setEnabled(true);
        }
        else
           JOptionPane.showConfirmDialog(null, "Please Create A New Shape First !!  ", "No Shapes Found!", 2,0);
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void DrawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DrawButtonActionPerformed
        if(!Canvas.getSupportedShapes().isEmpty()){
            try {
                Constructor <? extends Shape> constructor = Canvas.getSupportedShapes().get(PluginsList.getSelectedIndex()).getDeclaredConstructor();
                //getItemAt(PluginsList.getSelectedIndex()).getDeclaredConstructor();
                constructor.setAccessible(true);
                CurrentShape = constructor.newInstance();
                CurrentShapeProperties = CurrentShape.getProperties();
                prepareAddDialogBox();
            } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
           JOptionPane.showConfirmDialog(null, "Please Load A Plugin First !!  ", "No plugins Found!", 2,0);
       
           //Class plugin = Canvas.getSupportedShapes().get(PluginsList.getSelectedIndex());
          // ConcreteShape shape = new plugin.getConstructors();
    }//GEN-LAST:event_DrawButtonActionPerformed

    private void LoadButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        try {
            FileNameExtensionFilter jarFilter = new FileNameExtensionFilter("JAR files", "jar");
            jFileChooser1.setFileFilter(jarFilter);
            jFileChooser1.showOpenDialog(null);
            File pluginFile = jFileChooser1.getSelectedFile();
            /*  if (pluginFile == null)
            return;*/
            String qualifiedClassName = JOptionPane.showInputDialog("Enter Qualified Class Name: ","eg.edu.alexu.cse.oop.draw.");
            
            
            URL url = pluginFile.toURL();
            URL[] URLs = new URL[] {url};
            ClassLoader loader = new URLClassLoader(URLs);
            Class  myClass = Class.forName(qualifiedClassName, true, loader);
            
            // if (myClass == null) System.out.println("MyClass = null");
            Canvas.installPluginShape(myClass);
            PluginsList.removeAllItems();
            Canvas.getSupportedShapes().forEach((supportedShape) -> {
                PluginsList.addItem(supportedShape.getSimpleName());
            });
            
            } catch (MalformedURLException | ClassNotFoundException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
            /*List URLS = new ArrayList();
            try {
            URLS.add(jFileChooser1.getSelectedFile().toURI().toURL());
            } catch (MalformedURLException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
            /*    try {
            File file = new File(FileScanner.in);
            URL url = file.toURI().toURL();
            
            URLClassLoader classLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(classLoader, url);
            } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException | MalformedURLException ex) {
        }*/
    }                                          

    private void UndoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UndoButtonActionPerformed
        Canvas.undo();
        Canvas.refresh(PaintingCanvas.getGraphics());
        UpdateList();
        RedoButton.setEnabled(true);
        if (Canvas.getUndoSize() == 0)
            UndoButton.setEnabled(false);
    }//GEN-LAST:event_UndoButtonActionPerformed

    private void RedoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RedoButtonActionPerformed
        System.out.println(Canvas.getRedoSize());
        Canvas.redo();
        Canvas.refresh(PaintingCanvas.getGraphics());
        UpdateList();
        UndoButton.setEnabled(true);
        if (Canvas.getRedoSize() == 0)
             RedoButton.setEnabled(false);  
    }//GEN-LAST:event_RedoButtonActionPerformed

    private void ClearAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearAllButtonActionPerformed
        if (Canvas.getShapes().length != 0){
            Canvas.saveArray();
            RedoButton.setEnabled(false);
            while (Canvas.getShapes().length != 0)
                Canvas.removeShape(Canvas.getShapes()[Canvas.getShapes().length-1]);
            Canvas.refresh(PaintingCanvas.getGraphics());
            UpdateList();
        }
        else
            JOptionPane.showConfirmDialog(null, "Canvas Is Already Clear :) ", "No Need!", 2,3);
    }//GEN-LAST:event_ClearAllButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new GUI().setVisible(true);
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog AddDialog;
    private javax.swing.JButton ApplyButton;
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton CircleButton;
    private javax.swing.JButton ClearAllButton;
    private javax.swing.JColorChooser ColorChooser;
    private javax.swing.JButton ColorizeButton;
    private javax.swing.JFrame ColorsFrame;
    private javax.swing.JButton CopyButton;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JButton DoneButton;
    private javax.swing.JButton DrawButton;
    private javax.swing.JButton EditButton;
    private javax.swing.JButton EllipseButton;
    private javax.swing.JButton EnterButton;
    private javax.swing.JCheckBox FillCheckBox;
    private javax.swing.JButton LineSegmentButton;
    private javax.swing.JButton LoadButton;
    private javax.swing.JButton MoveButton;
    private javax.swing.JLabel MoveByLabel;
    private javax.swing.JTextField MoveByTextField;
    private javax.swing.JDialog MoveDialog;
    private javax.swing.JButton MoveDownButton;
    private javax.swing.JButton MoveLeftButton;
    private javax.swing.JButton MoveRightButton;
    private javax.swing.JButton MoveUpButton;
    private javax.swing.JButton OKButton;
    private javax.swing.JCheckBox OutlineCheckBox;
    private javax.swing.JPanel PaintingCanvas;
    private javax.swing.JComboBox<String> PluginsList;
    private javax.swing.JLabel PropertiesLabel1;
    private javax.swing.JLabel PropertiesLabel2;
    private javax.swing.JLabel PropertiesLabel3;
    private javax.swing.JLabel PropertiesLabel4;
    private javax.swing.JPanel PropertiesPanel;
    private javax.swing.JTextField PropertiesTextField1;
    private javax.swing.JTextField PropertiesTextField2;
    private javax.swing.JTextField PropertiesTextField3;
    private javax.swing.JTextField PropertiesTextField4;
    private javax.swing.JButton RectangleButton;
    private javax.swing.JButton RedoButton;
    private javax.swing.JLabel SelectShapeLabel;
    private javax.swing.JComboBox<String> ShapesList;
    private javax.swing.JButton SquareButton;
    private javax.swing.JButton TriangleButton;
    private javax.swing.JButton UndoButton;
    private javax.swing.JTextField XTextField;
    private javax.swing.JTextField YTextField;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel xLabel;
    private javax.swing.JLabel yLabel;
    // End of variables declaration//GEN-END:variables
}
