package actor;

import java.awt.Color;

/**
 * A <code>Tail</code> is a RatBotActor that darkens over time. 
 * Rats leave a tail as they move. <br />
 */

public class Tail extends RatBotActor
{
    private static final Color DEFAULT_COLOR = Color.PINK;
    private static final double DARKENING_FACTOR = 0.15;
    private static final int LIMIT = 10;
    
    /**
     * Constructs a pink tail.
     */
    public Tail()
    {
        setColor(DEFAULT_COLOR);
    }

    /**
     * Constructs a tail of a given color.
     * @param initialColor the initial color of this tail
     */
    public Tail(Color initialColor)
    {
        setColor(initialColor);
    }

    /**
     * Constructs a copy of this Tail.
     * @param in the Tail being copied.
     */
    public Tail(Tail in)
    {
        super(in);
        setColor(in.getColor());
    }
    /**
     * Causes the color of this tail to darken.
     */
    @Override
    public void act()
    {
        fadeOut();
    }
    
    /**
     * Fades out the color of a destroyed tail until it is nearly the same 
     * color as the arena (light gray) at which point it is removed.
     */   
    public void fadeOut()
    {
        Color c = getColor();
        Color base = Color.LIGHT_GRAY;
        int red = (int) (c.getRed() - (c.getRed()-base.getRed()) * (DARKENING_FACTOR));
        int green = (int) (c.getGreen() - (c.getGreen()-base.getGreen()) * (DARKENING_FACTOR));
        int blue = (int) (c.getBlue() - (c.getBlue()-base.getBlue()) * (DARKENING_FACTOR));

        setColor(new Color(red, green, blue));
        
        if(Math.abs(c.getRed()-base.getRed()) < LIMIT && 
                Math.abs(c.getGreen()-base.getGreen()) < LIMIT && 
                Math.abs(c.getBlue()-base.getBlue()) < LIMIT)
            removeSelfFromGrid();        
    }
    
    
    @Override
    public String toString()
    {
        return "Tail";
    }
    
    @Override
    public RatBotActor getClone()
    {
        RatBotActor clone = new Tail(this);
        return clone;
    }

}
