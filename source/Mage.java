
import java.awt.*;

/**
 * Mage class, extending <code>BasicKnight</code> class
 * @see BasicKnight
 * @author Mehmet Can Boysan
 */
public class Mage extends BasicKnight {

    public Mage (double x, double y, int health, int normalHitPoint, int specialHitPoint,
                 int specialSkillRadius, int normalAttackRadius, int speed,
                 Team assignedTeam)
    {
        super();
        this.setX(x);
        this.setY(y);
        this.setCurrentHealth(health);
        this.setMaxHealth(health);
        this.setNormalHitPoint(normalHitPoint);
        this.setSpecialHitPoint(specialHitPoint);
        this.setSpecialSkillRadius(specialSkillRadius);
        this.setNormalAttackRadius(normalAttackRadius);
        this.setSpeed(speed);
        this.setAssignedTeam(assignedTeam);
    }

    /**
     * Overridden <code>performSpecialSkill()</code> method. Applies damage to knights inside its circular Special
     * Shape region.
     */
    @Override
    protected void performSpecialSkill() {

        for(Team opTeams: this.getAssignedTeam().getOpponentTeams()) {
            for(Knight opKnight: opTeams.getKnights()){
                if(this.getSpecialSkillShape().contains(opKnight.getKnightShape().getBounds2D()))
                {
                    opKnight.applyDamage(this.getSpecialHitPoint());
                    this.updateTeamStats(opKnight, this.getSpecialHitPoint());

                    this.getKnightsToInteract().add(opKnight);
                    this.setIsCurrentlyAttacking(true);
                }
            }
        }
    }

    /**
     * Overridden <code>draw()</code> method. Draws normal attack with <code>Color.CYAN</code> and special attack
     * with <code>Color.MAGENTA</code>
     * @param g Graphics object
     */
    @Override
    public void draw(Graphics g) {
        drawShape(g);

        if(this.isCurrentlyAttacking()) {
            checkDrawReferences();
            super.drawAttack(g, Color.CYAN);
            if(this.isSpecialSkill()){
                super.drawSpecial(g, Color.MAGENTA);
            }
        }
        else{
            if(this.isSpecialSkill()){
                super.drawSpecial(g, Color.MAGENTA);
            }
        }
        super.draw(g);
    }

    /**
     * Draws a diamond shape that the mage is represented.
     * @param g Graphics Object
     */
    @Override
    protected void drawShape(Graphics g)
    {
        //Diamond
        int[] xArr = new int[] {
                (int) this.getKnightShape().getX() + (int) this.getKnightShape().getWidth()/2,
                (int) this.getKnightShape().getX(),
                (int) this.getKnightShape().getX() + (int) this.getKnightShape().getWidth()/2,
                (int) this.getKnightShape().getX() + (int) this.getKnightShape().getWidth()
        };

        int[] yArr = new int[]{
                (int) this.getKnightShape().getY(),
                (int) this.getKnightShape().getY() + (int) this.getKnightShape().getHeight()/2,
                (int) this.getKnightShape().getY() + (int) this.getKnightShape().getHeight(),
                (int) this.getKnightShape().getY() + (int) this.getKnightShape().getHeight()/2};

        int numPoints = 4;

        Polygon p = new Polygon(xArr,yArr,numPoints);

        g.setColor(this.getAssignedTeam().getColor());
        g.fillPolygon(p);
    }
}