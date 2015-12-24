import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Most basic knight object extending the <code>Knight</code> class.
 * @author Mehmet Can Boysan
 */
public class BasicKnight extends Knight {

    /**
     * Constructor of the BasicKnight
     */
    public BasicKnight() {
        this.setScore(0);

        this.setKnightShape(new Rectangle(Knight.ENTITY_WIDTH, Knight.ENTITY_HEIGHT));
        this.setNormalAttackShape(new Ellipse2D.Double());
        this.setSpecialSkillShape(new Ellipse2D.Double());
        this.setTouchShape(new Ellipse2D.Double());

        this.setIsStrategyOverrided(false);
        this.setIsSpecialSkill(false);

        this.setIsCurrentlyAttacking(false);
        this.setKnightsToInteract(new ArrayList<Knight>());

        randomizeStrategy();
    }

    @Override
    public void checkStrategyRandomization() {
        if(this.getTimeUnits() > Knight.COOLDOWN_INTERVAL){
            randomizeStrategy();
            checkChanceForSpecialAttack();
            this.setIsCooldown(false);
            this.setTimeUnits(0);
        }else{
            this.setTimeUnits(this.getTimeUnits() + Simulation.GAME_LOOP_CURRENT_TIME_STEP);
            this.setIsCooldown(true);
        }
    }

    private void randomizeStrategy(){
        Strategy strategy;
        Random rand = new Random();
        int randomMember = rand.nextInt(100);
        if(randomMember >= 0 && randomMember <= 5){  //this initial randomization is made to prevent entity flickering
            randomMember = 4;
        }
        else {
            randomMember = rand.nextInt(4);
        }

        switch (randomMember){
            case 0:
                strategy = new AttackClosest();
                break;
            case 1:
                strategy = new AttackWeakest();
                break;
            case 2:
                strategy = new MoveRandom();
                break;
            case 3:
                strategy = new MoveToClosestOpponentBase();
                break;
            case 4:
                strategy = new AttackRandomEnemy();
                break;
            default:
                //this case is never reached logically
                strategy = new MoveRandom();
                break;
        }
        this.setStrategy(strategy);
        this.getStrategy().setOwner(this);
    }

    /**
     * Checks if there is a chance for the knight object to use its special skill.
     */
    protected void checkChanceForSpecialAttack()
    {
        Random random = new Random();
        int chance = random.nextInt(100);
        if(chance >= 0 && chance <= 20){
            this.setIsSpecialSkill(true);
        }
        else {
            this.setIsSpecialSkill(false);
        }
    }

    @Override
    public void performAction() {
        if(!this.isCooldown())
        {
            this.getKnightsToInteract().clear();
            this.setIsCurrentlyAttacking(false);

            if(this.isSpecialSkill())
                performSpecialSkill();
            else
                performNormalAttack();
        }
    }

    @Override
    protected void updateTeamStats(Knight opponentKnight, int damage) {
        if(opponentKnight.getCurrentHealth() <= 0){
            this.getAssignedTeam().updateScore();
            this.getAssignedTeam().updateTotalKills();
            this.setScore(this.getScore() + Team.KILL_SCORE);
        }
        this.getAssignedTeam().updateTotalDamage(damage);
    }

    @Override
    protected void performNormalAttack() {
        boolean isKnightFound = false;
        for(Team opTeam: this.getAssignedTeam().getOpponentTeams()){
            for(Knight opKnight: opTeam.getKnights()){
                if(this.getNormalAttackShape().intersects(opKnight.getTouchShape().getBounds2D()))
                {
                    opKnight.applyDamage(this.getNormalHitPoint());
                    this.updateTeamStats(opKnight, this.getNormalHitPoint());

                    this.getKnightsToInteract().add(opKnight);
                    this.setIsCurrentlyAttacking(true);

                    isKnightFound = true;
                    break;
                }
                else{
                    this.getKnightsToInteract().clear();
                }
            }
            if(isKnightFound)
                break;
        }
    }

    /**
     * Draw the knight's health
     * @param g Graphics object
     */
    @Override
    public void draw(Graphics g) {
        int textOffset = (int)(this.getKnightShape().getY() + this.getKnightShape().getWidth()/ 1.5);

        g.setColor(Color.WHITE);
        g.drawString(String.valueOf(this.getCurrentHealth()),
                (int) this.getKnightShape().getX(), textOffset);
    }

    /**
     * Checks to see if any opponent knight is removed from the opponent team's knights list. This was necessary
     * because knights and teams are updated before traversing the draw methods of the knights.
     */
    @Override
    protected void checkDrawReferences() {
        for(int i = 0; i < this.getKnightsToInteract().size(); i++)
        {
            Knight toInteract = this.getKnightsToInteract().get(i);
            if(!this.getAssignedTeam().getKnights().contains(toInteract)) {
                for (Team opTeam : this.getAssignedTeam().getOpponentTeams()) {
                    if (opTeam.getKnights().contains(toInteract))
                        return;
                }
                this.getKnightsToInteract().remove(i);
                i--;
            }
        }
    }

    /**
     * Draws a line to the knights that this knight is interacting.
     * @param g             Graphics object
     * @param attackColor   Color of the attack
     */
    @Override
    protected void drawAttack(Graphics g, Color attackColor)
    {
        g.setColor(attackColor);
        for(int i = 0; i < this.getKnightsToInteract().size() ; i++){
            g.drawLine((int) this.getKnightShape().getCenterX(),
                    (int) this.getKnightShape().getCenterY(),
                    (int) this.getKnightsToInteract().get(i).getKnightShape().getCenterX(),
                    (int) this.getKnightsToInteract().get(i).getKnightShape().getCenterY());

            if(this.getKnightsToInteract().get(i).getCurrentHealth() <= 0){
                this.getKnightsToInteract().remove(i);
                i--;
            }
        }
        this.setIsCurrentlyAttacking(false);
    }

    /**
     * Draws special attack (transparent circle)
     * @param g             Graphics object
     * @param specialColor  Color of the special skill
     */
    @Override
    protected void drawSpecial(Graphics g, Color specialColor) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(specialColor);
        Composite originalComposite = g2d.getComposite();

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.15));

        g2d.fill(this.getSpecialSkillShape());
        g2d.setComposite(originalComposite);
    }
}