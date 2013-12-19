
package world;

import actor.Rat;
import actor.RatBot;
import actor.RatBotActor;
import actor.Tail;
import grid.Grid;
import grid.Location;
import grid.RatBotsGrid;
import gui.RatBotsArena;
import gui.RatBotsColorAssigner;
import gui.RatBotsScoreBoard;
import java.util.ArrayList;
import java.util.Random;

/**
 * A RatBotWorld is full of RatBotActors used in the game RatBots.  
 * @author Spock
 */
public class RatBotWorld extends ActorWorld
{
    /**
     * The number of moves in a round of RatBots competition.
     */
    public static final int NUM_MOVES_IN_ROUND = 500;
    /**
     * The number of rounds in a match of RatBots competition.  
     */
    public static final int NUM_ROUNDS_IN_MATCH = 1000;

    private static final String DEFAULT_MESSAGE = "RatBots is awesome.";
    private static Random randy = new Random();

    private static int moveNum = 0;
    private static int roundNum = 1;
    
    private boolean roundRobin = false;
    private int rr1=0; 
    private int rr2=0;
    private boolean matchReady = false;    
    
    
    private RatBotsArena arena = new RatBotsArena();
    
    private ArrayList<Rat> ratsInMaze = new ArrayList<Rat>();
    private ArrayList<Rat> allrats = new ArrayList<Rat>();
    
    /**
     * Constructs a RatBot world with a default grid.
     */
    public RatBotWorld()
    {
        initializeGridForRound();
        initializeMatch();
    }

    /**
     * Constructs a RatBot world with a given grid.
     * @param grid the grid for this world.
     */
    public RatBotWorld(Grid<RatBotActor> grid)
    {
        super(grid);
        initializeGridForRound();
        initializeMatch();
    }

    /**
     * gets the Arena used in this World.
     * @return the Arena
     */
    public RatBotsArena getArena() { return arena; }
    /**
     * Gets the current move number in the round being played.  
     * @return the move number in the current round.
     */
    public static int getMoveNum() { return moveNum; }
    /**
     * Gets the current round number in the match being played.  
     * @return the round number in the current match.
     */
    public static int getRoundNum() { return roundNum; }
    
    private void initializeMatch()
    {
//        System.out.println("INITIALIZING MATCH");
        moveNum = 0;
        roundNum = 1;
        matchReady = true;
        
        if(roundRobin)
        {
            initializeRoundRobinMatch();
        }
        initializeGridForRound();
    }
    
    private void initializeRoundRobinMatch()
    {
        if(ratsInMaze.size() > 1)
        {
            System.out.println(ratsInMaze.get(0).getRatBot().getName()+","+ratsInMaze.get(0).getRoundsWon()
                    +","+  ratsInMaze.get(1).getRatBot().getName()+","+ratsInMaze.get(1).getRoundsWon() );
//                        +"      time(sec)="+(int)((System.currentTimeMillis() - matchstart)/1000));
            if(ratsInMaze.get(0).getRoundsWon()+ratsInMaze.get(1).getRoundsWon()<100)
                System.out.println("Incomplete match...???");
            //score match
            if(ratsInMaze.get(0).getRoundsWon()>ratsInMaze.get(1).getRoundsWon())
            {
                ratsInMaze.get(0).increaseMatchesWon();
                ratsInMaze.get(1).increaseMatchesLost();
            }
            if(ratsInMaze.get(0).getRoundsWon()==ratsInMaze.get(1).getRoundsWon())
            {
                ratsInMaze.get(0).increaseMatchesTied();
                ratsInMaze.get(1).increaseMatchesTied();
            }
            if(ratsInMaze.get(0).getRoundsWon()<ratsInMaze.get(1).getRoundsWon())
            {
                ratsInMaze.get(1).increaseMatchesWon();
                ratsInMaze.get(0).increaseMatchesLost();
            }
            ratsInMaze.get(0).setRoundsWon(0);
            ratsInMaze.get(1).setRoundsWon(0);

        }
        ratsInMaze.clear(); 
        rr2++;
        if(rr2==allrats.size())
        {
            rr1++;
            rr2=rr1+1;
            if(rr1==allrats.size()-1)
            {
                for(Rat x : allrats)
                {
                    System.out.println(x.getRatBot().getName()+
                            ",  TP=,"+x.getTotalScore() +
                            ",  w=,"+x.getMatchesWon()+
                            ",  t=,"+x.getMatchesTied()+
                            ",  l=,"+x.getMatchesLost()                              
                            );
                }
                System.out.println("TOURNEY COMPLETE");

            }
        }
        ratsInMaze.add(allrats.get(rr1));
        ratsInMaze.add(allrats.get(rr2));
        
    }
    
    
    /**
     * Initialize the arena and each of the RatBots for a round of competition.
     */
    public final void initializeGridForRound()
    {
        clearAllObjectsFromGrid();
        arena.initializeArena(this);
        for(Rat r : ratsInMaze) 
        {
            r.initialize();
            r.putSelfInGrid(getGrid(), getRandomEmptyCenterLocation());
            r.setDirection(getRandomDirection());
        }
        moveNum = 0; 
    }
    /**
     * Clears the Arena in preparation of starting a new round. 
     * @return an ArrayList of the Rats in the arena.
     */
    public void clearAllObjectsFromGrid()
    {
        RatBotsGrid<RatBotActor> gr = (RatBotsGrid<RatBotActor>)this.getGrid();
        for(int x=0; x<gr.getNumCols(); x++)
        {
            for(int y=0; y<gr.getNumRows(); y++)
            {
                Location loc = new Location(y,x);
                RatBotActor a = gr.get(loc);
                if(a != null)
                {
                    a.removeSelfFromGrid();
                }
            }
        }
    }
    /**
     * Scores the results from a round of competition.
     */
    public void scoreRound()
    {
        int max = RatBotsScoreBoard.getMaxScore();
        for(Rat r : ratsInMaze)
        {
            if(r.getScore() == max)
                r.increaseRoundsWon();
        }
        roundNum++;
    }
    
