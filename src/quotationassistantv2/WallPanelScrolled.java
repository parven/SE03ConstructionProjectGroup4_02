/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quotationassistantv2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
/**
 * Class where the drawing panel is.
 * @author nixon_000
 */
public class WallPanelScrolled extends JPanel
{
    private ArrayList<Wall> walls = new ArrayList();
    private WallPanel wallPanel;
    private MainFrame mf;
    private WallPanel wp;
    private JTextArea wallDisplay;
    private String jobIndicator;
    private JPanel headerPanel;
    private JButton topDownView;

    public String getJobIndicator() {
        return jobIndicator;
    }

    public void setJobIndicator(String jobIndicator) {
        this.jobIndicator = jobIndicator;
    }
    
    public void addWall(Wall bays) 
    {
        if(null == walls)
        {
            walls = new ArrayList();
        }
        walls.add(bays);
        this.repaint();
    }
    
    public void removeWall(int index)
    {
        if(null != walls.get(index))
        {
            walls.remove(index);
            this.repaint();
        }
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
    }
    
    Border borderLined = BorderFactory.createLineBorder(Color.black,3);
    Border title = BorderFactory.createTitledBorder(borderLined, "Wall");
    private QuotePanel qp;
    
    public WallPanelScrolled(QuotePanel qp) 
    {
        super(new BorderLayout());
        
        this.qp = qp;
        
        setBorder(title);
        setBackground(Color.white);
        
        //Set up a headerPanel
        
        headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.white);
        add(headerPanel,BorderLayout.PAGE_START);
        
        wallDisplay = new JTextArea();
        wallDisplay.setEditable(false);
        setWallDisplay();
        
        topDownView = new JButton("Top-Down View");
        headerPanel.add(topDownView,BorderLayout.LINE_END);
        topDownView.setEnabled(false); //Button not implemented
        
        ButtonListener buttonListener = new ButtonListener();
        topDownView.addActionListener(buttonListener);
        
        
        //Set up the Wall Panel.
        wallPanel = new WallPanel(this);
        
        //Set up the Wall Panel to the ScrollPane
        JScrollPane scroller = new JScrollPane(wallPanel);
        scroller.setPreferredSize(new Dimension(830,580));
        add(scroller, BorderLayout.CENTER);
    }
    
    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
             TopDownViewDialog wdialog = new TopDownViewDialog(mf, "Top Down View",true);
             wdialog.setVisible(true);
        }
    }
    
    public QuotePanel getQuotePanel()
    {
        return this.qp;
    }

    private void setWallDisplay() 
    {
        
        headerPanel.add(wallDisplay,BorderLayout.LINE_START);
        wallDisplay.setText(""); 
        wallDisplay.setText("Job Indicator: ");  
        if(null == this.getJobIndicator())
        {
           wallDisplay.setText(wallDisplay.getText() + 
                "Please enter Job indicator and start new quote");
        }
        else
        {
           wallDisplay.setText(wallDisplay.getText() + this.getJobIndicator());
        }
        
    }

    void redrawWPSPanel() 
    {
       this.remove(wallDisplay);
       setWallDisplay();
       this.repaint();
    }
}

