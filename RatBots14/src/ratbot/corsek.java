package ratbot;
import grid.Location;
import actor.RatBot;
import java.util.Random;
/**
 * @author Brian
 * 
 */
public class corsek extends RatBot
{
    Random randy = new Random();
    int direction;
    int method;
    //0=seek wall
    //1=seek hole in wall
    //2=seek corner cheese
    public corsek()
    {
        setName("<:3 )~~~~~                 ");
        direction = randy.nextInt(4)*90;
        
    }
    
    @Override
    public void initForRound()
    {
    	direction = randy.nextInt(4)*90;
    }
    
    @Override
    public int chooseAction()
    {    
    	scroll();
    	//System.out.println(numberOfSpacesCanMoveInDirection(getDirection()));
    	method = setAction();
    	System.out.println(method);
    	
//        System.out.println("Location: "+getLocation()+"Direction"+getDirection()+"Can Move:"+canMove());
            if(method == 0)
            {
            	if(cfc())
            		return getDirection();
            	int tubeway = findTube();
            	if(tubeway>=360)
            	{
            		return tubeway-360;
            	}
            	return tubeway;
            }
            
            if(method == 2)
            {
            	return inTube();
            }
            
            return -1;
        
          //  return getDirection();
    }
    
    public void scroll()
    {
    	String name = getName();
    	name = (name.substring(1)+name.charAt(0));
    	setName(name);
    }
    
    public int setAction()
    {
    	if(getX() == 0 || getX() == 19)
		{return 2;}
    	else if(getY() == 0 || getY() == 19)	
		{return 2;}
    	else
		{
    		if(method == 2)
    		{
    			direction+=90;
    			if(direction >= 360)
    			{
    				direction-=360;
    			}
    		}
    		return 0;
    	}
    }
    
    public boolean cfc()
    {int far = 3;
    	for(int i = 0; i < far; i++)
    	{
	    	for(int dir = (direction-90); dir<=(direction+90); dir+=45)
	        {
	    		int dist = numberOfSpacesCanMoveInDirection(getDirection());
	    		if(dist > far)
	    			{far = dist;}
	            if(seesCheese(dir))
	            {
	            	if(numberOfSpacesCanMoveInDirection(dir) <= i)
	            	{
	            		setDirection(dir);
	            		return true;
	            	}
	            }
	        }	    	
    	}
    	return false;
    }
    
    public int findTube()
    { 
    	int loopCount = 0;    
    	int dirl = direction;
    	int dirr = direction;
    	setDirection(direction);
    	if(canMove())
    		{System.out.println("straight");
    		return direction;}
    	do
        {
    		dirl-=45;
    		setDirection(dirl);     
            loopCount++;
            if(canMove())
            	{return dirl;}
            dirr+=45;
            setDirection(dirr);
            loopCount++;
            if(canMove())
        		{return dirr;}
        } while(!canMove() && loopCount < 10);
    	return -1;
    }
    
    public int inTube()
    { 
    	int loopCount = 0;
    	while(!canMove() && loopCount < 4)
    	{
    		setDirection(direction+90);
    		loopCount++;
    	} 
    	return getDirection();
    }
}

/**
 * Things to do:
 * 
 * Avoid dead ends
 * 
 * tube finding method
 * 
 * WONT TURN AFTER HITTING WALL
 * 
 * 
 * 
 * 
 * 
 * 
 */