    //inheirits javadoc comment from world.
    @Override
    public void show()
    {
        if (getMessage() == null)
            setMessage(DEFAULT_MESSAGE);
        super.show();
    }

    //inheirits javadoc comment from world.
    @Override
    public void step()
    {
        Grid<RatBotActor> gr = getGrid();

        if(!matchReady)
            initializeMatch();
            
        
        moveNum++;
        if(moveNum > NUM_MOVES_IN_ROUND)
        {
//            clearAllObjectsFromGrid();
            scoreRound();
            initializeGridForRound();
            moveNum = 1;
        }
        if(roundNum >= NUM_ROUNDS_IN_MATCH && roundRobin) 
        {
            initializeMatch();
            return;
        } 
        ArrayList<RatBotActor> actors = new ArrayList<RatBotActor>();
        // Look at all grid locations.
        for (int r = 0; r < gr.getNumRows(); r++)
        {
            for (int c = 0; c < gr.getNumCols(); c++)
            {
                // If there's an object at this location, put it in the array.
                Location loc = new Location(r, c);
                if (gr.get(loc) != null) 
                    actors.add(gr.get(loc));
            }
        }
        
        if(actors.size() > 1)
        {
            //shuffle their order for acting.
            for(int z=0;z<actors.size()*2;z++)
            {
                //Pick a random one.
                int from = randy.nextInt(actors.size());
                //Swap it to the front.
                RatBotActor a = actors.get(from);
                RatBotActor b = actors.get(0);
                actors.set(from,b);
                actors.set(0,a);              
            }
        }

        for (RatBotActor a : actors)
        {
            // only act if another actor hasn't removed a
            if (a.getGrid() == gr)
                a.act();
        }
                
    }
    /**
     * Add a new RatBot to the arena. 
     * @param bot the RatBot to be added.  
     */
    public void add(RatBot bot)
    {
        Rat newRat = new Rat(bot,RatBotsColorAssigner.getAssignedColor());
        Location inCenter = this.getRandomEmptyCenterLocation();
        allrats.add(newRat);
        
        ratsInMaze.add(newRat);
        initializeGridForRound();
    }    
    /**
     * Gets one of the 4 possible directions.
     * @return a random direction.
     */
    public int getRandomDirection()
    {
        return randy.nextInt(4)*90;
    }
    /**
     * Gets an empty Location in the center room.  
     * @return a random empty Location from the center room.  
     */
    public Location getRandomEmptyCenterLocation()
    {
        Grid<RatBotActor> gr = getGrid();
        int rows = gr.getNumRows();
        int cols = gr.getNumCols();

        // get all valid empty locations and pick one at random
        ArrayList<Location> emptyLocs = new ArrayList<Location>();
        for (int i = (rows-RatBotsArena.CENTER_SIZE)/2; i < (rows+RatBotsArena.CENTER_SIZE)/2; i++)
        {
            for (int j = (cols-RatBotsArena.CENTER_SIZE)/2; j < (cols+RatBotsArena.CENTER_SIZE)/2; j++)
            {
                Location loc = new Location(i, j);
                if (gr.isValid(loc) && (gr.get(loc) == null || gr.get(loc) instanceof Tail))
                    emptyLocs.add(loc);
            }
        }
        if (emptyLocs.isEmpty())
        {
            System.out.println("WARNING: could not find an empty center location!!!");
            return new Location(15,15);
        }
        int r = randy.nextInt(emptyLocs.size());
        return emptyLocs.get(r);       
    }
      
}
