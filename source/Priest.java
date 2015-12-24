
import java.awt.*;

/**
 * Priest class, extending <code>BasicKnight</code> class
 * @see BasicKnight
 * @author Mehmet Can Boysan
 */
public class Priest extends BasicKnight {

    public Priest (double x, double y, int health, int normalHitPoint, int specialHitPoint,
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
     * Finds all nearby teammates and heals them with the specified amount of special skill hit points.
     */
    @Override
    protected void performSpecialSkill() {
        for(Knight teamMate: this.getAssignedTeam().getKnights()){
            if(this.getSpecialSkillShape().contains(teamMate.getKnightShape().getBounds2D()))
            {
                if(teamMate.getCurrentHealth() + this.getSpecialHitPoint() <= teamMate.getMaxHealth())
                {
                    teamMate.setCurrentHealth(teamMate.getCurrentHealth() + this.getSpecialHitPoint());

                    this.getKnightsToInteract().add(teamMate);
                    this.setIsCurrentlyAttacking(true);
                }
            }
        }
    }

    /**
     * Overridden <code>draw()</code> method. Draws normal attack with <code>Color.BLACK</code> and special attack
     * with <code>Color.GREEN</code>
     * @param g Graphics object
     */
    @Override
    public void draw(Graphics g) {
        drawShape(g);

        if(this.isCurrentlyAttacking()) {
            checkDrawReferences();
            super.drawAttack(g, Color.BLACK);
            if(this.isSpecialSkill()){
                super.drawSpecial(g, Color.GREEN);
            }
        }
        else{
            if(this.isSpecialSkill()){
                super.drawSpecial(g, Color.GREEN);
            }
        }
        super.draw(g);
    }

    /**
     * Draws a rectangle shape that the priest is represented.
     * @param g Graphics Object
     */
    @Override
    protected void drawShape(Graphics g)
    {
        //Rectangle
        g.setColor(this.getAssignedTeam().getColor());
        g.fillRect((int) this.getKnightShape().getX(), (int) this.getKnightShape().getY(),
                (int) this.getKnightShape().getWidth(), (int) this.getKnightShape().getHeight());
    }
}