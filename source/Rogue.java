import java.awt.*;

/**
 * Rogue class, extending <code>BasicKnight</code> class. The most complicated Knight class
 * @see BasicKnight
 * @author Mehmet Can Boysan
 */
public class Rogue extends BasicKnight {

    private boolean isPerformingSpecialSkill;

    public Rogue (double x, double y, int health, int normalHitPoint, int specialHitPoint,
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

        this.isPerformingSpecialSkill = false;
    }

    /**
     * If decided to perform special skill, perform it until completion. Otherwise calls the base class'
     * <code>performAction()</code> method.
     */
    @Override
    public void performAction() {
        if(isPerformingSpecialSkill){
            this.setIsCurrentlyAttacking(false);
            performSpecialSkill();
        }
        else
            super.performAction();
    }

    /**
     * Selects the first enemy encountered that falls into its special attack radius. Speeds up, and moves to that
     * enemy until it hits it no matter what. Then returns to its initial state.
     */
    @Override
    protected void performSpecialSkill() {

        if(!isPerformingSpecialSkill){  //if chosen to perform special skill, do it no matter what
            for (Team opTeam : this.getAssignedTeam().getOpponentTeams()) {
                for (Knight opKnight : opTeam.getKnights()) {
                    if (this.getSpecialSkillShape().contains(opKnight.getKnightShape()))
                    {
                        this.setIsStrategyOverrided(true);
                        this.setPointAssigned(opKnight.getTouchShape());
                        this.getKnightsToInteract().add(opKnight);
                        this.setSpeed(this.getSpeed() * 2);

                        isPerformingSpecialSkill = true;
                        break;
                    }
                }
                if(isPerformingSpecialSkill)
                    break;
            }
        }else {
            if(this.getKnightsToInteract().size() > 0) {    //if the knight selected is not killed or anything
                if (this.getTouchShape().intersects(this.getKnightsToInteract().get(0).getTouchShape().getBounds2D()))
                    //if there is possible intersection
                {
                    this.getKnightsToInteract().get(0).applyDamage(this.getSpecialHitPoint());
                    this.updateTeamStats(this.getKnightsToInteract().get(0), this.getSpecialHitPoint());
                    this.setIsCurrentlyAttacking(true);

                    this.setIsStrategyOverrided(false);
                    this.setPointAssigned(null);

                    isPerformingSpecialSkill = false;
                    this.setSpeed(this.getSpeed() / 2);

                } else {    //move towards the knight selected
                    this.setPointAssigned(this.getKnightsToInteract().get(0).getTouchShape());
                }
            }
            else {  //if all fails
                this.setIsStrategyOverrided(false);
                this.setPointAssigned(null);

                isPerformingSpecialSkill = false;
                this.setSpeed(this.getSpeed() / 2);
            }
        }
    }

    /**
     * Overridden <code>draw()</code> method. Draws normal attack with <code>Color.WHITE</code> and special attack
     * with <code>Color.BLUE</code>
     * @param g Graphics object
     */
    @Override
    public void draw(Graphics g)
    {
        drawShape(g);

        if(this.isCurrentlyAttacking()) {
            checkDrawReferences();
            super.drawAttack(g, Color.WHITE);
            if(this.isSpecialSkill()){
                super.drawSpecial(g, Color.BLUE);
            }
        }
        else{
            if(this.isSpecialSkill()){
                super.drawSpecial(g, Color.BLUE);
            }
        }
        super.draw(g);
    }

    /**
     * Draws a triangle shape that the rogue is represented.
     * @param g Graphics Object
     */
    @Override
    protected void drawShape(Graphics g)
    {
        //Triangle
        int[] xArr = new int[] {
                (int) this.getKnightShape().getX(),
                (int) this.getKnightShape().getX() + (int) this.getKnightShape().getWidth()/2,
                (int) this.getKnightShape().getX() + (int) this.getKnightShape().getWidth()
        };

        int[] yArr = new int[]{
                (int) this.getKnightShape().getY() + (int) this.getKnightShape().getHeight(),
                (int) this.getKnightShape().getY(),
                (int) this.getKnightShape().getY() + (int) this.getKnightShape().getHeight()
        };

        int numPoints = 3;

        Polygon p = new Polygon(xArr, yArr, numPoints);

        g.setColor(this.getAssignedTeam().getColor());
        g.fillPolygon(p);
    }
}