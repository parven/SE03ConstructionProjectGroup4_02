/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quotationassistantv2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 *
 * @author nixon_000
 */
/**
 * Class not implemented
 * @author nixon_000
 */
public class TopDownViewDialog extends JDialog
{

    final Font f = new Font("Calibri", Font.BOLD, 14);
    JPanel topDownViewPanel;
    public TopDownViewDialog(Frame owner, String title, boolean modal) 
    {
        super(owner, title, modal);
        setSize(new Dimension(350,350));
        setBackground(Color.white);
        setResizable(false);
        
        topDownViewPanel = new TopDownViewPanel();
        add(topDownViewPanel);
        
    }
    
    
    
    public class TopDownViewPanel extends JPanel
    {

        public TopDownViewPanel() 
        {
            setSize(new Dimension(350,350));
            setBackground(Color.white);
        }
        
        
        /**
        * Repaints the component - not directly invoked, called through repaint or 
        * similar operation.
        * @param brush a graphics object
        */    
       @Override
        public void paintComponent(Graphics brush)
        {
            super.paintComponent(brush);
            Graphics2D g2 = (Graphics2D) brush;
            g2.setStroke(new BasicStroke(8));
            g2.setFont(f);
            g2.drawLine(200,200,200,100);
            g2.drawLine(100,100,200,100);
            g2.drawString("Wall 1",100,90);
            g2.drawString("Wall 2",150,150);
            
            
        }
    }
    
}
