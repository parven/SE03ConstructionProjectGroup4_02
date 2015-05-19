/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package quotationassistantv2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *Drawing area, where all the drawing is performed
 * @author nixon_000
 */
public class WallPanel extends JPanel
{
    Border borderLined = BorderFactory.createLineBorder(Color.black,3);
    Border title = BorderFactory.createTitledBorder(borderLined, "Wall");
    
    private WallPanelScrolled wps;
    boolean flagY = false;
    int tempY = 0;
    
    
    private ArrayList<Integer> wallRect = new ArrayList();

    public ArrayList<Integer> getWallRect() {
        return wallRect;
    }

    public void setWallRect(ArrayList<Integer> wallRect) {
        this.wallRect = wallRect;
    }
    
    
    final Font f = new Font("Calibri", Font.BOLD, 16);
    final int INITIAL_X = 10;
    final int INITIAL_Y = 540;
    final int SLEEPER_W = 100; 
    final int SLEEPER_H = 20; 
    int x = 0;
    int y = 0;
    int minimumY = 10;
    
    MouseMoves expandRect = new MouseMoves();
    
    public WallPanel(WallPanelScrolled wps)
    {
        this.wps = wps;
        setBackground(Color.white);
        addMouseMotionListener(expandRect);
        addMouseListener(expandRect);
        setPreferredSize(new Dimension(830,580));
        setAutoscrolls(true);
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
        /*Faster Rendering for mouse interaction*/
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        
        if (!wps.getWalls().isEmpty())
        {
            x = INITIAL_X;
            int wallNo = 1;
            /*Iterate for all Walls*/
            for (Wall wa : wps.getWalls())
            {
                /*Rectangle counters*/
                int rectangles = 0;
                for (int bays : wa.getBaysArray())
                {
                    y = INITIAL_Y;
                    for (int i=0; i<bays;i++)
                    {
                        brush.drawRect(x,y, SLEEPER_W, SLEEPER_H);
                        brush.setFont(f);
                        //Centering the Name
                        brush.drawString("Wall "+wallNo, x+28, y+16);
                        y = y - SLEEPER_H;
                    }
                                        
                    /*Painting rectangles to simulate at entire bay*/
                    if(wps.getWalls().get(wallNo-1).getArrayRect().size() != wps.getWalls().get(wallNo-1).getBaysArray().size())
                    {
                        wps.getWalls().get(wallNo-1).getArrayRect().add(new Rectangle(x,y+SLEEPER_H, SLEEPER_W, (SLEEPER_H*bays)));
                    }
                    g2.draw(wps.getWalls().get(wallNo-1).getArrayRect().get(rectangles));
                    rectangles++;
                    x = x + SLEEPER_W;
                    
                }
                wallNo++;
            }
        }
        
        if (x> 830)
        {
             this.setPreferredSize(new Dimension(x+20,560));
             this.revalidate();
        }
    }   
    /*Allows to expands bays*/
    public class MouseMoves extends MouseAdapter
    {
        private int x;
        private int y;
        
        @Override
        public void mousePressed(MouseEvent event) {
            x = event.getX();
            y = event.getY();

        }
        

        @Override
        public void mouseDragged(MouseEvent event) 
        {
            int dragx = event.getX() - x;
            int dragy = event.getY() - y;
            
            int j = 0;
            /*Check which bay is being dragged*/
            for(Wall wall : wps.getWalls())            
            {   
                
                int arrRectsize = wps.getWalls().get(j).getArrayRect().size();
                for (int i = 0; i<arrRectsize;i++)
                {
                    if (wps.getWalls().get(j).getArrayRect().get(i).getBounds().contains(x, y)) 
                    {
                        int initialP = wps.getWalls().get(j).getArrayRect().get(i).y;
                        if (initialP > event.getY()) 
                        {
                            initialP = event.getY();
                            wps.getWalls().get(j).getArrayRect().get(i).y += dragy;
                            wps.getWalls().get(j).getArrayRect().get(i).height += Math.abs(dragy);
                            repaint();
                            revalidate();
                        }else 
                        {
                            wps.getWalls().get(j).getArrayRect().get(i).y += dragy;
                            wps.getWalls().get(j).getArrayRect().get(i).height -= dragy;
                            repaint();
                        }
                        repaint();
                        revalidate();
                    }      
                }
                j++;
            }
            x += dragx;
            y += dragy;
        }
        
        @Override
        public void mouseReleased(MouseEvent event) 
        {
            x = event.getX();
            y = event.getY();
            
            /*Check which bay is being unreleased*/
            int j = 0;  
            for(Wall wall : wps.getWalls())            
            {  
                int arrRectsize = wps.getWalls().get(j).getArrayRect().size();
                for (int i = 0; i<arrRectsize;i++)
                {
                if (wps.getWalls().get(j).getArrayRect().get(i).getBounds().contains(x, y)) 
                {
                    int newSleepers = sleepersCount(wps.getWalls().get(j).getArrayRect().get(i).height);
                    
                    wps.getWalls().get(j).getBaysArray().set(i, newSleepers);
                    wps.getQuotePanel().redrawQuote();
                    
                   
                    
                }    
                
            }
            j++;
            }
            int index = 0;
             for(Wall w : wps.getWalls())            
                    {
                        wps.getWalls().get(index).getArrayRect().clear(); 
                        index++;
                    }
            repaint();
            revalidate();
        } 
        
        
        
        
        public int sleepersCount(int height)
        {
            //Number of full sleepers needed, not fractions.
            int fullSleepers = (int) ((height)/20);
            //Calculating exceeding sleeper.
            int extraSleeper = ((height)/20);
            //Deciding whether an extra full sleeper is needed or not.
            if (fullSleepers != 0)
            {
            if (extraSleeper % fullSleepers > 0.5)
            {
                fullSleepers++;
            }
            }
            return fullSleepers;
            
        }
        
        
    }
   
}

