/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quotationassistantv2;

import java.awt.BorderLayout;
import javax.swing.JPanel;


/**
 *Container of all JPanels
 * @author nixon_000
 */
public class MainPanel extends JPanel
{
    public MainPanel()
    {
        super(new BorderLayout());
              
        //Instances of each Panel class
        QuotePanel qp = new QuotePanel();
        WallPanelScrolled wps = new WallPanelScrolled(qp);
        TopPanel tp = new TopPanel(wps, qp);
        
        //Adding Panels to Main Window
        add(tp, BorderLayout.PAGE_START);
        add(wps, BorderLayout.CENTER);
        add(qp, BorderLayout.LINE_END);
    }
}
