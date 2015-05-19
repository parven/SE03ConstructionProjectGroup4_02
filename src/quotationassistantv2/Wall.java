/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quotationassistantv2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.Border;

/**
 *Class with all attributes of Walls
 * @author nixon_000
 */
public class Wall extends JDialog
{
    //Instance Variables
    private static boolean locallyBuilt;
    private static boolean poorAccess;
    private static int difficulty;
    private int angle;
    
    //Components
    private JLabel lengthLabel, sHeightLabel, eHeightLabel, angleLabel, lUnit, sUnit,
           eUnit, angleUnit, difficultyLabel;
    
    private JSpinner lengthSpin, sHeightSpin, eHeightSpin, angleSpin;

    private JCheckBox locallyBuiltCheck, poorAccessCheck;
    
    private JComboBox difficultyCombo;
    
    private JButton okButton;
    
    private SpinnerModel lSpinner = new SpinnerNumberModel(2.0,2.0,150.0,0.5);
    private SpinnerModel sSpinner = new SpinnerNumberModel(0.2,0.2,150.0,0.2);
    private SpinnerModel eSpinner = new SpinnerNumberModel(0.2,0.2,150.0,0.2);
    private SpinnerModel aSpinner = new SpinnerNumberModel(1,1,359,1);
    
    //Misc
    private Border borderLined = BorderFactory.createLineBorder(Color.black,3);
    private Border wallPanelTitle = BorderFactory.createTitledBorder(borderLined, "Wall Details");
    private Border locationPanelTitle = BorderFactory.createTitledBorder(borderLined, "Location Details");
        
    private String[] difficulties = {"Normal","Sandy","Limestone","Bluestone"};
    
    private WallPanelScrolled wps;
    
    private ArrayList<Rectangle> arrayRect = new ArrayList();
    
    private ArrayList<Integer> baysArray;

    public Wall(boolean locallyBuilt, boolean poorAccess, 
            int difficulty, ArrayList<Integer> baysArray, int angle) 
    {
        this.locallyBuilt = locallyBuilt;
        this.poorAccess = poorAccess;
        this.difficulty = difficulty;
        this.baysArray = baysArray;
        this.angle = angle;
    }
    
    public Wall(WallPanelScrolled wps,JFrame main,String title)
    {
        super(main,title,true);
        this.wps = wps;
        
        //setLocationRelativeTo(main);
        setSize(new Dimension(700,200));
        setResizable(false);
             
        JPanel wallAtt = new JPanel();
        wallAtt.setBorder(wallPanelTitle);
        add(wallAtt,BorderLayout.PAGE_START);
        
        lengthLabel = new JLabel("Length");
        lengthSpin = new JSpinner(lSpinner); 
        lUnit = new JLabel("mts.  ");
        
        
        sHeightLabel = new JLabel("Start Height");
        sHeightSpin = new JSpinner(sSpinner); 
        sUnit = new JLabel("mts.  ");
        
                
        eHeightLabel = new JLabel("End Height");
        eHeightSpin = new JSpinner(eSpinner); 
        eUnit = new JLabel("mts.  ");
            
        wallAtt.add(lengthLabel);wallAtt.add(lengthSpin);wallAtt.add(lUnit);
        wallAtt.add(sHeightLabel);wallAtt.add(sHeightSpin);wallAtt.add(sUnit);
        wallAtt.add(eHeightLabel);wallAtt.add(eHeightSpin);wallAtt.add(eUnit);
        
        
        angleLabel = new JLabel("   Compound Angle");
        angleSpin = new JSpinner(aSpinner);
        angleUnit = new JLabel ("º");
        wallAtt.add(angleLabel);wallAtt.add(angleSpin);wallAtt.add(angleUnit);
        angleLabel.setVisible(false);angleSpin.setVisible(false);angleUnit.setVisible(false);
        if (wps.getWalls().size()>0) 
        {
            angleLabel.setVisible(true);angleSpin.setVisible(true);angleUnit.setVisible(true);
        }
        

        JPanel locationAtt = new JPanel();
        locationAtt.setBorder(locationPanelTitle);
        add(locationAtt,BorderLayout.CENTER);
        
        locallyBuiltCheck = new JCheckBox("Locally Built?");
        locallyBuiltCheck.setSelected(this.locallyBuilt);
        poorAccessCheck = new JCheckBox("Poor Access?");
        poorAccessCheck.setSelected(this.poorAccess);
        difficultyLabel = new JLabel("Difficulty");
        difficultyCombo = new JComboBox(difficulties);
        difficultyCombo.setSelectedIndex(this.difficulty);
        
        locationAtt.add(locallyBuiltCheck);locationAtt.add(poorAccessCheck);
        locationAtt.add(difficultyLabel);locationAtt.add(difficultyCombo);
        
        JPanel buttonPanel = new JPanel();
       // buttonPanel.setBorder(borderLined);
        add(buttonPanel,BorderLayout.PAGE_END);
        
        okButton = new JButton("Accept");
        buttonPanel.add(okButton);
        
        ButtonListener listener = new ButtonListener();
        okButton.addActionListener(listener);
         
    }

