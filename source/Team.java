import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Team class that contains the knights and information about their teams
 * @see Knight
 * @author Mehmet Can Boysan
 */
public class Team {
    private static int MAX_TEAM_SIZE = 8;
    public static int BASE_SHAPE_RADIUS = 20;
    public static int KILL_SCORE = 75;

    private JLabel teamNameLabel;
    private JLabel scoreLabel = new JLabel("0");
    private JLabel totalKillsLabel = new JLabel("0");
    private JLabel totalDamageLabel = new JLabel("0");

    private String teamName;
    private double baseX;
    private double baseY;
    private Ellipse2D baseShape;
    private int totalScore;
    private int totalKills;
    private int totalDamage;
    private Color color;
    private ArrayList<Knight> knights;
    private Rectangle teamPlayground;
    private ArrayList<Team> opponentTeams;

    /**
     * Constructor of the <code>Team</code> class
     * @param teamName  Name of the team
     * @param teamColor Color that represents the team
     */
    public Team(String teamName, Color teamColor) {
        this.teamName = teamName;
        this.teamNameLabel = new JLabel(teamName);

        this.totalScore = 0;
        this.totalKills = 0;
        this.totalDamage = 0;
        this.color = teamColor;
        this.knights = new ArrayList<Knight>();
        this.opponentTeams = new ArrayList<Team>();
        this.baseShape = new Ellipse2D.Double();
    }

    /**
     * Populate the Knights
     */
    public void initializeTeamMembers()
    {
        for(int i = 0 ; i < MAX_TEAM_SIZE ; i++)
            generateTeamMember();
    }

    /**
     * Generate new team member if applicable. Uses factory pattern to do so.
     */
    public void generateTeamMember()
    {
        if(knights.size() < MAX_TEAM_SIZE){
            SoldierFactory soldierFactory;
            double x = baseX;
            double y = baseY;
            int health;
            int normalHitPoint;
            int specialHitPoint;
            int specialSkillRadius;
            int normalAttackRadius;
            int speed;
            Team assignedTeam = this;

            Random rand = new Random();
            int randomMember = rand.nextInt(3); //random team member assignment is made
            switch (randomMember){
                case 0:
                    health = 500;
                    normalHitPoint = 150;
                    specialHitPoint = 250;
                    specialSkillRadius = 200;
                    normalAttackRadius = 20;
                    speed = 100;
                    soldierFactory = new RogueFactory();
                    break;
                case 1:
                    health = 400;
                    normalHitPoint = 100;
                    specialHitPoint = 75;
                    specialSkillRadius = 100;
                    normalAttackRadius = 75;
                    speed = 75;
                    soldierFactory = new MageFactory();
                    break;
                case 2:
                    health = 350;
                    normalHitPoint = 150;
                    specialHitPoint = 75;
                    specialSkillRadius = 100;
                    normalAttackRadius = 20;
                    speed = 50;
                    soldierFactory = new PriestFactory();
                    break;
                default:
                    //this case is never reached logically
                    health = -1;
                    normalHitPoint = -1;
                    specialHitPoint = -1;
                    specialSkillRadius = -1;
                    normalAttackRadius = -1;
                    speed = -1;
                    soldierFactory = new RogueFactory();
                    break;
            }
            knights.add(soldierFactory.produce(x, y, health, normalHitPoint, specialHitPoint,
                    specialSkillRadius, normalAttackRadius, speed, assignedTeam));
        }
    }

    public void addTeamMember(Knight k)
    {
        knights.add(k);
    }

    public void removeTeamMember(Knight k)
    {
        knights.remove(k);
    }

    public double getBaseX() {
        return baseX;
    }

    /**
     * Sets the X position of the base.
     */
    public void setBaseX(double baseX) {
        this.baseX = baseX;
    }

    /**
     * @return Y position of the base
     */
    public double getBaseY() {
        return baseY;
    }

    /**
     * Sets the Y position of the base. Also sets the Circular region that the base is represented.
     */
    public void setBaseY(double baseY) {
        this.baseY = baseY;

        this.getBaseShape().setFrameFromCenter(this.getBaseX(),
                this.getBaseY(),
                this.getBaseX() + BASE_SHAPE_RADIUS,
                this.getBaseY() + BASE_SHAPE_RADIUS);
    }

    /**
     * @return  Total team score
     */
    public int getTotalScore() {
        return totalScore;
    }

    /**
     * @param totalScore    Team score
     */
    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * @return Color of the team
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color Color of the team
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * @return  All the knights that the team has
     */
    public ArrayList<Knight> getKnights() {
        return knights;
    }

    /**
     * @return  All the opponent teams
     */
    public ArrayList<Team> getOpponentTeams() {
        return opponentTeams;
    }

    /**
     * @param opponentTeams All the opponent teams
     */
    public void setOpponentTeams(ArrayList<Team> opponentTeams) {
        this.opponentTeams = opponentTeams;
    }

    /**
     * @return  Playground that the teams allowed to move. Usually the entire map.
     */
    public Rectangle getTeamPlayground() {
        return teamPlayground;
    }

    /**
     * @param teamPlayground    Playground that the teams allowed to move. Usually the entire map.
     */
    public void setTeamPlayground(Rectangle teamPlayground) {
        this.teamPlayground = teamPlayground;
    }

    /**
     * @return  Total kills the team members made so far.
     */
    public int getTotalKills() {
        return totalKills;
    }

    /**
     * @param totalKills    Total kills the team members made.
     */
    public void setTotalKills(int totalKills) {
        this.totalKills = totalKills;
    }

    /**
     * @return  Total damage made on the opponent team members.
     */
    public int getTotalDamage() {
        return totalDamage;
    }

    /**
     * @param totalDamage  Total damage made on the opponent team members.
     */
    public void setTotalDamage(int totalDamage) {
        this.totalDamage = totalDamage;
    }

    /**
     * @return  Team name JLabel
     */
    public JLabel getTeamNameLabel() {
        return teamNameLabel;
    }

    /**
     * @return  Team score JLabel
     */
    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    /**
     * @param scoreLabel  Team score JLabel
     */
    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    /**
     * @return  Team total kills JLabel
     */
    public JLabel getTotalKillsLabel() {
        return totalKillsLabel;
    }

    /**
     * @return  Team total damage JLabel
     */
    public JLabel getTotalDamageLabel() {
        return totalDamageLabel;
    }

    /**
     * @param totalDamageLabel  Team total damage JLabel
     */
    public void setTotalDamageLabel(JLabel totalDamageLabel) {
        this.totalDamageLabel = totalDamageLabel;
    }

    /**
     * Updates the team score by <code>KILL_SCORE</code> value
     */
    public void updateScore(){
        this.setTotalScore(this.getTotalScore() + KILL_SCORE);
    }

    /**
     * Updates the total damage made by the team members with the specified amount.
     * @param dmg   Damage made on the opponent team member
     */
    public void updateTotalDamage(int dmg){
        this.setTotalDamage(this.getTotalDamage() + dmg);
    }

    /**
     * Updates the total number of opponent kills.
     */
    public void updateTotalKills(){
        this.setTotalKills(this.getTotalKills() + 1);
    }

    /**
     * @return Name of the team.
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @return  The circular shape that represents the team's base.
     */
    public Ellipse2D getBaseShape() {
        return baseShape;
    }
}