package actor;

/**
 * @author Spock
 */
import grid.Location;
import grid.RatBotsGrid;

/**
 * A RatBot is the 'brain' for a Rat in the game of RatBots.  Each time that 
 * a Rat is given its turn to act, it asks its RatBot how it should act.  The
 * Rat also provides the RatBot with all of the sensor information it has.  
 * </br>
 * It is important to note that even though a RatBot has a Location, 
 * changing that location does not actually move the Rat (or the RatBot.)  
 * It is only changing where you think you are, but really you're only fooling 
 * yourself.  The same is true about the other parameters a RatBot has.  
 * </br>
 * While this structure is intended to keep you from 'cheating' the game.  
 * I'm sure some of you will find a way around it.  Therefore, it is a rule 
 * that any attempts to work around the rules will eliminate you from all 
 * competitions.  (Although I would like to get feedback so that I can 
 * improve how the game works.)  
 */
public class RatBot 
{   
    //CONSTANTS for possible moves.

    /**
     * The value to return to not move during a turn. 
     */
    public static final int REST = -1;
    /**
     * The value to return to move in the desired direction. 
     */
    public static final int MOVE_NORTH = 0;
    /**
     * The value to return to move in the desired direction. 
     */
    public static final int MOVE_NORTHEAST = 45;
    /**
     * The value to return to move in the desired direction. 
     */
    public static final int MOVE_EAST = 90;
    /**
     * The value to return to move in the desired direction. 
     */
    public static final int MOVE_SOUTHEAST = 135;
    /**
     * The value to return to move in the desired direction. 
     */
    public static final int MOVE_SOUTH = 180;
    /**
     * The value to return to move in the desired direction. 
     */
    public static final int MOVE_SOUTHWEST = 225;
    /**
     * The value to return to move in the desired direction. 
     */
    public static final int MOVE_WEST = 270;
    /**
     * The value to return to move in the desired direction. 
     */
    public static final int MOVE_NORTHWEST = 315;
    /**
     * The value to return to build a wall in the desired direction.  
     */
    public static final int BUILD_WALL_NORTH = 360;
    /**
     * The value to return to build a wall in the desired direction.  
     */
    public static final int BUILD_WALL_EAST = 450;
    /**
     * The value to return to build a wall in the desired direction.  
     */
    public static final int BUILD_WALL_SOUTH = 540;
    /**
     * The value to return to build a wall in the desired direction.  
     */
    public static final int BUILD_WALL_WEST = 630;
               
    //instance variables
    /**
     * This will be set each turn to the current direction of the Rat 
     * that this RatBot is controlling.  Changing this does not change the
     * direction of the Rat.  The only way to change direction is to set 
     * desiredHeading, which will update the next time the Rat acts.
     */
    private int direction;
    /**
     * This will be set each turn to the current location of the Rat 
     * that this RatBot is controlling.  Changing this does not change the
     * location of the Rat.  (It will only change what you think it is, 
     * and it will be corrected the next time the Rat acts.)
     */
    private Location location;
    /**
     * This will be set each turn to the current score of the Rat 
     * that this RatBot is controlling.  Changing this does not change the
     * score of the Rat.  (It will only change what you think it is, 
     * and it will be corrected the next time the Rat acts.)
     */
    private int score;
    private int bestScore;
    /**
     * This will be set each turn to the current number of rounds won by the Rat 
     * that this RatBot is controlling.  Changing this does not change the
     * actual rounds won of the Rat.  (It will only change what you think it is, 
     * and it will be corrected the next time the Rat acts.)
     */
    private int roundsWon;
    /**
     * This will be set each turn to the include only the objects that the Rat 
     * that this RatBot is controlling can 'see' in the maze.  A Rat can see:
     * all adjacent spaces (including diagonally), 
     * and all objects until it sees a maze wall or Rat in each direction.  
     * Changing this does not change the nature of the maze's Grid.
     * (It will only change what you think it is, 
     * and it will be corrected the next time the Rat acts.)
     */    
    private RatBotsGrid<RatBotActor> sensorGrid;
    /**
     * The name of this RatBot.  You are welcome to change this whenever you
     * want to.  
     */
    private String name;  
    
