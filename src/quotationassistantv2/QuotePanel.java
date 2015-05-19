/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quotationassistantv2;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;

/**
 *
 * @author nixon_000
 */
public class QuotePanel extends JPanel
{
    private final double SLEEPER_HEIGHT = 0.2;
    private final double SLEEPER_LENGTH = 2.0;
    private final double BASE_COST = 425;
    private final int LOCATION_SURCHARGE = 5;
    private final int ACCESS_SURCHARGE = 30;
    private final int SANDY_SURCHARGE = 10;
    private final int LIMESTONE_SURCHARGE = 15;
    private final int BLUESTONE_SURCHARGE = 30;
    
    private Border borderLined = BorderFactory.createLineBorder(Color.black,3);
    private Border title = BorderFactory.createTitledBorder(borderLined, "Quotation");
    private Quote quote;
    private JTextArea quoteDisplay;
    private int bayTotal;
    
    public QuotePanel() 
    {  
        setBorder(title);
        setBackground(Color.white);
        
        quoteDisplay = new JTextArea();
        quoteDisplay.setEditable(false);
                
        drawQuote();
        
        this.revalidate();
    }
    
    public void setQuote(Quote quote)
    {
        this.quote = quote;
    }
    
    public Quote getQuote()
    {
        return this.quote;
    }
    
    private void drawQuote()
    {
        //Add the text box to the quote panel
        this.add(quoteDisplay);
        
        //Print quote header
        quoteDisplay.setText("Southern Area Wall Builders\t\tRetaining Wall Quote\r\n\r\n");       
        quoteDisplay.setText(quoteDisplay.getText() + "Quotation ID: ");
        
        //Display the quote ID number if valid
        //Otherwise, ask user to generate new quote
        if(null == quote || null == quote.getId())
        {
            quoteDisplay.setText(
                quoteDisplay.getText() + 
                "Please enter Job indicator and start new quote.\r\n\r\n"
            );
        }
        else
        {
            quoteDisplay.setText(
                quoteDisplay.getText() + 
                this.quote.getId() + 
                "\r\n\r\n"
            );
        }
        
        //Print data on any walls currently defined
        if(null != quote)
        {            
            bayTotal = 0;

            //For every wall in the quote:
            for(int i=0; i<quote.getWalls().size(); i++)
            {
                //Opening bar
                String wallConfig = "|";
                int bayCount = 0;
                double squareMeters = 0;
                
                for(int bay : quote.getWalls().get(i).getBaysArray())
                {
                    //Create string showing configuration
                    wallConfig += bay + "|";
                    //Keep track of number of sleeper
                    bayCount += bay;
                }
                
                bayTotal += bayCount;
                squareMeters += bayCount*SLEEPER_HEIGHT*SLEEPER_LENGTH;
                
                quoteDisplay.setText(
                    quoteDisplay.getText() + "Wall #" + (i+1) + "\r\n" +
                    "Wall Configuration: " + wallConfig + 
                    " - " + bayCount + " Sleepers\r\n\r"
                );
                
                quoteDisplay.setText(
                    quoteDisplay.getText() + "Base Cost: " + Math.round(squareMeters*100)/100 + 
                    "sqm @ $" + BASE_COST + "/sqm = $" + Math.round(squareMeters*BASE_COST*100)/100 +
                    "\r\n\r\n"
                );
            }
            
            //Print subtotal
            quote.setSubTotal(BASE_COST * bayTotal * SLEEPER_HEIGHT * SLEEPER_LENGTH);
            quoteDisplay.setText(
                quoteDisplay.getText() + "Sub-Total: $" +
                quote.getSubTotal() + "\r\n\r\n"
            );
            
            //Print any active modifiers
            if(null != quote && quote.getWalls().size() > 0)
            {
                quote.setModifierTotal(0);
                if(false == quote.getWalls().get(0).isLocallyBuilt())
                {
                    quoteDisplay.setText(
                        quoteDisplay.getText() + "Location surcharge: " +
                        LOCATION_SURCHARGE + "%\r\n"
                    );
                    quote.addToModifer(LOCATION_SURCHARGE);
                }
                if(true == quote.getWalls().get(0).isPoorAccess())
                {
                    quoteDisplay.setText(
                        quoteDisplay.getText() + "Poor access surcharge: " +
                        ACCESS_SURCHARGE + "%\r\n"
                    );
                    quote.addToModifer(ACCESS_SURCHARGE);
                }
                switch(quote.getWalls().get(0).getDifficulty())
                {
                    case 0: //Normal
                    {
                        //No surcharge
                        break;
                    }
                    case 1: //Sandy
                    {
                        quoteDisplay.setText(
                        quoteDisplay.getText() + "Soil surcharge - Sandy: " +
                        SANDY_SURCHARGE + "%\r\n"
                        );
                        quote.addToModifer(SANDY_SURCHARGE);
                        break;
                    }
                    case 2: //Limestone
                    {
                        quoteDisplay.setText(
                        quoteDisplay.getText() + "Soil surcharge - Limestone: " +
                        LIMESTONE_SURCHARGE + "%\r\n"
                        );
                        quote.addToModifer(LIMESTONE_SURCHARGE);
                        break;
                    }
                    case 3: //Bluestone
                    {
                        quoteDisplay.setText(
                        quoteDisplay.getText() + "Soil surcharge - Bluestone: " +
                        BLUESTONE_SURCHARGE + "%\r\n"
                        );
                        quote.addToModifer(BLUESTONE_SURCHARGE);
                        break;
                    }
                }
                
                quoteDisplay.setText(
                        quoteDisplay.getText() + "\r\nTotal surcharges: " +
                        quote.getModifierTotal() + "%\r\n"
                );
            }
            
            //Print grand total
            quoteDisplay.setText(
                quoteDisplay.getText() + "--------------------" +
                "\r\nGrand Total: $" +
                quote.getGrandTotal() + "\r\n\r\n"
            );
        }
    }
    
    public void redrawQuote()
    {
        this.removeAll();
        drawQuote();
        this.repaint();
    }
}
