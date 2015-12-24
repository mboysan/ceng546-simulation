
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

/**
 * Abstract Knight class. For time limitations, some of its methods were not documented.
 * @author Mehmet Can Boysan.
 */
public abstract class Knight {

    public static int COOLDOWN_INTERVAL = 1000;     //1 second
    public static int ENTITY_WIDTH = 20;            //width of the Knight shape
    public static int ENTITY_HEIGHT = 20;           //height of the Knight shape
    public static int TOUCH_SHAPE_RADIUS = 10;      //radius of the Knight's Touch shape

    private double x;
    private double y;
    private int currentHealth;
    private int maxHealth;
    private int score;
    private int speed;

    private boolean isStrategyOverridden;
    private Strategy strategy;

    private Team assignedTeam;

    private Ellipse2D touchShape;
    private Rectangle knightShape;
    private Ellipse2D pointAssigned;

    private int normalHitPoint;
    private Ellipse2D normalAttackShape;
    private int normalAttackRadius;

    private boolean isSpecialSkill;
    private int specialHitPoint;
    private Ellipse2D specialSkillShape;
    private int specialSkillRadius;

    private boolean isCurrentlyAttacking;
    private List<Knight> knightsToInteract;

    private boolean isCooldown;
    private int timeUnits;

    public abstract void draw(Graphics g);

    protected void drawShape(Graphics g){}
    protected void drawAttack(Graphics g, Color attackColor){}
    protected void drawSpecial(Graphics g, Color specialColor){}
    protected void checkDrawReferences(){}

