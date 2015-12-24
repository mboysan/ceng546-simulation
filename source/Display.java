import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Display class where all the simulation drawings are implemented
 * @author Mehmet Can Boysan
 */
public class Display extends JFrame implements ActionListener{

    private Map<Color,String> colorMap;

    public static int WINDOW_WIDTH = 1200;
    public static int WINDOW_HEIGHT = 700;

    private static String INCREASE_NUM_TEAMS_BUTTON = "increaseNumTeamsButton";
    private static String DECREASE_NUM_TEAMS_BUTTON = "deceaseNumTeamsButton";
    private static String INCREASE_SPPED_BUTTON = "increaseSpeedButton";
    private static String DECREASE_SPPED_BUTTON = "decreaseSpeedButton";
    private static String PAUSE_BUTTON = "pauseButton";
    private static String RESET_BUTTON = "resetButton";

    private Simulation simulation;
    private List<Team> teams;

    private Rectangle playground;   //rectangular region that the entities are allowed to move
    private int playgroundX;
    private int playgroundWidth;
    private GamePanel gamePanel;

    private JButton increaseNumTeamsJButton;
    private JButton decreaseNumTeamsJButton;
    private JButton pauseSimulationJButton;
    private JButton increaseSimulationSpeedJButton;
    private JButton decreaseSimulationSpeedJButton;
    private JButton resetSimulationButton;

    private BufferedImage mapBackgroundImage;
    private BufferedImage logoImage;

    /**
     * Constructor of the Display class
     */
    public Display()
    {
        //Draw JFrame
        super("Extra Details Omitted Oblivion");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(Display.WINDOW_WIDTH, Display.WINDOW_HEIGHT);
        this.setResizable(false);
        this.setVisible(true);

        //Put all the color codes necessary with their names for later use.
        colorMap = new HashMap<Color, String>();
        colorMap.put(Color.RED, "Red");
        colorMap.put(Color.BLUE, "Blue");
        colorMap.put(Color.BLACK, "Black");
        colorMap.put(Color.MAGENTA, "Magenta");
        colorMap.put(Color.YELLOW, "Yellow");
        colorMap.put(Color.CYAN, "Cyan");

        //Initialize GamePanel
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        gamePanel.setLayout(new BorderLayout());

        simulation = Simulation.getInstance();
    }

