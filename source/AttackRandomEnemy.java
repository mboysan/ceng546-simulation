import java.awt.geom.Ellipse2D;
import java.util.Random;

/**
 * Selects a random enemy to attack and moves towards it.
 * @author Mehmet Can Boysan
 */
public class AttackRandomEnemy extends Strategy
{
    @Override
    public void act()
    {
        this.getOwner().performAction();

        if(!this.getOwner().isStrategyOverridden())     //if not explicitly stated by a knight
        {
            Ellipse2D pAssigned = findRandomEnemy();
            if(pAssigned != null){
                stepMove(pAssigned);
            }else if(this.getOwner().getPointAssigned() != null)
                stepMove(this.getOwner().getPointAssigned());
        }
        else if(this.getOwner().getPointAssigned() != null){    //if knight has other objective
            stepMove(this.getOwner().getPointAssigned());
        }
    }

    /**
     * Finds random enemy
     */
    private Ellipse2D findRandomEnemy()
    {
        Random random = new Random();
        int randomOpponentTeam;
        int randomKnight;
        randomOpponentTeam = random.nextInt(this.getOwner().getAssignedTeam().getOpponentTeams().size());
        randomKnight = random.nextInt(
                this.getOwner().getAssignedTeam().getOpponentTeams().get(randomOpponentTeam).getKnights().size());


        return this.getOwner().getAssignedTeam().getOpponentTeams().get(randomOpponentTeam).
                        getKnights().get(randomKnight).getTouchShape();
    }
}
