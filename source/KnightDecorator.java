
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.List;

/**
 * Base decorator class implementing the delegate methods of the <code>Knight</code> abstract class
 * @see Knight
 * @author Mehmet Can Boysan
 */
public abstract class KnightDecorator extends Knight {

    public static int GRADE1_DECORATION_THRESHOLD = 75;
    public static int GRADE2_DECORATION_THRESHOLD = 150;
    public static int GRADE3_DECORATION_THRESHOLD = 300;

    private Ellipse2D decorationShape;

    /**
     * Finds appropriate position to draw the shape
     */
    protected abstract void calculateDecorationShape();

    /**
     * Gets the circular shape used to decorate knight
     * @return  Circular shape
     */
    public Ellipse2D getDecorationShape() {
        return decorationShape;
    }

    /**
     * Draws the shape on the position calculated.
     * @param g Graphics object
     */
    @Override
    public void draw(Graphics g) {
        getComponent().draw(g);
    }

    @Override
    public void drawShape(Graphics g) {
        getComponent().drawShape(g);
    }

    @Override
    public void drawAttack(Graphics g, Color attackColor) {
        getComponent().drawAttack(g, attackColor);
    }

    @Override
    public void checkDrawReferences() {
        getComponent().checkDrawReferences();
    }

    @Override
    public double getX() {
        return getComponent().getX();
    }

    @Override
    public void setX(double x) {
        getComponent().setX(x);
    }

    @Override
    public double getY() {
        return getComponent().getY();
    }

    @Override
    public void setY(double y) {
        getComponent().setY(y);
    }

    @Override
    public Strategy getStrategy() {
        return getComponent().getStrategy();
    }

    @Override
    public void setStrategy(Strategy strategy) {
        getComponent().setStrategy(strategy);
    }

    @Override
    public int getCurrentHealth() {
        return getComponent().getCurrentHealth();
    }

    @Override
    public void setCurrentHealth(int currentHealth) {
        getComponent().setCurrentHealth(currentHealth);
    }

    @Override
    public int getMaxHealth() {
        return getComponent().getMaxHealth();
    }

    @Override
    public void setMaxHealth(int maxHealth) {
        getComponent().setMaxHealth(maxHealth);
    }

    @Override
    public int getNormalHitPoint() {
        return getComponent().getNormalHitPoint();
    }

    @Override
    public void setNormalHitPoint(int normalHitPoint) {
        getComponent().setNormalHitPoint(normalHitPoint);
    }

    @Override
    public boolean isCooldown() {
        return getComponent().isCooldown();
    }

    @Override
    public void setIsCooldown(boolean coolDown) {
        getComponent().setIsCooldown(coolDown);
    }

    @Override
    public int getScore() {
        return getComponent().getScore();
    }

    @Override
    public void setScore(int score) {
        getComponent().setScore(score);
    }

    @Override
    public int getSpeed() {
        return getComponent().getSpeed();
    }

    @Override
    public void setSpeed(int speed) {
        getComponent().setSpeed(speed);
    }

    @Override
    public int getSpecialSkillRadius() {
        return getComponent().getSpecialSkillRadius();
    }

    @Override
    public void setSpecialSkillRadius(int specialSkillRadius) {
        getComponent().setSpecialSkillRadius(specialSkillRadius);
    }

    @Override
    public Rectangle getKnightShape() {
        return getComponent().getKnightShape();
    }

    @Override
    public void setKnightShape(Rectangle knightShape) {
        getComponent().setKnightShape(knightShape);
    }

    @Override
    public boolean isSpecialSkill() {
        return getComponent().isSpecialSkill();
    }

    @Override
    public void setIsSpecialSkill(boolean isSpecialAttack) {
        getComponent().setIsSpecialSkill(isSpecialAttack);
    }

    @Override
    public Team getAssignedTeam() {
        return getComponent().getAssignedTeam();
    }

    @Override
    public void setAssignedTeam(Team assignedTeam) {
        getComponent().setAssignedTeam(assignedTeam);
    }

    @Override
    public void checkStrategyRandomization() {
        getComponent().checkStrategyRandomization();
    }

    @Override
    public int getTimeUnits() {
        return getComponent().getTimeUnits();
    }

    @Override
    public void setTimeUnits(int timeUnits) {
        getComponent().setTimeUnits(timeUnits);
    }

    @Override
    public boolean isCurrentlyAttacking() {
        return getComponent().isCurrentlyAttacking();
    }

    @Override
    public void setIsCurrentlyAttacking(boolean isCurrentlyAttacking) {
        getComponent().setIsCurrentlyAttacking(isCurrentlyAttacking);
    }

    @Override
    public List<Knight> getKnightsToInteract() {
        return getComponent().getKnightsToInteract();
    }

    @Override
    public void setKnightsToInteract(List<Knight> knightsToInteract) {
        getComponent().setKnightsToInteract(knightsToInteract);
    }

    @Override
    public Ellipse2D getPointAssigned() {
        return getComponent().getPointAssigned();
    }

    @Override
    public void setPointAssigned(Ellipse2D pointAssigned) {
        getComponent().setPointAssigned(pointAssigned);
    }

    @Override
    public Ellipse2D getNormalAttackShape() {
        return getComponent().getNormalAttackShape();
    }

    @Override
    public void setNormalAttackShape(Ellipse2D normalAttackShape) {
        getComponent().setNormalAttackShape(normalAttackShape);
    }

    @Override
    public int getNormalAttackRadius() {
        return getComponent().getNormalAttackRadius();
    }

    @Override
    public void setNormalAttackRadius(int normalAttackRadius) {
        getComponent().setNormalAttackRadius(normalAttackRadius);
    }

    @Override
    public Ellipse2D getSpecialSkillShape() {
        return getComponent().getSpecialSkillShape();
    }

    @Override
    public void setSpecialSkillShape(Ellipse2D specialSkillShape) {
        getComponent().setSpecialSkillShape(specialSkillShape);
    }

    @Override
    public int getSpecialHitPoint() {
        return getComponent().getSpecialHitPoint();
    }

    @Override
    public void setSpecialHitPoint(int specialHitPoint) {
        getComponent().setSpecialHitPoint(specialHitPoint);
    }

    @Override
    public Ellipse2D getTouchShape() {
        return getComponent().getTouchShape();
    }

    @Override
    public void setTouchShape(Ellipse2D touchShape) {
        getComponent().setTouchShape(touchShape);
    }

    @Override
    public boolean isStrategyOverridden() {
        return getComponent().isStrategyOverridden();
    }

    @Override
    public void setIsStrategyOverrided(boolean isStrategyOverrided) {
        getComponent().setIsStrategyOverrided(isStrategyOverrided);
    }

    @Override
    public void updateTeamStats(Knight opponentKnight, int damage) {
        getComponent().updateTeamStats(opponentKnight, damage);
    }

    @Override
    public void performAction() {
        getComponent().performAction();
    }

    @Override
    public void performNormalAttack() {
        getComponent().performNormalAttack();
    }

    @Override
    public void performSpecialSkill() {
        getComponent().performSpecialSkill();
    }

    @Override
    public void applyDamage(int dmg) {
        getComponent().applyDamage(dmg);
    }

    private Knight component;

    public KnightDecorator() {
        this.decorationShape = new Ellipse2D.Double();
    }

    public Knight getComponent() {
        return component;
    }

    public void setComponent(Knight component) {
        this.component = component;
    }
}