    /**
     * When the Strategy's <code>resetSimulation</code> is called the display will reset itself with the specified
     * teams.
     * @see Strategy
     * @param allTeams Teams of the simulation
     */
    public void resetDisplay(List<Team> allTeams)
    {
        this.teams = allTeams;

        //Read Images for the display
        try {
            mapBackgroundImage = ImageIO.read(getClass().getResourceAsStream("./asset_map.jpg"));
            logoImage = ImageIO.read(getClass().getResourceAsStream("./asset_logo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        initialize();
        setTeamBasePositionsAndPlaygrounds();
        this.validate();
    }

    /**
     * Initializes all the Panels, containers and buttons.
     */
    private void initialize(){

        increaseNumTeamsJButton = new JButton("Num Teams (+)");
        increaseNumTeamsJButton.setActionCommand(INCREASE_NUM_TEAMS_BUTTON);
        increaseNumTeamsJButton.addActionListener(this);

        decreaseNumTeamsJButton = new JButton("Num Teams (-)");
        decreaseNumTeamsJButton.setActionCommand(DECREASE_NUM_TEAMS_BUTTON);
        decreaseNumTeamsJButton.addActionListener(this);

        pauseSimulationJButton = new JButton("<html>Pause<br/>Simulation</html>");
        pauseSimulationJButton.setActionCommand(PAUSE_BUTTON);
        pauseSimulationJButton.addActionListener(this);

        increaseSimulationSpeedJButton = new JButton("Speed (+)");
        increaseSimulationSpeedJButton.setActionCommand(INCREASE_SPPED_BUTTON);
        increaseSimulationSpeedJButton.addActionListener(this);

        decreaseSimulationSpeedJButton = new JButton("Speed (-)");
        decreaseSimulationSpeedJButton.setActionCommand(DECREASE_SPPED_BUTTON);
        decreaseSimulationSpeedJButton.addActionListener(this);

        resetSimulationButton = new JButton("Reset Simulation");
        resetSimulationButton.setActionCommand(RESET_BUTTON);
        resetSimulationButton.addActionListener(this);

        if(simulation.getCurrentNumberOfTeams() == Simulation.MAX_NUMBER_OF_TEAMS){
            increaseNumTeamsJButton.setEnabled(false);
        }
        if(simulation.getCurrentNumberOfTeams() == Simulation.MIN_NUMBER_OF_TEAMS){
            decreaseNumTeamsJButton.setEnabled(false);
        }

        decreaseSimulationSpeedJButton.setEnabled(false);

        //Width of the Labels
        int labelContainerWidth = 200;
        GridLayout gl = new GridLayout(4,3);

        if(teams.size() == 2){
            labelContainerWidth = 200;
            gl = new GridLayout(4,3);
        }
        else if(teams.size() == 3) {
            labelContainerWidth = 260;
            gl = new GridLayout(4,4);
        }
        else if(teams.size() == 4){
            labelContainerWidth = 300;
            gl = new GridLayout(4,5);
        }

        playgroundX = (labelContainerWidth + (labelContainerWidth/2));
        playgroundWidth = WINDOW_WIDTH - (labelContainerWidth + (labelContainerWidth/2));

        JLabel scoreLabel = new JLabel("Score:");
        JLabel totalKillLabel = new JLabel("Total Kills:");
        JLabel totalDamageLabel = new JLabel("Total Damage:");

        JPanel mainContainer = new JPanel();
        JPanel statsContainer = new JPanel();
        JPanel labelContainer = new JPanel();

        labelContainer.setLayout(gl);

        labelContainer.add(new JLabel());

        //Team names
        for(Team t: teams){
            String teamName = "<html>"+t.getTeamName()+"<br/>("+colorMap.get(t.getColor())+")</html>";
            t.getTeamNameLabel().setText(teamName);
            labelContainer.add(t.getTeamNameLabel());
        }

        labelContainer.add(scoreLabel);
        for(Team t: teams){
            labelContainer.add(t.getScoreLabel());
        }

        labelContainer.add(totalKillLabel);
        for(Team t: teams){
            labelContainer.add(t.getTotalKillsLabel());
        }

        labelContainer.add(totalDamageLabel);
        for(Team t: teams){
            labelContainer.add(t.getTotalDamageLabel());
        }

        //Button placement start
        JPanel teamButtonsContainer = new JPanel(new GridLayout(1,2));
        teamButtonsContainer.add(decreaseNumTeamsJButton);
        teamButtonsContainer.add(increaseNumTeamsJButton);

        JPanel simulationControlButtonsContainer = new JPanel(new GridLayout(1,3));
        simulationControlButtonsContainer.add(decreaseSimulationSpeedJButton);
        simulationControlButtonsContainer.add(pauseSimulationJButton);
        simulationControlButtonsContainer.add(increaseSimulationSpeedJButton);

        JPanel resetButtonContainer = new JPanel(new GridLayout(1,1));
        resetButtonContainer.add(resetSimulationButton);

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new GridLayout(3, 1));
        buttonContainer.add(teamButtonsContainer);
        buttonContainer.add(simulationControlButtonsContainer);
        buttonContainer.add(resetButtonContainer);
        //// Button placement end

        statsContainer.setLayout(new GridLayout(2, 1));
        statsContainer.add(labelContainer);
        statsContainer.add(buttonContainer);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        Dimension d2 = new Dimension(playgroundX,WINDOW_HEIGHT);
        leftPanel.setMaximumSize(d2);
        leftPanel.setMinimumSize(d2);
        leftPanel.setPreferredSize(d2);
        leftPanel.add(statsContainer, BorderLayout.NORTH);

        JLabel picLabel = new JLabel(new ImageIcon(logoImage));

        leftPanel.add(picLabel, BorderLayout.CENTER);

        mainContainer.setLayout(new BorderLayout());
        mainContainer.add(leftPanel, BorderLayout.WEST);
        mainContainer.setOpaque(false);

        playground = new Rectangle(playgroundX, 0,
                playgroundWidth, WINDOW_HEIGHT);

        gamePanel.removeAll();
        gamePanel.add(mainContainer, BorderLayout.CENTER);
        gamePanel.setOpaque(false);
        this.add(gamePanel);
    }

    /**
     * Setup the teams' base positions
     */
    public void setTeamBasePositionsAndPlaygrounds()
    {
        int offset = 100;
        if(teams.size() == 2) {
            teams.get(0).setBaseX(playground.getX() + offset);
            teams.get(0).setBaseY(playground.getY() + offset);
            teams.get(0).setTeamPlayground(playground);

            teams.get(1).setBaseX(playground.getX() + playground.getWidth() - 1.5*offset);
            teams.get(1).setBaseY(playground.getY() + playground.getHeight() - 1.5*offset);
            teams.get(1).setTeamPlayground(playground);
        }
        else if(teams.size() == 3){
            teams.get(0).setBaseX(playground.getX() + offset);
            teams.get(0).setBaseY(playground.getY() + offset);
            teams.get(0).setTeamPlayground(playground);

            teams.get(1).setBaseX(playground.getX() + playground.getWidth() - offset);
            teams.get(1).setBaseY(playground.getY() + offset);
            teams.get(1).setTeamPlayground(playground);

            teams.get(2).setBaseX(playground.getX() + playground.getWidth() / 2);
            teams.get(2).setBaseY(playground.getY() + playground.getHeight() - 1.5 * offset);
            teams.get(2).setTeamPlayground(playground);
        }
        else if(teams.size() == 4){
            teams.get(0).setBaseX(playground.getX() + offset);
            teams.get(0).setBaseY(playground.getY() + offset);
            teams.get(0).setTeamPlayground(playground);

            teams.get(1).setBaseX(playground.getX() + playground.getWidth() - 1.5*offset);
            teams.get(1).setBaseY(playground.getY() + playground.getHeight() - 1.5*offset);
            teams.get(1).setTeamPlayground(playground);

            teams.get(2).setBaseX(playground.getX() + playground.getWidth() - offset);
            teams.get(2).setBaseY(playground.getY() + offset);
            teams.get(2).setTeamPlayground(playground);

            teams.get(3).setBaseX(playground.getX() + offset);
            teams.get(3).setBaseY(playground.getY() + playground.getHeight() - 1.5*offset);
            teams.get(3).setTeamPlayground(playground);
        }
    }

    /**
     * Updates the labels of the given team with their current stats
     * @param t Team
     */
    public void updateTeamStats(Team t)
    {
        t.getScoreLabel().setText(String.valueOf(t.getTotalScore()));
        t.getTotalDamageLabel().setText(String.valueOf(t.getTotalDamage()));
        t.getTotalKillsLabel().setText(String.valueOf(t.getTotalKills()));
    }

    /**
     * Action listener for the buttons
     * @param e ActionEvent
     */
    public void actionPerformed(ActionEvent e) {

        if (PAUSE_BUTTON.equals(e.getActionCommand())){
            if(simulation.isSimulationPaused()){
                pauseSimulationJButton.setText("<html>Pause<br/>Simulation</html>");
                simulation.resumeSimulation();
            }else {
                pauseSimulationJButton.setText("<html>Resume<br/>Simulation</html>");
                simulation.pauseSimulation();
            }
        }
        else if(RESET_BUTTON.equals(e.getActionCommand())){
            simulation.resetSimulation(simulation.getCurrentNumberOfTeams());
        }
        else if(INCREASE_NUM_TEAMS_BUTTON.equals(e.getActionCommand()))
        {
            if(simulation.increaseTotalTeams()){
                increaseNumTeamsJButton.setEnabled(false);
            }
            else {
                increaseNumTeamsJButton.setEnabled(true);
            }
            decreaseNumTeamsJButton.setEnabled(true);
        }
        else if(DECREASE_NUM_TEAMS_BUTTON.equals(e.getActionCommand()))
        {
            if(simulation.decreaseTotalTeams()){
                decreaseNumTeamsJButton.setEnabled(false);
            }
            else {
                decreaseNumTeamsJButton.setEnabled(true);
            }
            increaseNumTeamsJButton.setEnabled(true);
        }
        else if(INCREASE_SPPED_BUTTON.equals(e.getActionCommand()))
        {
            if(simulation.increaseSpeed()){
                increaseSimulationSpeedJButton.setEnabled(false);
            }
            else {
                increaseSimulationSpeedJButton.setEnabled(true);
            }
            decreaseSimulationSpeedJButton.setEnabled(true);
        }
        else if(DECREASE_SPPED_BUTTON.equals(e.getActionCommand()))
        {
            if(simulation.decreaseSpeed()){
                decreaseSimulationSpeedJButton.setEnabled(false);
            }
            else {
                decreaseSimulationSpeedJButton.setEnabled(true);
            }
            increaseSimulationSpeedJButton.setEnabled(true);
        }
    }

    /**
     * Returns the GamePanel object of the Display
     */
    public GamePanel getGamePanel() {
        return gamePanel;
    }

    /**
     * Inner JPanel class created to update, Teams and knights drawings
     */
    class GamePanel extends JPanel{

        public GamePanel() {
        }

        /**
         * Redrawing occurs here.
         * @param g
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;

            g2d.drawImage(mapBackgroundImage, (int) playground.getX(), (int) playground.getY(),
                    (int) playground.getWidth(), (int) playground.getHeight(), null);

            for(Team t: teams)
            {
                //Draw team base
                g2d.setColor(t.getColor());
                g2d.draw(t.getBaseShape());

                //Draw knights
                for(Knight k: t.getKnights()){
                    k.draw(g2d);
                }
            }
        }
    }
}