    //Constructor
    /**
     * Constructs a new RatBot.
     * The name of the RatBot will be 'default' until it is changed.
     */
    public RatBot()
    {
        name = "default";
    }
    /**
     * Chooses the action for this RatBot. </br>
     * negative numbers = REST </br>
     * 0 to 359 = MOVE in that heading (if possible.) </br>
     *      will be rounded to nearest 45 degree value.
     * 360 to 719 = BUILD_WALL </br>
     *      subtract 360 to determine direction to build wall.
     *      will be rounded to nearest 90 degree value.
     * @return the selected action (as an integer) 
     */
    public int chooseAction()
    {
        // Every RatBot that extends this class should override this method.
        return REST;   
    }
    
    /**
     * This method should include code that will initialize this RatBot 
     * at the start of a new round.  
     */
    public void initForRound()
    {
        // Every RatBot that extends this class should override this method.
        /* empty */
    }
    
    //Accessors and Modifiers----------------------------------------------
        
    /**
     * Gets the direction that this RatBot is facing. 
     * @return the direction
     */
    public int getDirection() { return direction; }        
    /**
     * Sets the direction that this RatBot thinks it is facing.  
     * --This actually does change the direction the RatBot is facing.  
     * @param dir the direction
     */
    public void setDirection(int dir) 
    {
        direction = dir % Location.FULL_CIRCLE;
        if (direction < 0)
            direction += Location.FULL_CIRCLE;
        //RatBots can move diagonally in RatBots 14
        if(direction%Location.HALF_RIGHT != 0)
            direction = direction - direction%Location.HALF_RIGHT;
    }

    /**
     * Gets the location that this RatBot is on the grid.  
     * @return the location
     */
    public Location getLocation() { return location; }
    /**
     * Sets the location that this RatBot thinks it is at.  
     * (Remember that this doesn't actually change how the Rat will face.)  
     * @param loc the location
     */
    public void setLocation(Location loc) { location = loc; }
    
    /**
     * Gets the name of this RatBot.
     * @return the name
     */
    public String getName() { return name; } 
    /**
     * Sets the name of this RatBot.  
     * (You can actually change your name whenever you want.)
     * @param in the name
     */
    public void setName(String in) { name = in; }
    
    /**
     * Gets the current score in this round for this RatBot.  
     * @return the score
     */
    public int getScore() { return score; }
    /**
     * Gets the current best score in this round among all Rats in this match
     * @return the current best score among all rats for this round 
     */
    public int getBestScore() { return bestScore; }
    /**
     * Sets the score in this round of this RatBot.  
     * This method is intended to allow the Rat to tell the RatBot its score.
     * (Remember that this doesn't actually change your score. Just what you
     * think it is.) 
     * @param in the score
     */
    public void setScore(int in) { score = in; }
    
    /**
     * Get the number of rounds won in the match by this RatBot.
     * @return the number of rounds won
     */
    public int getRoundsWon() { return roundsWon; }
    /**
     * Sets the number of rounds won by this RatBot.  
     * This method is intended to allow the Rat to tell the RatBot information.
     * (Remember that this doesn't actually change the number of rounds you've
     * won. Just what you think it is.) 
     * @param in rounds won
     */
    public void setRoundsWon(int in) { roundsWon = in; }
    
    /**
     * Gets the sensor grid for this RatBot.  The sensor grid will be filled 
     * with whatever the Rat can see at the moment.  Spaces that it cannot 
     * see will be left empty in the grid.  
     * (Note that this is not 'the' Grid that is used in the game - just a 
     * copy of the items you can see.
     * @return the sensor grid
     */
    public RatBotsGrid<RatBotActor> getSensorGrid() { return sensorGrid; }
    /**
     * Sets the sensor grid for this RatBot.  
     * This method is intended to allow the Rat to tell the RatBot information.
     * (Remember that this won't actually change the actual game grid - 
     * just what you think you can see in it.) 
     * @param in the sensor grid
     */
    public void setSensorGrid(RatBotsGrid<RatBotActor> in) { sensorGrid = in; }
           
    
    
