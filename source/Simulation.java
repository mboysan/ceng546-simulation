import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Simulation singleton class. Main method resides in this class.
 * Date: 22.04.2015
 * @author Mehmet Can Boysan
 */
public class Simulation{

    public static int GAME_LOOP_CURRENT_TIME_STEP = 50;
    private static int GAME_LOOP_DEFAULT_TIME_STEP = 50;
    private static int GAME_LOOP_MAX_TIME_STEP = 50;
    private static int GAME_LOOP_MIN_TIME_STEP = 25;

    public static int MAX_NUMBER_OF_TEAMS = 4;
    public static int MIN_NUMBER_OF_TEAMS = 2;

    private Display display;

    private List<Team> allTeams;
    private Timer gameTimer;
    private boolean isSimulationPaused;
    private int currentNumberOfTeams;

    private static Simulation ourInstance = new Simulation();

    /**
     * @return singleton object of the <code>Simulation</code> class.
     */
    public static Simulation getInstance() {
        return ourInstance;
    }

    /**
     * Constructor of the Simulation Class
     */
    private Simulation() {
        this.gameTimer = new Timer();
        this.allTeams = new ArrayList<Team>();
        this.isSimulationPaused = false;
        this.currentNumberOfTeams = 2;
    }

    /**
     * Main method of the Simulation
     * @param args
     */
    public static void main(String args[]) {

        final Simulation simulation = Simulation.getInstance();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                simulation.setDisplay(new Display());
                simulation.resetSimulation(simulation.getCurrentNumberOfTeams());
            }
        });
    }

    /**
     * Resets the simulation to its initial state.
     * @param numOfTeams number of teams to work on the simulation
     */
    public void resetSimulation(int numOfTeams)
    {
        GAME_LOOP_CURRENT_TIME_STEP = GAME_LOOP_DEFAULT_TIME_STEP;

        pauseSimulation();

        allTeams.clear();

        initializeTeams(numOfTeams);

        getDisplay().resetDisplay(allTeams);

        initializeTeamMembers();

        resumeSimulation();
    }

    /**
     * Pauses the simulation.
     */
    public void pauseSimulation(){
        this.gameTimer.cancel();
        this.isSimulationPaused = true;
    }

    /**
     * Resumes the simulation
     */
    public void resumeSimulation(){
        initializeGameLoop();
        this.isSimulationPaused = false;
    }

    /**
     * Checks if the maximum allowed number of teams is reached and if not, resets the simulation by incrementing
     * current number of teams.
     * @return true if further increase in team numbers is allowed.
     */
    public boolean increaseTotalTeams(){
        int inc = this.currentNumberOfTeams + 1;
        if(inc <= MAX_NUMBER_OF_TEAMS){
            resetSimulation(inc);
            this.currentNumberOfTeams ++;
        }
        return (inc >= MAX_NUMBER_OF_TEAMS);
    }

    /**
     * Checks if the minimum allowed number of teams is reached and if not, resets the simulation by decrementing
     * current number of teams.
     * @return true if further decrease in team numbers is allowed.
     */
    public boolean decreaseTotalTeams(){
        int dec = this.currentNumberOfTeams - 1;
        if(dec >= MIN_NUMBER_OF_TEAMS){
            resetSimulation(dec);
            this.currentNumberOfTeams --;
        }
        return (dec <= MIN_NUMBER_OF_TEAMS);
    }

    /**
     * Checks if the minimum allowed game speed is reached and if not, resumes the simulation by decrementing step
     * time by 5.
     * @return true if further increase in speed is allowed.
     */
    public boolean increaseSpeed()
    {
        int dec = GAME_LOOP_CURRENT_TIME_STEP - 5;
        if(dec >= GAME_LOOP_MIN_TIME_STEP)
        {
            pauseSimulation();
            GAME_LOOP_CURRENT_TIME_STEP -= 5;
            resumeSimulation();
        }
        return (dec <= GAME_LOOP_MIN_TIME_STEP);
    }

    /**
     * Checks if the maximum allowed game speed is reached and if not, resumes the simulation by incrementing step
     * time by 5.
     * @return true if further decrease in speed is allowed.
     */
    public boolean decreaseSpeed()
    {
        int dec = GAME_LOOP_CURRENT_TIME_STEP + 5;
        if(dec <= GAME_LOOP_MAX_TIME_STEP)
        {
            pauseSimulation();
            GAME_LOOP_CURRENT_TIME_STEP += 5;
            resumeSimulation();
        }
        return (dec >= GAME_LOOP_MAX_TIME_STEP);
    }

    /**
     * Initializes teams and adds them to the <code>allTeams</code> List. Also assigns opponent teams for each teams.
     */
    private void initializeTeams(int numOfTeams)
    {
        Team redTeam = new Team("Imperials", Color.RED);
        Team blueTeam = new Team("Nords", Color.BLUE);
        Team blackTeam = new Team("Bretons", Color.BLACK);
        Team pinkTeam = new Team("Khajiits", Color.MAGENTA);

        if(numOfTeams >= 2){
            allTeams.add(redTeam);
            allTeams.add(blueTeam);
        }
        if(numOfTeams >= 3){
            allTeams.add(blackTeam);
        }
        if(numOfTeams >= 4){
            allTeams.add(pinkTeam);
        }

        //Set opponent teams for each team
        for(int i = 0; i < allTeams.size(); i++)
            for(int k = 0; k< allTeams.size(); k++)
                if(i!=k)
                    allTeams.get(i).getOpponentTeams().add(allTeams.get(k));
    }

    /**
     * Initializes team members for all the teams.
     */
    private void initializeTeamMembers()
    {
        //Initialize Team members in each team
        for(Team t: allTeams)
            t.initializeTeamMembers();
    }

    /**
     * Initializes the <code>gameTimer</code> Timer object with the current game speed and starts it.
     */
    private void initializeGameLoop()
    {
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                stepAll();
            }
        };
        gameTimer = new Timer();
        gameTimer.schedule(tt, 0, GAME_LOOP_CURRENT_TIME_STEP);
    }

    /**
     * Checks if the knight passed a score threshold. And if passes, creates a new <code>KnightDecorator</code>
     * sub-class object.
     * @param currentKnight Knight to check if decoration is applicable.
     * @return Decorated knight or the same knight object.
     */
    private Knight checkIfDecorationApplicable(Knight currentKnight)
    {
        if(currentKnight.getScore() > KnightDecorator.GRADE3_DECORATION_THRESHOLD) {
            if(!(currentKnight instanceof Grade3Decorator)) {
                if(currentKnight instanceof Grade2Decorator) {
                    currentKnight = new Grade3Decorator(currentKnight);
                }
                else if(currentKnight instanceof Grade1Decorator){
                    currentKnight = new Grade3Decorator(new Grade2Decorator(currentKnight));
                }
                else{
                    currentKnight = new Grade3Decorator(new Grade2Decorator(new Grade1Decorator(currentKnight)));
                }
            }
        }
        else if(currentKnight.getScore() > KnightDecorator.GRADE2_DECORATION_THRESHOLD){
            if(!(currentKnight instanceof Grade2Decorator)){
                if(currentKnight instanceof Grade1Decorator){
                    currentKnight = new Grade2Decorator(currentKnight);
                }
                else{
                    currentKnight = new Grade2Decorator(new Grade1Decorator(currentKnight));
                }
            }
        }
        else if(currentKnight.getScore() > KnightDecorator.GRADE1_DECORATION_THRESHOLD){
            if(!(currentKnight instanceof Grade1Decorator)){
                currentKnight = new Grade1Decorator(currentKnight);
            }
        }

        return currentKnight;
    }

    /**
     * Updates the team stats, sends knights for decoration, makes knights act according to their currently assigned
     * strategies and if knight health fell below zero removes the knight.
     */
    private void updateTeamAndKnightActions()
    {
        for(Team t: allTeams)
        {
            display.updateTeamStats(t);
            for(int i = 0 ; i < t.getKnights().size() ; i++)
            {
                t.getKnights().set(i, checkIfDecorationApplicable(t.getKnights().get(i)));
                if(t.getKnights().get(i).getCurrentHealth() > 0) {
                    t.getKnights().get(i).checkStrategyRandomization();
                    t.getKnights().get(i).getStrategy().act();
                }
                else
                {
                    t.removeTeamMember(t.getKnights().get(i));
                    i --;
                }
            }
            t.generateTeamMember();
        }
    }

    /**
     * Main game loop. Calls <code>updateTeamAndKnightActions</code> and calls the display's
     * gamePanel's repaint method.
     * First update knights and teams and then redraw them.
     */
    private void stepAll()
    {
        updateTeamAndKnightActions();
        display.getGamePanel().repaint();
    }

    /**
     * @param display Sets the simulation's display object.
     */
    public void setDisplay(Display display) {
        this.display = display;
    }

    /**
     * @param allTeams Set the simulation teams
     */
    public void setAllTeams(List<Team> allTeams) {
        this.allTeams = allTeams;
    }

    /**
     * @return Gets the simulation's display object.
     */
    public Display getDisplay() {
        return display;
    }

    /**
     * @return Gets the simulation's team list
     */
    public List<Team> getAllTeams() {
        return allTeams;
    }

    /**
     * @return Gets the current number of teams
     */
    public int getCurrentNumberOfTeams() {
        return currentNumberOfTeams;
    }

    /**
     * @return State of the simulation.
     */
    public boolean isSimulationPaused() {
        return isSimulationPaused;
    }
}