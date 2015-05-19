/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quotationassistantv2;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *Class with the menu
 * @author nixon_000
 */
public class TopPanel extends JPanel 
{
    private MainFrame mf;
    private WallPanel wp;
    private WallPanelScrolled wps;
    private QuotePanel qp;
    private Quote quote;
    private JButton newQuote, newWall, killWall;
    private JLabel jobLabel;
    private JTextField jobText;
    private Border borderLined = BorderFactory.createLineBorder(Color.black,3);
    private Border title = BorderFactory.createTitledBorder(borderLined, "Client Details");
    
    public TopPanel(WallPanelScrolled wps, QuotePanel qp)
    {
        this.wps = wps;
        this.qp = qp;
        setBorder(title);
        
        setLayout(new FlowLayout(FlowLayout.LEADING,15,10));
        jobLabel = new JLabel("Job Indicator");
        add(jobLabel);
        
        jobText = new JTextField(20);
        add(jobText);
        
        ButtonListener buttonListener = new ButtonListener();
        
        newQuote = new JButton("Start New Quote");
        add(newQuote);
        newQuote.addActionListener(buttonListener);
        newQuote.setActionCommand("New Quote");
        
        newWall = new JButton("Add New Wall Section");
        add(newWall);
        newWall.setEnabled(false);
        newWall.addActionListener(buttonListener);
        newWall.setActionCommand("New Wall");
        
        killWall = new JButton("Delete last section");
        add(killWall);
        killWall.setEnabled(false);
        killWall.addActionListener(buttonListener);
        killWall.setActionCommand("Kill Wall");
        
    }
    
 private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            switch(e.getActionCommand())
            {
                //Create a new Quote
                case "New Quote":
                {
                    quote = new Quote();
                    qp.setQuote(quote);
                    qp.getQuote().setId(jobText.getText());
                    wps.setJobIndicator(jobText.getText());
                    ArrayList<Wall> newWallArr = new ArrayList();
                    wps.setWalls(newWallArr);
                    qp.redrawQuote();
                    wps.redrawWPSPanel();
                    
                    newWall.setEnabled(true);
                    break;
                }
                //Create a new Wall
                case "New Wall":
                {
                    Wall wdialog = new Wall(wps, mf, "Wall Initial Details");
                    wdialog.setVisible(true);
                    qp.redrawQuote();
                    killWall.setEnabled(true);
                    break;
                }
                //Delete Last Wall
                case "Kill Wall":
                {
                    int index = wps.getWalls().size() - 1;
                    wps.removeWall(index);
                    qp.getQuote().getWalls().remove(index);

                    if(0 == index)
                    {
                        killWall.setEnabled(false);
                    }
                    qp.redrawQuote();
                    break;
                }
            }
        }    
    }
}