    /**
     * Determines whether it is possible to move in a given direction from a 
     * given Location.  The determination is based on the information that is 
     * visible to the RatBot (not knowledge of the entire maze.)  
     * @param loc the location to check for movement from.
     * @param dir the direction of the move being considered.
     * @return true if the RatBot can certainly move in this way.  false 
     * otherwise (including if the move is through 'Fog'.)
     */
    public boolean canMove(Location loc, int dir)
    {
        boolean ableToMove = true;
        Location next = loc.getAdjacentLocation(dir);
        //Make sure the next location being considered is on the grid
        if(!getSensorGrid().isValid(next))
            return false;
        //Make sure destination is not occupied by another RatBotActor
        if(getSensorGrid().get(next) instanceof Rat)
            return false;
        if(getSensorGrid().get(next) instanceof Fog)
            return false;
        
//        if(dir%90 == 0)
//        {   //For N-S-E-W
//            if(getSensorGrid().isWall(loc, dir))
//                ableToMove = false;
//        }
//        else
//        {   //For NE-NW-SE-SW
//            //Four walls must all be absent to move diagonally.
//            if(getSensorGrid().isWall(loc, dir+Location.HALF_LEFT))
//                ableToMove = false;
//            if(getSensorGrid().isWall(loc, dir+Location.HALF_RIGHT))
//                ableToMove = false;
//            if(getSensorGrid().isWall(next, dir+Location.HALF_LEFT+Location.HALF_CIRCLE))
//                ableToMove = false;
//            if(getSensorGrid().isWall(next, dir+Location.HALF_RIGHT+Location.HALF_CIRCLE))
//                ableToMove = false;
//        }      
        return ableToMove;
    }
    /**
     * Determines if this RatBot canMove in the specified direction.
     * @param dir the direction of the potential move.
     * @return true if the move is possible.
     */
    public boolean canMove(int dir)
    {
        return canMove(location, dir);
    }
    /**
     * Determines if this RatBot canMove in the direction it is facing.
     * @return true if the move is possible. 
     */
    public boolean canMove()
    {
        return canMove(location, direction);
    }
    
    //====================================================================
    // The methods below are provided for those not experienced with the 
    // GridWorld environment.
    //====================================================================
    
    /**
     * Gets the Actor at the given location.  This is limited by what the 
     * RatBot can see (which are only spaces that it could potentially move
     * to in a straight line.)  For any space not visible, a Fog object will 
     * be returned.  
     * @param loc the Location in question.
     * @return the RatBotActor at that Location.
     */
    public RatBotActor getActorAtLocation(Location loc)
    {
        RatBotsGrid<RatBotActor> gr = getSensorGrid();
        return gr.get(loc);
    }
    /**
     * Gets the Actor at the given x,y coordinates.  This is limited by what the 
     * RatBot can see (which are only spaces that it could potentially move
     * to in a straight line.)  For any space not visible, a Fog object will 
     * be returned.     
     * @param x the x coordinate of the location in question.
     * @param y the y coordinate of the location in question.
     * @return the RatBotActor at that location.  
     */
    public RatBotActor getActorAtLocation(int x, int y)
    {
        return getActorAtLocation(new Location(y,x));
    }
    /**
     * Gets the x coordinate within the maze of this RatBot.
     * @return the x coordinate of this RatBot.
     */
    public int getX()
    {
        return getLocation().getCol();
    }
    /**
     * Gets the y coordinate within the maze of this RatBot.
     * @return the y coordinate of this RatBot.
     */
    public int getY()
    {
        return getLocation().getRow();
    }
    /**
     * Calculates the number of spaces this RatBot could move in a given 
     * direction before it encounters a wall or Rat blocking its path.
     * @param dir the direction of potential movement.
     * @return the number of spaces this RatBot could move in that direction.
     */
    public int numberOfSpacesCanMoveInDirection(int dir)
    {
        int result = 0;
        Location loc = getLocation();
        
        while(canMove(loc,dir))
        {
            loc = loc.getAdjacentLocation(dir);
            result++;
        }
        return result;
    }
    /**
     * Determines whether or not a RatBot can see a Cheese in a given direction.  
     * To 'see' a Cheese means that the RatBot could move unobstructed to the
     * Cheese.
     * @param dir the direction being viewed.
     * @return true if a Cheese is visible in this direction.
     */
    public boolean seesCheese(int dir)
    {
        Location loc = getLocation();
        RatBotsGrid<RatBotActor> gr = getSensorGrid();
        
        while(canMove(loc,dir))
        {
            loc = loc.getAdjacentLocation(dir);
            if(gr.get(loc) instanceof Cheese)
                return true;
        }
        return false; 
    }
    /**
     * Determines whether or not a RatBot can see a Rat in a given direction.  
     * To 'see' a Rat means that the RatBot could move unobstructed to the
     * Rat.
     * @param dir the direction being viewed.
     * @return true if a Rat is visible in this direction.
     */   
    public boolean seesRat(int dir)
    {
        Location loc = getLocation();
        RatBotsGrid<RatBotActor> gr = getSensorGrid();
        
        while(canMove(loc,dir))
        {
            loc = loc.getAdjacentLocation(dir);
            if(gr.get(loc) instanceof Rat)
                return true;
        }
        return false; 
        
    }
    
    
}
