package ratbot;

import actor.RatBot;
import java.util.Random;
/**
 * @author Spock
 * CheeseGetter chooses a random move each turn.
 * However, CheeseGetter will always go to a Cheese it sees.  
 */
public class CheeseGetter extends RatBot
{
    Random randy = new Random();
    
    public CheeseGetter()
    {
        setName("CheeseGetter");
    }
    
    @Override
    public int chooseAction()
    {        
        for(int dir = 0; dir<360; dir+=45)
        {
            if(seesCheese(dir))
                return dir;
        }
        
        //If I don't see Cheese...
        return randy.nextInt(360);  //move some Random direction!
    }
    
}