    public Knight() {}

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.getKnightShape().x = (int)x;
        this.x = x;
    }

    public double getY() {
        return y;
    }

    /**
     * Sets the Y position of the Knight. preferably called after the <code>setX()</code> method. Also sets the
     * position of the knight's normal skill shape, special skill shape and touch shape
     * @param y Y position on the map.
     */
    public void setY(double y) {
        this.getKnightShape().y = (int)y;

        //sets the normal attack circle position of the knight
        this.getNormalAttackShape().setFrameFromCenter(this.getKnightShape().getCenterX(),
                this.getKnightShape().getCenterY(),
                this.getKnightShape().getCenterX() + this.getNormalAttackRadius(),
                this.getKnightShape().getCenterY() + this.getNormalAttackRadius());

        //sets the special skill circle position of the knight
        this.getSpecialSkillShape().setFrameFromCenter(this.getKnightShape().getCenterX(),
                this.getKnightShape().getCenterY(),
                this.getKnightShape().getCenterX() + this.getSpecialSkillRadius(),
                this.getKnightShape().getCenterY() + this.getSpecialSkillRadius());

        //sets the touch circle position of the knight
        this.getTouchShape().setFrameFromCenter(this.getKnightShape().getCenterX(),
                this.getKnightShape().getCenterY(),
                this.getKnightShape().getCenterX() + TOUCH_SHAPE_RADIUS,
                this.getKnightShape().getCenterY() + TOUCH_SHAPE_RADIUS);

        this.y = y;
    }

    /**
     * @return  Currently assigned strategy object.
     * @see Strategy
     */
    public Strategy getStrategy() {
        return strategy;
    }

    /**
     * @param strategy Newly assigned strategy object.
     * @see Strategy
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getNormalHitPoint() {
        return normalHitPoint;
    }

    public void setNormalHitPoint(int normalHitPoint) {
        this.normalHitPoint = normalHitPoint;
    }

    /**
     * Specifies if the knight currently able to use its special skill or normal attack.
     * @return  true if not allowed to attack, false otherwise.
     */
    public boolean isCooldown() {
        return isCooldown;
    }

    /**
     * States if the knight currently able to use its special skill or normal attack.
     * @param coolDown  true if not allowed to attack, false otherwise.
     */
    public void setIsCooldown(boolean coolDown) {
        this.isCooldown = coolDown;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpecialSkillRadius() {
        return specialSkillRadius;
    }

    public void setSpecialSkillRadius(int specialSkillRadius) {
        this.specialSkillRadius = specialSkillRadius;
    }

    /**
     * @return  Shape that represents the Knight object's shape
     */
    public Rectangle getKnightShape() {
        return knightShape;
    }

    /**
     * @param knightShape  Shape that represents the Knight object's shape
     */
    public void setKnightShape(Rectangle knightShape) {
        this.knightShape = knightShape;
    }

    /**
     * Specifies if the knight is allowed to use its special skill ability
     * @return  true if allowed, false otherwise
     */
    public boolean isSpecialSkill() {
        return isSpecialSkill;
    }

    /**
     * States if the knight is allowed to use its special skill ability
     * @param isSpecialAttack  true if allowed, false otherwise
     */
    public void setIsSpecialSkill(boolean isSpecialAttack) {
        this.isSpecialSkill = isSpecialAttack;
    }

    /**
     * @return  Team of the knight that it is assigned.
     */
    public Team getAssignedTeam() {
        return assignedTeam;
    }

    /**
     * @param assignedTeam  Team of the knight that it is assigned.
     */
    public void setAssignedTeam(Team assignedTeam) {
        this.assignedTeam = assignedTeam;
    }

    /**
     * Method that changes the current assigned strategy of the knight
     */
    public void checkStrategyRandomization(){}

    /**
     * @return  Time units that represent the ability of the knight to perform some required checks
     */
    public int getTimeUnits() {
        return timeUnits;
    }

    /**
     * @param timeUnits  Time units that represent the ability of the knight to perform some required checks
     */
    public void setTimeUnits(int timeUnits) {
        this.timeUnits = timeUnits;
    }

    /**
     * Required to signal the <code>drawAttack()</code> method of the knight
     * @return  true if candidate knights found to attack, false otherwise
     */
    public boolean isCurrentlyAttacking() {
        return isCurrentlyAttacking;
    }

    /**
     * Sets the signalling for the <code>drawAttack()</code> method of the knight
     * @param isCurrentlyAttacking  true if candidate knights found to attack, false otherwise
     */
    public void setIsCurrentlyAttacking(boolean isCurrentlyAttacking) {
        this.isCurrentlyAttacking = isCurrentlyAttacking;
    }

    /**
     * Required for the <code>drawAttack()</code> mechanism of the Knight to specify the lines that will be
     * drawn to the knights
     * @return  List of knights that needs interaction.
     */
    public List<Knight> getKnightsToInteract() {
        return knightsToInteract;
    }

    /**
     * Sets the knights that will be interacted on the next <code>drawAttack()</code> of the Knight to specify the
     * lines that will be drawn to the knights
     * @param knightsToInteract  List of knights that needs interaction.
     */
    public void setKnightsToInteract(List<Knight> knightsToInteract) {
        this.knightsToInteract = knightsToInteract;
    }

    /**
     * Gets the currently assigned point (region)
     * @return  Assigned circular region
     */
    public Ellipse2D getPointAssigned() {
        return pointAssigned;
    }

    /**
     * Sets the currently assigned point (region)
     * @param pointAssigned  Assigned circular region
     */
    public void setPointAssigned(Ellipse2D pointAssigned) {
        this.pointAssigned = pointAssigned;
    }

    public Ellipse2D getNormalAttackShape() {
        return normalAttackShape;
    }

    public void setNormalAttackShape(Ellipse2D normalAttackShape) {
        this.normalAttackShape = normalAttackShape;
    }

    public int getNormalAttackRadius() {
        return normalAttackRadius;
    }

    public void setNormalAttackRadius(int normalAttackRadius) {
        this.normalAttackRadius = normalAttackRadius;
    }

    public Ellipse2D getSpecialSkillShape() {
        return specialSkillShape;
    }

    public void setSpecialSkillShape(Ellipse2D specialSkillShape) {
        this.specialSkillShape = specialSkillShape;
    }

    public int getSpecialHitPoint() {
        return specialHitPoint;
    }

    public void setSpecialHitPoint(int specialHitPoint) {
        this.specialHitPoint = specialHitPoint;
    }

    /**
     * Gets the touch shape to indicate a hitting region for the opponent knight
     * @return  Circular shape encapsulating the <code>KnightShape</code>
     */
    public Ellipse2D getTouchShape() {
        return touchShape;
    }

    /**
     * Sets the Touch shape to indicate a hitting region for the opponent knight
     * @param touchShape  Circular shape encapsulating the <code>KnightShape</code>
     */
    public void setTouchShape(Ellipse2D touchShape) {
        this.touchShape = touchShape;
    }

    /**
     * Signals the strategy that the knight will act according to the way it is assigned to.
     * @return  true if knight will move to a previously specified region, false otherwise
     */
    public boolean isStrategyOverridden() {
        return isStrategyOverridden;
    }

    /**
     * Sets the signal for the strategy that the knight will act according to the way it is assigned to.
     * @param isStrategyOverrided  true if knight will move to a previously specified region, false otherwise
     */
    public void setIsStrategyOverrided(boolean isStrategyOverrided) {
        this.isStrategyOverridden = isStrategyOverrided;
    }

    /**
     * Updates the Knight's team's stats.
     * @param opponentKnight    Opponent Knight received that took damage
     * @param damage            Amount of damage
     */
    protected void updateTeamStats(Knight opponentKnight, int damage){}

    /**
     * General method called by the Knight's strategy's act() method. Performs normal or special attack where
     * applicable.
     */
    public void performAction(){}

    /**
     * Traverses the opponent teams' knights and performs normal attack if intersection occurred.
     */
    protected void performNormalAttack(){}

    protected void performSpecialSkill(){}

    /**
     * Applies the specified amount of damage to this knight
     * @param dmg   Amount of damage
     */
    public void applyDamage(int dmg) {
        this.setCurrentHealth(this.getCurrentHealth() - dmg);
    }

}