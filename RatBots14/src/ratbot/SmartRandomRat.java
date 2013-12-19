package ratbot;

import actor.RatBot;
import java.util.Random;
/**
 * @author Spock
 * SmartRandomRat chooses a random direction each time, 
 * but does not choose a direction that runs into a wall.
 */
public class SmartRandomRat extends RatBot
{
    Random randy = new Random();
    
    public SmartRandomRat()
    {
        setName("SmartRandomRat");
    }
    
    @Override
    public int chooseAction()
    {     
        int loopCount = 0;
    
        do
        {
            setDirection(randy.nextInt(360));  //turn some Random direction!     
            loopCount++;
        } while(!canMove() && loopCount < 10); //choose again if can't move.
        
        return getDirection();
    }
    
}
