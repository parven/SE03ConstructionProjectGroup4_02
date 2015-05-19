/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quotationassistantv2;

import java.util.ArrayList;

/**
 *
 * @author Brendan
 */
public class Quote {
    //Quote number
    private String quoteId;
    private double costSubTotal;
    private int modifierTotal; //Integer number of percent, cast to double when using
    
    //Array of walls that this quote will cover
    private ArrayList<Wall> wallArray = new ArrayList();
    
    public Quote()
    {
        
    }
    
    public void setId(String id)
    {
        this.quoteId = id;
    }
    
    public String getId()
    {
        return this.quoteId;
    }
    
    public void setSubTotal(double subtotal)
    {
        this.costSubTotal = subtotal;
    }
    
    public double getSubTotal()
    {
        return this.costSubTotal;
    }
    
    public double getGrandTotal()
    {
        return this.costSubTotal * (1 + ((double)this.modifierTotal)/100);
    }
    
    public void setModifierTotal(int modifier)
    {
        this.modifierTotal = modifier;
    }
    
    public void addToModifer(int modifier)
    {
        this.modifierTotal += modifier;
    }
    
    public double getModifierTotal()
    {
        return this.modifierTotal;
    }
    
    public void addWall(Wall wall)
    {
        wallArray.add(wall);
    }
    
    public ArrayList<Wall> getWalls()
    {
        return wallArray;
    }
}