    private class ButtonListener implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e) 
        {      
            /*Store the widgets values in variables*/
            double length,startHeight,endHeight;
            length = (double) lengthSpin.getValue();
            startHeight = (double) sHeightSpin.getValue();
            endHeight = (double) eHeightSpin.getValue();
            
            /*Job Attributes - Instance Variables as they are the same for the 
            whole job*/
            setPoorAccess(poorAccessCheck.isSelected());
            setLocallyBuilt(locallyBuiltCheck.isSelected());
            setDifficulty(difficultyCombo.getSelectedIndex());
            
            int angle = 0;
            if (wps.getWalls().size()>1)
            {
                angle = (int) angleSpin.getValue();
            }
            
            //Stores Quantity of sleepers by bay
            baysArray = calculateBays(length,startHeight,endHeight);
            Wall newWall = new Wall(isLocallyBuilt(),isPoorAccess(),
                    getDifficulty(),baysArray,angle);
            
            wps.addWall(newWall);
            wps.getQuotePanel().getQuote().addWall(newWall);
            dispose();
        }
        
    }

    public ArrayList<Integer> getBaysArray() {
        return baysArray;
    }

    public void setBaysArray(ArrayList<Integer> baysArray) {
        this.baysArray = baysArray;
    }
 
    public boolean isLocallyBuilt() 
    {
        return locallyBuilt;
    }

    public void setLocallyBuilt(boolean locallyBuilt) 
    {
        this.locallyBuilt = locallyBuilt;
    }

    public boolean isPoorAccess() 
    {
        return poorAccess;
    }

    public void setPoorAccess(boolean poorAccess) 
    {
        this.poorAccess = poorAccess;
    }

    public int getDifficulty() 
    {
        return difficulty;
    }

    public void setDifficulty(int difficultyIndex) 
    {
        this.difficulty = difficultyIndex;
    }
    
    /**
     * Calculate the number of bays and sleepers, base on the length, 
     * start Height and end Height
     * @param wallLength
     * @param endLowest
     * @param endHighest
     * @return 
     */
    public ArrayList<Integer> calculateBays(double wallLength, double endLowest, 
            double endHighest)
    {
        final int BAY_LENGTH = 2;
        
        //Sleeper counter initialisation
        double tempHeight;
        double tempLength;
       
        //Trigonometry Variables
        double angle;
        double tgAngle;
                
        int totalBays;  
        double bayHeight;
        int endBaySleepers;
        int fullSleepers;
        double extraSleeper;
        int baseSleepers;
        int totalSleepers;
        
       
         
        //Array with sleeper count for each bay
        ArrayList<Integer> baySleepers = new ArrayList();
        
        /*Botton sleepers are known base on lowest height. Lowest height is not 
        needed on trigonometry calculations*/
        tempHeight = endHighest - endLowest;
        /*Final bay sleeper count is known, it is not needed on trigonometry
        calculations*/
        tempLength = wallLength - BAY_LENGTH; 
        
        //Triangle angle
        angle = Math.toDegrees(Math.atan(tempHeight/tempLength));
        //Convert Angle to radians
        angle = Math.toRadians(angle);
        //Tangent of radians, use to calculate then height 
        tgAngle = Math.tan(angle);
        
        totalBays = (int)(tempLength/2);
        
        //Highest bay sleeper count
        endBaySleepers = (int) (endHighest/0.2);
        //Adding end bay sleeper count on the array
        baySleepers.add(endBaySleepers);
                
        for (int i=totalBays ; i>0 ; i--) 
        {
            //Height of previous bay
            tempLength = tempLength - BAY_LENGTH;
            bayHeight=(tempLength*tgAngle);
           
            //Number of full sleepers needed, not fractions.
            fullSleepers = (int) ((bayHeight)/0.2);
            //Calculating exceeding sleeper.
            extraSleeper = ((bayHeight)/0.2);
            //Deciding whether an extra full sleeper is needed or not.
            if (extraSleeper % fullSleepers > 0.5)
            {
                fullSleepers++;
            }
            
            //Number of sleepers needed from the ground to the lowest height
            baseSleepers = (int)(endLowest/0.2);
            
            //Total Sleeper per bay
            totalSleepers = fullSleepers+baseSleepers;
            
            //Adding bay sleeper count on the array
            baySleepers.add(totalSleepers);
        }
        
        Collections.reverse(baySleepers);
        return baySleepers;
    }

   public ArrayList<Rectangle> getArrayRect() {
        return arrayRect;
    }

    public void setArrayRect(ArrayList<Rectangle> arrayRect) {
        this.arrayRect = arrayRect;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
    
    
}
