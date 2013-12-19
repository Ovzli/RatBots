package actor;

import java.awt.Color;

/**
 * A <code>Block</code> is an actor that does nothing. It is commonly used to
 * block other actors from moving. <br />
 */

public class Cheese extends RatBotActor
{     
    private static final Color DEFAULT_COLOR = Color.YELLOW;
    private final int CORNER_CHEESE_VALUE = 200;
    private final int DROP_CHEESE_MAX_VALUE = 50;
    private final int START_CHEESE_MAX_VALUE = 20;
    private final double POINT_INCREASE_PER_TURN = 0.2;
    private double pointValue;
    private int maxPointValue;
    private boolean cornerCheese;    

    /**
     * Constructs a Starter Cheese.
     */
    public Cheese()
    {
        pointValue = 1;
        maxPointValue = START_CHEESE_MAX_VALUE;
        cornerCheese = false;
        setColor();
    }
    
    /**
     * Constructs a Cheese. used during the run of play.
     */
    public Cheese(boolean corner)
    {
        pointValue = 1;
        maxPointValue = DROP_CHEESE_MAX_VALUE;
        cornerCheese = corner;
        if(corner)
        {
            pointValue = CORNER_CHEESE_VALUE;
            maxPointValue = CORNER_CHEESE_VALUE;
        }
        setColor();
    }
    /**
     * Constructs a Cheese that is a copy of another Cheese.
     * @param in the Cheese to be copied.
     */
    public Cheese(Cheese in)
    {
        super(in);
        pointValue = in.getPointValue();
        maxPointValue = in.getMaxPointValue();
    }

    /**
     * Overrides the <code>act</code> method in the <code>Actor</code> class
     * to do nothing except increase in point value if appropriate.
     */
    @Override
    public void act()
    {
        if(pointValue < maxPointValue)
            pointValue += POINT_INCREASE_PER_TURN;
        
        setColor();
     }
    
    private void setColor()
    {
        int red = (int)(255.0*pointValue/maxPointValue);
        if(red>255) red = 255;
        if(red<0) red = 0;
        setColor(new Color(red,255,maxPointValue)); 
        
        if(maxPointValue == CORNER_CHEESE_VALUE)
            setColor(new Color(255,0,0));
    }
    /**
     * Determines whether this is a corner Cheese.
     * @return true if this is a corner cheese.
     */
    public boolean isCorner()
    {
        return cornerCheese;
    }
    /**
     * Gets the current point value of this Cheese.
     * @return the point value of this cheese.
     */
    public int getPointValue()
    {
        return (int)pointValue;
    }
    /**
     * Gets the maximum point value of this Cheese.
     * @return the maximum point value of this cheese. 
     */
    public int getMaxPointValue()
    {
        return maxPointValue;
    }
    
    @Override
    public RatBotActor getClone()
    {
        RatBotActor clone = new Cheese(this);
        return clone;
    }

    @Override
    public String toString()
    {
        if(cornerCheese)
            return "CornerCheese worth "+getPointValue();     
        else 
            return "Cheese worth "+getPointValue()+" out of "+maxPointValue;
    }
}
