/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quotationassistantv2;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author nixon_000
 */
public class QuotationAssistant 
{
    /**
     * GUI creation code is placed on the Event Dispatch Thread (EDT). 
     * This will prevent potential race conditions that could lead to deadlock.
     * http://docs.oracle.com/
     * @param args
     */
    public static void main(String[] args) 
    {
        
        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run() 
        {
                createAndShowGUI();
            }
        });
    }
    
    private static void createAndShowGUI() 
    {
        JFrame frame = new JFrame("Quotation Assistant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700,400);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        JComponent newContentPane = new MainPanel();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        
        
        frame.pack();
        frame.setVisible(true);
        
        
    }
}
